package com.github.snuk87.keycloak.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.component.ComponentModel;
import org.keycloak.events.Event;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.*;
import org.mockito.Mockito;

class KafkaEventListenerProviderTests {

	private KafkaEventListenerProvider listener;

	private KeycloakSession mockKeycloakSession = Mockito.mock(KeycloakSession.class);

	private Producer<String, String> mockProducer = Mockito.mock(Producer.class);

	@BeforeEach
	void setUp() {
		Serializer<String> stringSerializer = new StringSerializer();
		listener = new KafkaEventListenerProvider("", "", "",
				new String[]{"REGISTER"}, "admin-events", mockProducer, mockKeycloakSession);
	}

	@Test
	void shouldProduceEventWhenTypeIsDefinedAndUsernamePresent() throws Exception {
		Event event = new Event();
		event.setType(EventType.REGISTER);
		HashMap<String, String> details = new HashMap<>();
		details.put("username", "username");
		event.setDetails(details);

		RecordMetadata recordMetadata = Mockito.mock(RecordMetadata.class);

		Mockito.when(recordMetadata.topic()).thenReturn("MOCK_TOPIC");
		Mockito.when(mockProducer.send(Mockito.any())).thenReturn(CompletableFuture.completedFuture(recordMetadata));

	    listener.onEvent(event);

		Mockito.verify(mockProducer, Mockito.times(1)).send(Mockito.any());
	}

	@Test
	void shouldProduceEventAndSetUsernameWhenTypeIsDefinedAndUsernameNotPresent() throws Exception {
		final String REALM_ID = "REALM_ID";
		Event event = new Event();
		event.setType(EventType.REGISTER);
		event.setRealmId(REALM_ID);

		// Mocks declaration
		RealmProvider mockRealmProvider = Mockito.mock(RealmProvider.class);
		RealmModel mockRealmModel = Mockito.mock(RealmModel.class);
		UserProvider mockUserProvider = Mockito.mock(UserProvider.class);
		UserModel mockUserModel = Mockito.mock(UserModel.class);
		RecordMetadata recordMetadata = Mockito.mock(RecordMetadata.class);

		//Mocks instrumentation
		Mockito.when(mockKeycloakSession.realms()).thenReturn(mockRealmProvider);
		Mockito.when(mockRealmModel.getId()).thenReturn(REALM_ID);
		Mockito.when(mockRealmModel.isEnabled()).thenReturn(true);
		Mockito.when(mockRealmProvider.getRealmsStream()).thenReturn(List.of(mockRealmModel).stream());
		Mockito.when(mockKeycloakSession.users()).thenReturn(mockUserProvider);
		Mockito.when(mockUserProvider.getUserById(Mockito.any(), Mockito.any())).thenReturn(mockUserModel);

		Mockito.when(recordMetadata.topic()).thenReturn("MOCK_TOPIC");
		Mockito.when(mockProducer.send(Mockito.any())).thenReturn(CompletableFuture.completedFuture(recordMetadata));

		listener.onEvent(event);

        assertTrue(event.getDetails().containsKey("username"));
		Mockito.verify(mockProducer, Mockito.times(1)).send(Mockito.any());
	}


	@Test
	void shouldDoNothingWhenTypeIsNotDefined() throws Exception {
		Event event = new Event();
		event.setType(EventType.CLIENT_DELETE);

		listener.onEvent(event);

		Mockito.verifyNoInteractions(mockProducer);
	}

	@Test
	void shouldProduceEventWhenTopicAdminEventsIsNotNull() throws Exception {
		AdminEvent event = new AdminEvent();
		RecordMetadata recordMetadata = Mockito.mock(RecordMetadata.class);

		Mockito.when(recordMetadata.topic()).thenReturn("MOCK_TOPIC");
		Mockito.when(mockProducer.send(Mockito.any())).thenReturn(CompletableFuture.completedFuture(recordMetadata));


		listener.onEvent(event, false);

		Mockito.verify(mockProducer, Mockito.times(1)).send(Mockito.any());
	}


	@Test
	void shouldDoNothingWhenTopicAdminEventsIsNull() throws Exception {
		listener = new KafkaEventListenerProvider("", "", "", new String[] { "REGISTER" },
				null, mockProducer, mockKeycloakSession);
		AdminEvent event = new AdminEvent();

		listener.onEvent(event, false);

		Mockito.verifyNoInteractions(mockProducer);
	}
}

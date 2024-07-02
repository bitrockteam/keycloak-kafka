package com.github.snuk87.keycloak.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.apache.kafka.clients.producer.MockProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.component.ComponentModel;
import org.keycloak.events.Event;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.*;
import org.keycloak.provider.InvalidationHandler;
import org.keycloak.provider.Provider;
import org.keycloak.services.clientpolicy.ClientPolicyManager;
import org.keycloak.sessions.AuthenticationSessionProvider;
import org.keycloak.vault.VaultTranscriber;
import org.mockito.Mockito;

class KafkaEventListenerProviderTests {
/*
	private KafkaEventListenerProvider listener;
	private KafkaProducerFactory factory;

	private KeycloakSession mockKeycloakSession = Mockito.mock(KeycloakSession.class);


	@BeforeEach
	void setUp() throws Exception {
		factory = new KafkaMockProducerFactory();

		listener = new KafkaEventListenerProvider("", "", "", new String[]{"REGISTER"}, "admin-events", Map.of(),
				factory,mockKeycloakSession);
	}

	@Test
	void shouldProduceEventWhenTypeIsDefined() throws Exception {
		Event event = new Event();
		event.setType(EventType.REGISTER);
		MockProducer<?, ?> producer = getProducerUsingReflection();
		RealmProvider mockRealmProvider = Mockito.mock(RealmProvider.class);
        RealmModel mockRealmModel = Mockito.mock(RealmModel.class);
		UserProvider mockUserProvider = Mockito.mock(UserProvider.class);
		UserModel mockUserModel = Mockito.mock(UserModel.class);

		Mockito.when(mockKeycloakSession.realms()).thenReturn(mockRealmProvider);
		Mockito.when(mockRealmProvider.getRealmByName(Mockito.any())).thenReturn(mockRealmModel);
		Mockito.when(mockKeycloakSession.users()).thenReturn(mockUserProvider);
		Mockito.when(mockUserProvider.getUserByEmail(Mockito.any(), Mockito.any())).thenReturn(mockUserModel);

	    listener.onEvent(event);

		assertEquals(1, producer.history().size());
	}

	@Test
	void shouldDoNothingWhenTypeIsNotDefined() throws Exception {
		Event event = new Event();
		event.setType(EventType.CLIENT_DELETE);
		MockProducer<?, ?> producer = getProducerUsingReflection();

		listener.onEvent(event);

		assertTrue(producer.history().isEmpty());
	}

	@Test
	void shouldProduceEventWhenTopicAdminEventsIsNotNull() throws Exception {
		AdminEvent event = new AdminEvent();
		MockProducer<?, ?> producer = getProducerUsingReflection();

		listener.onEvent(event, false);

		assertEquals(1, producer.history().size());
	}

	@Test
	void shouldDoNothingWhenTopicAdminEventsIsNull() throws Exception {
		listener = new KafkaEventListenerProvider("", "", "", new String[] { "REGISTER" }, null, Map.of(), factory, null);
		AdminEvent event = new AdminEvent();
		MockProducer<?, ?> producer = getProducerUsingReflection();

		listener.onEvent(event, false);

		assertTrue(producer.history().isEmpty());
	}

	private MockProducer<?, ?> getProducerUsingReflection() throws Exception {
		Field producerField = KafkaEventListenerProvider.class.getDeclaredField("producer");
		producerField.setAccessible(true);
		return (MockProducer<?, ?>) producerField.get(listener);
	}
*/
}

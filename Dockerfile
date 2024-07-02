FROM bitnami/keycloak:24.0.5
COPY ./target/keycloak-kafka-1.2.0-3-jar-with-dependencies.jar /opt/bitnami/keycloak/providers/keycloak-kafka-1.2.0-3-jar-with-dependencies.jar
RUN /opt/bitnami/keycloak/bin/kc.sh build

ENTRYPOINT ["/opt/bitnami/keycloak/bin/kc.sh"]

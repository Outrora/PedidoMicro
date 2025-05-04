package br.com.fiap.profile;

import java.util.Map;

import org.testcontainers.containers.MongoDBContainer;
import io.quarkus.test.junit.QuarkusTestProfile;

public class MongoProfile implements QuarkusTestProfile {
    static final MongoDBContainer MONGO = new MongoDBContainer("mongo:6.0");

    static {
        MONGO.start();
    }

    @Override
    public String getConfigProfile() {
        return "testcontainers"; // ativa application-testcontainers.properties, se existir
    }

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of(
                "quarkus.mongodb.connection-string", MONGO.getReplicaSetUrl());
    }

}

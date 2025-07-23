package ryzendee.app;


import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractTestcontainers {

    //TODO: Изменить на 17-alpine
    private static final String POSTGRES_IMAGE = "postgres:15.6";

    @ServiceConnection
    protected static PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_IMAGE));

}

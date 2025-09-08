package ryzendee.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ryzendee.app.testutils.testcontainers.EnableTestcontainers;

@ActiveProfiles("test")
@EnableTestcontainers
@SpringBootTest
class ContractorServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}

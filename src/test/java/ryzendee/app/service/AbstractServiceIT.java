package ryzendee.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ryzendee.app.testutils.testcontainers.EnableTestcontainers;
import ryzendee.app.testutils.CacheUtil;
import ryzendee.app.testutils.DatabaseUtil;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({DatabaseUtil.Config.class, CacheUtil.Config.class})
@EnableTestcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
abstract class AbstractServiceIT {

    @Autowired
    protected DatabaseUtil databaseUtil;

    @Autowired
    protected CacheUtil cacheUtil;

}

package ryzendee.app.testutils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;

/**
 * Утилита для тестов, упрощающая взаимодействие с кэшами
 *
 * @author Dmitry Ryazantsev
 */
public class CacheUtil {

    @Autowired
    private CacheManager cacheManager;

    public void putInCache(String cache, String key, Object value) {
        getCache(cache).put(key, value);
    }

    public <T> T getFromCache(String cache, String key, Class<T> type) {
        return getCache(cache).get(key, type);
    }

    public Cache.ValueWrapper getFromCache(String cache, String key) {
        return getCache(cache).get(key);
    }

    public void clearCache(String cache) {
        getCache(cache).clear();
    }

    private Cache getCache(String cache) {
        return cacheManager.getCache(cache);
    }

    @TestConfiguration
    public static class Config {

        @Bean
        public CacheUtil cacheUtil() {
            return new CacheUtil();
        }
    }
}

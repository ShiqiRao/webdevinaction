package ff.letsgo.smr.util;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class RedisCache implements Cache {
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final String id;
    private RedisTemplate redisTemplate;
    private static final long EXPIRE_TIME_IN_MINUTES = 30;

    public RedisCache(String id) {
        if (id == null) throw new IllegalArgumentException("A RedisCache instance require an ID");
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void putObject(Object key, Object value) {
        try {
            getRedisTemplate().opsForValue().set(key, value, EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
            logger.debug("Put query result to redis.");
        } catch (Throwable throwable) {
            logger.error("Redis put failed.", throwable);
        }
    }

    @Override
    public Object getObject(Object key) {
        try {
            Object object = getRedisTemplate().opsForValue().get(key);
            logger.debug("Get query result to redis.");
            return object;
        } catch (Throwable throwable) {
            logger.error("Redis get failed.", throwable);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object removeObject(Object key) {
        try {
            Object object = getRedisTemplate().delete(key);
            logger.debug("Delete query result to redis.");
            return object;
        } catch (Throwable throwable) {
            logger.error("Redis delete failed.", throwable);
            return null;
        }
    }

    @Override
    public void clear() {
        getRedisTemplate().execute((RedisCallback) redisConnection -> {
            redisConnection.flushDb();
            return null;
        });
        logger.debug("All cache has been cleared.");
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    private RedisTemplate getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = ApplicationContextHolder.getBean("redisTemplate");
        }
        return redisTemplate;
    }
}

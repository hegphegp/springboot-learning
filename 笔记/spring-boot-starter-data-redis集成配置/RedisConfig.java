import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Optional;

/** redis 配置管理器 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(Optional.ofNullable(obj).orElse("").toString());
            }
            return sb.toString();
        };
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheManager rcm = new RedisCacheManager(cacheTemplate(factory));
        rcm.setDefaultExpiration(300);
        return rcm;
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setHashKeySerializer(new StringRedisSerializer(Charset.forName("utf-8")));
        redisTemplate.setKeySerializer(new StringRedisSerializer(Charset.forName("utf-8")));
        redisTemplate.setValueSerializer(new StringRedisSerializer(Charset.forName("utf-8")));
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean //@Bean("permanentCacheManager")
    public CacheManager permanentCacheManager(RedisConnectionFactory factory){
        return new RedisCacheManager(cacheTemplate(factory));
    }

    @Bean //@Bean("minuteCacheManager")
    public CacheManager minuteCacheManager(RedisConnectionFactory factory) {
        RedisCacheManager rcm = new RedisCacheManager(cacheTemplate(factory));
        rcm.setDefaultExpiration(60);
        return rcm;
    }

    @Bean //@Bean("hourCacheManager")
    public CacheManager hourCacheManager(RedisConnectionFactory factory) {
        RedisCacheManager rcm = new RedisCacheManager(cacheTemplate(factory));
        rcm.setDefaultExpiration(3600);
        return rcm;
    }

    public RedisTemplate cacheTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}

@Service
class RedisTestService {
    @Cacheable(value = "findOnlineMissionPeople", cacheManager = "permanentCacheManager", condition = "#permanentCache == true ")
    public Object findOnlineMissionPeople(String startTime, String endTime, boolean permanentCache) {
        return null;
    }

    @Cacheable(value = "findOnlinePeople", cacheManager = "permanentCacheManager", condition = "#permanentCache == true ")
    public Object findOnlinePeople(String time, String endTime, boolean permanentCache) {
        return null;
    }

    @Cacheable(value="andCache", cacheManager = "minuteCacheManager", key="#userId + 'findById'")
    public Object findById(String userId) {
        return null ;
    }

    //将缓存保存进andCache，并当参数userId的长度小于32时才保存进缓存，默认使用参数值及类型作为缓存的key
    @Cacheable(value="andCache", cacheManager = "minuteCacheManager", condition="#userId.length < 32")
    public Object isReserved(String userId) {
        return null;
    }
}
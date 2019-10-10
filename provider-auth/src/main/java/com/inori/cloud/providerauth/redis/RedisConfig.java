package com.inori.cloud.providerauth.redis;

import com.inori.cloud.providerauth.pojo.TblUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, TblUser> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, TblUser> template = new RedisTemplate<String, TblUser>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }

    public class RedisObjectSerializer implements RedisSerializer<Object> {

        private Converter<Object, byte[]> serializer = new SerializingConverter();
        private Converter<byte[], Object> deserializer = new DeserializingConverter();

        static final byte[] EMPTY_ARRAY = new byte[0];

        public Object deserialize(byte[] bytes) {
            if (isEmpty(bytes)) {
                return null;
            }

            try {
                return deserializer.convert(bytes);
            } catch (Exception ex) {
                throw new SerializationException("Cannot deserialize", ex);
            }
        }

        public byte[] serialize(Object object) {
            if (object == null) {
                return EMPTY_ARRAY;
            }

            try {
                return serializer.convert(object);
            } catch (Exception ex) {
                return EMPTY_ARRAY;
            }
        }

        private boolean isEmpty(byte[] data) {
            return (data == null || data.length == 0);
        }
    }
}

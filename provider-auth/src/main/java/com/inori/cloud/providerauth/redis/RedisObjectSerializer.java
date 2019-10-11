package com.inori.cloud.providerauth.redis;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

@Deprecated
public class RedisObjectSerializer implements RedisSerializer<Object> {
    private Converter<Object, byte[]> serializer = new SerializingConverter();
    private Converter<byte[], Object> deserializer = new DeserializingConverter();

    private static final byte[] EMPTY_BYTE = new byte[0];

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null) {
            return EMPTY_BYTE;
        }

        try {
            return serializer.convert(o);
        } catch (Exception e) {
            throw new SerializationException("cannot serialize! ", e);
        }

    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (isEmpty(bytes)) {
            return null;
        }

        try {
            return deserializer.convert(bytes);
        } catch (Exception e) {
            throw new SerializationException("cannot deserialize！", e);
        }
    }

    public boolean isEmpty(byte[] data) {
        return data == null || data.length == 0;

    }
}

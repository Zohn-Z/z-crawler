package com.zzz.proxypool.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zzz.proxypool.config.BaseConfig;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.List;
import java.util.function.Function;

/**
 * @description: Redis工具
 * @author: zzz
 * @since: 2023/4/4 15:20
 **/
public class RedisUtil {


    private static final String host = BaseConfig.REDIS_HOST;

    private static final int port = BaseConfig.REDIS_PORT;

    public static final RedisClient redisClient = RedisClient.create(RedisURI.builder().withHost(host).withPort(port)
            .build());

    public static RedisClient getRedisClient() {
        return redisClient;
    }

    public static long del(String... keys) {
        return execute(o -> o.del(keys));
    }

    public static List<String> keys(String pattern) {
        return execute(x -> x.keys(pattern));
    }

    public static <T> T get(String key, Class<T> clazz) {
        return JSONObject.parseObject(get(key), clazz);
    }

    public static String get(String key) {
        return execute(o -> o.get(key));
    }

    public static <T> String set(String key, T value) {
        return set(key, JSON.toJSONString(value));
    }

    public static String set(String key, String value) {
        return execute(o -> o.set(key, value));
    }

    public static <T> String set(String key, T value, SetArgs setArgs) {
        return set(key, JSON.toJSONString(value), setArgs);
    }

    public static String set(String key, String value, SetArgs setArgs) {
        return execute(o -> o.set(key, value, setArgs));
    }

    public static <T> T execute(Function<RedisCommands<String, String>, T> function) {
        return execute(getRedisClient(), function);
    }

    public static <T> T execute(RedisClient redisClient, Function<RedisCommands<String, String>, T> function) {
        try (StatefulRedisConnection<String, String> connect = redisClient.connect()) {
            return execute(connect, function);
        }
    }

    public static <T> T execute(StatefulRedisConnection<String, String> connect, Function<RedisCommands<String, String>, T> function) {
        return function.apply(connect.sync());
    }
}

package com.zzz.proxypool.repository;

import com.zzz.proxypool.entity.ProxyEntity;
import com.zzz.proxypool.util.RedisUtil;
import io.lettuce.core.SetArgs;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: Redis实现的存储
 * @author: 张宗正
 * @since: 2023/4/6 14:35
 **/
public class RedisRepository {

    public static final String PREFIX = "proxy::";

    public static void main(String[] args) {
//        final ProxyEntity proxyEntity = new ProxyEntity();
//        proxyEntity.setHost("127.0.0.1");
//        proxyEntity.setPort(80);
//        proxyEntity.setType(Proxy.Type.SOCKS.name());
////        proxyEntity.setProxyType(Proxy.Type.SOCKS.name());
//        save(proxyEntity);
//        System.out.println(list());

        List<ProxyEntity> entities = listForeign();
        entities.forEach(System.out::println);
    }

    public static List<ProxyEntity> listForeign() {
        return list().stream().filter(x -> "US".equalsIgnoreCase(x.getCountry())).collect(Collectors.toList());
    }

    /**
     * todo 先查出所有key,再挨个查询。后面再优化
     *
     * @return
     */
    public static List<ProxyEntity> list() {
        List<String> keys = RedisUtil.keys(PREFIX + "*");
        return keys.stream().map(x -> RedisUtil.get(x, ProxyEntity.class)).collect(Collectors.toList());
    }

    public static ProxyEntity getByHostAndPost(String host, Integer port) {
        return RedisUtil.get(getKey(host, port), ProxyEntity.class);
    }

    public static long del(ProxyEntity entity) {
        return RedisUtil.del(getKey(entity));
    }

    public static String save(ProxyEntity entity) {
        return RedisUtil.set(getKey(entity), entity,  new SetArgs().ex(60 * 60));
    }

    public static String getKey(ProxyEntity entity) {
        return getKey(entity.getHost(), entity.getPort());
    }

    public static String getKey(String host, Integer port) {
        return PREFIX + host + ":" + port;
    }
}

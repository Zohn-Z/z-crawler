package com.zzz.proxypool.util;

import com.zzz.proxypool.entity.ProxyEntity;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * @description:
 * @author: zzz
 * @since: 2023/4/4 13:33
 **/
public class VerifyUtil {

    private static final Logger log = LoggerFactory.getLogger(VerifyUtil.class);

    private static final String URL = "https://www.baidu.com/";

    static final Request BAIDU_REQUEST = new Request.Builder().url(URL).get().build();

    private static final OkHttpClient client = new OkHttpClient.Builder().dispatcher(new Dispatcher())
            .callTimeout(6000, TimeUnit.MILLISECONDS).build();


    public static boolean verify(ProxyEntity x) {
        boolean res = false;
        try {
            res = verify(x.getHost(), x.getPort(), x.getType());
        } catch (Exception e) {
            log.error(e.toString());
        }
        return res;
    }

    // TODO: 2023/4/7 怎么校验最合理
    public static boolean verify(String host, Integer port, String type) {
        assert StringUtils.isNotBlank(host);
        assert 0 < port && port < 65536;
        assert StringUtils.isNotBlank(type);

        Proxy.Type proxyType;
        if ("http".equalsIgnoreCase(type) || "https".equalsIgnoreCase(type)) {
            proxyType = Proxy.Type.HTTP;
        } else if ("socket".equalsIgnoreCase(type)) {
            proxyType = Proxy.Type.SOCKS;
        } else {
            proxyType = Proxy.Type.HTTP;
        }

        return verify(new InetSocketAddress(host, port), proxyType);
    }

    public static boolean verify(InetSocketAddress inetSocketAddress, Proxy.Type proxyType) {
        return verify(new Proxy(proxyType, inetSocketAddress));
    }

    public static boolean verify(Proxy proxy) {
        return verify(proxy, VerifyUtil::defaultVerify2);
    }

    public static boolean verify(Proxy t, Predicate<Proxy> predicate) {
        return predicate.test(t);
    }

    public static boolean defaultVerify(Proxy proxy) {
        final OkHttpClient client = new OkHttpClient.Builder().callTimeout(6000, TimeUnit.MILLISECONDS).proxy(proxy)
                .build();
        //
        final Call call = client.newCall(BAIDU_REQUEST);

        try (Response response = call.execute()) {
            assert response.body() != null;
            return response.isSuccessful();
        } catch (IOException e) {
            log.error(e.toString());
            return false;
        }
    }

    public static boolean defaultVerify2(Proxy proxy) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(URL);
            connection = (HttpURLConnection) url.openConnection(proxy);
            connection.setConnectTimeout(4 * 1000);
            connection.setInstanceFollowRedirects(false);
            connection.setReadTimeout(6 * 1000);

            return connection.getResponseCode() == 200;
        } catch (IOException e) {
            log.error(e.toString());
            return false;
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }
}

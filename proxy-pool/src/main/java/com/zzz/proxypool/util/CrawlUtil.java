package com.zzz.proxypool.util;

import com.zzz.proxypool.CrawlSchedule;
import com.zzz.proxypool.crawler.AbstractCrawler;
import com.zzz.proxypool.entity.ProxyEntity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: zzz
 * @since: 2023/4/13 21:25
 **/
public class CrawlUtil {

    private static final Logger log = LoggerFactory.getLogger(CrawlUtil.class);

    public static List<ProxyEntity> crawl(AbstractCrawler crawler) {
        List<ProxyEntity> proxies = Collections.emptyList();

        try {
            List<String> respList = crawler.getUrlList().stream().map(CrawlUtil::doRequest).collect(Collectors.toList());

            log.info("爬" + crawler.getUrlList());

            proxies = respList.stream().flatMap(resp -> crawler.parse(resp).stream())
                    .collect(Collectors.toList());

            log.info(" {} 获取到 {} 个代理, {}", crawler.getClass().getName(), proxies.size(), proxies);
        } catch (Exception e) {
            log.error(e.toString());
        }

        return proxies;
    }

    private static String doRequest(String url) {
        OkHttpClient httpClient = new OkHttpClient.Builder().callTimeout(600, TimeUnit.SECONDS).build();

        Request request = new Request.Builder().url(url).get().build();
        String resp;

        try (Response response = httpClient.newCall(request).execute()) {
            assert response.body() != null;

            if (!response.isSuccessful()) {
                return null;
            }

            resp = response.body().string();
        } catch (IOException e) {
            log.error(e.toString());
            return null;
        }

        return resp;
    }
}

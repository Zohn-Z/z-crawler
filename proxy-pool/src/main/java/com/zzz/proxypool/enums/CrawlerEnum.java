package com.zzz.proxypool.enums;

import com.zzz.proxypool.crawler.*;

/**
 * @description: 解析器枚举
 * @author: zzz
 * @since: 2023/4/5 22:23
 **/
public enum CrawlerEnum {
    /**
     *
     */
//    UNKNOWN("unknown", "", null, IResponseParser.class),
    FATE_ZERO("fatezero", "http://proxylist.fatezero.org/proxy.list", FatezeroCrawler.class),
    PROXY_DB("proxydb", "http://proxydb.net/", ProxyDbCrawler.class),
    KUAIDAILI("kuaidaili", "http://www.kuaidaili.com/free/", KuaiDaiLiCrawler.class),
    IP66("66ip", "http://www.66ip.cn/", Ip66Crawler.class),
    PROXY_LIST_PLUS("proxylistplus", "https://list.proxylistplus.com/", ProxyListPlusCrawler.class)
    ;

    private final String identify;

    private final String baseUrl;

    private final Class<? extends AbstractCrawler> clazz;

    CrawlerEnum(String identify, String baseUrl, Class<? extends AbstractCrawler> clazz) {
        this.identify = identify;
        this.baseUrl = baseUrl;
        this.clazz = clazz;
    }

    public String getIdentify() {
        return identify;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Class<? extends AbstractCrawler> getClazz() {
        return clazz;
    }
}
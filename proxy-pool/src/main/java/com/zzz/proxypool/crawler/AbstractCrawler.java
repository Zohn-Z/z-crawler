package com.zzz.proxypool.crawler;

import com.zzz.proxypool.entity.ProxyEntity;
import com.zzz.proxypool.enums.CrawlerEnum;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: 张宗正
 * @since: 2023/4/7 10:35
 **/
public abstract class AbstractCrawler {

    public abstract CrawlerEnum getEnum();

    public abstract List<ProxyEntity> parse(String responseData);

    /**
     * 默认返回baseUrl,若代理网站有分页,可以自己重写该方法
     *
     * @return
     */
    public List<String> getUrlList() {
        return Collections.singletonList(getEnum().getBaseUrl());
    }

    public String getIdentify() {
        return getEnum().getIdentify();
    }

    protected boolean filter(ProxyEntity entity) {
        return true;
    }


    /**
     * 解析并过滤
     *
     * @param responseData
     * @return
     */
    public List<ProxyEntity> parseAndFilter(String responseData) {
        List<ProxyEntity> entities = parse(responseData);

        entities = entities.stream().filter(this::filter).collect(Collectors.toList());

        entities.forEach(this::postProcessing);

        return entities;
    }

    /**
     * 后置处理
     *
     * @param entity
     */
    protected void postProcessing(ProxyEntity entity) {
        if (Objects.isNull(entity.getSource())) {
            entity.setSource(getIdentify());
        }
    }
}

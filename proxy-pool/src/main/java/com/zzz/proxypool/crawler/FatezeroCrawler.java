package com.zzz.proxypool.crawler;

import com.alibaba.fastjson2.JSON;
import com.zzz.proxypool.entity.ProxyEntity;
import com.zzz.proxypool.enums.CrawlerEnum;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: zzz
 * @since: 2023/4/1 18:28
 **/
public class FatezeroCrawler extends AbstractCrawler {

    @Override
    public CrawlerEnum getEnum() {
        return CrawlerEnum.FATE_ZERO;
    }

    @Override
    public List<ProxyEntity> parse(String responseData) {
        String[] split = responseData.split("\n");
        if (ObjectUtils.isEmpty(split)) {
            return Collections.emptyList();
        }

        return Arrays.stream(split).map(s -> {
            Map<?, ?> map = JSON.parseObject(s, Map.class);
            ProxyEntity proxyEntity = new ProxyEntity();
            proxyEntity.setHost((String) map.get("host"));
            proxyEntity.setPort((Integer) map.get("port"));
            proxyEntity.setType((String) map.get("type"));
            proxyEntity.setCountry((String) map.get("country"));
            proxyEntity.setAnonymityType((String) map.get("anonymity"));
//                  proxyEntity set .responseTime(map.get("responseTime") == null ? null : (long) ((int) map.get("responseTime") * 1000))
            proxyEntity.setSource(getIdentify());

            return proxyEntity;
        }).collect(Collectors.toList());
    }


}

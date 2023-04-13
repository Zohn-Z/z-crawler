package com.zzz.proxypool.crawler;

import com.zzz.proxypool.entity.ProxyEntity;
import com.zzz.proxypool.enums.CrawlerEnum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: 张宗正
 * @since: 2023/4/7 9:59
 **/
public class KuaiDaiLiCrawler extends AbstractCrawler {

    @Override
    public CrawlerEnum getEnum() {
        return CrawlerEnum.KUAIDAILI;
    }

    @Override
    public List<ProxyEntity> parse(String responseData) {
        List<ProxyEntity> res = new ArrayList<>();
        Document doc = Jsoup.parse(responseData);
        Elements tables = doc.select("tbody");
        for (Element table : tables) {
            Elements trs = table.select("tr");
            for (Element tr : trs) {
                Elements tds = tr.select("td");
                if (tds.size() != 8) {
                    continue;
                }

                ProxyEntity enity = new ProxyEntity();
                enity.setHost(tds.get(0).text().trim());
                enity.setPort(Integer.parseInt(tds.get(1).text()));
                enity.setAnonymityType(tds.get(2).text().trim());
                enity.setLocation(tds.get(4).text().trim());

                res.add(enity);
            }
        }
        return res;
    }
}

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
 * @since: 2023/4/7 10:30
 **/
public class Ip66Crawler extends AbstractCrawler {

    private final int PAGE_SIZE = 20;

    @Override
    public CrawlerEnum getEnum() {
        return CrawlerEnum.IP66;
    }

    @Override
    public List<String> getUrlList() {
        String baseUrl = getEnum().getBaseUrl();
        List<String> urlList = new ArrayList<>(PAGE_SIZE);
        for (int i = 1; i <= PAGE_SIZE; i++) {
            urlList.add(baseUrl + i + ".html");
        }
        return urlList;
    }

    @Override
    public List<ProxyEntity> parse(String responseData) {
        List<ProxyEntity> res = new ArrayList<>();
        Document doc = Jsoup.parse(responseData);
        Elements tables = doc.select("tbody");
        for (Element table : tables) {
            Elements trs = table.select("tr");
            for (int i = 1; i < trs.size(); i++) {
                Element tr = trs.get(i);
                Elements tds = tr.select("td");
                if (tds.size() != 5) {
                    continue;
                }

                ProxyEntity entity = new ProxyEntity();
                entity.setHost(tds.get(0).text().trim());
                entity.setPort(Integer.parseInt(tds.get(1).text()));
                entity.setLocation(tds.get(2).text().trim());
                entity.setAnonymityType(tds.get(3).text().trim());

                res.add(entity);
            }
        }
        return res;
    }
}

package com.zzz.proxypool.crawler;

import com.zzz.proxypool.CrawlSchedule;
import com.zzz.proxypool.entity.ProxyEntity;
import com.zzz.proxypool.enums.CrawlerEnum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: 张宗正
 * @since: 2023/4/7 16:25
 **/
public class ProxyListPlusCrawler extends AbstractCrawler {

    private final Logger log = LoggerFactory.getLogger(ProxyListPlusCrawler.class);

    private final int PAGE_SIZE = 6;

    private final String url = "Fresh-HTTP-Proxy-List-";

    @Override
    public CrawlerEnum getEnum() {
        return CrawlerEnum.PROXY_LIST_PLUS;
    }

    @Override
    public List<String> getUrlList() {
        String baseUrl = getEnum().getBaseUrl();
        List<String> urlList = new ArrayList<>(PAGE_SIZE);
        for (int i = 1; i <= PAGE_SIZE; i++) {
            urlList.add(baseUrl + url + i);
        }
        return urlList;
    }

    @Override
    public List<ProxyEntity> parse(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("div[id=page] table[class=bg] tbody tr:gt(1)");
        List<ProxyEntity> proxyList = new ArrayList<>();

        for (Element element : elements) {
            try {
                String host = element.select("td:eq(1)").first().text();
                Integer port = Integer.valueOf(element.select("td:eq(2)").first().text().trim());
                String anonymityType = element.select("td:eq(3)").first().text();
                String country = element.select("td:eq(4)").first().text();
                String type = element.select("td:eq(6)").first().text() == "yes" ? "https" : "http";

                ProxyEntity proxyEntity = new ProxyEntity();
                proxyEntity.setHost(host);
                proxyEntity.setPort(port);
                proxyEntity.setType(type);
                proxyEntity.setAnonymityType(anonymityType);
                proxyEntity.setCountry(country);

                proxyList.add(proxyEntity);
            } catch (Exception e) {
                log.error(e.toString());
            }
        }

        return proxyList;
    }
}

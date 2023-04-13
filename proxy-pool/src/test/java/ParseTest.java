import com.zzz.proxypool.entity.ProxyEntity;
import com.zzz.proxypool.enums.CrawlerEnum;
import com.zzz.proxypool.crawler.*;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: 张宗正
 * @since: 2023/4/7 13:28
 **/
class ParseTest {

    String getFilePath(CrawlerEnum crawlerEnum) {
        return "data/" + crawlerEnum.getIdentify();
    }

    String getData(CrawlerEnum crawlerEnum) {
        return new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(this.getClass().getResourceAsStream(getFilePath(crawlerEnum))))).lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Test
    void ip66Parse() {
        AbstractCrawler crawler = new Ip66Crawler();

        List<ProxyEntity> parse = crawler.parse(getData(crawler.getEnum()));

        assert parse.size() == 7;
    }

    @Test
    void kuaidaili() {
        AbstractCrawler crawler = new KuaiDaiLiCrawler();

        List<ProxyEntity> parse = crawler.parse(getData(crawler.getEnum()));

        assert parse.size() == 15;
    }

    @Test
    void proxyListPlus() {
        AbstractCrawler crawler = new ProxyListPlusCrawler();

        List<ProxyEntity> parse = crawler.parse(getData(crawler.getEnum()));

        assert parse.size() == 50;
    }


    @Test
    void proxyDb() {
        AbstractCrawler crawler = new ProxyDbCrawler();

        List<ProxyEntity> parse = crawler.parse(getData(crawler.getEnum()));

        assert parse.size() == 15;
    }
}
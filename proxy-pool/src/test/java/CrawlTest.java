import com.zzz.proxypool.CrawlSchedule;
import com.zzz.proxypool.crawler.AbstractCrawler;
import com.zzz.proxypool.crawler.ProxyListPlusCrawler;
import com.zzz.proxypool.entity.ProxyEntity;
import com.zzz.proxypool.util.CrawlUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @description:
 * @author: zzz
 * @since: 2023/4/13 21:23
 **/
public class CrawlTest {

    @Test
    void testCrawl() {
        AbstractCrawler crawler = new ProxyListPlusCrawler();
        final List<ProxyEntity> crawl = CrawlUtil.crawl(crawler);

    }
}

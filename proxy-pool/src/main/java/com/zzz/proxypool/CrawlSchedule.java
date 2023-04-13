package com.zzz.proxypool;

import com.zzz.proxypool.crawler.AbstractCrawler;
import com.zzz.proxypool.entity.ProxyEntity;
import com.zzz.proxypool.util.CrawlUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @description: 爬虫定时任务
 * @author: zzz
 * @since: 2023/4/3 20:32
 **/
public class CrawlSchedule extends Scheduler {

    private final Logger log = LoggerFactory.getLogger(CrawlSchedule.class);

    private final BlockingQueue<ProxyEntity> proxyQueue;
    private Collection<AbstractCrawler> crawlers;
    private ExecutorService executorService;

    public CrawlSchedule(BlockingQueue<ProxyEntity> proxyQueue) {
        if (proxyQueue == null) {
            throw new NullPointerException("proxyQueue null");
        }
        this.proxyQueue = proxyQueue;
    }

    public CrawlSchedule(BlockingQueue<ProxyEntity> proxyQueue, Collection<AbstractCrawler> crawlers,
                         ExecutorService executorService, ScheduledExecutorService scheduledExecutorService) {
        this(proxyQueue);
        this.crawlers = crawlers;
        this.executorService = executorService;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    public CrawlSchedule(BlockingQueue<ProxyEntity> proxyQueue, Collection<AbstractCrawler> crawlers,
                         ExecutorService executorService, ScheduledExecutorService scheduledExecutorService, long initialDelay,
                         long period, TimeUnit unit) {
        this(proxyQueue, crawlers, executorService, scheduledExecutorService);
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
    }

    public static CrawScheduleBuilder builder() {
        return new CrawScheduleBuilder();
    }

    @Override
    public void run() {
        crawlers.forEach(crawler -> executorService.execute(() -> proxyQueue.addAll(CrawlUtil.crawl(crawler))));
    }

    public static class CrawScheduleBuilder {
        private long initialDelay = 0;
        private long period = 3;
        private TimeUnit unit = TimeUnit.HOURS;

        private BlockingQueue<ProxyEntity> proxyQueue;
        private Collection<AbstractCrawler> crawlers;
        private ExecutorService executorService;
        private ScheduledExecutorService scheduledExecutorService;

        public CrawScheduleBuilder() {
        }

        public CrawlSchedule build() {
            if (proxyQueue == null) {
                throw new NullPointerException("validator must bind queue");
            }
            if (crawlers == null) {
                crawlers = Collections.emptyList();
            }
            if (executorService == null) {
                executorService = Executors.newFixedThreadPool(5);
            }
            if (scheduledExecutorService == null) {
                scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            }

            return new CrawlSchedule(proxyQueue, crawlers, executorService, scheduledExecutorService, initialDelay,
                    period, unit);
        }

        public CrawScheduleBuilder bind(BlockingQueue<ProxyEntity> proxyQueue) {
            this.proxyQueue = proxyQueue;
            return this;
        }

        public CrawScheduleBuilder addCrawler(AbstractCrawler crawler) {
            if (crawlers == null) {
                crawlers = new ArrayList<>();
            }
            this.crawlers.add(crawler);
            return this;
        }

        public CrawScheduleBuilder addCrawlers(Collection<AbstractCrawler> crawlers) {
            crawlers.forEach(this::addCrawler);
            return this;
        }

        public CrawScheduleBuilder crawlers(Collection<AbstractCrawler> crawlers) {
            this.crawlers = crawlers;
            return this;
        }

        public CrawScheduleBuilder initialDelay(long initialDelay) {
            this.initialDelay = initialDelay;
            return this;
        }

        public CrawScheduleBuilder period(long period) {
            this.period = period;
            return this;
        }

        public CrawScheduleBuilder unit(TimeUnit unit) {
            this.unit = unit;
            return this;
        }

        public CrawScheduleBuilder executorService(ExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        public CrawScheduleBuilder scheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
            this.scheduledExecutorService = scheduledExecutorService;
            return this;
        }
    }
}

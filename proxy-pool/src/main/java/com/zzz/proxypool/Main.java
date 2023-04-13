package com.zzz.proxypool;

import com.zzz.proxypool.crawler.*;
import com.zzz.proxypool.repository.RedisRepository;

import java.util.concurrent.Executors;

/**
 * @description:
 * @author: zzz
 * @since: 2023-4-7 23:43:43
 **/
public class Main {
    public static void main(String[] args) {
        //
        VerifyQueue verifyQueue = new VerifyQueue();

        // 定时爬取代理网站，并将解析所得的代理放入验证队列
        final Scheduler crawlSchedule = CrawlSchedule.builder()
                .bind(verifyQueue)
                .addCrawler(new FatezeroCrawler())
                .addCrawler(new Ip66Crawler())
                .addCrawler(new KuaiDaiLiCrawler())
                .addCrawler(new ProxyDbCrawler())
                .addCrawler(new ProxyListPlusCrawler())
                .build();

        crawlSchedule.start();

        // 定时从Redis获取代理并放入验证队列
        VerifyProxyPoolSchedule schedule = new VerifyProxyPoolSchedule(verifyQueue);
        schedule.start();

        // 代理验证器持续从验证队列中获取代理，并进行验证
        final ProxyVerifier proxyValidator = ProxyVerifier.builder()
                .bind(verifyQueue) // 绑定队列
                .ifVerificationSucceeds(RedisRepository::save) // 验证成功则保存到Redis中
                .ifVerificationFails(RedisRepository::del) // 验证失败则从Redis中删除
                .executorService(Executors.newFixedThreadPool(10))
                .build();

        proxyValidator.startVerify();
    }
}
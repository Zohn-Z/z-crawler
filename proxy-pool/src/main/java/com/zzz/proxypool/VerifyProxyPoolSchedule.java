package com.zzz.proxypool;

import com.zzz.proxypool.entity.ProxyEntity;
import com.zzz.proxypool.repository.RedisRepository;

import java.util.concurrent.*;

/**
 * @description: 定时验证代理池里的代理
 * @author: 张宗正
 * @since: 2023/4/8 14:32
 **/
public class VerifyProxyPoolSchedule extends Scheduler {

    public BlockingQueue<ProxyEntity> proxyQueue;

    public VerifyProxyPoolSchedule(BlockingQueue proxyQueue) {
        this.proxyQueue = proxyQueue;
    }

    @Override
    public void run() {
        proxyQueue.addAll(RedisRepository.list());
    }
}

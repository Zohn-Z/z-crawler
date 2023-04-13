# z-crawler
基于Java实现的轻依赖、简单的爬虫和IP代理池。

实现该代理池主要有以下三个步骤：

- 开启定时任务，自定义爬虫，爬取代理网站，通过jsoup解析html等方式获取到代理，并放入校验队列当中。
- 开启定时任务，定时从代理池（已持久化的代理）中取出代理并放入校验队列中。
- 校验器持续从校验队列中取出代理，并进行校验。校验成功则进行持久化。

爬虫可以简单的自定义，集成抽象类  **AbstractCrawler** 并实现抽象方法（解析等），并在枚举类 **CrawlerEnum** 中添加对应的信息（URL等）即可。

校验器也可以简单的自定义，校验成功或校验失败后的处理。

```java
ProxyVerifier proxyValidator = ProxyVerifier.builder()
                .bind(verifyQueue) // 绑定队列
                .ifVerificationSucceeds(RedisRepository::save) // 验证成功则保存到Redis中
                .ifVerificationFails(RedisRepository::del) // 验证失败则从Redis中删除
                .executorService(Executors.newFixedThreadPool(10))
                .build();
```

#### 运行方式

引入项目之后，刷新Maven，修改 **com.zzz.proxypool.config.BaseConfig** 中的Redis配置，运行 **Main** 类即可。

项目是基于Java8的，未来可能会升级到Java17。

目前自定义爬虫的网站有5个，未来会继续更新。

持久化用的是Redis，主要图的是Redis的去重特性。未来会引入MySQL,MongoDB等。

#### 项目的依赖：

- jsoup
- okhttp
- fastjson2
- slf4j
- apache-commons
- lettuce (Redis客户端)
- junit

走过路过给个star哦~

package kopo.sideproject.service.impl;


import kopo.sideproject.crawler.ICrawler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("CrawlingService")
@RequiredArgsConstructor
public class CrawlingService {

    private final List<ICrawler> crawlers;

    public void doCrawling() {
        log.info("Crawling for all sites Starts");

        // 주입받은 모든 크롤러를 순회하며 실행
        crawlers.forEach(crawler -> {
            log.info(crawler.getClass().getName() + "started!");
            crawler.crawl();
        });

        log.info("Crawling for all sites Ends");

    }
}

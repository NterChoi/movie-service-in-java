package kopo.sideproject.controller;

import kopo.sideproject.service.impl.CrawlingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@RestController
public class CrawlingController {

    private final CrawlingService crawlingService;

    public String crawl() throws Exception{
        log.info(this.getClass().getName() + ".crawl Start");

        crawlingService.doCrawling();
        log.info(this.getClass().getName() + ".crawl End");

        return "Crawling Finished!";
    }
}

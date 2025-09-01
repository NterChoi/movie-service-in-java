package kopo.sideproject.crawler.impl;

import kopo.sideproject.crawler.ICrawler;
import kopo.sideproject.dto.CrawlerResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("MegaboxCrawlerService")
public class MegaboxCrawlerService implements ICrawler {

    @Override
    public List<CrawlerResultDTO> crawl() {

        log.info(this.getClass().getName() + ".crawl() Start");

        // TODO: Jsoup을 사용하여 메가박스 웹사이트 크롤링 로직 구현
        // 현재는 빈 리스트를 반환하는 예시
        List<CrawlerResultDTO> crawlerResultDTOS = new ArrayList<>();

        log.info(this.getClass().getName() + ".crawl() End");
        return crawlerResultDTOS;

    }
}

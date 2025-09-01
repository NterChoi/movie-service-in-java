package kopo.sideproject.crawler;

import kopo.sideproject.dto.CrawlerResultDTO;

import java.util.List;

public interface ICrawler {
    List<CrawlerResultDTO> crawl();
}

package kopo.sideproject.service.impl;


import kopo.sideproject.crawler.ICrawler;
import kopo.sideproject.dto.CrawlerResultDTO;
import kopo.sideproject.repository.MovieRepository;
import kopo.sideproject.repository.ShowtimeRepository;
import kopo.sideproject.repository.entity.MovieEntity;
import kopo.sideproject.repository.entity.ShowtimeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service("CrawlingService")
@RequiredArgsConstructor
public class CrawlingService {

    private final List<ICrawler> crawlers;
    private final MovieRepository movieRepository;
    private final ShowtimeRepository showtimeRepository;

    public void doCrawling(){
        log.info("Crawling for all sites Starts");

        // 주입받은 모든 크롤러를 순회하며 실행
        for (ICrawler crawler : crawlers) {
            try {
                // 크롤러의 클래스 이름에서 영화관 체인 이름을 추출 (예: MegaboxCrawlerService -> Megabox)
                String cinemaName = crawler.getClass().getSimpleName().replace("CrawlerService", "");
                log.info(crawler.getClass().getName() + " started!");

                // 1. 크롤러 실행
                List<CrawlerResultDTO> crawledData = crawler.crawl();

                // 2. 크롤링한 데이터를 DB에 저장 (하나의 트랜잭션으로 묶음)
                saveCrawledData(crawledData, cinemaName);
            } catch (Exception e) {
                // 개별 크롤러에서 에러 발생 시 로그 남기고 다음 크롤러로 넘어감
                log.error("Crawling failed for " + crawler.getClass().getSimpleName() + ", continuing to next.", e);
            }
        }

        log.info("Crawling for all sites Ends");
    }

    @Transactional
    public void saveCrawledData(List<CrawlerResultDTO> crawledData, String cinemaName){
        log.info("Saving " + cinemaName + " data to DB. Total items: " + crawledData.size());

        // 1. 이전에 저장된 해당 영화관의 상영 정보를 모두 삭제
        showtimeRepository.deleteByCinemaName(cinemaName);
        log.info("Deleted existing showtimes for" + cinemaName);

        // 2. 크롤링된 데이터를 하나씩 순회하며 저장
        crawledData.forEach(dto -> {
            //3. 영화 제목으로 DB에서 MovieEntity를 조회
            Optional<MovieEntity> movieOptional = movieRepository.findByMovieTitle(dto.movieTitle());

            // 4. MovieEntity가 존재할 경우에만 ShowtimeEntity를 저장
            movieOptional.ifPresent(movieEntity -> {
                ShowtimeEntity showtimeEntity = ShowtimeEntity.builder()
                        .movie(movieEntity)
                        .cinemaName(cinemaName)
                        .screenInfo(dto.theater())
                        .showtime(dto.showtime())
                        .build();

                showtimeRepository.save(showtimeEntity);
            });

            // 영화가 존재하지 않을 경우 로그 (필요에 따라 추가 처리 가능)
            if (movieOptional.isEmpty()) {
                log.warn("Movie not found in DB, skipping showtime: " + dto.movieTitle());
            }
        });

        log.info("Finished saving" + cinemaName + "data.");
    }
}

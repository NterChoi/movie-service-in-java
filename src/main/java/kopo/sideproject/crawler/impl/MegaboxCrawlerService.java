package kopo.sideproject.crawler.impl;

import kopo.sideproject.crawler.ICrawler;
import kopo.sideproject.dto.CrawlerResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("MegaboxCrawlerService")
public class MegaboxCrawlerService implements ICrawler {

    @Override
    public List<CrawlerResultDTO> crawl() throws Exception {

        log.info(this.getClass().getName() + ".crawl() Start");

        // 1. 크롤링 할 목표 URL
        String url = "http://www.megabox.co.kr/timetable";

        List<CrawlerResultDTO> results = new ArrayList<>();

        try {
            // 2. Jsoup으로 URL에 접속해서 전체 HTML 문서를 가져옵니다.
            org.jsoup.nodes.Document doc = Jsoup.connect(url).get();

            // 3. 영화별로 묶여있는 전체 영역을 선택합니다.
            // HTML을 분석한 결과, <div class="theater-list">가 각 영화 정보를 담고 있었습니다.
            org.jsoup.select.Elements movieSections = doc.select("div.theater-list");
            log.info("페이지에서 " + movieSections.size() + "개의 영화 정보를 찾았습니다.");

            // 4. 각 영화 정보 영역을 하나씩 순회합니다.
            for (org.jsoup.nodes.Element movieSection : movieSections) {

                // 5. 영화 제목을 추출합니다.
                // .theater-tit 클래스 하위의 p > a 태그에 제목이 있었습니다.
                String movieTitle = movieSection.select(".theater-tit > p > a").text();
                if (movieTitle.isEmpty()) {
                    continue; // 제목이 비어있으면 다음 영화 정보로 넘어갑니다.
                }

                // 6. 해당 영화의 상영관 그룹(.theater-type-box)을 모두 선택합니다.
                org.jsoup.select.Elements theaterBoxes = movieSection.select(".theater-type-box");

                // 7. 각 상영관 그룹을 순회합니다.
                for (org.jsoup.nodes.Element theaterBox : theaterBoxes) {

                    // 8. 상영관 이름을 추출합니다. (예 : "르 리클라이너 1관")
                    String theaterName = theaterBox.select(".theater-name").text();

                    // 9. 상영 시간표 테이블의 각 시간(td)을 모두 선택합니다.
                    org.jsoup.select.Elements timeCells = theaterBox.select(".time-list-table td");

                    // 10. 각 시간 정보를 순회합니다.
                    for (org.jsoup.nodes.Element timeCell : timeCells) {
                        String showtime = timeCell.select(".time").text(); // 상영 시간
                        String seats = timeCell.select(".time-seat").text();

                        // 정보가 유효한 경우에만 DTO를 생성하고 리스트에 추가합니다.
                        if (!showtime.isEmpty()) {
                            log.info("크롤링 결과 -> 영화: " + movieTitle + ", 상영관: " + theaterName + ", 시간:" + showtime + ", 좌석: " + seats);

                            CrawlerResultDTO dto = CrawlerResultDTO.builder()
                                    .movieTitle(movieTitle)
                                    .theater(theaterName)
                                    .showtime(showtime)
                                    .build();

                            results.add(dto);
                        }
                    }
                }

                // 중요: 다음 영화 정보를 크롤링하기 전에 1초간 대기합니다.
                Thread.sleep(1000);
            }
        } catch (java.io.IOException e) {
            // 크롤링 중 에러가 발생하면 로그를 남깁니다.
            log.error("메가박스 크롤링 중 에러 발생: " + e.getMessage());
            // 스레드 인터럽트 상태를 다시 설정
            Thread.currentThread().interrupt();
        }
        log.info(this.getClass().getName() + ".crawl() End");
        return results;
    }
}

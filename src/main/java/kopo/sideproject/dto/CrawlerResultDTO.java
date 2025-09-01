package kopo.sideproject.dto;

import lombok.Builder;


@Builder
public record CrawlerResultDTO(
        String movieTitle,
        String theater,
        String showtime,
        String detailIsUrl

) {
}

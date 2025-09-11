package kopo.sideproject.service;

import kopo.sideproject.dto.TmdbResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tmdb-api", url = "https://api.themoviedb.org/3")
public interface IMovieApiService {

    /**
     * TMDB API로부터 현재 상영 중인 영화 목록을 가져옵니다.
     *
     * @param page 조회할 페이지 번호
     * @return TMDB API 응답 DTO
     */
    @GetMapping("/movie/now_playing")
    TmdbResponseDTO getNowPlayingMovies(@RequestParam("api_key") String apiKey,
                                        @RequestParam("page") int page,
                                        @RequestParam("language") String language);
}

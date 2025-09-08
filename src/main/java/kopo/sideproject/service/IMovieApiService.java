package kopo.sideproject.service;

import kopo.sideproject.dto.TmdbResponseDTO;

public interface IMovieApiService {

    /**
     * TMDB API로부터 현재 상영 중인 영화 목록을 가져옵니다.
     * @param page 조회할 페이지 번호
     * @return TMDB API 응답 DTO
     */
    TmdbResponseDTO getNowPlayingMovies(int page);
}

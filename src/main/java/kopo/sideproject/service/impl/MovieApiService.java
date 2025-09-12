package kopo.sideproject.service.impl;

import kopo.sideproject.dto.TmdbResponseDTO;
import kopo.sideproject.repository.MovieRepository;
import kopo.sideproject.repository.entity.MovieEntity;
import kopo.sideproject.service.IMovieApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieApiService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    // Feign Client를 주입받습니다.
    private final IMovieApiService movieApiService;

    private final MovieRepository movieRepository;

    public TmdbResponseDTO getNowPlayingMovies(int page) {
        log.info(this.getClass().getName() + ".getNowPlayingMovies Start!");

        TmdbResponseDTO response = movieApiService.getNowPlayingMovies( page, "ko-KR");

        if (response != null && response.results() != null) {

            response.results().forEach(movieDto -> {
                MovieEntity movieEntity = MovieEntity.builder()
                        .movieTitle(movieDto.title())
                        .posterUrl("https://image.tmdb.org/t/p/w500" + movieDto.posterPath())
                        .overview(movieDto.overview())
                        .voteAverage(movieDto.voteAverage())
                        .build();

                movieRepository.save(movieEntity);
                log.info(movieDto.title() + " 저장 완료");
            });

        }

        log.info(this.getClass().getName() + ".getNowPlayingMovies End!");

        return response;

    }
}

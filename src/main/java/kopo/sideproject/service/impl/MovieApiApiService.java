package kopo.sideproject.service.impl;

import kopo.sideproject.dto.TmdbResponseDTO;
import kopo.sideproject.repository.MovieRepository;
import kopo.sideproject.repository.entity.MovieEntity;
import kopo.sideproject.service.IMovieApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieApiApiService implements IMovieApiService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    private final WebClient.Builder webClientBuilder;

    private final MovieRepository movieRepository;

    private static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3/";

    @Override
    public TmdbResponseDTO getNowPlayingMovies(int page) {
        log.info(this.getClass().getName() + ".getNowPlayingMovies Start!");

        String url = TMDB_API_BASE_URL + "movie/now_playing?api_key=" + apiKey + "&language=ko-KR&region=KR&page=" + page;

        TmdbResponseDTO response = webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(TmdbResponseDTO.class)
                .block(); // 비동기 응답을 동기적으로 기다림

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



        log.info(this.getClass().getName() + ".getNowPlayingMovies End!");

        return response;

    }
}

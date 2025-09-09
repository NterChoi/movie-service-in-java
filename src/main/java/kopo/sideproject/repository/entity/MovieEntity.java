package kopo.sideproject.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
@AllArgsConstructor
@Slf4j
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "movie_title", nullable = false)
    private String movieTitle;

    @Column(name = "genre")
    private String genre;

    @Column(name = "director")
    private String director;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "overview", length = 2000)
    private String overview;

    @Column(name = "vote_average")
    private double voteAverage;

    // Movie(1) : Showtime(N)
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ShowtimeEntity> showtimes = new ArrayList<>();
}

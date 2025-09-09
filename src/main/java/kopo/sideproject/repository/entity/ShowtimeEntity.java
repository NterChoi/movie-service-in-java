package kopo.sideproject.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SHOWTIME")
public class ShowtimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showtime_id")
    private Long showtimeId;

    // 크롤링한 상영 시간 문자열 (예: "10:00")
    @Column(name = "showtime")
    private String showtime;

    // 상세 상영관 정보 (예: "르 리클라이너 1관")
    @Column(name = "screen_info")
    private String screenInfo;

    // 영화관 체인 이름 (예: "Megabox")
    @Column(name = "cinema_name")
    private String cinemaName;

    // Showtime(N) : Movie(1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;

    // Showtime(N) : Theater(1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id")
    private TheaterEntity theater;

}

package kopo.sideproject.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "THEATER_INFO")
public class TheaterEntity {

    @Id
    @Column(name = "THEATER_CODE")
    private String theaterCode;

    @Column(name = "REGION_WIDE")
    private String regionWide; // 광역단체

    @Column(name = "REGION_BASIC")
    private String regionBasic; // 기초단체

    @Column(name = "THEATER_NAME", nullable = false)
    private String theaterName;

    @Column(name = "TOTAL_SCREENS")
    private Integer totalScreens;

    @Column(name = "TOTAL_SEATS")
    private Integer totalSeats;

    @Column(name = "FILM_SCREENS")
    private Integer filmScreens; // 필름 상영관수

    @Column(name = "TWO_D_SCREENS")
    private Integer twoDScreens;

    @Column(name = "THREE_D_SCREENS")
    private Integer threeDScreens;

    @Column(name = "FOUR_D_SCREENS")
    private Integer fourDScreens;

    @Column(name = "IMAX_SCREENS")
    private Integer imaxScreens;

    @Column(name = "IS_PERMANENT")
    private String isPermanent; // 상설여부

    @Column(name = "HAS_SPECIAL_HALL")
    private String hasSpecialHall; // 특별관 운영 여부

    @Column(name = "IS_REGISTERED")
    private String isRegistered; // 가입여부

    @Column(name = "TRANSMISSION_COMPANY")
    private String transmissionCompany; // 전송사업자명

    @Column(name = "OPENING_DATE")
    private String openingDate; // 개관일 ( 우선 String으로 받고, 필요 시 LocalDate로 파싱)

    @Column(name = "STATUS")
    private String status; // 영업상태

    @Column(name = "OPERATION_TYPE")
    private String operationType; // 운영형태

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "HOMEPAGE")
    private String homepage;
}

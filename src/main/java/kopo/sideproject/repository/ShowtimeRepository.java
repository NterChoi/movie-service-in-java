package kopo.sideproject.repository;

import kopo.sideproject.repository.entity.ShowtimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ShowtimeRepository extends JpaRepository<ShowtimeEntity, Long> {

    /**
     * 특정 영화관 체인의 모든 상영 정보를 삭제
     * @param cinemaName 영화관 체인 이름 (예: "Megabox")
     */
    @Transactional
    void deleteByCinemaName(String cinemaName);
}

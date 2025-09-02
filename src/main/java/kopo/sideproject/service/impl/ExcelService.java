package kopo.sideproject.service.impl;

import kopo.sideproject.repository.TheaterRepository;
import kopo.sideproject.service.IExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExcelService implements IExcelService {

    private final TheaterRepository theaterRepository;


    @Override
    public void uploadTheatersFromExcel() throws Exception {
        log.info(this.getClass().getName() + ".uploadTheatersFromExcel Start!");

        // 다음 단계에서 여기에 엑셀 파일을 읽고 파싱하여
        // 데이터베이스에 저장하는 코드를 구현할 예정

        log.info(this.getClass().getName() + ".uploadTheatersFromExcel End!");
    }
}

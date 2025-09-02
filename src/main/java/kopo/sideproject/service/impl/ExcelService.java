package kopo.sideproject.service.impl;

import kopo.sideproject.repository.TheaterRepository;
import kopo.sideproject.repository.entity.TheaterEntity;
import kopo.sideproject.service.IExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Iterator;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExcelService implements IExcelService {

    private final TheaterRepository theaterRepository;

    @Transactional
    @Override
    public void uploadTheatersFromExcel() throws Exception {
        log.info(this.getClass().getName() + ".uploadTheatersFromExcel Start!");

        theaterRepository.deleteAllInBatch();
        log.info("기존 Theater 정보 삭제 완료");

        ClassPathResource resource = new ClassPathResource("excel/KOBIS_영화상영관정보_2025-09-02.xlsx");

        try(InputStream inputStream = resource.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // 8번째 줄부터 데이터가 시작하므로, 첫 7줄은 건너뜁니다.
            for (int i = 0; i <5 && rowIterator.hasNext(); i++) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                try {
                    TheaterEntity theaterEntity = TheaterEntity.builder()
                            .theaterCode(getCellValue(row.getCell(2)))
                            .regionWide(getCellValue(row.getCell(0)))
                            .regionBasic(getCellValue(row.getCell(1)))
                            .theaterName(getCellValue(row.getCell(3)))
                            .totalScreens(getIntegerCellValue(row.getCell(4)))
                            .totalSeats(getIntegerCellValue(row.getCell(5)))
                            .filmScreens(getIntegerCellValue(row.getCell(6)))
                            .twoDScreens(getIntegerCellValue(row.getCell(7)))
                            .threeDScreens(getIntegerCellValue(row.getCell(8)))
                            .fourDScreens(getIntegerCellValue(row.getCell(9)))
                            .imaxScreens(getIntegerCellValue(row.getCell(10)))
                            .isPermanent(getCellValue(row.getCell(11)))
                            .hasSpecialHall(getCellValue(row.getCell(12)))
                            .isRegistered(getCellValue(row.getCell(13)))
                            .transmissionCompany(getCellValue(row.getCell(14)))
                            .openingDate(getCellValue(row.getCell(15)))
                            .status(getCellValue(row.getCell(16)))
                            .operationType(getCellValue(row.getCell(17)))
                            .address(getCellValue(row.getCell(18)))
                            .phoneNumber(getCellValue(row.getCell(19)))
                            .homepage(getCellValue(row.getCell(20)))
                            .build();

                    theaterRepository.save(theaterEntity);
                } catch (Exception e) {
                    log.error("엑셀 처리 중 오류 발생: " + row.getRowNum() + "번째 행", e);
                }
            }
        }


        log.info(this.getClass().getName() + ".uploadTheatersFromExcel End!");
    }

    private Integer getIntegerCellValue(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return 0;
        }

        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        }

        if (cell.getCellType() == CellType.STRING) {
            try {
                return Integer.parseInt(cell.getStringCellValue());
            }catch (NumberFormatException e){
                return 0; // 숫자로 변환 실패 시 0으로 처리
            }
        }
        return 0;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // 숫자 값을 문자열로 변환할 때 .0 같은 소수점 제거
                return String.valueOf(cell.getNumericCellValue());
            default:
                return "";
        }
    }
}

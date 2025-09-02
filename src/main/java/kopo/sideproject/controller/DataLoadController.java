package kopo.sideproject.controller;

import kopo.sideproject.dto.MsgDTO;
import kopo.sideproject.service.IExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/load")
@RestController
@RequiredArgsConstructor
public class DataLoadController {

    private final IExcelService excelService;

    @GetMapping("/theaters-from-excel")
    public ResponseEntity<MsgDTO> uploadTheatersFromExcel() throws Exception{
        log.info(this.getClass().getName() + ".uploadTheatersFromExcel Start!");

        MsgDTO dto;

        try {

            // 1. 서비스 호출
            excelService.uploadTheatersFromExcel();

            // 2. 성공 시 메시지 생성
            dto = MsgDTO.builder().msg("엑셀 데이터 로드 성공!").build();
        } catch (Exception e) {
            // 3. 실패 시 에러 로그 및 메시지 생성
            log.error("Error in uploadTheatersFromExcel!", e);
            dto = MsgDTO.builder().msg("엑셀 데이터 로드 실패: " + e.getMessage()).build();

            // 500 Internal Server Error 상태와 함께 실패 메시지 반환
            return ResponseEntity.internalServerError().body(dto);
        }

        log.info(this.getClass().getName() + ".uploadTheatersFromExcel End!");

        return ResponseEntity.ok(dto);
    }

}

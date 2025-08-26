package kopo.sideproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public record UserInfoDTO(
         String email,
         String password,
         String existsYn // 회원아이디 존재 여부
) {
}

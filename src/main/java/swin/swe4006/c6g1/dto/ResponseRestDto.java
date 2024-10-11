package swin.swe4006.c6g1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseRestDto {
    private String message;
    private Object data;
}

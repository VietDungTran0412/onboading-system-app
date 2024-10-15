package swin.swe4006.c6g1.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HealthCheckDto {
    private String hostname;
    private String ipAddress;
    private String apiVersion;
}

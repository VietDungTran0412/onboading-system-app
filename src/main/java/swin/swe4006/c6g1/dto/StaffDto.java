package swin.swe4006.c6g1.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StaffDto {
    @NotBlank
    private String name;
    @Min(18)
    @Max(60)
    @NotNull
    private Integer age;
    private String address;
    private String role;
    private String email;
}

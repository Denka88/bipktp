package com.denka88.bipktp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
public class PeriodDto {
    
    @NotEmpty
    private Year year;
    
}

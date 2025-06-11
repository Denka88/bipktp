package com.denka88.bipktp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecialityDto {
    
    @NotEmpty
    private String name;
    
    private String qualification;
    
}

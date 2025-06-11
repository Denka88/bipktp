package com.denka88.bipktp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommitteeDto {
    
    @NotEmpty
    private String name;
        
}

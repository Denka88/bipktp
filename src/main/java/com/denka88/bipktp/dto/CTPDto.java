package com.denka88.bipktp.dto;

import com.denka88.bipktp.model.Committee;
import com.denka88.bipktp.model.Discipline;
import com.denka88.bipktp.model.Period;
import com.denka88.bipktp.model.Speciality;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CTPDto {
    
    @NotEmpty
    private String name;
    
    @NotEmpty
    private Period period;
    
    @NotEmpty
    private Committee committee;
    
    @NotEmpty
    private Speciality speciality;
    
    @NotEmpty
    private Discipline discipline;
    
}

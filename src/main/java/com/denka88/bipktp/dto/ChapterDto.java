package com.denka88.bipktp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ChapterDto {
    
    @NotEmpty
    private String title;
    
    private Long ctpId;
    
}

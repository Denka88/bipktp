package com.denka88.bipktp.dto;

import com.denka88.bipktp.model.Chapter;
import com.denka88.bipktp.model.LessonType;
import com.denka88.bipktp.model.TeachMethod;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecordDto {
    
    @NotEmpty
    private String title;

    @NotEmpty
    private String homework;
    
    @NotEmpty
    private int hours;

    @NotEmpty
    private LessonType lessonType;

    @NotEmpty
    private List<TeachMethod> teachMethods;

    @NotEmpty
    private Chapter chapter;
    
}

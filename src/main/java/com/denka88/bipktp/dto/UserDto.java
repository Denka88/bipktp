package com.denka88.bipktp.dto;

import com.denka88.bipktp.model.Discipline;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    
    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

    @NotEmpty
    private String surname;

    @NotEmpty
    private String name;

    @NotEmpty
    private String patronymic;

    private List<Long> disciplineIds;
    
    private boolean isAdmin;
}

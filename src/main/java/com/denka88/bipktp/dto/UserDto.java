package com.denka88.bipktp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    
    @NotEmpty
    @Size(min = 4, max = 15, message = "Минимум 4 символа, максимум 15")
    private String login;

    @NotEmpty
    @Size(min = 6, max = 30, message = "Минимум 6 символов, максимум 30")
    private String password;

    @NotEmpty
    private String surname;

    @NotEmpty
    private String name;
    
    private String patronymic;

    private List<Long> disciplineIds;
    
    private boolean isAdmin;
}

package com.denka88.bipktp.config;

import com.denka88.bipktp.dto.UserDto;
import com.denka88.bipktp.model.User;
import com.denka88.bipktp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupConfig implements ApplicationListener<ApplicationReadyEvent> {
    
    private final UserService userService;
    
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        User user = userService.findByLogin("admin").orElse(null);
        if (user == null) {
            UserDto userDto = new UserDto();
            userDto.setLogin("admin");
            userDto.setPassword("123");
            userDto.setSurname("");
            userDto.setName("Администратор");
            userDto.setPatronymic("");
            userDto.setAdmin(true);
            userService.save(userDto);
        }
    }
    
}

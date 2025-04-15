package com.epam.users.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserRequest {

    @NotNull
    @Min(value = 5) @Max(value=10)
    private String username;

    @NotNull
    @Max(value=20)
    private String name;

    @Email
    private String email;
}

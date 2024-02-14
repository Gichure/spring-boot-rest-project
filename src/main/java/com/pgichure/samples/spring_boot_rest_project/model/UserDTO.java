package com.pgichure.samples.spring_boot_rest_project.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotNull
    @Size(max = 55)
    @UserEmailUnique
    private String email;

    @NotNull
    @Size(max = 32)
    private String pssword;

}

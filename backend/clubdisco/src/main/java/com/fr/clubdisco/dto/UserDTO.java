package com.fr.clubdisco.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UserDTO {

    private String email;

    private String displayName;

    @Size(min = 6, max = 120)
    private String password;
}

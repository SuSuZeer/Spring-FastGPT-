package org.Fast.dto;

import lombok.Data;

@Data
public class LoginFormDTO {
    private String account;
    private String email;
    private String code;
    private String password;
}

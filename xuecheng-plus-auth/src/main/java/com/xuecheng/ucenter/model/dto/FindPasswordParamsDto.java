package com.xuecheng.ucenter.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindPasswordParamsDto {
    private String cellphone;
    private String email;
    private String checkcodekey;
    private String checkcode;
    private String confirmpwd;
    private String password;
}
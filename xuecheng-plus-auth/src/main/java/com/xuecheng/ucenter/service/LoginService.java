package com.xuecheng.ucenter.service;

import com.xuecheng.ucenter.model.dto.FindPasswordParamsDto;
import com.xuecheng.ucenter.model.dto.RegisterDto;

public interface LoginService {
    void findPassword(FindPasswordParamsDto findPasswordParamsDto);

    void register(RegisterDto registerDto);
}

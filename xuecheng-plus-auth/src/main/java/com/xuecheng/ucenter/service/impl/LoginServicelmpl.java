package com.xuecheng.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.ucenter.mapper.XcUserMapper;
import com.xuecheng.ucenter.mapper.XcUserRoleMapper;
import com.xuecheng.ucenter.model.dto.FindPasswordParamsDto;
import com.xuecheng.ucenter.model.dto.RegisterDto;
import com.xuecheng.ucenter.model.po.XcUser;
import com.xuecheng.ucenter.model.po.XcUserRole;
import com.xuecheng.ucenter.service.LoginService;
import com.xuecheng.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class LoginServicelmpl implements LoginService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private XcUserMapper userMapper;
    @Autowired
    private XcUserRoleMapper userRoleMapper;


    @Override
    public void findPassword(FindPasswordParamsDto findPasswordParamsDto) {
        String phone = findPasswordParamsDto.getCellphone();
        String email = findPasswordParamsDto.getEmail();
        String checkCode = findPasswordParamsDto.getCheckcode();
        String password = findPasswordParamsDto.getPassword();
        String confirmPwd = findPasswordParamsDto.getConfirmpwd();

        if(phone != null){
            if (!ValidationUtils.isValidPhone(phone)) {
                XueChengPlusException.cast("手机号格式不正确");
            }
        }else{
            if (!ValidationUtils.isValidEmail(email)) {
                XueChengPlusException.cast("邮箱格式不正确");
            }
        }

        // 1. 使用工具类校验



        if (!ValidationUtils.isValidPassword(password)) {
            XueChengPlusException.cast("密码必须为6-20位，且包含字母和数字");
        }

        // 2. 校验确认密码
        if (!password.equals(confirmPwd)) {
            XueChengPlusException.cast("两次输入的密码不一致");
        }

        // 3. 校验验证码（从Redis获取）
        String cacheKey = "sms:code:" + phone;
        String savedCode =(String) redisTemplate.opsForValue().get(cacheKey);
        if (savedCode == null || !savedCode.equals(checkCode)) {
            XueChengPlusException.cast("验证码错误或已过期");
        }

        // 4. 查询用户并更新密码
        XcUser user = null;
        if(phone != null){
            user = userMapper.selectOne(new LambdaQueryWrapper<XcUser>().eq(XcUser::getCellphone, phone));
        }else{
            user = userMapper.selectOne(new LambdaQueryWrapper<XcUser>().eq(XcUser::getEmail, email));
        }

        if (user == null) {
            XueChengPlusException.cast("未找到该用户");
        }

        user.setPassword(password);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);

        // 5. 删除验证码
        redisTemplate.delete(cacheKey);

        log.info("密码重置成功 - 手机号: {}", phone);
    }

    @Transactional
    @Override
    public void register(RegisterDto registerDto) {
        String phone = registerDto.getCellphone();
        String username = registerDto.getUsername();
        String email = registerDto.getEmail();
        String nickname = registerDto.getNickname();
        String password = registerDto.getPassword();
        String confirmPwd = registerDto.getConfirmPwd();
        String checkCodeKey = registerDto.getCheckCodeKey();
        String checkCode = registerDto.getCheckCode();

        // 校验验证码（从Redis获取）
        String cacheKey = "sms:code:" + phone;
        String savedCode =(String) redisTemplate.opsForValue().get(cacheKey);
        if (savedCode == null || !savedCode.equals(checkCode)) {
            XueChengPlusException.cast("验证码错误或已过期");
        }
        //校验两次密码是否一致
        if (!password.equals(confirmPwd)) {
            XueChengPlusException.cast("两次输入的密码不一致");
        }
        //校验用户是否存在
        XcUser user1 = userMapper.selectOne(new LambdaQueryWrapper<XcUser>().eq(XcUser::getCellphone, phone));
        if (user1 != null) {
            XueChengPlusException.cast("账号已注册");
        }

        XcUser user = new XcUser();
        //向用户表、用户角色关系表插入数据、角色为学生
        user.setUsername(username);
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPassword(password);
        user.setName(nickname);
        user.setUtype("student");
        user.setBirthday(LocalDateTime.now());
        user.setCellphone(phone);
        user.setStatus("1");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);

        XcUserRole userRole = new XcUserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId("17");
        userRole.setCreateTime(LocalDateTime.now());
        userRoleMapper.insert(userRole);

    }
}

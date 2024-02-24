package org.Fast.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.Fast.dto.LoginFormDTO;
import org.Fast.dto.Result;
import org.Fast.entity.User;

/***
 * @title IUserService
 * @author SUZE
 * @Date 15:55
 **/
public interface IUserService extends IService<User> 
{

    Result LogByP(LoginFormDTO loginForm);

    Result LogByE(LoginFormDTO loginForm);


}

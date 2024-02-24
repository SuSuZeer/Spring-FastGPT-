package org.Fast.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.Fast.entity.GPT;
import org.Fast.entity.User;
import org.Fast.mapper.GPTMapper;
import org.Fast.mapper.IUserMapper;
import org.Fast.service.GPTService;
import org.Fast.service.IUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/***
 * @title GPTServiceImpl
 * @author SUZE
 * @Date 22:10
 **/
@Service
@Qualifier("IUserService")
@Primary
public class GPTServiceImpl extends ServiceImpl<GPTMapper, GPT> implements GPTService
{

}

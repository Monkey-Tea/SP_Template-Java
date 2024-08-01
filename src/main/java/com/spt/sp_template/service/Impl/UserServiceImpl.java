package com.spt.sp_template.service.Impl;

import cn.hutool.core.util.RandomUtil;
import com.spt.sp_template.common.Page;
import com.spt.sp_template.entity.User;
import com.spt.sp_template.exception.ServiceException;
import com.spt.sp_template.mapper.UserMapper;
import com.spt.sp_template.service.UserService;
import com.spt.sp_template.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void insertUser(User user) {
        userMapper.insert(user);

    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }

    @Override
    public void batchDeleteUser(List<Integer> ids) {
        for (Integer id : ids){
            userMapper.deleteUser(id);
        }
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public User selectById(Integer id) {

        return userMapper.selectById(id);
    }

    @Override
    public List<User> selectByName(String name) {
        return userMapper.selectByName(name);
    }

    @Override
    public List<User> selectByMore(String username, String name) {
        return userMapper.selectByMore(username , name);
    }

    @Override
    public List<User> selectByMo(String username, String name) {
        return userMapper.selectByMo(username,name);
    }

    @Override
    public Page<User> selectByPage(Integer pageNum, Integer pageSize, String username, String name) {
        Integer skipNum = (pageNum -1) * pageSize; // 计算出来  1 -> 0,5    2 -> 5,5   3 -> 10,5

        Page<User> page = new Page<>();
        List<User> userList = userMapper.selectByPage(skipNum, pageSize, username, name);
        Integer total = userMapper.selectByCountPage(username,name);
        page.setTotal(total);
        page.setList(userList);


        return page;
    }

    //验证用户账号是否合法
    //用户登录
    @Override
    public User login(User user) {
        //根据用户名查询数据库的用户信息
       User dbUser = userMapper.selectByUsername(user.getUsername());
       if (dbUser == null){
           //抛出一个自定义的异常
           throw new ServiceException("用户名或密码错误");
       }//前端的pa放前面,数据库的pa放后面
       if (!user.getPassword().equals(dbUser.getPassword()) ){
           throw new ServiceException("用户名或密码错误");
       }
       //生成token
        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());
       dbUser.setToken(token);
        return dbUser;
    }

    //用户注册
    @Override
    public User register(User user) {
        //判断用户名是否已经存在
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser != null){
            //抛出一个自定义的异常
            throw new ServiceException("用户名已存在");
        }
        user.setName(user.getUsername());//默认名称+随机数
        userMapper.insert(user);
        return user;
    }

    @Override
    public void resetPassword(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser == null){
            throw new ServiceException("用户不存在");
        }
       if (!user.getPhone().equals(dbUser.getPhone())){
           throw new ServiceException("验证错误");
       }
       dbUser.setPassword("123");//重置密码
        userMapper.updateUser(dbUser);
    }
}

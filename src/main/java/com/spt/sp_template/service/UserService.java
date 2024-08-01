package com.spt.sp_template.service;


import com.spt.sp_template.common.Page;
import com.spt.sp_template.entity.User;

import java.util.List;

public interface UserService {

    void insertUser(User user);

    void updateUser(User user);

    void deleteUser(Integer id);

    void batchDeleteUser(List<Integer> ids);

    List<User> selectAll();

    User selectById(Integer id);

    List<User> selectByName(String name);

    List<User> selectByMore(String username, String name);

    List<User> selectByMo(String username, String name);


    Page<User> selectByPage(Integer pageNum, Integer pageSize, String username, String name);


    User login(User user);

    User register(User user);

    void resetPassword(User user);
}

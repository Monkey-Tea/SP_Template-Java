package com.spt.sp_template.controller;

import com.spt.sp_template.common.Page;
import com.spt.sp_template.common.Result;
import com.spt.sp_template.entity.User;
import com.spt.sp_template.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 新增用户信息
     */
    @PostMapping("/add")
    public Result add(@RequestBody User user){
        try{
            userService.insertUser(user);
        }catch (Exception e){
            if (e instanceof DuplicateKeyException){
                return Result.error("插入数据错误");
            } else {
                return Result.error("系统错误");
            }

        }
        return Result.success();
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userService.updateUser(user);
        return Result.success();
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        userService.deleteUser(id);
        return Result.success();
    }

    /**
     * 批量删除用户信息
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids){
        userService.batchDeleteUser(ids);
        return Result.success();
    }

    /**
     * 查询全部用户信息
     * @return
     */
    @GetMapping("/selectAll")
    public Result selectAll(){
        List<User> userList = userService.selectAll();
        return Result.success(userList);
    }

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        User user = userService.selectById(id);
        return Result.success(user);
    }

    /**
     * 根据名称查询用户信息
     * @param name
     * @return
     */
    @GetMapping("/selectByName")
    public Result selectByName(@RequestParam String name){
        List<User> userList = userService.selectByName(name);
        return Result.success(userList);
    }

    /**
     * 多条件查询
     * @param username
     * @param name
     * @return
     */
    @GetMapping("/selectByMore")
    public Result selectByMore(@RequestParam String username, @RequestParam String name){
        List<User> userList = userService.selectByMore(username , name);
        return Result.success(userList);
    }

    /**
     * 多条件模糊查询
     * @param username
     * @param name
     * @return
     */
    @GetMapping("/selectByMo")
    public Result selectByMo(@RequestParam String username, @RequestParam String name){
        List<User> userList = userService.selectByMo(username,name);
        return Result.success(userList);
    }


    /**
     * 多条件分页模糊查询
     * @param pageNum 页码
     * @param pageSize 每页多少条数据
     * @param username
     * @param name
     * @return
     */
    @GetMapping("/selectByPage")
    public Result selectByPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam String username,
            @RequestParam String name){

        Page<User> page = userService.selectByPage(pageNum,pageSize,username,name);
        return Result.success(page);
    }





}

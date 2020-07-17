package com.lcy.controller;


import com.lcy.common.lang.Result;
import com.lcy.entity.User;
import com.lcy.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2020-07-13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    //@RequiresAuthentication
    @GetMapping("/index")
    public Object index(){
        User user = userService.getById(1L);
        return Result.succ(user);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user){
        return  Result.succ(user);
    }
}

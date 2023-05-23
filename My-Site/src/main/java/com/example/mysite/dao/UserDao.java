package com.example.mysite.dao;

import com.example.mysite.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Xufeng Jiang
 * @date 2023年04月24日 22:00
 * @description
 */
@Mapper
public interface UserDao {
    List<User> findAll();

    User getUserInfoByCond(@Param("username") String username, @Param("password") String password);

    User getUserInfoById(@Param("uid") Integer uId);


}

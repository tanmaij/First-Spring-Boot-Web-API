package com.todo.thuctap.daoimpl;

import com.todo.thuctap.dao.AuthDao;
import com.todo.thuctap.dto.response.AuthDTO;
import com.todo.thuctap.exception.AuthorizationException;
import com.todo.thuctap.exception.CustomException;
import com.todo.thuctap.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AuthDaoImpl implements AuthDao {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public AuthDTO login(String username, String password) {
        List<Map<String,Object>> User=jdbcTemplate.queryForList("select * from `User` where UserId='"+username+"'");
        if(User.size()==0)
            throw new CustomException("Tên tài khoản không tồn tại");
        else{
            if(!User.get(0).get("password").equals(password))
                throw new AuthorizationException("Mật khẩu không đúng");
            else
                return new AuthDTO(username,jwtUtils.generateAccessToken(username));
        }
    }
}

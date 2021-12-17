package com.todo.thuctap.dao;

import com.todo.thuctap.dto.response.AuthDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthDao {
    AuthDTO login(String username, String password);

}

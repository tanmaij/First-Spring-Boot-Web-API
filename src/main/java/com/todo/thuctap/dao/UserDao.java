package com.todo.thuctap.dao;

import com.todo.thuctap.dto.response.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserDao {
    void add(com.todo.thuctap.dto.request.UserDTO userDTO);
    void delete(com.todo.thuctap.dto.request.UserDTO userDTO);
    void update(com.todo.thuctap.dto.request.UserDTO userDTO);
    List<UserDTO> getAll();
    UserDTO getByUsername(String username);
}


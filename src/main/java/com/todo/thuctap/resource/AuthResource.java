package com.todo.thuctap.resource;

import com.todo.thuctap.dao.AuthDao;
import com.todo.thuctap.dao.UserDao;
import com.todo.thuctap.dto.request.UserDTO;
import com.todo.thuctap.dto.response.AuthDTO;
import com.todo.thuctap.dto.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthResource {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthDao authDao;

    @GetMapping("")
    public ResponseEntity<String> get(){
        return ResponseEntity.ok("Đăng ký thành công");
    }
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody UserDTO userDTO){
        userDao.add(userDTO);
        return ResponseEntity.ok().body(new ResponseDTO("Đăng ký thành công"));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok().body(authDao.login(userDTO.getUsername(),userDTO.getPassword()));
    }

}

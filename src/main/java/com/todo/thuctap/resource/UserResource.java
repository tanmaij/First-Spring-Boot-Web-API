package com.todo.thuctap.resource;

import com.todo.thuctap.dao.UserDao;
import com.todo.thuctap.dto.request.UserDTO;
import com.todo.thuctap.dto.response.ResponseDTO;
import com.todo.thuctap.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserResource {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserDao userDao;

    @DeleteMapping("/")
    @ResponseBody
    public ResponseEntity<ResponseDTO> delete(@RequestHeader("Authorization") String headers)
    {
        String accessToken = headers.split(" ")[1];
        UserDTO userDTO=new UserDTO();
        userDTO.setUsername(jwtUtils.getUsernameFromAccessToken(accessToken));
        userDao.delete(userDTO);
        return  ResponseEntity.ok().body(new ResponseDTO("Xóa thành công"));
    }

    @PatchMapping( "/")
    @ResponseBody
    public ResponseEntity<ResponseDTO> update(@RequestHeader("Authorization") String headers, @RequestBody UserDTO userDTO)
    {
        String accessToken = headers.split(" ")[1];
        userDTO.setUsername(jwtUtils.getUsernameFromAccessToken(accessToken));
        userDao.update(userDTO);
        return  ResponseEntity.ok().body(new ResponseDTO("Cập nhật mật khẩu thành công"));
    }

}

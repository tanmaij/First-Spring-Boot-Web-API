package com.todo.thuctap.daoimpl;

import com.todo.thuctap.common.DatabaseConstants;
import com.todo.thuctap.dao.UserDao;
import com.todo.thuctap.dto.response.UserDTO;
import com.todo.thuctap.exception.CustomException;
import com.todo.thuctap.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(com.todo.thuctap.dto.request.UserDTO userDTO){
        String sql= "call "+ DatabaseConstants.SCHEMA_NAME +".sp_User_Add('"+userDTO.getUsername()+"','"+userDTO.getPassword()+"')";
        try {
                System.out.println(sql);
            jdbcTemplate.execute(sql);
        }catch(Exception e){
            throw new CustomException("Lỗi không xác định");
        }
    }
    @Override
    public void delete(com.todo.thuctap.dto.request.UserDTO userDTO) {

        List<Map<String,Object>> getUser=jdbcTemplate.queryForList("call "+ DatabaseConstants.SCHEMA_NAME+".sp_User_GetById('"+userDTO.getUsername()+"')");
        if(getUser.size()==0)
            throw  new NotFoundException("Không tìm thấy người dùng");

        String sql = "call " + DatabaseConstants.SCHEMA_NAME + ".sp_User_Delete('" + userDTO.getUsername() + "')";
        try {
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            throw new CustomException("Lỗi không xác định");
        }
    }

    @Override
    public void update(com.todo.thuctap.dto.request.UserDTO userDTO) {
        List<Map<String,Object>> getUser=jdbcTemplate.queryForList("call "+ DatabaseConstants.SCHEMA_NAME+".sp_User_GetById('"+userDTO.getUsername()+"')");
        if(getUser.size()==0)
            throw new NotFoundException("Không tìm thấy người dùng");
        System.out.print(getUser);
        if(!getUser.get(0).get("Password").equals(userDTO.getOldPassword()))
            throw  new NotFoundException("Mật khẩu cũ không đúng");
        String sql = "call " + DatabaseConstants.SCHEMA_NAME + ".sp_User_Update('" + userDTO.getUsername() + "','"+userDTO.getPassword()+"')";
        try {
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            throw new CustomException("Lỗi không xác định");
        }
    }

    @Override
    public List<UserDTO> getAll(){
        return new ArrayList<>();
    }
    @Override
    public UserDTO getByUsername(String username){
        return new UserDTO();
    }
}

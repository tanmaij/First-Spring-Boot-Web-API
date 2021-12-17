package com.todo.thuctap.dao;

import com.todo.thuctap.dto.request.JobDTO;
import com.todo.thuctap.dto.request.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface JobDao {
    List<Map<String,Object>> getAllByUsername(UserDTO userDTO);
    void add(JobDTO jobDTO);
    void update(JobDTO jobDTO);
    void delete(JobDTO jobDTO);

}

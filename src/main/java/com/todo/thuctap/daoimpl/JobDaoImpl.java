package com.todo.thuctap.daoimpl;

import com.todo.thuctap.common.DatabaseConstants;
import com.todo.thuctap.dao.JobDao;
import com.todo.thuctap.dto.request.JobDTO;
import com.todo.thuctap.dto.request.UserDTO;
import com.todo.thuctap.exception.CustomException;
import com.todo.thuctap.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class JobDaoImpl implements JobDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> getAllByUsername(UserDTO userDTO) {
        String sql = "{call " + "todoapp.sp_Job_GetByUserId(?)}";
//        List<SqlParameter> params = Arrays.asList(
//                new SqlParameter(Types.VARCHAR));
//        Map result = jdbcTemplate.call(con -> {
//            CallableStatement callableStatement = con.prepareCall(sql);
//            callableStatement.setObject(1, userDTO.getUsername(), Types.VARCHAR);
//            return callableStatement;
//        }, params);
        List<Map<String,Object>> result=jdbcTemplate.queryForList(sql,userDTO.getUsername());
        return result;
    }

    @Override
    public void add(JobDTO jobDTO) {
        try{
            jdbcTemplate.execute("call "+ DatabaseConstants.SCHEMA_NAME +".sp_Job_AddUpdate(null,'"+jobDTO.getContent()+"',null,'"+jobDTO.getUsername()+"')");

        }catch (Exception ex)
        {
            throw new CustomException("Lỗi không xác định");
        }
    }

    @Override
    public void update(JobDTO jobDTO) {
        List<Map<String,Object>> getJob=jdbcTemplate.queryForList("call "+ DatabaseConstants.SCHEMA_NAME+".sp_Job_GetById("+jobDTO.getJobId()+")");
        if(getJob.size()==0)
            throw  new NotFoundException("Không tìm thấy việc");
        if(!jobDTO.getUsername().equals(getJob.get(0).get("UserId")))
            throw new CustomException("Bạn không sở hữu công việc này");
        try{
            jdbcTemplate.execute("call "+ DatabaseConstants.SCHEMA_NAME +".sp_Job_AddUpdate("+jobDTO.getJobId()+",'"+jobDTO.getContent()+"',"+jobDTO.getCompleted()+",null)");
        }catch (Exception ex)
        {
            throw new CustomException("Lỗi không xác định");
        }
    }
    @Override
    public void delete(JobDTO jobDTO) {
        List<Map<String,Object>> getJob=jdbcTemplate.queryForList("call "+ DatabaseConstants.SCHEMA_NAME+".sp_Job_GetById("+jobDTO.getJobId()+")");
        if(getJob.size()==0)
            throw  new NotFoundException("Không tìm thấy việc");
        if(!jobDTO.getUsername().equals(getJob.get(0).get("UserId")))
            throw new CustomException("Bạn không sở hữu công việc này");
        try{
            jdbcTemplate.execute("call "+ DatabaseConstants.SCHEMA_NAME +".sp_Job_Delete("+jobDTO.getJobId()+")");
        }catch (Exception ex)
        {
            throw new CustomException("Lỗi không xác định");
        }
    }
}

package com.todo.thuctap.resource;

import com.todo.thuctap.dao.JobDao;
import com.todo.thuctap.dto.request.JobDTO;
import com.todo.thuctap.dto.request.UserDTO;
import com.todo.thuctap.dto.response.ResponseDTO;
import com.todo.thuctap.service.ReportService;
import com.todo.thuctap.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
public class JobResource {
    @Autowired
    private JobDao jobDao;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private ReportService reportService;

    enum ReportType {
        PDF, XLS, XLSX, RTF, HTML
    }

    @GetMapping("/reports/{username}")
    public ResponseEntity<ByteArrayResource> in(@PathVariable String username) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        byte[] file = reportService.getReportDemo(jobDao.getAllByUsername(userDTO));

        return ResponseEntity
                .ok()
                .headers(headers -> {
                    ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                            .filename("hello.pdf")
                            .build();
                    headers.setContentLength(file.length);
                    headers.setContentDisposition(contentDisposition);
                    headers.setContentType(MediaType.APPLICATION_PDF);
                })
                .body(new ByteArrayResource(file));

    }

    @GetMapping("/")
    public ResponseEntity<List<Map<String, Object>>> getByUserName(@RequestHeader("Authorization") String headers) {

        String accessToken = headers.split(" ")[1];
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(jwtUtils.getUsernameFromAccessToken(accessToken));
        return ResponseEntity.ok().body(jobDao.getAllByUsername(userDTO));

    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<ResponseDTO> add(@RequestHeader("Authorization") String headers, @RequestBody JobDTO jobDTO) {
        String accessToken = headers.split(" ")[1];
        jobDTO.setUsername(jwtUtils.getUsernameFromAccessToken(accessToken));
        jobDao.add(jobDTO);
        return ResponseEntity.ok().body(new ResponseDTO("Thêm thành công"));

    }

    @PutMapping("/{jobId}")
    @ResponseBody
    public ResponseEntity<ResponseDTO> update(@RequestHeader("Authorization") String headers, @PathVariable String jobId, @RequestBody JobDTO jobDTO) {
        String accessToken = headers.split(" ")[1];
        jobDTO.setUsername(jwtUtils.getUsernameFromAccessToken(accessToken));
        jobDTO.setJobId(jobId);
        jobDao.update(jobDTO);
        return ResponseEntity.ok().body(new ResponseDTO("Chỉnh sửa thành công"));
    }

    @DeleteMapping("/{jobId}")
    @ResponseBody
    public ResponseEntity<ResponseDTO> delete(@RequestHeader("Authorization") String headers, @PathVariable String jobId) {

        String accessToken = headers.split(" ")[1];
        JobDTO jobDTO = new JobDTO();
        jobDTO.setUsername(jwtUtils.getUsernameFromAccessToken(accessToken));
        jobDTO.setJobId(jobId);
        jobDao.delete(jobDTO);
        return ResponseEntity.ok().body(new ResponseDTO("Xóa công việc thành công"));
    }

}

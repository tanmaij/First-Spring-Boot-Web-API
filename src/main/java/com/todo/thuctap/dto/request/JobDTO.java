package com.todo.thuctap.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private String jobId;
    private String username;
    private String content;
    private int completed;
}

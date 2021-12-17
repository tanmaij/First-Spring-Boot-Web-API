package com.todo.thuctap.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private String createdAt;
    private String Content;
    private Boolean IsComplete;
}

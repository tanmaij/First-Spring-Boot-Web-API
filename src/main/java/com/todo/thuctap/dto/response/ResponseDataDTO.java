package com.todo.thuctap.dto.response;

import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
public class ResponseDataDTO {
    private String message;
    private Object data;
}
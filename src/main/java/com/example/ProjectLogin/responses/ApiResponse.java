package com.example.ProjectLogin.responses;

import lombok.Getter;
import org.hibernate.engine.internal.ImmutableEntityEntry;

@Getter
public class ApiResponse {

    private final String message;
    private final boolean success;
    private final Object data;

    ApiResponse(String message, boolean success, Object data){
        this.message= message;
        this.success=success;
        this.data=data;
    }
}

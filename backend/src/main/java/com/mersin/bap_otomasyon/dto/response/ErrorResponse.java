package com.mersin.bap_otomasyon.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    private boolean success;
    private ErrorDetail error;
    private LocalDateTime timestamp;

    public static ErrorResponse of(String code, String message, String path) {
        return of(code, message, path, null);
    }

    public static ErrorResponse of(String code, String message, String path, Map<String, Object> details) {
        ErrorResponse response = new ErrorResponse();
        response.success = false;
        response.timestamp = LocalDateTime.now();
        ErrorDetail error = new ErrorDetail();
        error.code = code;
        error.message = message;
        error.timestamp = response.timestamp.toString();
        error.path = path;
        error.details = details;
        response.error = error;
        return response;
    }

    public boolean isSuccess() { return success; }
    public ErrorDetail getError() { return error; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public static class ErrorDetail {
        private String code;
        private String message;
        private Map<String, Object> details;
        private String timestamp;
        private String path;

        public String getCode() { return code; }
        public String getMessage() { return message; }
        public Map<String, Object> getDetails() { return details; }
        public String getTimestamp() { return timestamp; }
        public String getPath() { return path; }
    }
}

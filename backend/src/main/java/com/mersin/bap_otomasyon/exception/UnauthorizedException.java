package com.mersin.bap_otomasyon.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BusinessException {

    public UnauthorizedException() {
        super("BAP-AUTH-001", "Kimlik dogrulama gerekli");
    }

    public UnauthorizedException(String message) {
        super("BAP-AUTH-001", message);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}

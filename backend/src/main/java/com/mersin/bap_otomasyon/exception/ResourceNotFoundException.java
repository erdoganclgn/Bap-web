package com.mersin.bap_otomasyon.exception;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String kaynak, Object id) {
        super("BAP-SYS-404", kaynak + " bulunamadi: " + id);
    }
}

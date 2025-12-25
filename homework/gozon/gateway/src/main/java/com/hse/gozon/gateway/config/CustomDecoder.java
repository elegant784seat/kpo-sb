package com.hse.gozon.gateway.config;

import com.hse.gozon.gateway.Exception.InternalServerErrorException;
import com.hse.gozon.gateway.Exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 404) {
            throw new NotFoundException("объект не найден для метода " + s);
        } else if ((response.status() == 500)) {
            throw new InternalServerErrorException("произошла ошибка сервера в методе" + s);
        }
        return defaultDecoder.decode(s, response);
    }
}

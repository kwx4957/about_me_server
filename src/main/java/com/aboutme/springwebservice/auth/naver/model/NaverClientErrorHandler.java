package com.aboutme.springwebservice.auth.naver.model;

import com.aboutme.springwebservice.auth.naver.exception.BaseException;
import com.aboutme.springwebservice.auth.naver.exception.InvalidAuthException;
import com.aboutme.springwebservice.auth.naver.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class NaverClientErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return clientHttpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR ||
                clientHttpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

        if (clientHttpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            if(clientHttpResponse.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                throw new BaseException("Authroizationnnnn error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (clientHttpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            if(clientHttpResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new InvalidAuthException("Invalid access token");
            }
            if(clientHttpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new UserNotFoundException("Naver user not found");
            }
        }
    }
}

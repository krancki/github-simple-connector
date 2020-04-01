package com.krancki.githubconnector.github.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

public class GithubResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = response.getStatusCode();
        return statusCode.is4xxClientError()
                || statusCode.is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = response.getStatusCode();
        if (statusCode.is4xxClientError()) {
            throw new ResponseStatusException(statusCode, response.getStatusText());
        }
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Github is not available");
    }
}
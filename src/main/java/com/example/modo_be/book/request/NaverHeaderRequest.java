package com.example.modo_be.book.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NaverHeaderRequest {

    private String clientId;
    private String clientSecret;
}

package com.example.modo_be.auth.component;

import com.example.modo_be.auth.exception.TokenEmptyException;
import com.example.modo_be.auth.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.theme.AbstractThemeResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Slf4j
@Component
@AllArgsConstructor
public class BearerAuthInterceptor implements HandlerInterceptor {

    //    private AuthExtractor authExtractor;
    private final TokenService tokenService;

    public static final String Authorization = "accessToken";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration<String> headers = request.getHeaders(Authorization);
        String token = "";
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (value.startsWith("Bearer")) {
                token = value.substring("Bearer ".length()).trim();
            }
        }

        if (token.equals("")) {
            throw new TokenEmptyException();
        }
        tokenService.validateToken(token);

        return true;
    }
}

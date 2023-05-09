package com.example.modo_be.auth.service;


import com.example.modo_be.auth.dto.TokenUserInfo;
import com.example.modo_be.auth.exception.WrongPassword;
import com.example.modo_be.auth.request.SignInRequest;
import com.example.modo_be.user.domain.User;
import com.example.modo_be.user.exception.UserNotFound;
import com.example.modo_be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;


    public TokenUserInfo validateUser(SignInRequest signInRequest){

        if(!userRepository.existsByUserEmail(signInRequest.getUserEmail())){
            throw new UserNotFound();
        }

        User user = userRepository.findByUserEmail(signInRequest.getUserEmail());


        if(!BCrypt.checkpw(signInRequest.getPassword(),user.getUserPw())){
            throw new WrongPassword();
        }

        return user.toTokenUserInfo();
    }


}

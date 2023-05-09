package com.example.modo_be.user.service;


import com.example.modo_be.user.domain.User;
import com.example.modo_be.user.exception.UserAlreadyExist;
import com.example.modo_be.user.repository.UserRepository;
import com.example.modo_be.user.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void signUp(SignUpRequest signUpRequest){
        signUpRequest.passwordValidation();

        // ID duplicaiton check는 여기서
        if(userRepository.existsByUserEmail(signUpRequest.getUserEmail())){
            throw new UserAlreadyExist();
        }

        User user = signUpRequest.toEntity(signUpRequest.getPassword());
        userRepository.save(user);
        // entity save
    }

}

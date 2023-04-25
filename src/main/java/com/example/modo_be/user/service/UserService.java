package com.example.modo_be.user.service;


import com.example.modo_be.user.domain.User;
import com.example.modo_be.user.exception.UserNotFound;
import com.example.modo_be.user.repository.UserRepository;
import com.example.modo_be.user.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void signUp(SignUpRequest signUpRequest){
        signUpRequest.validate();


        // ID duplicaiton check는 여기서
        if(userRepository.existsByUserId(signUpRequest.getId())){
            throw new UserNotFound();
        }

        User user = signUpRequest.toEntity();
        userRepository.save(user);
        // entity save
    }




}

package com.example.modo_be.user.domain;


import com.example.modo_be.auth.dto.TokenUserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userEmail;

    private String userPw;
    private String userPhoneNum;
    private String userNickName;

    private String userGrade;

    private String userIntroduction;

    private String userAddress;

    private String userLongitude;

    private String userLatitude;

    private String userLibraryName;

    private String userPower;


    public TokenUserInfo toTokenUserInfo(){
        return TokenUserInfo.builder()
                .userEmail(userEmail)
                .userNickName(userNickName).build();
    }



    @Builder
    public User(long id, String userEmail, String userPw,
                String userPhoneNum, String userNickName, String userGrade, String userIntroduction,
                String userAddress, String userLongitude, String userLatitude,
                String userLibraryName, String userPower) {
        this.id = id;
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.userPhoneNum = userPhoneNum;
        this.userNickName = userNickName;
        this.userGrade = userGrade;
        this.userIntroduction = userIntroduction;
        this.userAddress = userAddress;
        this.userLongitude = userLongitude;
        this.userLatitude = userLatitude;
        this.userLibraryName = userLibraryName;
        this.userPower = userPower;
    }
}

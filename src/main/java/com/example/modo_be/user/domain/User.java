package com.example.modo_be.user.domain;


import com.example.modo_be.auth.dto.TokenUserInfo;
import com.example.modo_be.book.domain.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "user")
    private List<Book> books;

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

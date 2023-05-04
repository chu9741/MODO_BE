package com.example.modo_be.user.request;

import com.example.modo_be.user.domain.User;
import com.example.modo_be.user.exception.UserInvalidRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank(message = "ID는 필수로 입력해야 합니다.")
    private final String userEmail;

    @NotBlank(message = "PW는 필수로 입력해야 합니다.")
    private final String password;

    @NotBlank(message = "닉네임은 필수로 입력해야 합니다.")
    private final String nickName;

    @NotBlank(message = "주소는 필수로 입력해야 합니다..")
    private final String address;

    @NotBlank(message = "경도는 필수로 입력되어야 합니다.")
    private final String longitude;

    @NotBlank(message = "위도는 필수로 입력되어야 합니다.")
    private final String latitude;

    @NotBlank(message = "휴대폰 번호는 필수로 입력해야 합니다.")
    private final String phoneNum;

    public void passwordValidation(){
        if(password.isBlank()){
            throw new UserInvalidRequest(password, "비밀번호를 입력해야 합니다. ");
        }
        if(password.equals("")){
            throw new UserInvalidRequest(password,"비밀번호를 입력해야 합니다.");
        }
        if(password.length() < 8){
            throw new UserInvalidRequest(password, "비밀번호는 8자리 이상 입력해야 합니다.");
        }
        if(!password.matches(".*[^a-zA-Z0-9].*")
                && password.matches(".*[0-9].*")
                && password.toLowerCase().matches(".*[a-z].*")){
            //통과
            return;
        }
        else{
            throw new UserInvalidRequest(password, "비밀번호는 영문과 숫자만으로 이루어져야합니다.");
        }
    }

    public User toEntity(String rawPw){
        // encrypt here , validation 반드시 진행 후 Entity 변환
        String encodePassword = encodePassWord(rawPw);

        return User.builder().userEmail(this.userEmail).userPw(encodePassword)
                .userAddress(this.address).userLatitude(this.latitude)
                .userLongitude(this.longitude).userNickName(this.nickName)
                .userPhoneNum(this.phoneNum)
                .build();
    }

    public String encodePassWord(String rawPw){
        return BCrypt.hashpw(rawPw, BCrypt.gensalt());
    }


    @Builder
    public SignUpRequest(String userEmail, String password, String nickName, String address, String longitude, String latitude, String phoneNum) {
        this.userEmail = userEmail;
        this.password = password;
        this.nickName = nickName;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phoneNum = phoneNum;
    }
}

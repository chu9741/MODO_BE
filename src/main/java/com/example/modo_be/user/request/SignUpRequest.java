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
    private final String id;

    @NotBlank(message = "PW는 필수로 입력해야 합니다.")
    private final String pw;

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
        if(pw.isBlank()){
            throw new UserInvalidRequest(pw, "비밀번호를 입력해야 합니다. ");
        }
        if(pw.equals("")){
            throw new UserInvalidRequest(pw,"비밀번호를 입력해야 합니다.");
        }
        if(pw.length() < 8){
            throw new UserInvalidRequest(pw, "비밀번호는 8자리 이상 입력해야 합니다.");
        }
        if(!pw.matches(".*[^a-zA-Z0-9].*")
                && pw.matches(".*[0-9].*")
                && pw.toLowerCase().matches(".*[a-z].*")){
            //통과
            return;
        }
        else{
            throw new UserInvalidRequest(pw, "비밀번호는 영문과 숫자만으로 이루어져야합니다.");
        }
    }

    public User toEntity(String rawPw){
        // encrypt here , validation 반드시 진행 후 Entity 변환
        String encodePassword = encodePassWord(rawPw);

        return User.builder().userId(this.id).userPw(encodePassword)
                .userAddress(this.address).userLatitude(this.latitude)
                .userLongitude(this.longitude).userNickName(this.nickName)
                .userPhoneNum(this.phoneNum)
                .build();
    }

    public String encodePassWord(String rawPw){
        return BCrypt.hashpw(rawPw, BCrypt.gensalt());
    }


    @Builder
    public SignUpRequest(String id, String pw, String nickName, String address, String longitude, String latitude, String phoneNum) {
        this.id = id;
        this.pw = pw;
        this.nickName = nickName;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phoneNum = phoneNum;
    }
}

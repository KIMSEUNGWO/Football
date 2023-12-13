package football.start.allOfFootball.controller.mypage;

import football.start.allOfFootball.enums.SocialEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MypageMainDto {

    private String profileImage;
    private String name;
    private String phone;
    private String changePasswordDate;



}

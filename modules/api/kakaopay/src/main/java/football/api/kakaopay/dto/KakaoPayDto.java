package football.api.kakaopay.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoPayDto {

    private Integer price;
    private String redirect;
}

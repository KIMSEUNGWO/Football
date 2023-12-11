package football.start.allOfFootball.dto;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderPostForm {

    private Long couponNum;
    private String policy;

}

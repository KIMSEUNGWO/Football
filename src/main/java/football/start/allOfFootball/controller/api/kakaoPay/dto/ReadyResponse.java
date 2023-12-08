package football.start.allOfFootball.controller.api.kakaoPay.dto;

import lombok.*;
import net.minidev.json.JSONObject;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReadyResponse {

    private String tid;
    private String next_redirect_pc_url;
    private String partner_order_id;

}

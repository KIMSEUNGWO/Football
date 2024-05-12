package football.start.allOfFootball.controller.mypage;

import football.internal.database.enums.paymentEnums.CashEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CashListForm {

    private String cashDate;
    private String cashTime;
    private CashEnum cashEnum;
    private Integer cash;
    private String cashStr;
    private String nowCash;
}

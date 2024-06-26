package football.common.enums.paymentEnums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

@Getter
public enum RefundEnum {

    전체환불(value -> value),
    환불80(value -> (int) Math.round(value * 0.80)),
    환불20(value -> (int) Math.round(value * 0.20)),
    환불불가(value -> 0);

    private Function<Integer, Integer> expression;

    public int refund(int value) {
        return expression.apply(value);
    }

    RefundEnum(Function<Integer, Integer> expression) {
        this.expression = expression;
    }
}

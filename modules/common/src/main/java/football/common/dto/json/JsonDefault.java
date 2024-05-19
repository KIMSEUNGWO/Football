package football.common.dto.json;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JsonDefault {

    private String result;
    private String message;

    public JsonDefault(String message) {
        this.message = message;
    }
}

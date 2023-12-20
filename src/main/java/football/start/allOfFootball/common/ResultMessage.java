package football.start.allOfFootball.common;

import org.springframework.validation.BindingResult;

import java.util.Map;

public class ResultMessage {

    public static void bindingMessageToMap(Map<String, String> map, BindingResult bindingResult) {
        String errorMessage = bindingResult.getFieldError().getDefaultMessage();
        String field = bindingResult.getFieldError().getField();
        System.out.println("field = " + field);
        System.out.println("errorMessage = " + errorMessage);
        map.put("status", "error");
        map.put("message", errorMessage);
    }


    public static void errorMessage(Map<String, String> map, String errorMessage) {
        map.put("status", "error");
        map.put("message", errorMessage);
    }

    public static void okMessage(Map<String, String> map, String okMessage) {
        map.put("status", "ok");
        map.put("message", okMessage);
    }
}

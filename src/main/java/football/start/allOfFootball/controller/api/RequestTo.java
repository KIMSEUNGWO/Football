package football.start.allOfFootball.controller.api;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RequestTo {

    public <T> JSONObject requestPost(T body, String url, HttpHeaders headers) {
        HttpEntity<T> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        log.info("전송요청 POST : {}, {}", entity.toString(), this);
        return restTemplate.postForObject(url, entity, JSONObject.class);
    }

    public <T> JSONObject requestGet(T body, String url, HttpHeaders headers) {
        HttpEntity<T> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        log.info("전송요청 GET : {}, {}", entity.toString(), this);
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, JSONObject.class);
        return responseEntity.getBody();
    }
}

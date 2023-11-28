package football.start.allOfFootball.controller.admin;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchDto {

    private List<String> region;
    private String word;

}

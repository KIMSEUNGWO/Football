package football.start.allOfFootball.controller.login;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class LoginDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

}

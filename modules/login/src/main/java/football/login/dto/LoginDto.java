package football.login.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
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

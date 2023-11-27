package football.start.allOfFootball.controller.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class EmailDto {

    @NotNull
    @NotEmpty
    @NotBlank
    @Email(regexp = "^[0-9a-zA-Z]+@[a-z]+\\.[a-z]{1,4}",message = "유효하지 않은 이메일입니다.")
    private String email;
}

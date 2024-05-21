package football.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {

    @NotEmpty
    @Email(regexp = "^[0-9a-zA-Z]+@[a-z]+\\.[a-z]{1,4}")
    private String email;
}

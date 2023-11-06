package football.start.allOfFootball.controller.login;


import lombok.Getter;

@Getter
public class LoginDto {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

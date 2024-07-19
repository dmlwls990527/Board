package Board.board.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String nickName;
    private Integer age;
    private String phoneNumber;
}

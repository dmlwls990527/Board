package Board.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String nickName;

    @Column(nullable = false)
    private Integer age;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = true) // createdDate는 nullable로 설정해 두었습니다.
    private LocalDateTime createdDate;
}

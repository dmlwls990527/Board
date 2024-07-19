package Board.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@NoArgsConstructor

public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private String writer; // 작성자 닉네임 미구현

    private int view; //조회수

    private String filePath;

    private String filename;
    private Long user_id; // 사용자 id 미구현



}

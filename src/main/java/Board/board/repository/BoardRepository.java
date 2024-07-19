package Board.board.repository;

import Board.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    //제목으로 검색
    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);
}

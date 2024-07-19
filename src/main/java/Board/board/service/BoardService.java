package Board.board.service;

import Board.board.domain.Board;
import Board.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public void write(Board board, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);

            board.setFilename(fileName);
            board.setFilePath("/files/" + fileName);
        }

        LocalDateTime now = LocalDateTime.now();
        board.setCreatedDate(now);
        board.setUpdatedDate(now);
        boardRepository.save(board);
    }

    public void update(Board board, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);

            board.setFilename(fileName);
            board.setFilePath("/files/" + fileName);
        }

        board.setUpdatedDate(LocalDateTime.now());
        boardRepository.save(board);
    }

    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Page<Board> searchList(String searchKeyword, Pageable pageable) {
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    public Board boardView(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + id));
        board.setView(board.getView() + 1); // 조회수 증가
        boardRepository.save(board); // 증가된 조회수 저장
        return board;
    }

    public void boardDelete(Long id) {
        boardRepository.deleteById(id);
    }

    public Board boardModify(Board board, Long id) {
        Board old = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + id));
        old.setContent(board.getContent());
        old.setView(board.getView());
        old.setTitle(board.getTitle());
        old.setWriter(board.getWriter());
        old.setUpdatedDate(LocalDateTime.now());
        return old;
    }


}

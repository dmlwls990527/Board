package Board.board.service;

import Board.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import Board.board.domain.Board;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    public void write(Board board, MultipartFile file) throws IOException {
        String projectPath=System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files";

        UUID uuid=UUID.randomUUID();

        String fileName=uuid+file.getOriginalFilename();

        File saveFile=new File(projectPath,fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilePath("/files/"+fileName);


        boardRepository.save(board);
    }
    public Page<Board> boardList(Pageable pageable){

       return boardRepository.findAll(pageable);
    }
    public Page<Board> searchList(String searchKeyword,Pageable pageable){
        return boardRepository.findByTitleContaining(searchKeyword,pageable);
    }

    public Board  boardView(Long id){
        return boardRepository.findById(id).get();
    }
    public void boardDelete(Long id){
        boardRepository.deleteById(id);
    }
    public Board boardModify(Board board,Long id){
        Board old=boardRepository.findById(id).get();
        old.setContent(board.getContent());
        old.setView(board.getView());
        old.setTitle(board.getTitle());
        old.setWriter(board.getWriter());
        return old;
    }
}

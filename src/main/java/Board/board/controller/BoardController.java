package Board.board.controller;

import Board.board.domain.Board;
import Board.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/board") // 모든 URL 패턴에 /board를 추가합니다.
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/write")
    public String boardWriteForm() {
        return "boardwrite";
    }

    @PostMapping("/writedo")
    public String boardWriteDo(Board board, Model model, @RequestParam("file") MultipartFile file) throws IOException {
        boardService.write(board, file);
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "message";
    }

    @GetMapping("/list")
    public String boardList(Model model, @RequestParam(required = false) String searchKeyword,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Board> list = searchKeyword == null ? boardService.boardList(pageable) : boardService.searchList(searchKeyword, pageable);
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(1, nowPage - 4);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
    }

    @GetMapping("/view")
    public String boardView(Model model, @RequestParam("id") Long id) {
        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/delete")
    public String boardDelete(@RequestParam("id") Long id, Model model) {
        boardService.boardDelete(id);
        model.addAttribute("message", "글 삭제가 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "message";
    }

    @GetMapping("/modify/{id}")
    public String boardModify(@PathVariable("id") Long id, Model model) {
        model.addAttribute("board", boardService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/update/{id}")
    public String boardUpdate(@PathVariable("id") Long id, Board board, Model model, @RequestParam("file") MultipartFile file) throws IOException {
        Board newBoard = boardService.boardModify(board, id);
        boardService.update(newBoard, file);
        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "message";
    }

}

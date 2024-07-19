package Board.board.controller;

import Board.board.domain.User;
import Board.board.dto.UserDTO;
import Board.board.exception.UserCreationException;
import Board.board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public String createUser(@RequestBody UserDTO userDTO, Model model){
        // 입력 데이터 검증
       try {
           validateUserDTO(userDTO);

           //검증 성공 -> 사용자 생성
           userService.createUser(userDTO);
           model.addAttribute("message", "사용자 생성이 완료되었습니다.");
           model.addAttribute("searchUrl", "/board/list");
           return "message";
       }catch (UserCreationException ex){
           model.addAttribute("message",ex.getMessage());
           model.addAttribute("searchUrl","redirect:/board/create");
           return "message";
       }
    }
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    @GetMapping("/create")
    public String createPage(){
        return "create";
    }






    private void validateUserDTO(UserDTO userDTO) {
        // Validate username (must be in Korean)
        if (userDTO.getUsername() == null || !userDTO.getUsername().matches("[가-힣]+")) {
            throw new UserCreationException("Username은 한글이어야 합니다.");
        }

        // Validate age (must be a number between 1 and 100)
        if (userDTO.getAge() == null) {
            throw new UserCreationException("Age는 필수 입력 항목입니다.");
        }

        if (userDTO.getAge() < 1 || userDTO.getAge() > 100) {
            throw new UserCreationException("Age는 1과 100 사이의 숫자여야 합니다.");
        }

        // Validate nickname (must be alphanumeric or Korean)
        if (userDTO.getNickName() == null || !userDTO.getNickName().matches("[a-zA-Z0-9가-힣]+")) {
            throw new UserCreationException("Nickname은 영문자, 숫자 또는 한글이어야 합니다.");
        }

        // Validate email (must follow standard email format)
        if (userDTO.getEmail() == null || !userDTO.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new UserCreationException("Email 형식이 올바르지 않습니다.");
        }

        // Validate phone number (must be numeric)
        if (userDTO.getPhoneNumber() == null || !userDTO.getPhoneNumber().matches("\\d+")) {
            throw new UserCreationException("Phone number는 숫자여야 합니다.");
        }

        // Validate password (must be at least 2 characters and alphanumeric)
        if (userDTO.getPassword() == null || !userDTO.getPassword().matches("^[a-zA-Z0-9]{2,}$")) {
            throw new UserCreationException("Password는 2자 이상의 영문자 또는 숫자여야 합니다.");
        }
    }

}

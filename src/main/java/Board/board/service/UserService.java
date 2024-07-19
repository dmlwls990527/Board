package Board.board.service;

import Board.board.domain.User;
import Board.board.dto.UserDTO;
import Board.board.exception.UserCreationException;
import Board.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    //회원가입
    public User createUser(UserDTO userDTO){

        User user=new User();
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));  //비밀번호 암호화
        user.setPassword(userDTO.getPassword());
        user.setNickName(userDTO.getNickName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setUsername(userDTO.getUsername());
        user.setCreatedDate(LocalDateTime.now());

        return userRepository.save(user);
    }

}

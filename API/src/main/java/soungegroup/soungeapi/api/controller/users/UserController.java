package soungegroup.soungeapi.api.controller.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.api.controller.DTO.LoginDTO;
import soungegroup.soungeapi.domain.model.users.User;
import soungegroup.soungeapi.domain.service.users.impl.UserServiceImpl;
import soungegroup.soungeapi.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {
    private final DateUtil dateUtil;
    private final UserServiceImpl userService;
    private  final List<LoginDTO> activeUsers = new ArrayList<LoginDTO>();

       @GetMapping("/login")
       public User login(@RequestBody LoginDTO login){
       List<User> user =  userService.findUserByEmailAndPasswordHash(login.getEmail(), login.hasSenha());
       if(user.size() ==1){
           login.setUser(user.get(0));
           activeUsers.add(login);
           return  user.get(0);
       }
       else {
           return null;
            }
       }
       @DeleteMapping("/login")
    public String logout(@RequestBody LoginDTO loginDTO){
          if (activeUsers.remove(loginDTO)){
              return String.format("Usuario %s fez logout com sucesso", loginDTO.hasUser().getName());
          }
          return "usuario de email " + loginDTO.getEmail() + "não encontrado";


       }




}

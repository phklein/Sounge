package soungegroup.soungeapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.request.*;
import soungegroup.soungeapi.response.UserLoginResponse;
import soungegroup.soungeapi.response.UserProfileResponse;
import soungegroup.soungeapi.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService service;

    @PostMapping
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Salvar e autenticar usuário",
            description = "Cria um registro de usuário no banco e o autentica")
    public ResponseEntity<UserLoginResponse> saveAndLogin(@RequestBody @Valid UserSaveRequest body) {
        return service.saveAndLogin(body);
    }

    @GetMapping("/report")
    @Operation(tags = {"Usuários - Consultas"}, summary = "Baixar relatório CSV de usuários",
            description = "Retorna um arquivo CSV com as informações de todos os usuários")
    public ResponseEntity<String> getReport() {
        return service.export();
    }

    @GetMapping("/{id}")
    @Operation(tags = {"Usuários - Consultas"}, summary = "Buscar perfil de usuário pelo ID",
            description = "Verifica se o usuário existe pelo Id, e retorna o perfil dele")
    public  ResponseEntity<UserProfileResponse> getProfileById(@PathVariable Long id){
        return  service.getProfileById(id);
    }

    @PostMapping("/auth")
    @Operation(tags = {"Usuários - Consultas"}, summary = "Autenticar um usuário (Login)",
            description = "Busca o usuário pelo email e senha, e o autentica caso achar")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest body) {
        return service.login(body);
    }

    @DeleteMapping("/{id}/auth")
    @Operation(tags = {"Usuários - Consultas"}, summary = "Desautenticar um usuário (Logoff)",
            description = "Busca uma sessão de usuário pelo ID, e remove ela caso achar")
    public ResponseEntity<Void> logoff(@PathVariable Long id) {
        return service.logoff(id);
    }

    @PutMapping("/{id}")
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Atualizar perfil de usuário",
            description = "Atualiza nome, descrição e id do spotify do usuário")
    public ResponseEntity<Void> updateProfile(@PathVariable Long id,
                                              @RequestBody @Valid UserProfileUpdateRequest body){
        return service.updateProfilePage(id, body);
    }

    @PatchMapping("/{id}/photo")
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Atualizar foto de perfil do usuário",
            description = "Atualiza a URL da foto de perfil do usuário")
    public ResponseEntity<Void> updatePicture(@PathVariable Long id,
                                              @RequestBody @Valid PictureUpdateRequest body) {
        return service.updatePicture(id, body);
    }

    @PatchMapping("/{id}/password")
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Atualizar senha do usuário",
            description = "Atualiza a senha do usuário")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,
                                               @RequestBody @Valid UserPasswordUpdateRequest body) {
        return service.updatePassword(id, body);
    }

    @PostMapping("/{id}/likePost/{postId}")
    @Operation(tags = {"Usuários - Operações"}, summary = "Dar like em um post",
            description = "Adiciona um like à um post, caso ainda não exista")
    public ResponseEntity<Void> likePost(@PathVariable Long id,
                                         @PathVariable Long postId) {
        return service.likePost(id, postId);
    }

    @DeleteMapping("/{id}/likePost/{postId}")
    @Operation(tags = {"Usuários - Operações"}, summary = "Remover um like de um post",
            description = "Remove um like de um post, caso exista")
    public ResponseEntity<Void> unlikePost(@PathVariable Long id,
                                           @PathVariable Long postId) {
        return service.unlikePost(id, postId);
    }

    @PostMapping("/{id}/likeUser/{likedId}")
    @Operation(tags = {"Usuários - Operações"}, summary = "Dar like em um usuário",
            description = "Adiciona um like à um usuário, caso ainda não exista")
    public ResponseEntity<Void> likeUser(@PathVariable Long id,
                                         @PathVariable Long likedId) {
        return service.likeUser(id, likedId);
    }

    @DeleteMapping("/{id}/likeUser/{likedId}")
    @Operation(tags = {"Usuários - Operações"}, summary = "Remover um like de um usuário",
            description = "Remove um like de um usuário, caso exista")
    public ResponseEntity<Void> unlikeUser(@PathVariable Long id,
                                           @PathVariable Long likedId) {
        return service.unlikeUser(id, likedId);
    }

    @PostMapping("/{id}/group/{groupId}")
    @Operation(tags = {"Usuários - Operações"}, summary = "Entrar em uma banda",
            description = "Relaciona o usuário à uma banda pelo ID, caso seja o primeiro a entrar, se torna líder")
    public ResponseEntity<Void> joinGroup(@PathVariable Long id,
                                          @PathVariable Long groupId) {
        return service.joinGroup(id, groupId);
    }

    @DeleteMapping("/{id}/group")
    @Operation(tags = {"Usuários - Operações"}, summary = "Sair da banda",
            description = "Remove o usuário da banda em que está")
    public ResponseEntity<Void> leaveGroup(@PathVariable Long id) {
        return service.leaveGroup(id);
    }

    @PostMapping("/{id}/genres/{genreName}")
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Adicionar gênero musical aos interesses do usuário",
            description = "Adicionar um gênero musical aos interesses do usuário, caso não exista lá")
    public ResponseEntity<Void> addGenre(@PathVariable Long id,
                                         @PathVariable GenreName genreName) {
        return service.addGenre(id, genreName);
    }

    @DeleteMapping("/{id}/genres/{genreName}")
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Remover gênero musical dos interesses do usuário",
            description = "Remove um gênero musical dos interesses do usuário, caso exista lá")
    public ResponseEntity<Void> removeGenre(@PathVariable Long id,
                                            @PathVariable GenreName genreName) {
        return service.removeGenre(id, genreName);
    }

    @PostMapping("/{id}/roles/{roleName}")
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Adicionar função ao usuário",
            description = "Adicionar uma função ao usuário, caso não exista lá")
    public ResponseEntity<Void> addRole(@PathVariable Long id,
                                        @PathVariable RoleName roleName) {
        return service.addRole(id, roleName);
    }

    @DeleteMapping("/{id}/roles/{roleName}")
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Remover função do usuário",
            description = "Remove uma função do usuário, caso exista lá")
    public ResponseEntity<Void> removeRole(@PathVariable Long id,
                                           @PathVariable RoleName roleName) {
        return service.removeRole(id, roleName);
    }

    @DeleteMapping("/{id}/{pwd}")
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Remover um usuário",
            description = "Remove um usuário pelo ID e senha")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @PathVariable String pwd) {
        return service.delete(id, pwd);
    }
}

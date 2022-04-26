package soungegroup.soungeapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.enums.*;
import soungegroup.soungeapi.request.*;
import soungegroup.soungeapi.response.UserLoginResponse;
import soungegroup.soungeapi.response.UserMatchResponse;
import soungegroup.soungeapi.response.UserProfileResponse;
import soungegroup.soungeapi.response.UserSimpleResponse;
import soungegroup.soungeapi.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService service;

    @PostMapping
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Salvar e autenticar usuário",
            description = "Cria um registro de usuário no banco e o autentica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Informações inválidas", content = @Content)
    })
    public ResponseEntity<UserLoginResponse> saveAndLogin(@RequestBody @Valid UserSaveRequest body) {
        return service.saveAndLogin(body);
    }

    @GetMapping("/report")
    @Operation(tags = {"Usuários - Consultas"}, summary = "Baixar relatório CSV de usuários",
            description = "Retorna um arquivo CSV com as informações de todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrado com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum registro na lista", content = @Content)
    })
    public ResponseEntity<String> getReport() {
        return service.export();
    }

    @GetMapping
    @Operation(tags = {"Usuários - Consultas"}, summary = "Buscar usuários pelo nome",
            description = "Verifica e retorna usuários com o nome inserido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrado com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum registro na lista", content = @Content)
    })
    public ResponseEntity<List<UserSimpleResponse>> findByName(@RequestParam String nameLike) {
        return service.findByName(nameLike);
    }

    @GetMapping("/{id}")
    @Operation(tags = {"Usuários - Consultas"}, summary = "Buscar perfil de usuário pelo ID",
            description = "Verifica se o usuário existe pelo Id, e retorna o perfil dele")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    public ResponseEntity<UserProfileResponse> getProfileById(@RequestParam Long viewerId,
                                                               @PathVariable Long id){
        return service.getProfileById(viewerId, id);
    }

    @GetMapping("/match")
    @Operation(tags = {"Usuários - Consultas"}, summary = "Buscar perfil de usuário pelo ID",
            description = "Verifica se o usuário existe pelo Id, e retorna o perfil dele")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    public ResponseEntity<List<UserMatchResponse>> findMatchList(@RequestParam Long userId,
                                                                 @RequestParam Optional<Integer> minAge,
                                                                 @RequestParam Optional<Integer> maxAge,
                                                                 @RequestParam Optional<RoleName> roleName,
                                                                 @RequestParam Optional<Sex> sex,
                                                                 @RequestParam Optional<SkillLevel> skillLevel) {
        return service.findMatchList(userId, minAge, maxAge, roleName, sex , skillLevel);
    }

    @PostMapping("/auth")
    @Operation(tags = {"Usuários - Autenticação"}, summary = "Autenticar um usuário (Login)",
            description = "Busca o usuário pelo email e senha, e o autentica caso achar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Informações inválidas", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Dois ou mais registros do usuário", content = @Content)
    })
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest body) {
        return service.login(body);
    }

    @GetMapping("/{id}/auth")
    @Operation(tags = {"Usuários - Autenticação"}, summary = "Verificar se usuário tem sessão",
            description = "Verifica se o usuário possui sessão ativa pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    public ResponseEntity<Boolean> checkSession(@PathVariable Long id) {
        return service.checkSession(id);
    }

    @DeleteMapping("/{id}/auth")
    @Operation(tags = {"Usuários - Autenticação"}, summary = "Desloga um usuário (Logoff)",
            description = "Busca uma sessão de usuário pelo ID, e remove ela caso achar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deslogado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sessão não encontrada")
    })
    public ResponseEntity<Void> logoff(@PathVariable Long id) {
        return service.logoff(id);
    }

    @PutMapping("/{id}")
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Atualizar perfil de usuário",
            description = "Atualiza nome, descrição e id do spotify do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Informações inválidas"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> updateProfile(@PathVariable Long id,
                                              @RequestBody @Valid UserProfileUpdateRequest body){
        return service.updateProfilePage(id, body);
    }

    @PatchMapping("/{id}/photo")
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Atualizar foto de perfil do usuário",
            description = "Atualiza a URL da foto de perfil do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Informações inválidas"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> updatePicture(@PathVariable Long id,
                                              @RequestBody @Valid PictureUpdateRequest body) {
        return service.updatePicture(id, body);
    }

    @PatchMapping("/{id}/password")
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Atualizar senha do usuário",
            description = "Atualiza a senha do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Informações inválidas"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,
                                               @RequestBody @Valid UserPasswordUpdateRequest body) {
        return service.updatePassword(id, body);
    }

    @PatchMapping("/{id}/signature")
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Atualizar assinatura do usuário",
            description = "Atualiza a assinatura do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Informações inválidas"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> updateSignature(@PathVariable Long id,
                                                @RequestParam SignatureType signatureType) {
        return service.updateSignature(id, signatureType);
    }

    @PostMapping("/{id}/likePost/{postId}")
    @Operation(tags = {"Usuários - Operações"}, summary = "Dar like em um post",
            description = "Adiciona um like à um post, caso ainda não exista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Like criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário/Post não encontrado"),
            @ApiResponse(responseCode = "405", description = "Like já existe")
    })
    public ResponseEntity<Void> likePost(@PathVariable Long id,
                                         @PathVariable Long postId) {
        return service.likePost(id, postId);
    }

    @DeleteMapping("/{id}/likePost/{postId}")
    @Operation(tags = {"Usuários - Operações"}, summary = "Remover um like de um post",
            description = "Remove um like de um post, caso exista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Like removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário/Post/Like não encontrado")
    })
    public ResponseEntity<Void> unlikePost(@PathVariable Long id,
                                           @PathVariable Long postId) {
        return service.unlikePost(id, postId);
    }

    @PostMapping("/{id}/likeUser/{likedId}")
    @Operation(tags = {"Usuários - Operações"}, summary = "Dar like em um usuário",
            description = "Adiciona um like à um usuário, caso ainda não exista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Like criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "405", description = "Like não existe")
    })
    public ResponseEntity<Void> likeUser(@PathVariable Long id,
                                         @PathVariable Long likedId) {
        return service.likeUser(id, likedId);
    }

    @DeleteMapping("/{id}/likeUser/{likedId}")
    @Operation(tags = {"Usuários - Operações"}, summary = "Remover um like de um usuário",
            description = "Remove um like de um usuário, caso exista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Like removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário/Like não encontrado")
    })
    public ResponseEntity<Void> unlikeUser(@PathVariable Long id,
                                           @PathVariable Long likedId) {
        return service.unlikeUser(id, likedId);
    }

    @PostMapping("/{id}/group/{groupId}")
    @Operation(tags = {"Usuários - Operações"}, summary = "Entrar em um grupo",
            description = "Relaciona o usuário à um grupo pelo ID, caso seja o primeiro a entrar, se torna líder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário/Grupo não encontrado")
    })
    public ResponseEntity<Void> joinGroup(@PathVariable Long id,
                                          @PathVariable Long groupId) {
        return service.joinGroup(id, groupId);
    }

    @DeleteMapping("/{id}/group")
    @Operation(tags = {"Usuários - Operações"}, summary = "Sair do grupo",
            description = "Remove o usuário do grupo em que está")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário/Grupo não encontrado")
    })
    public ResponseEntity<Void> leaveGroup(@PathVariable Long id) {
        return service.leaveGroup(id);
    }

    @PostMapping("/{id}/genres")
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Adicionar gênero musical aos interesses do usuário",
            description = "Adicionar um gênero musical aos interesses do usuário, caso não exista lá")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Gênero adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Gênero não existe"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "405", description = "Gênero já está na lista")
    })
    public ResponseEntity<Void> addGenre(@PathVariable Long id,
                                         @RequestParam GenreName genreName) {
        return service.addGenre(id, genreName);
    }

    @DeleteMapping("/{id}/genres")
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Remover gênero musical dos interesses do usuário",
            description = "Remove um gênero musical dos interesses do usuário, caso exista lá")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gênero removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Gênero não existe"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> removeGenre(@PathVariable Long id,
                                            @RequestParam GenreName genreName) {
        return service.removeGenre(id, genreName);
    }

    @PostMapping("/{id}/roles")
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Adicionar função ao usuário",
            description = "Adicionar uma função ao usuário, caso não exista lá")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Função adicionada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Função não existe"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "405", description = "Função já está na lista")
    })
    public ResponseEntity<Void> addRole(@PathVariable Long id,
                                        @RequestParam RoleName roleName) {
        return service.addRole(id, roleName);
    }

    @DeleteMapping("/{id}/roles")
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Remover função do usuário",
            description = "Remove uma função do usuário, caso exista lá")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Função removida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Função não existe"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> removeRole(@PathVariable Long id,
                                           @RequestParam RoleName roleName) {
        return service.removeRole(id, roleName);
    }

    @DeleteMapping("/{id}")
    @Operation(tags = {"Usuários - Criação e edição"}, summary = "Remover um usuário",
            description = "Remove um usuário pelo ID e senha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam String pwd) {
        return service.delete(id, pwd);
    }
}

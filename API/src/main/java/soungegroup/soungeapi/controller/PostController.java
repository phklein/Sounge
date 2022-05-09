package soungegroup.soungeapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.request.CommentSaveRequest;
import soungegroup.soungeapi.request.PostSaveRequest;
import soungegroup.soungeapi.request.PostUpdateRequest;
import soungegroup.soungeapi.response.CommentSimpleResponse;
import soungegroup.soungeapi.response.PostSimpleResponse;
import soungegroup.soungeapi.service.CommentService;
import soungegroup.soungeapi.service.PostService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@CrossOrigin
public class PostController {
    private final PostService service;
    private final CommentService commentService;

    @Autowired
    public PostController(PostService service, CommentService commentService) {
        this.service = service;
        this.commentService = commentService;
    }

    // Posts
    @PostMapping
    @Operation(tags = {"Posts - Criação e edição"}, summary = "Salvar um post",
            description = "Cria um registro de post no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Informações inválidas", content = @Content)
    })
    public ResponseEntity<Long> save(@RequestBody @Valid PostSaveRequest body) {
        return service.save(body);
    }

    @GetMapping
    @Operation(tags = {"Posts - Consultas"}, summary = "Buscar posts",
            description = "Busca até 50 posts aleatórios. Caso o userId seja passado, realiza um filtro pelo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrado com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum registro na lista", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    public ResponseEntity<List<PostSimpleResponse>> findAll(@RequestParam Optional<Long> userId,
                                                            @RequestParam Optional<GenreName> genre,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                            Optional<LocalDateTime> startDateTime,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                            Optional<LocalDateTime> endDateTime,
                                                            @RequestParam Optional<String> textLike) {
        return service.findAll(userId, genre, startDateTime, endDateTime, textLike);
    }

    @Operation(tags = {"Posts - Criação e edição"}, summary = "Atualizar um post",
            description = "Atualiza o texto e a mídia de um post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Informações inválidas"),
            @ApiResponse(responseCode = "404", description = "Post não encontrado")
    })
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody @Valid PostUpdateRequest body) {
        return service.update(id, body);
    }

    @DeleteMapping("/{id}")
    @Operation(tags = {"Posts - Criação e edição"}, summary = "Deletar um post",
            description = "Deleta um post pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Post não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    // Comments
    @PostMapping("/{postId}/comments")
    @Operation(tags = {"Posts - Criação e edição"}, summary = "Criar um comentário em um post",
            description = "Cria um comentário em um post pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Informações inválidas", content = @Content)
    })
    public ResponseEntity<Long> saveComment(@PathVariable Long postId,
                                            @RequestBody @Valid CommentSaveRequest body) {
        return commentService.save(postId, body);
    }

    @GetMapping("/{postId}/comments")
    @Operation(tags = {"Posts - Consultas"}, summary = "Buscar comentários de um post",
            description = "Busca até 50 comentários de um post pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrado com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum registro na lista", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post não encontrado", content = @Content)
    })
    public ResponseEntity<List<CommentSimpleResponse>> findCommentsByPostId(@PathVariable Long postId) {
        return commentService.findByPostId(postId);
    }

    @DeleteMapping("/{postId}/comments/{id}")
    @Operation(tags = {"Posts - Criação e edição"}, summary = "Deletar um comentário de um post",
            description = "Deleta um comentário de um post pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Post/Comentário não encontrado")
    })
    public ResponseEntity<Void> deleteComment(@PathVariable Long postId,
                                              @PathVariable Long id) {
        return commentService.delete(postId, id);
    }
}

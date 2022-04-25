package soungegroup.soungeapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.request.GroupPageUpdateRequest;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.request.PictureUpdateRequest;
import soungegroup.soungeapi.response.GroupPageResponse;
import soungegroup.soungeapi.service.GroupService;

import javax.validation.Valid;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
@CrossOrigin
public class GroupController {
    private final GroupService service;

    @PostMapping
    @Operation(tags = {"Grupos - Criação e edição"}, summary = "Salvar um grupo",
            description = "Cria um registro de grupo no banco")
    public ResponseEntity<Long> save(@RequestBody @Valid GroupSaveRequest body) {
        return service.save(body);
    }

    @GetMapping("/{id}")
    @Operation(tags = {"Grupos - Consultas"}, summary = "Buscar página de grupo pelo ID",
            description = "Verifica se o grupo existe pelo Id, e retorna a página dele")
    public ResponseEntity<GroupPageResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/report")
    @Operation(tags = {"Grupos - Consultas"}, summary = "Baixar relatório CSV de grupos",
            description = "Retorna um arquivo CSV com as informações de todos os grupos")
    public ResponseEntity<String> getReport() {
        return service.export();
    }

    @PutMapping("/{id}")
    @Operation(tags = {"Grupos - Criação e edição"}, summary = "Atualizar um grupo",
            description = "Atualiza o nome e a descrição de um grupo")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody @Valid GroupPageUpdateRequest body) {
        return service.update(id, body);
    }

    @PatchMapping("/{id}/photo")
    @Operation(tags = {"Grupos - Criação e edição"}, summary = "Atualizar foto do grupo",
            description = "Atualiza a URL da foto do grupo")
    public ResponseEntity<Void> updatePicture(@PathVariable Long id,
                                              @RequestBody @Valid PictureUpdateRequest body) {
        return service.updatePicture(id, body);
    }

    @DeleteMapping("/{id}")
    @Operation(tags = {"Grupos - Criação e edição"}, summary = "Remover um grupo",
            description = "Remove um grupo pelo ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}

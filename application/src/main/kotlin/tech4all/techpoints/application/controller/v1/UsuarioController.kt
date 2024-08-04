package tech4all.techpoints.application.controller.v1

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.core.io.ByteArrayResource
import tech4all.techpoints.domain.dto.UsuarioDTOInput
import tech4all.techpoints.domain.dto.UsuarioDTOOutput
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

interface UsuarioController {

    @Operation(summary = "Cadastrar um novo usuário", description = "Retorna os detalhes do usuário cadastrado")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            ApiResponse(responseCode = "400", description = "Email já cadastrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PostMapping("/cadastro")
    fun post(@RequestBody @Valid usuarioDTOInput: UsuarioDTOInput): ResponseEntity<UsuarioDTOOutput>

    @Operation(summary = "Soft delete de um usuário", description = "Marca o usuário como deletado, sem remover do banco")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
            ApiResponse(responseCode = "400", description = "Credenciais inválidas"),
            ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PostMapping("/deletar")
    fun softDelete(@RequestBody @Valid requestBody: Map<String, String>): ResponseEntity<Map<String, String>>

    @Operation(summary = "Hard delete de um usuário", description = "Remove o usuário do banco de dados permanentemente")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
            ApiResponse(responseCode = "400", description = "Credenciais inválidas"),
            ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @DeleteMapping("/deletar")
    @Transactional
    fun hardDelete(@RequestBody @Valid requestBody: Map<String, String>): ResponseEntity<Map<String, String>>

    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
            ApiResponse(responseCode = "204", description = "Nenhum usuário encontrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping("/listar")
    fun listar(): ResponseEntity<List<UsuarioDTOOutput>>

    @Operation(summary = "Buscar usuário pelo ID", description = "Retorna o usuário correspondente ao ID fornecido")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping("/buscar/{idUsuario}")
    fun buscar(@PathVariable idUsuario: Int): ResponseEntity<UsuarioDTOOutput>

    @Operation(summary = "Login de usuário", description = "Autentica um usuário com email e senha")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso"),
            ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PostMapping("/login")
    fun login(@RequestBody loginData: Map<String, String>): ResponseEntity<UsuarioDTOOutput>

    @Operation(summary = "Logoff de usuário", description = "Desautentica um usuário pelo ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário desautenticado com sucesso"),
            ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PostMapping("/logoff")
    fun logoff(@RequestParam idUsuario: Int): ResponseEntity<Map<String, String>>

    @Operation(summary = "Buscar usuário pelo email", description = "Retorna o usuário correspondente ao email fornecido")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping("/buscarPorEmail")
    fun buscarPorEmail(@RequestParam email: String): ResponseEntity<UsuarioDTOOutput>

    @Operation(summary = "Atualizar informações do usuário", description = "Atualiza as informações do usuário")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PatchMapping("/atualizar/{idUsuario}")
    fun patchUsuario(@PathVariable idUsuario: Int, @RequestBody atualizacao: Map<String, Any>): ResponseEntity<UsuarioDTOOutput>

    @Operation(summary = "Atualizar a imagem de perfil de usuário", description = "Atualiza a imagem de perfil de um usuário pelo ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Imagem de perfil atualizada com sucesso"),
            ApiResponse(responseCode = "400", description = "Requisição inválida"),
            ApiResponse(responseCode = "404", description = "ID do usuário não encontrado"),
            ApiResponse(responseCode = "415", description = "Tipo de mídia não suportado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PatchMapping(value = ["/atualizar-imagem/{idUsuario}"], consumes = ["image/jpeg", "image/png", "image/jpg"])
    fun patchImagem(@PathVariable idUsuario: Int, @RequestBody novaFoto: ByteArray): ResponseEntity<Map<String, String>>

    @Operation(summary = "Obter imagem de perfil do usuário", description = "Retorna a imagem de perfil do usuário pelo ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Imagem de perfil encontrada e retornada com sucesso"),
            ApiResponse(responseCode = "204", description = "Usuário não encontrado ou imagem de perfil não encontrada"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping("/imagem/{idUsuario}", produces = ["image/*"])
    fun getImagemPerfil(@PathVariable idUsuario: Int): ResponseEntity<ByteArrayResource>
}

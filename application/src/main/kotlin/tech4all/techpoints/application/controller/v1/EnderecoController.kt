package tech4all.techpoints.application.controller.v1

import tech4all.techpoints.domain.dto.EnderecoDTO
import tech4all.techpoints.domain.model.Endereco
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface EnderecoController {

    @Operation(summary = "Cadastrar um novo endereço", description = "Retorna os detalhes do endereço cadastrado")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso"),
            ApiResponse(responseCode = "400", description = "Requisição inválida"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PostMapping("/cadastro")
    fun post(@RequestBody @Valid novoEndereco: Endereco): ResponseEntity<Endereco>

    @Operation(summary = "Listar todos os endereços", description = "Retorna uma lista de todos os endereços cadastrados")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso"),
            ApiResponse(responseCode = "204", description = "Nenhum endereço encontrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping("/listar")
    fun listar(): ResponseEntity<List<Endereco>>

    @Operation(summary = "Buscar endereço pelo ID", description = "Retorna o endereço correspondente ao ID fornecido")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso"),
            ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping("/buscar/{idEndereco}")
    fun buscar(@PathVariable idEndereco: Int): ResponseEntity<Endereco>

    @Operation(summary = "Atualizar endereço pelo ID", description = "Atualiza e retorna o endereço correspondente ao ID fornecido")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
            ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PatchMapping("/atualizar/{idEndereco}")
    fun patch(@PathVariable idEndereco: Int, @RequestBody enderecoDTO: EnderecoDTO): ResponseEntity<Endereco>
}

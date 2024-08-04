package tech4all.techpoints.application.controller.v1

import tech4all.techpoints.domain.dto.DadoEmpresaDTO
import tech4all.techpoints.domain.dto.DadosEmpresaDTOAtt
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface DadosEmpresaController {

    @GetMapping
    @Operation(summary = "Obter dados de todas as empresas",
        description = "Retorna os detalhes das empresas")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Empresas encontradas"),
            ApiResponse(responseCode = "204", description = "Empresas não encontradas")
        ]
    )
    fun findAll(): ResponseEntity<List<DadoEmpresaDTO>>

    @GetMapping("/{id}")
    @Operation(
        summary = "Obter dados de uma empresa",
        description = "Retorna os detalhes da empresa pelo ID"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Empresa encontrada"),
            ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    fun findById(@PathVariable id: Int): ResponseEntity<DadoEmpresaDTO>

    @PostMapping
    @Operation(
        summary = "Cadastrar dados de uma nova empresa",
        description = "Retorna os detalhes da empresa cadastrada"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Empresa cadastrada com sucesso"),
            ApiResponse(responseCode = "400", description = "Erro de validação dos dados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    fun create(@RequestBody @Valid request: DadoEmpresaDTO): ResponseEntity<DadoEmpresaDTO>

    @PatchMapping("/{id}")
    @Operation(
        summary = "Atualizar dados de uma empresa",
        description = "Atualiza e retorna os detalhes da empresa pelo ID"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso"),
            ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
            ApiResponse(responseCode = "400", description = "Erro de validação dos dados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    fun update(@PathVariable id: Int, @RequestBody @Valid request: DadosEmpresaDTOAtt): ResponseEntity<DadoEmpresaDTO>
}

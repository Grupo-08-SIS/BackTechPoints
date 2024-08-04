package tech4all.techpoints.application.controller.v1

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tech4all.techpoints.domain.dto.*

interface DashboardAlunoController {

    @GetMapping("/pontosAoLongoDoTempo/{idUsuario}")
    @Operation(summary = "Obter pontos ao longo do tempo", description = "Retorna os pontos acumulados ao longo do tempo para um usuário específico")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Dados encontrados"),
            ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    fun getPontosAoLongoDoTempo(@PathVariable idUsuario: Int): ResponseEntity<List<PontosAoLongoDoTempoDTO>>

    @GetMapping("/pontosPorCursoAoMes/{idUsuario}")
    @Operation(summary = "Obter pontos por curso ao mês", description = "Retorna os pontos acumulados por curso ao longo do mês para um usuário específico")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Dados encontrados"),
            ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    fun getPontosPorCursoAoMes(@PathVariable idUsuario: Int): ResponseEntity<List<PontosPorCursoAoMesDTO>>

    @GetMapping("/pontosDaSemana/{idUsuario}")
    @Operation(summary = "Obter pontos da semana", description = "Retorna os pontos acumulados na semana atual para um usuário específico")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Dados encontrados"),
            ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    fun getPontosDaSemana(@PathVariable idUsuario: Int): ResponseEntity<PontosSemanaDTO>

    @GetMapping("/atividadesDoUsuario/{idUsuario}")
    @Operation(summary = "Obter atividades do usuário", description = "Retorna as atividades feitas e totais para um usuário específico")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Dados encontrados"),
            ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    fun getAtividadesDoUsuario(@PathVariable idUsuario: Int): ResponseEntity<List<AtividadesUsuarioDTO>>

    @GetMapping("/classificacao")
    @Operation(summary = "Obter classificação", description = "Retorna os usuários e seus pontos acumulados em ordem decrescente")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Dados encontrados"),
            ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    fun getClassificacao(@RequestParam(required = false) cursoId: Int?): ResponseEntity<List<ClassificacaoDTO>>

    @GetMapping("/pontos-por-curso")
    @Operation(summary = "Obter pontos por curso", description = "Retorna os pontos acumulados de um usuário em cada curso em que ele está inscrito")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Dados encontrados"),
            ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    fun getPontosPorCurso(@RequestParam idUsuario: Int): ResponseEntity<List<PontosPorCursoDTO>>
}

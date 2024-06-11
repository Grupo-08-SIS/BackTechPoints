package TechForAll.techPoints.controller

import TechForAll.techPoints.dto.AtividadesUsuarioDTO
import TechForAll.techPoints.dto.ClassificacaoDTO
import TechForAll.techPoints.service.DashboardAlunoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.websocket.server.PathParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/grafico")
@Validated
class DashboardAlunoController @Autowired constructor(
    private val graficoService: DashboardAlunoService
) {

    @Operation(
        summary = "Obter pontos ao longo do tempo",
        description = "Retorna os pontos acumulados ao longo do tempo para um usuário específico"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Dados encontrados"),
            ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping("/pontosAoLongoDoTempo/{idUsuario}")
    fun getPontosAoLongoDoTempo(@PathVariable idUsuario: Int): ResponseEntity<Any> {
        return try {
            val pontos = graficoService.getPontosAoLongoDoTempo(idUsuario)
            ResponseEntity.ok(pontos)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body(mapOf("message" to e.message))
        }
    }

    @Operation(
        summary = "Obter pontos por curso ao mês",
        description = "Retorna os pontos acumulados por curso ao longo do mês para um usuário específico"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Dados encontrados"),
            ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping("/pontosPorCursoAoMes/{idUsuario}")
    fun getPontosPorCursoAoMes(@PathVariable idUsuario: Int): ResponseEntity<Any> {
        return try {
            val pontos = graficoService.getPontosPorCursoAoMes(idUsuario)
            ResponseEntity.ok(pontos)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body(mapOf("message" to e.message))
        }
    }

    @Operation(
        summary = "Obter pontos da semana",
        description = "Retorna os pontos acumulados na semana atual para um usuário específico"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Dados encontrados"),
            ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping("/pontosDaSemana/{idUsuario}")
    fun getPontosDaSemana(@PathVariable idUsuario: Int): ResponseEntity<Any> {
        return try {
            val pontos = graficoService.getPontosSemana(idUsuario)
            ResponseEntity.ok(pontos)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body(mapOf("message" to e.message))
        }
    }

    @Operation(
        summary = "Obter atividades do usuário",
        description = "Retorna as atividades feitas e totais para um usuário específico"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Dados encontrados"),
            ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping("/atividadesDoUsuario/{idUsuario}")
    fun getAtividadesDoUsuario(@PathVariable idUsuario: Int): ResponseEntity<Any> {
        return try {
            val atividades: List<AtividadesUsuarioDTO> = graficoService.getAtividadesDoUsuario(idUsuario)
            if (atividades.isEmpty()) {
                ResponseEntity.status(404).body("Dados não encontrados")
            } else {
                ResponseEntity.ok(atividades)
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).body("Erro interno do servidor")
        }
    }


    @Operation(
        summary = "Obter classificação",
        description = "Retorna os usuários e seus pontos acumulados em ordem decrescente"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Dados encontrados"),
            ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping("/classificacao")
    fun getClassificacao(@RequestParam(required = false) cursoId: Int?,): ResponseEntity<Any> {
        return try {
            val alunos = graficoService.buscarClassificacao(cursoId)
            ResponseEntity.ok(alunos)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body(mapOf("message" to e.message))
        }
    }

    @Operation(
        summary = "Obter pontos por curso",
        description = "Retorna os pontos acumulados de um usuário em cada curso em que ele está inscrito"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Dados encontrados"),
            ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping("/pontos-por-curso")
    fun getPontosPorCurso(@RequestParam idUsuario: Int): ResponseEntity<Any> {
        return try {
            val pontos = graficoService.buscarPontosPorCurso(idUsuario)
            ResponseEntity.ok(pontos)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body(mapOf("message" to e.message))
        }
    }


}

package techForAll.techPoints.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import techForAll.techPoints.dtos.PontuacaoComPontosDTO
import techForAll.techPoints.service.PontuacaoService
import java.time.YearMonth

@RestController
@RequestMapping("/pontuacoes")
class PontuacaoController @Autowired constructor(
    private val service: PontuacaoService
) {

    @GetMapping("/{idAluno}")
    fun recuperarPontosAtividadeAgrupadoCurso(@PathVariable idAluno: Long): Map<Long, List<PontuacaoComPontosDTO>> {

        return service.recuperarTodosCursosAlunoPontuacao(idAluno);
    }

    @GetMapping("/kpi-semana/{idAluno}")
    fun recuperarKPISemana(@PathVariable idAluno: Long): Map<String, Map<Long, Int>>{

        return service.recuperarKPISemanaPassadaAndAtual(idAluno);
    }

    @GetMapping("/kpi-entregas/{idAluno}")
    fun recuperarKPIEntrega(@PathVariable idAluno: Long): Map<String, Int> {

        return service.recuperarKPIEntregas(idAluno);
    }

    @GetMapping("/pontos-mes/{idAluno}")
    fun recuperarPontosPorMes(@PathVariable idAluno: Long): Map<Pair<Long, String>, Map<YearMonth, Int>> {

        return service.recuperarPontosConquistadosPorMes(idAluno);
    }

    @GetMapping("/pontos-totais/{idAluno}")
    fun recuperarPontosTotaisPorCurso(@PathVariable idAluno: Long): Map<Long, Map<String, Any>> {
        return service.recuperarPontosTotaisPorCurso(idAluno)
    }

    @GetMapping("/ranking")
    fun recuperarRankingPorCurso(): Map<Long, Map<String, Any>> {
        return service.recuperarRankingPorCurso()
    }

    @GetMapping("/alunos")
    fun recuperarRankingComFiltros(
        @RequestParam(required = false) idade: Int?,
        @RequestParam(required = false) primeiroNome: String?,
        @RequestParam(required = false) escolaridade: String?,
        @RequestParam(required = false) sobrenome: String?,
        @RequestParam(required = false) etnia: String?,
        @RequestParam(required = false) sexo: String?,
        @RequestParam(required = false) cidade: String?,
        @RequestParam(required = false) cursoId: Long?
    ): ResponseEntity<Map<Long, Map<String, Any>>> {
        val ranking = service.recuperarRankingComFiltro(idade, primeiroNome, sobrenome, etnia, sexo, escolaridade, cidade, cursoId)
        return ResponseEntity.ok(ranking)
    }

    @GetMapping("/lista")
    @Operation(
        summary = "Recuperar lista de alunos",
        description = "Endpoint para recuperar listas associadas a um aluno específico pelo seu ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Listas recuperadas com sucesso."),
            ApiResponse(responseCode = "204", description = "Nenhuma lista encontrada."),
            ApiResponse(responseCode = "400", description = "ID do aluno inválido."),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
        ]
    )
    fun recuperarAlunoLista(
        @RequestParam(required = true) idAluno: Long
    ): ResponseEntity<Any> {
        return try {
            if (idAluno <= 0) {
                ResponseEntity.status(400).body(mapOf("message" to "ID do aluno inválido"))
            } else {
                val listas = service.buscarListasComAluno(idAluno)
                if (listas.isEmpty()) {
                    ResponseEntity.status(204).build()
                } else {
                    ResponseEntity.status(200).body(listas)
                }
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor: ${e.message}"))
        }
    }
}
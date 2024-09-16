package techForAll.techPoints.controller

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

//    @GetMapping("/{idAluno}/{idCurso}")
//    fun recuperarPontosTotaisCurso(@PathVariable idAluno: Long, @PathVariable idCurso: Long): PontuacaoComPontosDTO {
//
//        return service.recuperarAlunoCursoEspecifico(idAluno, idCurso);
//    }

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
        @RequestParam(required = false) escolaridade: String?,
        @RequestParam(required = false) cidade: String?,
        @RequestParam(required = false) cursoId: Long?
    ): ResponseEntity<Map<Long, Map<String, Any>>> {
        val ranking = service.recuperarRankingComFiltro(idade, escolaridade, cidade, cursoId)
        return ResponseEntity.ok(ranking)
    }
}
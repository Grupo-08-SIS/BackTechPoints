package techForAll.techPoints.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import techForAll.techPoints.domain.Aluno
import techForAll.techPoints.domain.Curso
import techForAll.techPoints.domain.Pontuacao
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
    fun recuperarPontosPorMes(@PathVariable idAluno: Long): Map<Long, Map<YearMonth, Int>> {

        return service.recuperarPontosConquistadosPorMes(idAluno);
    }
}
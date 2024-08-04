package tech4all.techpoints.application.controller.v1.impl

import tech4all.techpoints.application.controller.v1.DashboardAlunoController
import tech4all.techpoints.domain.service.DashboardAlunoService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech4all.techpoints.domain.dto.*

@RestController
@RequestMapping("/v1/graph-aluno")
@Tag(name = "DashboardAluno", description = "Dashboard para pontos e atividades de Usuário")
class DashboardAlunoControllerImpl(
    private val graficoService: DashboardAlunoService
) : DashboardAlunoController {

    override fun getPontosAoLongoDoTempo(idUsuario: Int): ResponseEntity<List<PontosAoLongoDoTempoDTO>> {
        val pontos = graficoService.getPontosAoLongoDoTempo(idUsuario)
        return ResponseEntity.ok(pontos)
    }

    override fun getPontosPorCursoAoMes(idUsuario: Int): ResponseEntity<List<PontosPorCursoAoMesDTO>> {
        val pontos = graficoService.getPontosPorCursoAoMes(idUsuario)
        return ResponseEntity.ok(pontos)
    }

    override fun getPontosDaSemana(idUsuario: Int): ResponseEntity<PontosSemanaDTO> {
        val pontos = graficoService.getPontosSemana(idUsuario)
        return ResponseEntity.ok(pontos)
    }

    override fun getAtividadesDoUsuario(idUsuario: Int): ResponseEntity<List<AtividadesUsuarioDTO>> {
        val atividades: List<AtividadesUsuarioDTO> = graficoService.getAtividadesDoUsuario(idUsuario)
        return if (atividades.isEmpty()) {
            ResponseEntity.status(404).body(emptyList())
        } else {
            ResponseEntity.ok(atividades)
        }
    }

    override fun getClassificacao(cursoId: Int?): ResponseEntity<List<ClassificacaoDTO>> {
        val alunos = graficoService.buscarClassificacao(cursoId)
        return ResponseEntity.ok(alunos)
    }

    override fun getPontosPorCurso(idUsuario: Int): ResponseEntity<List<PontosPorCursoDTO>> {
        val pontos = graficoService.buscarPontosPorCurso(idUsuario)
        return ResponseEntity.ok(pontos)
    }
}

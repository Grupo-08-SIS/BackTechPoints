package tech4all.techpoints.infrastructure.adapter

import org.springframework.stereotype.Component
import tech4all.techpoints.infrastructure.dto.ClassificacaoDTO
import tech4all.techpoints.infrastructure.dto.PontosPorCursoDTO
import tech4all.techpoints.infrastructure.repository.ClassificacaoRepository

@Component
class ClassificacaoAdapter(
    private val classificacaoRepository: ClassificacaoRepository
) {

    fun findClassificacao(cursoId: Int?): List<ClassificacaoDTO> {
        return classificacaoRepository.findClassificacao(cursoId)
    }

    fun findPontosPorCurso(idUsuario: Int): List<PontosPorCursoDTO> {
        return classificacaoRepository.findPontosPorCurso(idUsuario)
    }
}

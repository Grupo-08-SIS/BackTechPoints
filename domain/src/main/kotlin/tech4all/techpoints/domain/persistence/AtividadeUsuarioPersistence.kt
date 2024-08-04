package tech4all.techpoints.domain.persistence

import tech4all.techpoints.domain.model.Atividade
import tech4all.techpoints.domain.model.TotalAtividadesPorCurso
import tech4all.techpoints.domain.model.AtividadesPorCursoEUsuario

interface AtividadesUsuarioPersistence {

    fun findTotalAtividadesPorCurso(): List<TotalAtividadesPorCurso>

    fun findAtividadesPorCursoEUsuario(idUsuario: Int): List<AtividadesPorCursoEUsuario>

}

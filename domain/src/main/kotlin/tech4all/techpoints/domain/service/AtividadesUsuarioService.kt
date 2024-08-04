package tech4all.techpoints.domain.service

import tech4all.techpoints.domain.model.AtividadesPorCursoEUsuario
import tech4all.techpoints.domain.model.TotalAtividadesPorCurso

interface AtividadesUsuarioService {

    fun findTotalAtividadesPorCurso(): List<TotalAtividadesPorCurso>

    fun findAtividadesPorCursoEUsuario(idUsuario: Int): List<AtividadesPorCursoEUsuario>

}

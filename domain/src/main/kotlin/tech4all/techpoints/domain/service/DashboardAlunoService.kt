package tech4all.techpoints.domain.service

import tech4all.techpoints.domain.dto.*

interface DashboardAlunoService {

    fun getPontosAoLongoDoTempo(idUsuario: Int): List<PontosAoLongoDoTempoDTO>

    fun getPontosPorCursoAoMes(idUsuario: Int): List<PontosPorCursoAoMesDTO>

    fun getPontosSemana(idUsuario: Int): PontosSemanaDTO

    fun getAtividadesDoUsuario(idUsuario: Int): List<AtividadesUsuarioDTO>

    fun buscarClassificacao(cursoId: Int?): List<ClassificacaoDTO>

    fun buscarPontosPorCurso(idUsuario: Int): List<PontosPorCursoDTO>
}

package techForAll.techPoints.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import techForAll.techPoints.domain.Aluno
import techForAll.techPoints.repository.AlunoRepository
import techForAll.techPoints.repository.RecrutadorRepository

@Service
class DashboardRecrutadorService @Autowired constructor(
    private val recrutadorRepository: RecrutadorRepository,
    private val alunoRepository: AlunoRepository,
) {

    fun adicionarFavorito(idRecrutador: Long, idAluno: Long) {
        val recrutador = recrutadorRepository.findById(idRecrutador)
            .orElseThrow { NoSuchElementException("Recrutador não encontrado") }

        alunoRepository.findById(idAluno)
            .orElseThrow { NoSuchElementException("Aluno com ID $idAluno não encontrado") }

        adicionarLista(idAluno, recrutador.favoritos, "favoritos") { listaAtualizada ->
            recrutador.favoritos = listaAtualizada
        }
        recrutadorRepository.save(recrutador)
    }

    fun adicionarInteressado(idRecrutador: Long, idAluno: Long) {
        val recrutador = recrutadorRepository.findById(idRecrutador)
            .orElseThrow { NoSuchElementException("Recrutador não encontrado") }

        alunoRepository.findById(idAluno)
            .orElseThrow { NoSuchElementException("Aluno com ID $idAluno não encontrado") }

        adicionarLista(idAluno, recrutador.interessados, "interessados") { listaAtualizada ->
            recrutador.interessados = listaAtualizada
        }
        recrutadorRepository.save(recrutador)
    }

    private fun adicionarLista(
        idAluno: Long,
        lista: List<Long>,
        tipo: String,
        atualizarLista: (List<Long>) -> Unit
    ) {
        if (!lista.contains(idAluno)) {
            atualizarLista(lista + idAluno)
        } else {
            throw IllegalArgumentException("Aluno já está na lista de $tipo")
        }
    }

    fun removerFavorito(idRecrutador: Long, idAluno: Long) {
        val recrutador = recrutadorRepository.findById(idRecrutador)
            .orElseThrow { NoSuchElementException("Recrutador não encontrado") }

        if (!recrutador.favoritos.contains(idAluno)) {
            throw IllegalArgumentException("Aluno não está na lista de favoritos")
        }

        recrutador.favoritos = recrutador.favoritos.filter { it != idAluno }
        recrutadorRepository.save(recrutador)
    }

    fun removerInteressado(idRecrutador: Long, idAluno: Long) {
        val recrutador = recrutadorRepository.findById(idRecrutador)
            .orElseThrow { NoSuchElementException("Recrutador não encontrado") }

        if (!recrutador.interessados.contains(idAluno)) {
            throw IllegalArgumentException("Aluno não está na lista de interessados")
        }

        recrutador.interessados = recrutador.interessados.filter { it != idAluno }
        recrutadorRepository.save(recrutador)
    }

    fun listarFavoritos(idRecrutador: Long): List<Map<String, Any?>> {
        val recrutador = recrutadorRepository.findById(idRecrutador)
            .orElseThrow { NoSuchElementException("Recrutador não encontrado") }

        return listarAlunosIds(recrutador.favoritos)
    }

    fun listarInteressados(idRecrutador: Long): List<Map<String, Any?>> {
        val recrutador = recrutadorRepository.findById(idRecrutador)
            .orElseThrow { NoSuchElementException("Recrutador não encontrado") }

        return listarAlunosIds(recrutador.interessados)
    }

    private fun listarAlunosIds(ids: List<Long>): List<Map<String, Any?>> {
        return ids.mapNotNull { alunoId ->
            alunoRepository.findById(alunoId).map { aluno ->
                mapAluno(aluno)
            }.orElse(null)
        }
    }

    private fun mapAluno(aluno: Aluno): Map<String, Any?> {
        return mapOf(
            "id" to aluno.id,
            "nomeUsuario" to aluno.nomeUsuario,
            "primeiroNome" to aluno.primeiroNome,
            "sobrenome" to aluno.sobrenome,
            "email" to aluno.email,
            "telefone" to aluno.telefone,
            "escolaridade" to aluno.escolaridade,
            "descricao" to aluno.descricao,
            "dtNasc" to aluno.dtNasc,
            "endereco" to mapOf(
                "cidade" to aluno.endereco.cidade,
                "estado" to aluno.endereco.estado
            ),
        )
    }
}

package techForAll.techPoints.dto.rhdashboard

import techForAll.techPoints.dominio.Inscricao
import java.time.LocalDateTime

class AlunoEspecificoDto (
    var nome: String,
    var imagemPerfil: ByteArray,
    var municipio: String,
    var email: String,
    // var escolaridade: Escolaridade, TODO
    var emblemas: List<Inscricao>,
   // var descricaoAluno: String TODO
) {

    fun getIdade(dataNascimento: LocalDateTime): Int {
        val hoje = LocalDateTime.now().year;

        return hoje - dataNascimento.year;
    }
}
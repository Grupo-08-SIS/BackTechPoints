package techForAll.techPoints.service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import techForAll.techPoints.domain.Endereco
import techForAll.techPoints.dtos.CursoAlunosDto
import techForAll.techPoints.dtos.DemografiaDto

import techForAll.techPoints.repository.AlunoRepository
import techForAll.techPoints.repository.DashboardAdmRepository
import java.util.concurrent.ArrayBlockingQueue


@Service
class DashboardAdmService@Autowired constructor(
    private val dashAdmRepositoy: DashboardAdmRepository,
    private val alunoRepository: AlunoRepository,
    private val pontuacaoService: PontuacaoService,
    private val usuarioService: UsuarioService
){
    fun getAlunosPorCurso(): List<CursoAlunosDto> {
        return dashAdmRepositoy.findAlunosPorCurso()
    }

    fun getDemografiaPorTipoLista(tipoLista: String, idEmpresa: Long?): DemografiaDto {

        val ids = getAlunosPorTipoLista(tipoLista, idEmpresa)

        val idsFila = processarIdsJson(ids)
        if (idsFila.isEmpty()){
            throw NoSuchElementException("Nenhum aluno encontrado")
        }
        val demografia = processarFilaDemografia(idsFila)
        demografia.cursosFeitos.putAll(processarFilaCursosFeitosPorAlunos(idsFila))
        return demografia
    }
    fun gerarRelatorioDemografiaEmpresas(tipoLista: String, idEmpresa: Long?): String {

        val ids = getAlunosPorTipoLista(tipoLista, idEmpresa)

        val idsFila = processarIdsJson(ids)

        return gerarRelatorioCSV(idsFila.toList())
    }

    fun gerarRelatorioCSV(ids: List<Long>): String {
        val csvHeader = listOf(
            "ID", "Nome de Usuário", "CPF", "Primeiro Nome", "Sobrenome", "Email", "Telefone", "Tipo Usuário", "Sexo",
            "Etnia", "Escolaridade", "Data de Nascimento", "Deletado", "CEP", "Rua", "Cidade", "Estado", "Cursos"
        )

        val csvRows = ids.map { id ->
            val alunoComCursos = comporAlunoComCursos(id)

            val cursos = alunoComCursos["cursos"] as? Map<Long, Map<String, Any>>
            val endereco = alunoComCursos["endereco"] as Map<String, String>

            listOf(
                alunoComCursos["id"],
                alunoComCursos["nomeUsuario"],
                alunoComCursos["cpf"],
                alunoComCursos["primeiroNome"],
                alunoComCursos["sobrenome"],
                alunoComCursos["email"],
                alunoComCursos["telefone"],
                alunoComCursos["tipoUsuario"],
                alunoComCursos["sexo"],
                alunoComCursos["etnia"],
                alunoComCursos["escolaridade"],
                alunoComCursos["dataNascimento"],
                alunoComCursos["deletado"],
                endereco["cep"],
                endereco["rua"],
                endereco["cidade"],
                endereco["estado"],
                cursos!!.entries.joinToString("; ") { (cursoId, cursoData) ->
                    "${cursoData["nomeCurso"]} (Pontos: ${cursoData["pontosTotais"]})"
                }
            ).joinToString(",")
        }

        return (listOf(csvHeader.joinToString(",")) + csvRows).joinToString("\n")
    }

    fun comporAlunoComCursos(idAluno: Long): Map<String, Any?> {

        val aluno = usuarioService.buscarUsuarioPorId(idAluno)

        val cursos = pontuacaoService.recuperarPontosTotaisPorCurso(idAluno)

        return aluno.toMutableMap().apply {
            this["cursos"] = cursos

            val endereco = aluno["endereco"]
            this["endereco"] = if (endereco is Endereco) {

                mapOf(
                    "cep" to endereco.cep,
                    "rua" to endereco.rua,
                    "numero" to endereco.numero,
                    "cidade" to endereco.cidade,
                    "estado" to endereco.estado
                )
            } else {
                throw IllegalStateException("Endereço não pode ser nulo")
            }
        }
    }

    private fun processarFilaDemografia(idsFila: ArrayBlockingQueue<Long>): DemografiaDto {
        val demografia = DemografiaDto()

        val tamanhoInicial = idsFila.size

        for (i in 0 until tamanhoInicial) {
            val id = idsFila.poll()
            val aluno = alunoRepository.findById(id).orElse(null)

            if (aluno != null) {
                demografia.processarAluno(aluno)
            }

            idsFila.add(id)
        }

        return demografia
    }

    private fun getAlunosPorTipoLista(tipoLista: String, idEmpresa: Long?): List<Any>{
        val ids = when (tipoLista) {
            "todos" -> if (idEmpresa != null)dashAdmRepositoy.findIdsTodosByEmpresa(idEmpresa) else dashAdmRepositoy.findIdsTodos()
            "contratados" -> if (idEmpresa != null) dashAdmRepositoy.findIdsContratadosByEmpresa(idEmpresa) else dashAdmRepositoy.findIdsContratados()
            "interessados" -> if (idEmpresa != null) dashAdmRepositoy.findIdsInteressadosByEmpresa(idEmpresa) else dashAdmRepositoy.findIdsInteressados()
            "processoSeletivo" -> if (idEmpresa != null) dashAdmRepositoy.findIdsProcessoSeletivoByEmpresa(idEmpresa) else dashAdmRepositoy.findIdsProcessoSeletivo()
            else -> throw IllegalArgumentException("Tipo de lista inválido")
        }
        return ids
    }

    private fun processarIdsJson(idStrings: List<Any>): ArrayBlockingQueue<Long> {

        val ids = mutableListOf<Long>()

        /*
            pode receber um objeto desse tipo:
            idStrings = {ArrayList@18270}  size = 1
            0 = {Object[3]@18281}
                0 = "[7]"
                1 = "[5, 4]"
                2 = "[]"
         */

        for (idString in idStrings) {

            if (idString is Array<*>) {

                for (elemento in idString) {

                    if (elemento is String && elemento.isNotBlank() && elemento != "[]") {
                        elemento
                            .removeSurrounding("[", "]")
                            .split(",")
                            .mapNotNull { it.trim().toLongOrNull() }
                            .forEach { ids.add(it) }
                    }
                }
            }

            else if (idString is String && idString.isNotBlank() && idString != "[]") {

                idString
                    .removeSurrounding("[", "]")
                    .split(",")
                    .mapNotNull { it.trim().toLongOrNull() }
                    .forEach { ids.add(it) }
            }
        }
        if (ids.isNotEmpty()) {
            val idsFila = ArrayBlockingQueue<Long>(ids.size)
            idsFila.addAll(ids)
            return idsFila
        } else  throw NoSuchElementException("Nenhum aluno encontrado")
    }

    private fun processarFilaCursosFeitosPorAlunos(idsFila: ArrayBlockingQueue<Long>): Map<String, Int> {
        val cursosFeitos = mutableMapOf<String, Int>()

        while (idsFila.isNotEmpty()) {
            val idAluno = idsFila.poll()
            val pontosPorCurso = pontuacaoService.recuperarPontosTotaisPorCurso(idAluno)
            pontosPorCurso.forEach { (_, cursoInfo) ->
                val nomeCurso = cursoInfo["nomeCurso"] as String
                val pontosTotais = cursoInfo["pontosTotais"] as Int
                if (pontosTotais > 0) {
                    cursosFeitos[nomeCurso] = cursosFeitos.getOrDefault(nomeCurso, 0) + 1
                }
            }
        }

        return cursosFeitos
    }
}
package techForAll.techPoints.service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import techForAll.techPoints.domain.Endereco
import techForAll.techPoints.dtos.CursoAlunosDto
import techForAll.techPoints.dtos.DemografiaDto
import techForAll.techPoints.dtos.UsuarioInput

import techForAll.techPoints.repository.AlunoRepository
import techForAll.techPoints.repository.DashboardAdmRepository
import techForAll.techPoints.repository.EnderecoRepository
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDate
import java.util.concurrent.ArrayBlockingQueue


@Service
class DashboardAdmService@Autowired constructor(
    private val dashAdmRepositoy: DashboardAdmRepository,
    private val enderecoRepository: EnderecoRepository,
    private val enderecoService: EnderecoService,
    private val alunoRepository: AlunoRepository,
    private val pontuacaoService: PontuacaoService,
    private val usuarioService: UsuarioService
){
    fun getAlunosPorCurso(): List<CursoAlunosDto> {
        val rankingPorCurso = pontuacaoService.recuperarRankingPorCurso()

        return rankingPorCurso.map { (cursoId, cursoData) ->
            val nomeCurso = cursoData["nomeCurso"] as String
            val ranking = cursoData["ranking"] as List<Map<String, Any>>
            val quantidadeAlunos = ranking.size

            CursoAlunosDto(
                nomeCurso = nomeCurso,
                quantidadeAlunos = quantidadeAlunos
            )
        }
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

    fun gerarRelatorioDemografiaAlunos(
        sexo: String?,
        etnia: String?,
        idadeMaxima: Int?,
        cidade: String?,
        escolaridade: String?
    ): String {
        val alunos = alunoRepository.findAlunosFiltrados(sexo, etnia, idadeMaxima, cidade, escolaridade)

        return gerarRelatorioAlunos(alunos)
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

    fun gerarRelatorioAlunos(ids: List<Long>): String {
        val csvHeader = listOf(
            "ID", "Nome de Usuário", "CPF", "Primeiro Nome", "Sobrenome", "Email", "Telefone", "Tipo Usuário", "Sexo",
            "Etnia", "Escolaridade", "Data de Nascimento", "Deletado", "CEP", "Rua", "Cidade", "Estado",
        )

        val csvRows = ids.map { id ->
            val alunoComCursos = comporAlunoComCursos(id)

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
                throw IllegalStateException("Endereço não pode ser nulo. Verifique o ID informado e tente novamente.")
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

    fun getAlunosPorTipoLista(tipoLista: String, idEmpresa: Long?): List<Any>{
        val ids = when (tipoLista) {
            "todos" -> if (idEmpresa != null)dashAdmRepositoy.findIdsTodosByEmpresa(idEmpresa) else dashAdmRepositoy.findIdsTodos()
            "contratados" -> if (idEmpresa != null) dashAdmRepositoy.findIdsContratadosByEmpresa(idEmpresa) else dashAdmRepositoy.findIdsContratados()
            "interessados" -> if (idEmpresa != null) dashAdmRepositoy.findIdsInteressadosByEmpresa(idEmpresa) else dashAdmRepositoy.findIdsInteressados()
            "processoSeletivo" -> if (idEmpresa != null) dashAdmRepositoy.findIdsProcessoSeletivoByEmpresa(idEmpresa) else dashAdmRepositoy.findIdsProcessoSeletivo()
            else -> throw IllegalArgumentException("Tipo de lista inválido")
        }
        return ids
    }

    fun processarIdsJson(idStrings: List<Any>): ArrayBlockingQueue<Long> {

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

    fun processarArquivoCsv(file: MultipartFile): Map<String, Any> {

        if (file.isEmpty || !file.originalFilename!!.endsWith(".csv")) {
            throw IllegalArgumentException("Arquivo inválido. Apenas arquivos CSV são permitidos.")
        }

        val bufferedReader = BufferedReader(InputStreamReader(file.inputStream, Charsets.UTF_8))
        val alunosCadastrados = mutableListOf<UsuarioInput>()
        val erros = mutableListOf<String>()

        bufferedReader.useLines { lines ->

            lines.drop(1).forEachIndexed { index, line ->
                try {
                    val data = line.split(",").map { it.trim() }

                    // Verifica se a linha tem o número correto de colunas
                    if (data.size == 16) {

                        val nomeUsuario = data[0]
                        val cpf = data[1]
                        val email = data[2]
                        val primeiroNome = data[3]
                        val sobrenome = data[4]
                        val telefone = data[5]
                        val senha = data[6]
                        val dtNasc = LocalDate.parse(data[7])
                        val escolaridade = data[8]
                        val sexo = data[9]
                        val etnia = data[10]

                        // Endereço
                        val cep = data[11]
                        val rua = data[12]
                        val numero = data[13]
                        val cidade = data[14]
                        val estado = data[15]

                        val enderecoCadastro = Endereco(
                            cep = cep,
                            rua = rua,
                            numero = numero,
                            cidade = cidade,
                            estado = estado
                        )
                        enderecoService.cadastrarEndereco(enderecoCadastro)

                        var endereco = enderecoRepository.findByCepAndNumero(cep, numero)

                        val alunoInput = UsuarioInput(
                            nomeUsuario = nomeUsuario,
                            cpf = cpf,
                            senha = senha,
                            primeiroNome = primeiroNome,
                            sobrenome = sobrenome,
                            email = email,
                            telefone = telefone,
                            tipoUsuario = 1,
                            autenticado = false,
                            enderecoId = endereco!!.id,
                            dtNasc = dtNasc,
                            escolaridade = escolaridade,
                            sexo = sexo,
                            etnia = etnia,
                            cnpj = null,
                            cargoUsuario = null
                        )

                        usuarioService.cadastrarUsuario(alunoInput)

                        alunosCadastrados.add(alunoInput)

                    } else {
                        erros.add("Linha ${index + 2}: Número de colunas incorreto")
                    }

                } catch (ex: Exception) {
                    erros.add("Linha ${index + 2}: ${ex.message}")
                }
            }
        }

        return mapOf(
            "sucesso" to alunosCadastrados.size,
            "erros" to erros
        )
    }


}
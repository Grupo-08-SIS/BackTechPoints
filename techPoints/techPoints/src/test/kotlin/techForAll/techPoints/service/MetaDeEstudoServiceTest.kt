package techForAll.techPoints.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import techForAll.techPoints.domain.Aluno
import techForAll.techPoints.domain.Endereco
import techForAll.techPoints.domain.MetaEstudoSemana
import techForAll.techPoints.repository.AlunoRepository
import techForAll.techPoints.repository.MetaEstudoSemanaRepository
import techForAll.techPoints.repository.TempoEstudoRepository
import techForAll.techPoints.repository.TempoSessaoRepository
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class)
class MetaDeEstudoServiceTest{

    @Mock
    private lateinit var metaSemanalRepository: MetaEstudoSemanaRepository

    @Mock
    private lateinit var alunoRepository: AlunoRepository

    @Mock
    private lateinit var sessaoRepository: TempoSessaoRepository

    @Mock
    private lateinit var metaDiariaRepository: TempoEstudoRepository

    @InjectMocks
    private lateinit var metaDeEstudoService: MetaDeEstudoService

    @Test
    fun `cadastrarMetaDiaria deve salvar uma nova MetaDiara quando não existir`() {
        val metaEstudoSemanaId = 1L
        val nomeDia = "segunda"
        val data = "2023-10-01"
        val qtdTempoEstudo = "2h"
        val ativado = true

        val aluno = Aluno(
            escolaridade = "Ensino Médio",
            sexo = "Masculino",
            etnia = "Branco",
            endereco = Endereco(
                cep = "12345678",
                rua = "Rua das Laranjeiras",
                numero = "123",
                cidade = "São Paulo",
                estado = "São Paulo"
            ),
            dtNasc = LocalDate.of(2000, 1, 1),
            nomeUsuario = "Juninho",
            cpf = "12345678901",
            senha = "senha",
            primeiroNome = "Junior",
            sobrenome = "Games",
            email = "juninho@email.com",
            telefone = "123456789",
            imagemPerfil = null,
            autenticado = true)
        val metaEstudoSemana = MetaEstudoSemana(id = metaEstudoSemanaId, aluno = aluno, diasAtivos = emptyList(), horasTotal = 0.0, tempoSessao = emptyList())

        `when`(metaSemanalRepository.findById(metaEstudoSemanaId)).thenReturn(Optional.of(metaEstudoSemana))
        `when`(metaDiariaRepository.findByMetaEstudoSemanaIdAndNomeDia(metaEstudoSemanaId, nomeDia)).thenReturn(null)

        val result = metaDeEstudoService.cadastrarMetaDiaria(metaEstudoSemanaId, nomeDia, data, qtdTempoEstudo, ativado)

        println(result)
        assertNotNull(result)
        assertEquals(nomeDia, result.nomeDia)
        assertEquals(data, result.data)
        assertEquals(qtdTempoEstudo, result.qtdTempoEstudo)
        assertEquals(ativado, result.ativado)
    }
}
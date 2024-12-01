package techForAll.techPoints.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import techForAll.techPoints.domain.Aluno
import techForAll.techPoints.domain.Endereco
import techForAll.techPoints.domain.MetaEstudoSemana
import techForAll.techPoints.domain.TempoEstudo
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
    fun `cadastrarMetaDiaria deve salvar nova MetaDiaria quando não existir`() {
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
            autenticado = true
        )
        val metaEstudoSemana = MetaEstudoSemana(id = metaEstudoSemanaId, aluno = aluno, diasAtivos = emptyList(), horasTotal = 0.0, tempoSessao = emptyList())

        `when`(metaSemanalRepository.findById(metaEstudoSemanaId)).thenReturn(Optional.of(metaEstudoSemana))
        `when`(metaDiariaRepository.findByMetaEstudoSemanaIdAndNomeDia(metaEstudoSemanaId, nomeDia)).thenReturn(null)

        val result = metaDeEstudoService.cadastrarMetaDiaria(metaEstudoSemanaId, nomeDia, data, qtdTempoEstudo, ativado)

        assertNotNull(result)
        assertEquals(nomeDia, result.nomeDia)
        assertEquals(data, result.data)
        assertEquals(qtdTempoEstudo, result.qtdTempoEstudo)
        assertEquals(ativado, result.ativado)
    }

    @Test
    fun `Cadastrar Meta Diaria deve salvar nova meta se não existir`() {
        val metaEstudoSemanaId = 1L
        val nomeDia = "segunda"
        val data = "2024-01-01"
        val qtdTempoEstudo = "2h"
        val ativado = true

        val aluno = Aluno(
            nomeUsuario = "aluno1",
            cpf = "12345678901",
            senha = "senha",
            primeiroNome = "Aluno",
            sobrenome = "Teste",
            email = "aluno1@escola.com",
            telefone = "123456789",
            imagemPerfil = null,
            sexo = "Masculino",
            dtNasc = LocalDate.of(2000, 1, 1),
            escolaridade = "Ensino Médio",
            etnia = "Branco",
            endereco = Endereco(
                id = 1L,
                rua = "Rua 1",
                numero = "123",
                cidade = "Cidade 1",
                estado = "Estado 1",
                cep = "12345678"
            ),
            autenticado = true
        )

        val metaEstudoSemana = MetaEstudoSemana(
            id = metaEstudoSemanaId,
            aluno = aluno,
            diasAtivos = emptyList(),
            horasTotal = 0.0,
            tempoSessao = emptyList()
        )

        `when`(metaSemanalRepository.findById(metaEstudoSemanaId)).thenReturn(Optional.of(metaEstudoSemana))
        `when`(metaDiariaRepository.findByMetaEstudoSemanaIdAndNomeDia(metaEstudoSemanaId, nomeDia)).thenReturn(null)

        val result = metaDeEstudoService.cadastrarMetaDiaria(
            metaEstudoSemanaId = metaEstudoSemanaId,
            nomeDia = nomeDia,
            data = data,
            qtdTempoEstudo = qtdTempoEstudo,
            ativado = ativado
        )

        assertEquals(nomeDia, result.nomeDia)
        assertEquals(data, result.data)
        assertEquals(qtdTempoEstudo, result.qtdTempoEstudo)
        assertEquals(ativado, result.ativado)

        verify(metaSemanalRepository, times(1)).findById(metaEstudoSemanaId)
        verify(metaDiariaRepository, times(1)).findByMetaEstudoSemanaIdAndNomeDia(metaEstudoSemanaId, nomeDia)
        verify(metaDiariaRepository, times(1)).save(any(TempoEstudo::class.java))
    }

    @Test
    fun `Cadastrar Meta Diaria deve atualizar meta existente`() {
        val metaEstudoSemanaId = 1L
        val nomeDia = "segunda"
        val data = "2024-01-01"
        val qtdTempoEstudo = "3h"
        val ativado = false

        val aluno = Aluno(
            nomeUsuario = "aluno1",
            cpf = "12345678901",
            senha = "senha",
            primeiroNome = "Aluno",
            sobrenome = "Teste",
            email = "aluno1@escola.com",
            telefone = "123456789",
            imagemPerfil = null,
            sexo = "Masculino",
            dtNasc = LocalDate.of(2000, 1, 1),
            escolaridade = "Ensino Médio",
            etnia = "Branco",
            endereco = Endereco(
                id = 1L,
                rua = "Rua 1",
                numero = "123",
                cidade = "Cidade 1",
                estado = "Estado 1",
                cep = "12345678"
            ),
            autenticado = true
        )

        val metaEstudoSemana = MetaEstudoSemana(
            id = metaEstudoSemanaId,
            aluno = aluno,
            diasAtivos = emptyList(),
            horasTotal = 0.0,
            tempoSessao = emptyList()
        )

        val metaDiariaExistente = TempoEstudo(
            id = 1L,
            nomeDia = nomeDia,
            data = "2024-01-01",
            qtdTempoEstudo = "2h",
            ativado = true,
            metaEstudoSemana = metaEstudoSemana
        )

        `when`(metaSemanalRepository.findById(metaEstudoSemanaId)).thenReturn(Optional.of(metaEstudoSemana))
        `when`(metaDiariaRepository.findByMetaEstudoSemanaIdAndNomeDia(metaEstudoSemanaId, nomeDia))
            .thenReturn(metaDiariaExistente)

        val result = metaDeEstudoService.cadastrarMetaDiaria(
            metaEstudoSemanaId = metaEstudoSemanaId,
            nomeDia = nomeDia,
            data = data,
            qtdTempoEstudo = qtdTempoEstudo,
            ativado = ativado
        )

        assertEquals(nomeDia, result.nomeDia)
        assertEquals(data, result.data)
        assertEquals(qtdTempoEstudo, result.qtdTempoEstudo)
        assertEquals(ativado, result.ativado)

        verify(metaSemanalRepository, times(1)).findById(metaEstudoSemanaId)
        verify(metaDiariaRepository, times(1)).findByMetaEstudoSemanaIdAndNomeDia(metaEstudoSemanaId, nomeDia)
        verify(metaDiariaRepository, times(1)).save(any(TempoEstudo::class.java))
    }

    @Test
    fun `obterMetaEstudoSemana deve retornar MetaEstudoSemanaDto`() {
        val metaEstudoSemanaId = 1L
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
            autenticado = true
        )
        val tempoEstudo = TempoEstudo(
            id = 1L,
            nomeDia = "segunda",
            data = "2023-10-01",
            qtdTempoEstudo = "2",
            ativado = true,
            metaEstudoSemana = MetaEstudoSemana(id = metaEstudoSemanaId, aluno = aluno, diasAtivos = emptyList(), horasTotal = 0.0, tempoSessao = emptyList())
        )
        val metaEstudoSemana = MetaEstudoSemana(
            id = metaEstudoSemanaId,
            aluno = aluno,
            diasAtivos = listOf(tempoEstudo),
            horasTotal = 2.0,
            tempoSessao = emptyList()
        )

        `when`(metaSemanalRepository.findById(metaEstudoSemanaId)).thenReturn(Optional.of(metaEstudoSemana))

        val result = metaDeEstudoService.obterMetaEstudoSemana(metaEstudoSemanaId)

        assertNotNull(result)
        assertEquals(metaEstudoSemanaId, result.id)
        assertEquals(aluno.id, result.alunoId)
        result.diasAtivos?.let { assertEquals(1, it.size) }
        assertEquals(2.0, result.horasTotal)
        result.sessoes?.let { assertTrue(it.isEmpty()) }
    }

    @Test
    fun `obterMetaEstudoSemana deve lançar exceção quando MetaEstudoSemana não for encontrada`() {
        val metaEstudoSemanaId = 1L

        `when`(metaSemanalRepository.findById(metaEstudoSemanaId)).thenReturn(Optional.empty())

        val exception = assertThrows<NoSuchElementException> {
            metaDeEstudoService.obterMetaEstudoSemana(metaEstudoSemanaId)
        }

        assertEquals("Meta de estudo semanal não encontrada", exception.message)
    }
}
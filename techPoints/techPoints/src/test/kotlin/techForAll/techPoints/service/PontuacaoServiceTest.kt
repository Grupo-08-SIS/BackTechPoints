package techForAll.techPoints.service

import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import techForAll.techPoints.domain.Aluno
import techForAll.techPoints.domain.Endereco
import techForAll.techPoints.repository.AlunoRepository
import techForAll.techPoints.repository.DadosEmpresaRepository
import techForAll.techPoints.repository.DashboardAdmRepository
import techForAll.techPoints.repository.PontuacaoRepository
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class)
class PontuacaoServiceTest{

    @Mock
    private lateinit var entityManager: EntityManager

    @Mock
    private lateinit var pontuacaoRepository: PontuacaoRepository

    @Mock
    private lateinit var alunoRepository: AlunoRepository

    @Mock
    private lateinit var dashboardAdmRepository: DashboardAdmRepository

    @Mock
    private lateinit var dadosEmpresaRepository: DadosEmpresaRepository

    @Mock
    private lateinit var usuarioService: UsuarioService

    @InjectMocks
    private lateinit var pontuacaoService: PontuacaoService


    @Test
    fun `Aluno Existe deve retornar um Aluno caso exista`() {
        val alunoId = 1L
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
        `when`(alunoRepository.findById(alunoId)).thenReturn(Optional.of(aluno))

        val result = pontuacaoService.alunoExiste(alunoId)

        assertEquals(aluno, result)
    }

}
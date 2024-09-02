package techForAll.techPoints.service

import techForAll.techPoints.domain.Endereco
import techForAll.techPoints.dominio.NomeTipoUsuario
import techForAll.techPoints.dominio.TipoUsuario
import techForAll.techPoints.domain.Usuario
import techForAll.techPoints.dto.UsuarioDTOInput
import techForAll.techPoints.repository.EnderecoRepository
import techForAll.techPoints.repository.TipoUsuarioRepository
import techForAll.techPoints.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime
import java.util.*

class UsuarioServiceTest {

    @Mock
    private lateinit var usuarioRepository: UsuarioRepository

    @Mock
    private lateinit var enderecoRepository: EnderecoRepository

    @Mock
    private lateinit var tipoUsuarioRepository: TipoUsuarioRepository

    @InjectMocks
    private lateinit var usuarioService: UsuarioService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test cadastrarUsuario quando email ja existe`() {
        val usuarioDTO = UsuarioDTOInput(
                idUsuario = null,
                nomeUsuario = "nomeUsuario",
                cpf = "12345678909",
                senha = "senha",
                primeiroNome = "primeiroNome",
                sobrenome = "sobrenome",
                email = "teste@gmail.com",
                autenticado = false,
                dataCriacao = LocalDateTime.now(),
                deletado = false,
                dataDeletado = null,
                dataAtualizacao = null,
                idEndereco = 1,
                idTipo = 1,
                telefone = "(00) 00000-0000"
        )

        `when`(usuarioRepository.existsByEmail(anyString())).thenReturn(true)

        val exception = assertThrows(IllegalArgumentException::class.java) {
            usuarioService.cadastrarUsuario(usuarioDTO)
        }

        assertEquals("Email já cadastrado", exception.message)
    }

    @Test
    fun `test cadastrarUsuario quando tipo e endereco existe`() {
        val usuarioDTO = UsuarioDTOInput(
                idUsuario = null,
                nomeUsuario = "nomeUsuario",
                cpf = "12345678909",
                senha = "senha",
                primeiroNome = "primeiroNome",
                sobrenome = "sobrenome",
                email = "teste@gmail.com",
                autenticado = false,
                dataCriacao = LocalDateTime.now(),
                deletado = false,
                dataDeletado = null,
                dataAtualizacao = null,
                idEndereco = 1,
                idTipo = 1,
                telefone = "(00) 00000-0000"
        )

        val tipoUsuario = TipoUsuario(1, NomeTipoUsuario.ADM)
        val endereco = Endereco(1, "12345678", "Cidade", "SP", "RuaLouca")

        `when`(usuarioRepository.existsByEmail(anyString())).thenReturn(false)
        `when`(tipoUsuarioRepository.findById(anyInt())).thenReturn(Optional.of(tipoUsuario))
        `when`(enderecoRepository.findById(anyInt())).thenReturn(Optional.of(endereco))
        `when`(usuarioRepository.save(any(Usuario::class.java))).thenReturn(
            Usuario(
                idUsuario = 1,
                nomeUsuario = "nomeUsuario",
                cpf = "12345678909",
                senha = "senha",
                primeiroNome = "primeiroNome",
                sobrenome = "sobrenome",
                email = "teste@gmail.com",
                autenticado = false,
                dataDeletado = null,
                imagemPerfil = null,
                endereco = endereco,
                tipoUsuario = tipoUsuario,
                telefone = "(00) 00000-0000"
        )
        )

        val resultado = usuarioService.cadastrarUsuario(usuarioDTO)

        assertEquals(usuarioDTO.nomeUsuario, resultado.nomeUsuario)
        assertEquals(usuarioDTO.email, resultado.email)
    }

    @Test
    fun `test softDeleteUsuario com credenciais validas`() {
        val email = "teste@gmail.com"
        val senha = "senha"
        val usuario = Usuario(
                idUsuario = 1,
                nomeUsuario = "nomeUsuario",
                cpf = "12345678909",
                senha = senha,
                primeiroNome = "primeiroNome",
                sobrenome = "sobrenome",
                email = email,
                autenticado = false,
                dataDeletado = null,
                imagemPerfil = null,
                endereco = mock(Endereco::class.java),
                tipoUsuario = mock(TipoUsuario::class.java),
                telefone = "(00) 00000-0000"
        )

        `when`(usuarioRepository.findByEmailAndSenha(anyString(), anyString())).thenReturn(usuario)
        `when`(usuarioRepository.save(any(Usuario::class.java))).thenReturn(usuario)

        usuarioService.softDeleteUsuario(email, senha)

        assertTrue(usuario.deletado)
        assertNotNull(usuario.dataDeletado)
        verify(usuarioRepository, times(1)).save(usuario)
    }

    @Test
    fun `test softDeleteUsuario com credenciais invalidas`() {
        val email = "teste@gmail.com"
        val senha = "senha"

        `when`(usuarioRepository.findByEmailAndSenha(anyString(), anyString())).thenReturn(null)

        val exception = assertThrows(IllegalArgumentException::class.java) {
            usuarioService.softDeleteUsuario(email, senha)
        }

        assertEquals("Credenciais inválidas", exception.message)
    }

    @Test
    fun `test hardDeleteUsuario com credenciais validas`() {
        val email = "teste@gmail.com.com"
        val senha = "senha"
        val usuario = Usuario(
                idUsuario = 1,
                nomeUsuario = "nomeUsuario",
                cpf = "12345678909",
                senha = senha,
                primeiroNome = "primeiroNome",
                sobrenome = "sobrenome",
                email = email,
                autenticado = false,
                dataDeletado = null,
                imagemPerfil = null,
                endereco = mock(Endereco::class.java),
                tipoUsuario = mock(TipoUsuario::class.java),
                telefone = "(00) 00000-0000"
        )

        `when`(usuarioRepository.findByEmailAndSenha(anyString(), anyString())).thenReturn(usuario)
        doNothing().`when`(usuarioRepository).delete(any(Usuario::class.java))

        usuarioService.hardDeleteUsuario(email, senha)

        verify(usuarioRepository, times(1)).deletar(usuario.idUsuario)
    }

    @Test
    fun `test hardDeleteUsuario com credenciais invalidas`() {
        val email = "teste@gmail.com"
        val senha = "senha"

        `when`(usuarioRepository.findByEmailAndSenha(email, senha)).thenReturn(null)

        val exception = assertThrows(NoSuchElementException::class.java) {
            usuarioService.hardDeleteUsuario(email, senha)
        }

        assertEquals("Usuário não encontrado", exception.message)
    }

    @Test
    fun `test listarUsuarios`() {
        val usuario = Usuario(
                idUsuario = 1,
                nomeUsuario = "nomeUsuario",
                cpf = "12345678909",
                senha = "senha",
                primeiroNome = "primeiroNome",
                sobrenome = "sobrenome",
                email = "teste@gmail.com",
                autenticado = false,
                dataDeletado = null,
                imagemPerfil = null,
                endereco = mock(Endereco::class.java),
                tipoUsuario = mock(TipoUsuario::class.java),
                telefone = "(00) 00000-0000"
        )

        `when`(usuarioRepository.findAll()).thenReturn(listOf(usuario))

        val usuarios = usuarioService.listarUsuarios()

        assertEquals(1, usuarios.size)
    }

    @Test
    fun `test buscarUsuarioPorId com id valido`() {
        val usuario = Usuario(
                idUsuario = 1,
                nomeUsuario = "nomeUsuario",
                cpf = "12345678909",
                senha = "senha",
                primeiroNome = "primeiroNome",
                sobrenome = "sobrenome",
                email = "teste@gmail.com",
                autenticado = false,
                dataDeletado = null,
                imagemPerfil = null,
                endereco = mock(Endereco::class.java),
                tipoUsuario = mock(TipoUsuario::class.java),
                telefone = "(00) 00000-0000"
        )

        `when`(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario))

        val resultado = usuarioService.buscarUsuarioPorId(1)

        assertEquals(usuario.idUsuario, resultado.idUsuario)
    }

    @Test
    fun `test buscarUsuarioPorId com id invalido`() {
        `when`(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty())

        val exception = assertThrows(NoSuchElementException::class.java) {
            usuarioService.buscarUsuarioPorId(1)
        }

        assertEquals("Usuário não encontrado", exception.message)
    }

    @Test
    fun `test loginUsuario com credenciais validas`() {
        val email = "teste@gmail.com"
        val senha = "senha"
        val usuario = Usuario(
                idUsuario = 1,
                nomeUsuario = "nomeUsuario",
                cpf = "12345678909",
                senha = senha,
                primeiroNome = "primeiroNome",
                sobrenome = "sobrenome",
                email = email,
                autenticado = false,
                dataDeletado = null,
                imagemPerfil = null,
                endereco = mock(Endereco::class.java),
                tipoUsuario = mock(TipoUsuario::class.java),
                telefone = "(00) 00000-0000"
        )

        `when`(usuarioRepository.findByEmail(anyString())).thenReturn(usuario)
        `when`(usuarioRepository.save(any(Usuario::class.java))).thenReturn(usuario)

        usuarioService.loginUsuario(email, senha)

        assertTrue(usuario.autenticado == true)
        verify(usuarioRepository, times(1)).save(usuario)
    }

    @Test
    fun `test loginUsuario com credenciais invalidas`() {
        val email = "teste@gmail.com"
        val senha = "senha"
        val usuario = Usuario(
                idUsuario = 1,
                nomeUsuario = "nomeUsuario",
                cpf = "12345678909",
                senha = "outraSenha",
                primeiroNome = "primeiroNome",
                sobrenome = "sobrenome",
                email = email,
                autenticado = false,
                dataDeletado = null,
                imagemPerfil = null,
                endereco = mock(Endereco::class.java),
                tipoUsuario = mock(TipoUsuario::class.java),
                telefone = "(00) 00000-0000"
        )

        `when`(usuarioRepository.findByEmail(anyString())).thenReturn(usuario)

        val exception = assertThrows(IllegalArgumentException::class.java) {
            usuarioService.loginUsuario(email, senha)
        }

        assertEquals("Credenciais inválidas", exception.message)
    }

    @Test
    fun `test logoffUsuario com id valido`() {
        val usuario = Usuario(
                idUsuario = 1,
                nomeUsuario = "nomeUsuario",
                cpf = "12345678909",
                senha = "senha",
                primeiroNome = "primeiroNome",
                sobrenome = "sobrenome",
                email = "teste@gmail.com",
                autenticado = true,
                dataDeletado = null,
                imagemPerfil = null,
                endereco = mock(Endereco::class.java),
                tipoUsuario = mock(TipoUsuario::class.java),
                telefone = "(00) 00000-0000"
        )

        `when`(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario))
        `when`(usuarioRepository.save(any(Usuario::class.java))).thenReturn(usuario)

        usuarioService.logoffUsuario(1)

        assertTrue(usuario.autenticado == false)
        verify(usuarioRepository, times(1)).save(usuario)
    }

    @Test
    fun `test logoffUsuario com id invalido`() {
        `when`(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty())

        val exception = assertThrows(NoSuchElementException::class.java) {
            usuarioService.logoffUsuario(1)
        }

        assertEquals("Usuário não encontrado", exception.message)
    }

    @Test
    fun `test buscarUsuarioPorEmail com email valido`() {
        val email = "teste@gmail.com"
        val usuario = Usuario(
                idUsuario = 1,
                nomeUsuario = "nomeUsuario",
                cpf = "12345678909",
                senha = "senha",
                primeiroNome = "primeiroNome",
                sobrenome = "sobrenome",
                email = email,
                autenticado = false,
                dataDeletado = null,
                imagemPerfil = null,
                endereco = mock(Endereco::class.java),
                tipoUsuario = mock(TipoUsuario::class.java),
                telefone = "(00) 00000-0000"
        )

        `when`(usuarioRepository.findByEmail(anyString())).thenReturn(usuario)

        val resultado = usuarioService.buscarUsuarioPorEmail(email)

        assertEquals(usuario.idUsuario, resultado.idUsuario)
    }

    @Test
    fun `test buscarUsuarioPorEmail com email invalido`() {
        `when`(usuarioRepository.findByEmail(anyString())).thenReturn(null)

        val exception = assertThrows(NoSuchElementException::class.java) {
            usuarioService.buscarUsuarioPorEmail("invalid@example.com")
        }

        assertEquals("Usuário não encontrado", exception.message)
    }

    @Test
    fun `test atualizarUsuario com id valido`() {
        val usuario = Usuario(
                idUsuario = 1,
                nomeUsuario = "nomeUsuario",
                cpf = "12345678909",
                senha = "senha",
                primeiroNome = "primeiroNome",
                sobrenome = "sobrenome",
                email = "teste@gmail.com",
                autenticado = false,
                dataDeletado = null,
                imagemPerfil = null,
                endereco = mock(Endereco::class.java),
                tipoUsuario = mock(TipoUsuario::class.java),
                telefone = "(00) 00000-0000"
        )

        `when`(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario))
        `when`(usuarioRepository.save(any(Usuario::class.java))).thenReturn(usuario)

        val atualizacao = mapOf(
                "nomeUsuario" to "novoNomeUsuario",
                "cpf" to "98765432100"
        )

        val resultado = usuarioService.atualizarUsuario(1, atualizacao)

        assertEquals("novoNomeUsuario", resultado.nomeUsuario)
        assertEquals("98765432100", resultado.cpf)
    }

    @Test
    fun `test atualizarUsuario com id invalido`() {
        `when`(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty())

        val atualizacao = mapOf(
                "nomeUsuario" to "novoNomeUsuario",
                "cpf" to "98765432100"
        )

        val exception = assertThrows(NoSuchElementException::class.java) {
            usuarioService.atualizarUsuario(1, atualizacao)
        }

        assertEquals("Usuário não encontrado", exception.message)
    }

    @Test
    fun `test atualizarImagemUsuario com id e imagem valida`() {
        val usuario = Usuario(
                idUsuario = 1,
                nomeUsuario = "nomeUsuario",
                cpf = "12345678909",
                senha = "senha",
                primeiroNome = "primeiroNome",
                sobrenome = "sobrenome",
                email = "teste@gmail.com",
                autenticado = false,
                dataDeletado = null,
                imagemPerfil = null,
                endereco = mock(Endereco::class.java),
                tipoUsuario = mock(TipoUsuario::class.java),
                telefone = "(00) 00000-0000"
        )

        val novaFoto = byteArrayOf(1, 2, 3)

        `when`(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario))
        `when`(usuarioRepository.save(any(Usuario::class.java))).thenReturn(usuario)

        usuarioService.atualizarImagemUsuario(1, novaFoto)

        assertArrayEquals(novaFoto, usuario.imagemPerfil)
        verify(usuarioRepository, times(1)).save(usuario)
    }

    @Test
    fun `test atualizarImagemUsuario com imagem vazia`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            usuarioService.atualizarImagemUsuario(1, byteArrayOf())
        }

        assertEquals("Requisição inválida", exception.message)
    }

    @Test
    fun `test obterImagemPerfil com id valido`() {
        val usuario = Usuario(
                idUsuario = 1,
                nomeUsuario = "nomeUsuario",
                cpf = "12345678909",
                senha = "senha",
                primeiroNome = "primeiroNome",
                sobrenome = "sobrenome",
                email = "teste@gmail.com",
                autenticado = false,
                dataDeletado = null,
                imagemPerfil = byteArrayOf(1, 2, 3),
                endereco = mock(Endereco::class.java),
                tipoUsuario = mock(TipoUsuario::class.java),
                telefone = "(00) 00000-0000"
        )

        `when`(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario))

        val imagemPerfil = usuarioService.obterImagemPerfil(1)

        assertArrayEquals(byteArrayOf(1, 2, 3), imagemPerfil)
    }

    @Test
    fun `test obterImagemPerfil com id valido e sem imagem`() {
        val usuario = Usuario(
                idUsuario = 1,
                nomeUsuario = "nomeUsuario",
                cpf = "12345678909",
                senha = "senha",
                primeiroNome = "primeiroNome",
                sobrenome = "sobrenome",
                email = "teste@gmail.com",
                autenticado = false,
                dataDeletado = null,
                imagemPerfil = null,
                endereco = mock(Endereco::class.java),
                tipoUsuario = mock(TipoUsuario::class.java),
                telefone = "(00) 00000-0000"
        )

        `when`(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario))

        val exception = assertThrows(NoSuchElementException::class.java) {
            usuarioService.obterImagemPerfil(1)
        }

        assertEquals("Imagem de perfil não encontrada", exception.message)
    }

    @Test
    fun `test obterImagemPerfil com id invalido`() {
        `when`(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty())

        val exception = assertThrows(NoSuchElementException::class.java) {
            usuarioService.obterImagemPerfil(1)
        }

        assertEquals("Usuário não encontrado", exception.message)
    }
}


    package TechForAll.techPoints.service

    import TechForAll.techPoints.dominio.RedefinicaoSenha
    import TechForAll.techPoints.dominio.Usuario
    import TechForAll.techPoints.repository.RedefinicaoSenhaRepository
    import TechForAll.techPoints.repository.UsuarioRepository
    import org.junit.jupiter.api.Assertions.*
    import org.junit.jupiter.api.BeforeEach
    import org.junit.jupiter.api.Test
    import org.mockito.ArgumentMatchers.any
    import org.mockito.ArgumentMatchers.anyString
    import org.mockito.InjectMocks
    import org.mockito.Mock
    import org.mockito.Mockito.*
    import org.mockito.MockitoAnnotations
    import org.springframework.http.ResponseEntity
    import org.springframework.mail.SimpleMailMessage
    import org.springframework.mail.javamail.JavaMailSender
    import java.time.LocalDateTime
    import java.util.*

    class ResetSenhaServiceTest {

        @Mock
        private lateinit var emailSender: JavaMailSender

        @Mock
        private lateinit var redefinicaoSenhaRepository: RedefinicaoSenhaRepository

        @Mock
        private lateinit var usuarioRepository: UsuarioRepository

        @InjectMocks
        private lateinit var resetSenhaService: ResetSenhaService

        @BeforeEach
        fun setUp() {
            MockitoAnnotations.openMocks(this)
        }

        @Test
        fun `test senhaReset quando nao existe redefinição de senha ativa`() {
            val emailUser = "teste@gmail.com"
            val usuario = mock(Usuario::class.java)

            `when`(redefinicaoSenhaRepository.findByEmailAndValidoAndDataExpiracaoAfter(anyString(), any()))
            `when`(usuarioRepository.findByEmail(anyString())).thenReturn(usuario)
            doNothing().`when`(emailSender).send(any(SimpleMailMessage::class.java))

            val response = resetSenhaService.senhaReset(emailUser)
            assertEquals(200, response.statusCodeValue)
        }//falta esse


        @Test
        fun `test senhaReset quando existe redefinição de senha ativa`() {
            val emailUser = "teste@gmail.com"
            val redefinicaoSenha = mock(RedefinicaoSenha::class.java)

            `when`(redefinicaoSenhaRepository.findByEmailAndValidoAndDataExpiracaoAfter(anyString(), any())).thenReturn(listOf(redefinicaoSenha))

            val response = resetSenhaService.senhaReset(emailUser)
            assertEquals(409, response.statusCodeValue)
            assertTrue(response.body.toString().contains("Uma troca de senha já está em andamento para este usuário."))
        } //falta esse


        @Test
        fun `test enviarEmailRecuperacaoSenha`() {
            val email = "teste@gmail.com"
            val token = "12345678"

            doNothing().`when`(emailSender).send(any(SimpleMailMessage::class.java))

            resetSenhaService.enviarEmailRecuperacaoSenha(email, token)

            verify(emailSender, times(1)).send(any(SimpleMailMessage::class.java))
        }

        @Test
        fun `test gerarResetCode`() {
            val resetCode = resetSenhaService.gerarResetCode()
            assertEquals(8, resetCode.length)
        }

        @Test
        fun `test calcularValidade`() {
            val validade = resetSenhaService.calcularValidade()
            assertTrue(validade.isAfter(LocalDateTime.now()))
            assertTrue(validade.isBefore(LocalDateTime.now().plusDays(1).plusMinutes(1)))
        }

        @Test
        fun `test verificarToken quando o token é valido`() {
            val codigoRedefinicao = "12345678"
            val emailUser = "teste@gmail.com"

            `when`(redefinicaoSenhaRepository.existsByTokenAndEmailAndValidoAndDataExpiracaoAfter(anyString(), anyString())).thenReturn(true)

            val response = resetSenhaService.verificarToken(codigoRedefinicao, emailUser)
            assertEquals(200, response.statusCodeValue)
        }

        @Test
        fun `test verificarToken quando o token é invalido`() {
            val codigoRedefinicao = "12345678"
            val emailUser = "teste@gmail.com"

            `when`(redefinicaoSenhaRepository.existsByTokenAndEmailAndValidoAndDataExpiracaoAfter(anyString(), anyString())).thenReturn(false)

            val response = resetSenhaService.verificarToken(codigoRedefinicao, emailUser)
            assertEquals(400, response.statusCodeValue)
            assertTrue(response.body.toString().contains("Token ou email inválido"))
        }

        @Test
        fun `test atualizarSenha quando o usuario existir`() {
            val emailUser = "teste@gmail.com"
            val novaSenha = "novaSenha"
            val usuario = mock(Usuario::class.java)

            `when`(usuarioRepository.findByEmail(anyString())).thenReturn(usuario)
            `when`(usuarioRepository.save(any())).thenReturn(usuario)

            val response = resetSenhaService.atualizarSenha(emailUser, novaSenha)
            assertEquals(200, response.statusCodeValue)
        }

        @Test
        fun `test atualizarSenha quando o usuario nao existir`() {
            val emailUser = "teste@gmail.com"
            val novaSenha = "novaSenha"

            `when`(usuarioRepository.findByEmail(anyString())).thenReturn(null)

            val response = resetSenhaService.atualizarSenha(emailUser, novaSenha)
            assertEquals(404, response.statusCodeValue)
            assertTrue(response.body.toString().contains("Usuário não encontrado"))
        }
    }


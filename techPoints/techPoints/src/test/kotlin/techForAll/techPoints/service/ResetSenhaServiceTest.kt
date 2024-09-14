
    package techForAll.techPoints.service

    import techForAll.techPoints.domain.Usuario
    import techForAll.techPoints.repository.RedefinicaoSenhaRepository
    import techForAll.techPoints.repository.UsuarioRepository
    import org.junit.jupiter.api.Assertions.*
    import org.junit.jupiter.api.BeforeEach
    import org.junit.jupiter.api.Test
    import org.mockito.ArgumentMatchers.any
    import org.mockito.ArgumentMatchers.anyString
    import org.mockito.InjectMocks
    import org.mockito.Mock
    import org.mockito.Mockito.*
    import org.mockito.MockitoAnnotations.openMocks
    import org.springframework.mail.SimpleMailMessage
    import org.springframework.mail.javamail.JavaMailSender
    import java.time.LocalDateTime
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
            openMocks(this)
        }

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


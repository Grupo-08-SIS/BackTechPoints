package TechForAll.techPoints.controller

import TechForAll.techPoints.repository.RedefinicaoSenhaRepository
import TechForAll.techPoints.repository.UsuarioRepository
import TechForAll.techPoints.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/reset-senha")
class ResetSenhaController (private var usuarioService: UsuarioService)
{
    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    lateinit var senhaRepository: RedefinicaoSenhaRepository

    //alterar para request body depois, RequestParam é altamente inseguro
    @PostMapping("/solicitar-troca")
    fun solicitarTrocaSenha(@RequestParam("email") emailUser: String): ResponseEntity<Any> {
        return usuarioService.senhaReset(emailUser)
    }

    @PostMapping("/verificar-token")
    fun verificarToken(@RequestParam("codigo") codigoRedefinicao: String, @RequestParam("email") emailUser: String): ResponseEntity<Any> {
        val existeRedefinicao = senhaRepository.existsByTokenAndEmailAndValidoAndDataExpiracaoAfter(codigoRedefinicao, emailUser)
        return if (existeRedefinicao == true) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.status(400).body("Não há uma redefinição de senha válida para o email e token fornecidos")
        }
    } //voltando o "ok" o front redireciona para uma pagina com a possibilidade de alterar a senha

    @PatchMapping("/nova-senha")
    fun patch(@RequestParam("email") emailUser: String, @RequestBody novaSenha: String): ResponseEntity<Any> {
            val usuario = usuarioRepository.findByEmail(emailUser)
            usuario.senha = novaSenha.toString()
            usuario.dataAtualizacao = LocalDateTime.now()
            usuarioRepository.save(usuario)
            return ResponseEntity.status(200).build()
    }
}
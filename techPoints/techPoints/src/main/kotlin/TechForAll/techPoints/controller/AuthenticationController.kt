package TechForAll.techPoints.controller

import TechForAll.techPoints.dominio.AuthenticationDTO
import TechForAll.techPoints.dominio.Usuario
import TechForAll.techPoints.repository.UsuarioRepository
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("auth")
class AuthenticationController {
    @Autowired
    lateinit var repository: UsuarioRepository

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var senhaEncoder: BCryptPasswordEncoder // Corrigido o nome do bean

    @PostMapping("/login")
    fun login(@RequestBody @Valid novoLogin: AuthenticationDTO): ResponseEntity<Any> {
        val emailSenha = UsernamePasswordAuthenticationToken(novoLogin.email, novoLogin.senha)
        val auth = authenticationManager.authenticate(emailSenha)
        // Implementar a lógica para gerar um token JWT usando tokenService
        return ResponseEntity.ok<Any>("Login bem-sucedido")
    }

    @PostMapping("/cadastro")
    fun post(@RequestBody @Valid novoUsuario: Usuario): ResponseEntity<Any> {
        if (repository.findByEmail(novoUsuario.email) != null) return ResponseEntity.status(400).build()
        val encryptedPassword = senhaEncoder.encode(novoUsuario.senha)
        novoUsuario.senha = encryptedPassword // Atualizando a senha criptografada
        repository.save(novoUsuario)
        return ResponseEntity.status(201).build()
    }

}
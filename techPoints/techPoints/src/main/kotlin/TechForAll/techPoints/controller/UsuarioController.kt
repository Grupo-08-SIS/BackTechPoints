package TechForAll.techPoints.controller

import TechForAll.techPoints.dominio.Usuario
import TechForAll.techPoints.dominio.UsuarioDTO
import TechForAll.techPoints.repository.UsuarioRepository
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController {
    @Autowired
    lateinit var repository: UsuarioRepository

    @PostMapping("/cadastro")
    fun post(@RequestBody @Valid novoUsuario: Usuario): ResponseEntity<UsuarioDTO> {
        repository.save(novoUsuario)
        val novoUsuarioDTO = usuarioParaDTO(novoUsuario)
        return ResponseEntity.status(201).body(novoUsuarioDTO)
    }

    @GetMapping("/listar")
    fun listar(): ResponseEntity<List<UsuarioDTO>> {
        val usuarios = repository.findAll()
        val usuariosDTO = usuarios.map { usuarioParaDTO(it) }
        return if (usuariosDTO.isNotEmpty()) {
            ResponseEntity.status(200).body(usuariosDTO)
        } else {
            ResponseEntity.status(204).build()
        }
    }

    @GetMapping("/buscar/{idUsuario}")
    fun get(@PathVariable idUsuario: Int): ResponseEntity<UsuarioDTO> {
        if (repository.existsById(idUsuario)) {
            val usuario = repository.findById(idUsuario).get()
            val usuarioDTO = usuarioParaDTO(usuario)
            return ResponseEntity.status(200).body(usuarioDTO)
        }
        return ResponseEntity.status(404).build()
    }


    @PostMapping("/login")
    fun login(@RequestBody loginData: Map<String, String>): ResponseEntity<Any> {
        val email = loginData["email"]
        val senha = loginData["senha"]

        if (email != null && senha != null) {
            val user = repository.findByEmail(email)
            if (user != null && senha == user.senha) {
                return ResponseEntity.status(200).build()
            }
        }
        return ResponseEntity.status(401).build()
    } //falta fazer um login bom com autenticação

    @PatchMapping(value = ["/atualizar-imagem/{idUsuario}"],
        consumes = ["image/jpeg", "image/png", "image/jpg"])
    fun patchImagem(
        @PathVariable idUsuario: Int,
        @RequestBody novaFoto: ByteArray
    ):ResponseEntity<Void> {
        val usuario = repository.findById(idUsuario).get()
        usuario.imagemPerfil = novaFoto
        repository.save(usuario)

        return ResponseEntity.status(204).build()
    }

    fun usuarioParaDTO(usuario: Usuario): UsuarioDTO {
        return UsuarioDTO(
            idUsuario = usuario.idUsuario,
            nomeUsuario = usuario.nomeUsuario,
            cpf = usuario.cpf,
            primeiroNome = usuario.primeiroNome,
            sobrenome = usuario.sobrenome,
            email = usuario.email,
            dataCriacao = usuario.dataCriacao,
            deletado = usuario.deletado,
            dataDeletado = usuario.dataDeletado,
            dataAtualizacao = usuario.dataAtualizacao,
            endereco = usuario.endereco
        )
    }

}
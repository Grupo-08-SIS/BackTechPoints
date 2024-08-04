package tech4all.techpoints.application.controller.v1.impl

import tech4all.techpoints.application.controller.v1.EnderecoController
import tech4all.techpoints.domain.service.EnderecoService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech4all.techpoints.domain.model.Endereco
import tech4all.techpoints.domain.dto.EnderecoDTO

@RestController
@RequestMapping("/v1/enderecos")
@Tag(name = "Endereços", description = "Gerenciamento de endereços")
class EnderecoControllerImpl(
    private val service: EnderecoService
) : EnderecoController {

    override fun post(novoEndereco: Endereco): ResponseEntity<Endereco> {
        val enderecoCadastrado = service.cadastrarEndereco(novoEndereco)
        return ResponseEntity.status(201).body(enderecoCadastrado)
    }

    override fun listar(): ResponseEntity<List<Endereco>> {
        val enderecos = service.listarEnderecos()
        return if (enderecos.isNotEmpty()) {
            ResponseEntity.ok(enderecos)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    override fun buscar(idEndereco: Int): ResponseEntity<Endereco> {
        val enderecoEncontrado = service.buscarEnderecoPorId(idEndereco)
        return ResponseEntity.ok(enderecoEncontrado)
    }

    override fun patch(idEndereco: Int, enderecoDTO: EnderecoDTO): ResponseEntity<Endereco> {
        val enderecoAtualizado = service.atualizarEndereco(idEndereco, enderecoDTO)
        return ResponseEntity.ok(enderecoAtualizado)
    }
}

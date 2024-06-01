package TechForAll.techPoints.controller

import TechForAll.techPoints.dominio.Endereco
import TechForAll.techPoints.dto.EnderecoDTO
import TechForAll.techPoints.service.EnderecoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/enderecos")
class EnderecoController {

    @Autowired
    lateinit var service: EnderecoService

    @Operation(summary = "Cadastrar um novo endereço", description = "Retorna o ID do endereço cadastrado")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso"),
            ApiResponse(responseCode = "400", description = "Requisição inválida")
        ]
    )
    @PostMapping("/cadastro")
    fun post(@RequestBody @Valid novoEndereco: Endereco): ResponseEntity<Any> {
        val enderecoCadastrado = service.cadastrarEndereco(novoEndereco)
        return ResponseEntity.status(201).body(enderecoCadastrado)
    }

    @Operation(summary = "Listar todos os endereços", description = "Retorna uma lista de todos os endereços cadastrados")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso"),
            ApiResponse(responseCode = "204", description = "Nenhum endereço encontrado")
        ]
    )
    @GetMapping("/listar")
    fun listar(): ResponseEntity<List<Endereco>> {
        val enderecos = service.listarEnderecos()
        return if (enderecos.isNotEmpty()) {
            ResponseEntity.status(200).body(enderecos)
        } else {
            ResponseEntity.status(204).build()
        }
    }

    @Operation(summary = "Buscar endereço pelo ID", description = "Retorna o endereço correspondente ao ID fornecido")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso"),
            ApiResponse(responseCode = "404", description = "Endereço não encontrado")
        ]
    )
    @GetMapping("/buscar/{idEndereco}")
    fun get(@PathVariable idEndereco: Int): ResponseEntity<Endereco> {
        val enderecoEncontrado = service.buscarEnderecoPorId(idEndereco)
        return if (enderecoEncontrado.isPresent) {
            ResponseEntity.status(200).body(enderecoEncontrado.get())
        } else {
            ResponseEntity.status(404).build()
        }
    }

    @Operation(summary = "Atualizar endereço pelo ID", description = "Retorna o endereço atualizado")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
            ApiResponse(responseCode = "404", description = "Endereço não encontrado")
        ]
    )
    @PatchMapping("/atualizar/{idEndereco}")
    fun patch(@PathVariable idEndereco: Int, @RequestBody enderecoDTO: EnderecoDTO): ResponseEntity<Endereco> {
        val enderecoAtualizado = service.atualizarEndereco(idEndereco, enderecoDTO)
        return if (enderecoAtualizado.isPresent) {
            ResponseEntity.status(200).body(enderecoAtualizado.get())
        } else {
            ResponseEntity.status(404).build()
        }
    }
}

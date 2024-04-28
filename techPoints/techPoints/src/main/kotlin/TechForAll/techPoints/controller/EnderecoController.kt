package TechForAll.techPoints.controller

import TechForAll.techPoints.dominio.Endereco
import TechForAll.techPoints.repository.EnderecoRepository
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
    lateinit var repository: EnderecoRepository

    @Operation(summary = "Cadastrar um novo endereço", description = "Retorna o ID do endereço cadastrado")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso"),
            ApiResponse(responseCode = "400", description = "Requisição inválida")
        ]
    )
    @PostMapping("/cadastro")
    fun post(@RequestBody @Valid novoEndereco: Endereco): ResponseEntity<Int> {
        repository.save(novoEndereco)
        return ResponseEntity.status(201).body(novoEndereco.id)
    } // retorna o id do endereço para o javascript mandar este id no body da criação do usuario


    @Operation(summary = "Listar todos os endereços", description = "Retorna uma lista de todos os endereços cadastrados")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso"),
            ApiResponse(responseCode = "204", description = "Nenhum endereço encontrado")
        ]
    )
    @GetMapping("/listar")
    fun listar(): ResponseEntity<List<Endereco>> {
        val enderecos = repository.findAll()
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
        if (repository.existsById(idEndereco)) {
            val enderecoEncontrado = repository.findById(idEndereco).get()
            return ResponseEntity.status(200).body(enderecoEncontrado)
        }
        return ResponseEntity.status(404).build()
    }


    @Operation(summary = "Atualizar endereço pelo ID", description = "Retorna o endereço atualizado")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
            ApiResponse(responseCode = "404", description = "Endereço não encontrado")
        ]
    )
    @PutMapping("/atualizar/{idEndereco}")
    fun put(@PathVariable idEndereco: Int,@RequestBody novoEndereco: Endereco): ResponseEntity<Endereco> {
        if (repository.existsById(idEndereco)) {
            novoEndereco.id = idEndereco
            repository.save(novoEndereco)
            return ResponseEntity.status(200).body(novoEndereco)
        }
        return ResponseEntity.status(404).build()
    }

}
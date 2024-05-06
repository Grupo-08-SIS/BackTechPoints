package TechForAll.techPoints.controller

import TechForAll.techPoints.dominio.TipoPonto
import TechForAll.techPoints.repository.TipoPontoRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tipoPonto")
class TipoPontoController {
    @Autowired
    lateinit var repository: TipoPontoRepository


    @Operation(summary = "Cadastrar um novo tipo de ponto", description = "Retorna o ID do tipo do ponto cadastrado")
    @ApiResponses(
            value = [
                ApiResponse(responseCode = "201", description = "Tipo do Ponto cadastrado com sucesso"),
                ApiResponse(responseCode = "400", description = "Requisição inválida")
            ]
    )
    @PostMapping("/cadastro")
    fun post(@RequestBody @Valid novoTipoPonto: TipoPonto): ResponseEntity<Int> {
        repository.save(novoTipoPonto)
        return ResponseEntity.status(201).body(novoTipoPonto.idTipoPonto)
    }

    @Operation(summary = "Listar todos os tipos de pontos",
            description = "Retorna uma lista de todos os tipos de pontos cadastrados")
    @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "Lista de tipo de pontos retornada com sucesso"),
                ApiResponse(responseCode = "204", description = "Nenhum endereço encontrado")
            ]
    )
    @GetMapping("/listar")
    fun listar(): ResponseEntity<List<TipoPonto>> {
        val tipoPonto = repository.findAll()
        return if (tipoPonto.isNotEmpty()) {
            ResponseEntity.status(200).body(tipoPonto)
        } else {
            ResponseEntity.status(204).build()
        }
    }
}
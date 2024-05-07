package TechForAll.techPoints.controller

import TechForAll.techPoints.dominio.TipoPonto
import TechForAll.techPoints.dominio.Usuario
import TechForAll.techPoints.dominio.UsuarioDTO
import TechForAll.techPoints.repository.TipoPontoRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

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
    fun post(@RequestBody @Valid novoTipoPonto: TipoPonto): ResponseEntity<Any> {
        repository.save(novoTipoPonto)
        return ResponseEntity.status(201).body(novoTipoPonto)
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

    @Operation(summary = "Atualizar um tipo de ponto existente",
        description = "Atualiza os detalhes de um tipo de ponto existente")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Tipo de ponto atualizado com sucesso"),
            ApiResponse(responseCode = "404", description = "Tipo de ponto não encontrado")
        ]
    )
    @PatchMapping("/atualizar/{idPonto}")
    fun patchUsuario(
        @PathVariable idPonto: Int,
        @RequestBody atualizarPonto: TipoPonto
    ): ResponseEntity<TipoPonto> {
        if (repository.existsById(idPonto)) {
            val tipoPontoExistente = repository.findById(idPonto).get()

            atualizarPonto.nome?.let { tipoPontoExistente.nome = it }
            atualizarPonto.fkCurso?.let { tipoPontoExistente.fkCurso = it }

            val pontoAtualizado = repository.save(tipoPontoExistente)

            return ResponseEntity.status(200).body(pontoAtualizado)
        }
        return ResponseEntity.status(404).build()
    }

    @Operation(summary = "Desativar um tipo de ponto existente",
        description = "Desativa um tipo de ponto existente")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Tipo de ponto desativado com sucesso"),
            ApiResponse(responseCode = "400", description = "Tipo de ponto não encontrado")
        ]
    )
    @PatchMapping("/deletar/{idPonto}")
    fun softDelete(@PathVariable idPonto: Int): ResponseEntity<Any> {
        if (repository.existsById(idPonto)) {
            var tipoPonto = repository.findById(idPonto).get()
            tipoPonto.valido = false
            repository.save(tipoPonto)
            return ResponseEntity.status(200).build()
        }
        return ResponseEntity.status(400).build()
    }

}



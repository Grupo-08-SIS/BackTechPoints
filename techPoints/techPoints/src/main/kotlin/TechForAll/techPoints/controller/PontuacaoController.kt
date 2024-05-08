package TechForAll.techPoints.controller
import TechForAll.techPoints.dominio.Pontuacao
import TechForAll.techPoints.repository.PontuacaoRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/pontuacao")
class PontuacaoController (private val pontuacaoRepository: PontuacaoRepository) {

    @Operation(summary = "Listar todas as pontuações",
        description = "Retorna uma lista de todas as pontuações cadastradas")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Lista de pontuações retornada com sucesso"),
            ApiResponse(responseCode = "204", description = "Nenhuma pontuação encontrada")
        ]
    )
    @GetMapping("/listar")
    fun listar(): ResponseEntity<List<Pontuacao>> {
        val pontuacao = pontuacaoRepository.findAll()
        return if (pontuacao.isNotEmpty()) {
            ResponseEntity.status(200).body(pontuacao)
        } else {
            ResponseEntity.status(204).build()
        }
    }

    @Operation(summary = "Buscar pontuação do usuário",
        description = "Retorna a pontuação total de um usuário específico")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Pontuação do usuário retornada com sucesso"),
            ApiResponse(responseCode = "404", description = "Usuário não encontrado ou sem pontuação registrada")
        ]
    )
    @GetMapping("/buscar/{fkUsuario}")
    fun buscarPontuacaoDoUsuario(@PathVariable fkUsuario: Int): ResponseEntity<Any> {
        val pontuacao = pontuacaoRepository.obterTotalDePontos(fkUsuario)
        return if (pontuacao != null) {
            ResponseEntity.status(200).body(pontuacao)
        } else {
            ResponseEntity.status(404).build()
        }
    }

    @Operation(summary = "Atualizar pontuação do usuário",
        description = "Atualiza a pontuação de um usuário existente")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Pontuação do usuário atualizada com sucesso"),
            ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        ]
    )
    @PutMapping("/atualizar/{fkUsuario}")
    fun atualizar(@PathVariable fkUsuario: Int, @RequestBody pontuacao: Pontuacao): ResponseEntity<Any> {
        if (!pontuacaoRepository.existsById(fkUsuario)) {
            return ResponseEntity.status(404).build()
        }
        var updatedPontuacao = pontuacao.copy(idPontuacao = fkUsuario)
        updatedPontuacao.dataAtualizacao = LocalDateTime.now()
        pontuacaoRepository.save(updatedPontuacao)
        return ResponseEntity.status(200).body(updatedPontuacao)
    }

}
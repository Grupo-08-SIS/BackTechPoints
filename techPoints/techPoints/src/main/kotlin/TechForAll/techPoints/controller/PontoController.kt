import TechForAll.techPoints.dominio.*
import TechForAll.techPoints.repository.PontoRepository
import TechForAll.techPoints.repository.PontuacaoRepository
import TechForAll.techPoints.repository.TipoPontoRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pontos")
class PontoController {
    @Autowired
    lateinit var repository: PontoRepository
    lateinit var repository_tipo: TipoPontoRepository
    lateinit var pontuacaoRepository: PontuacaoRepository

    @Operation(summary = "Buscar total de pontos",
        description = "Retorna o total de pontos de uma pontuação específica associada ao usuário.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Total de pontos obtido com sucesso"),
        ApiResponse(responseCode = "404", description = "Pontuação não encontrada")
    ])
    @GetMapping("/{id}/pontuacao")
    fun buscarPontuacaoDoUsuario(@PathVariable id: Int): ResponseEntity<Int> {
        val pontos = pontuacaoRepository.obterTotalDePontos(id)
        return if (pontos != null) {
            ResponseEntity.status(200).body(pontos)
        } else {
            ResponseEntity.status(404).build()
        }
    }
    @Operation(summary = "Cadastrar um novo ponto", description = "Retorna o ID do ponto cadastrado")
    @ApiResponses(
            value = [
                ApiResponse(responseCode = "201", description = "Ponto cadastrado com sucesso"),
                ApiResponse(responseCode = "400", description = "Requisição inválida")
            ]
    )
    @PostMapping("/obter-pontos")
    fun post(@RequestBody @Valid ponto: Ponto): ResponseEntity<PontoDTO> {
        repository.save(ponto)
        val pontoDTO = pontoParaDTO(ponto)
        return ResponseEntity.status(201).body(pontoDTO)
    }

    @Operation(summary = "Listar todos os pontos",
            description = "Retorna uma lista de todos os pontos cadastrados")
    @ApiResponses(
            value = [
                ApiResponse(responseCode = "200", description = "Lista de pontos retornada com sucesso"),
                ApiResponse(responseCode = "204", description = "Nenhum ponto encontrado")
            ]
    )
    @GetMapping("/listar")
    fun listar(): ResponseEntity<List<Ponto>> {
        val pontos = repository.findAll()
        return if (pontos.isNotEmpty()) {
            ResponseEntity.status(200).body(pontos)
        } else {
            ResponseEntity.status(204).build()
        }
    }

    fun pontoParaDTO(ponto: Ponto): PontoDTO {
        return PontoDTO(
                ponto.idPonto,
                ponto.qtdPonto,
                ponto.tipoPonto
        )
    }

}


package TechForAll.techPoints.controller

import TechForAll.techPoints.dto.DadoEmpresaDTO
import TechForAll.techPoints.dto.DadosEmpresaDTOAtt
import TechForAll.techPoints.service.DadoEmpresaService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/empresa")
@Validated
class DadosEmpresaController @Autowired constructor(
    private val empresaService: DadoEmpresaService
) {

    @Operation(
        summary = "Cadastrar dados de uma nova empresa",
        description = "Retorna os detalhes da empresa cadastrada"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Empresa cadastrada com sucesso"),
            ApiResponse(responseCode = "400", description = "Erro de validação dos dados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PostMapping("/cadastro")
    fun post(@RequestBody @Valid dadoEmpresaDTO: DadoEmpresaDTO): ResponseEntity<Any> {
        return try {
            val empresa = empresaService.cadastrarEmpresa(dadoEmpresaDTO)
            ResponseEntity.status(201).body(empresa)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(400).body(mapOf("message" to e.message))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor"))
        }
    }

    @Operation(
        summary = "Obter dados de uma empresa",
        description = "Retorna os detalhes da empresa pelo ID"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Empresa encontrada"),
            ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping("/buscar/{id}")
    fun obterEmpresaPorId(@PathVariable id: Int): ResponseEntity<Any> {
        return try {
            val dadosEmpresaDTO = empresaService.obterEmpresaPorId(id)
            ResponseEntity.ok(dadosEmpresaDTO)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body(mapOf("message" to e.message))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor"))
        }
    }

    @Operation(
        summary = "Atualizar dados de uma empresa",
        description = "Atualiza e retorna os detalhes da empresa pelo ID"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso"),
            ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
            ApiResponse(responseCode = "400", description = "Erro de validação dos dados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PatchMapping("/atualizar/{id}")
    fun updateEmpresa(
        @PathVariable id: Int,
        @RequestBody @Valid dadoEmpresaDTO: DadosEmpresaDTOAtt
    ): ResponseEntity<Any> {
        return try {
            val empresaAtualizada = empresaService.atualizarEmpresa(id, dadoEmpresaDTO)
            ResponseEntity.status(200).body(empresaAtualizada)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body(mapOf("message" to "Empresa não encontrada"))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(400).body(mapOf("message" to e.message))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor"))
        }
    }
}

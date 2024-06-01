package TechForAll.techPoints.controller

import TechForAll.techPoints.dominio.DadosEmpresa
import TechForAll.techPoints.dto.DadoEmpresaDTO
import TechForAll.techPoints.dto.UsuarioDTOInput
import TechForAll.techPoints.service.DadoEmpresaService
import TechForAll.techPoints.service.UsuarioService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/empresa")
@Validated
class DadosEmpresaController @Autowired constructor (
    private val empresaService: DadoEmpresaService
) {

    @Operation(
        summary = "Cadastrar dados de uma nova empresa",
        description = "Retorna os detalhes da empresa cadastrada"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Empresa cadastrado com sucesso"),
            ApiResponse(responseCode = "400", description = "??"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PostMapping("/cadastro")
    fun post(@RequestBody @Valid dadoEmpresaDTO: DadoEmpresaDTO): ResponseEntity<Any> {
        return try {
            val empresa = empresaService.cadastrarEmpresa(dadoEmpresaDTO)
                ResponseEntity.status(201).body(dadoEmpresaDTO)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(400).body(mapOf("message" to e.message))
        }
    }//colocar o 500


    // fazer um get

    // fazer um patch
}
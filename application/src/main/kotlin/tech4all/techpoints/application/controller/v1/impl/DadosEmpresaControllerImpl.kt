package tech4all.techpoints.application.controller.v1.impl

import tech4all.techpoints.application.controller.v1.DadosEmpresaController
import tech4all.techpoints.domain.dto.DadoEmpresaDTO
import tech4all.techpoints.domain.dto.DadosEmpresaDTOAtt
import tech4all.techpoints.domain.service.DadoEmpresaService
import io.swagger.v3.oas.annotations.tags.Tag
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/empresas")
@Tag(name = "DadosEmpresa", description = "CRUD Empresa")
class DadosEmpresaControllerImpl(
    private val empresaService: DadoEmpresaService
) : DadosEmpresaController {

    override fun findAll(): ResponseEntity<List<DadoEmpresaDTO>> {
        return try {
            val companies = empresaService.findAll()
            ResponseEntity.ok(companies)
        } catch (e: Exception) {
            log.error("Error retrieving all companies", e)
            ResponseEntity.status(500).body(emptyList())
        }
    }

    override fun findById(id: Int): ResponseEntity<DadoEmpresaDTO> {
        return try {
            val company = empresaService.findById(id)
            ResponseEntity.ok(company)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body(null)
        } catch (e: Exception) {
            log.error("Error retrieving company by ID", e)
            ResponseEntity.status(500).body(null)
        }
    }

    override fun create(request: DadoEmpresaDTO): ResponseEntity<DadoEmpresaDTO> {
        return try {
            val createdCompany = empresaService.create(request)
            ResponseEntity.status(201).body(createdCompany)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(400).body(null)
        } catch (e: Exception) {
            log.error("Error creating company", e)
            ResponseEntity.status(500).body(null)
        }
    }

    override fun update(id: Int, request: DadosEmpresaDTOAtt): ResponseEntity<DadoEmpresaDTO> {
        return try {
            val updatedCompany = empresaService.update(id, request)
            ResponseEntity.ok(updatedCompany)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body(null)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(400).body(null)
        } catch (e: Exception) {
            log.error("Error updating company", e)
            ResponseEntity.status(500).body(null)
        }
    }
}

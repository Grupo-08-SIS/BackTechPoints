package tech4all.techpoints.infrastructure.adapter

import org.springframework.stereotype.Component
import tech4all.techpoints.infrastructure.entity.DadosEmpresaEntity
import tech4all.techpoints.infrastructure.repository.DadosEmpresaRepository

@Component
class DadosEmpresaAdapter(
    private val dadosEmpresaRepository: DadosEmpresaRepository
) {

    fun findAll(): List<DadosEmpresaEntity> {
        return dadosEmpresaRepository.findAll()
    }

    fun findById(id: Int): DadosEmpresaEntity? {
        return dadosEmpresaRepository.findById(id).orElse(null)
    }

    fun existsById(id: Int): Boolean {
        return dadosEmpresaRepository.existsById(id)
    }

    fun save(dadosEmpresa: DadosEmpresaEntity): DadosEmpresaEntity {
        return dadosEmpresaRepository.save(dadosEmpresa)
    }

    fun update(dadosEmpresa: DadosEmpresaEntity): DadosEmpresaEntity {
        return dadosEmpresaRepository.save(dadosEmpresa)
    }

    fun deleteById(id: Int) {
        dadosEmpresaRepository.deleteById(id)
    }
}

package TechForAll.techPoints.service

import TechForAll.techPoints.dominio.DadosEmpresa
import TechForAll.techPoints.dominio.TipoUsuario
import TechForAll.techPoints.dominio.Usuario
import TechForAll.techPoints.dto.DadoEmpresaDTO
import TechForAll.techPoints.repository.DadosEmpresaRepository
import TechForAll.techPoints.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class DadoEmpresaService @Autowired constructor (
    private val dadoEmpresaRepository: DadosEmpresaRepository, private val usuarioRepository: UsuarioRepository
) {

    fun cadastrarEmpresa(dadoEmpresaDTO: DadoEmpresaDTO): DadoEmpresaDTO {

        val usuario: Usuario = usuarioRepository.findById(dadoEmpresaDTO.idUsuario)
            .orElseThrow { IllegalArgumentException("Usuario não encontrado com o ID: ${dadoEmpresaDTO.idUsuario}") }

        val dadoEmpresa = DadosEmpresa(
            idEmpresa = dadoEmpresaDTO.idEmpresa,
            nomeEmpresa = dadoEmpresaDTO.nomeEmpresa,
            setorIndustria = dadoEmpresaDTO.setorIndustria,
            cargoUsuario = dadoEmpresaDTO.cargoUsuario,
            emailCorporativo = dadoEmpresaDTO.emailCorporativo,
            telefoneContato = dadoEmpresaDTO.telefoneContato,
            cnpj = dadoEmpresaDTO.cnpj,
            dataAtualizacao = LocalDateTime.now(),
            fkUsuario = usuario
        )

        val empresaSalva = dadoEmpresaRepository.save(dadoEmpresa)
        return dadoEmpresaParaDTO(empresaSalva)
    }

    private fun dadoEmpresaParaDTO(dadoEmpresa: DadosEmpresa): DadoEmpresaDTO {
        return DadoEmpresaDTO(
            idEmpresa = dadoEmpresa.idEmpresa,
            nomeEmpresa = dadoEmpresa.nomeEmpresa,
            setorIndustria = dadoEmpresa.setorIndustria,
            cargoUsuario = dadoEmpresa.cargoUsuario,
            emailCorporativo = dadoEmpresa.emailCorporativo,
            telefoneContato = dadoEmpresa.telefoneContato,
            cnpj = dadoEmpresa.cnpj,
            dataAtualizacao = dadoEmpresa.dataAtualizacao,
            idUsuario = dadoEmpresa.fkUsuario.idUsuario
        )
    }
}
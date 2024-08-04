package tech4all.techpoints.domain.service

import tech4all.techpoints.domain.model.Endereco
import tech4all.techpoints.domain.dto.EnderecoDTO

interface EnderecoService {

    fun cadastrarEndereco(novoEndereco: Endereco): Endereco

    fun listarEnderecos(): List<Endereco>

    fun buscarEnderecoPorId(idEndereco: Int): Endereco

    fun atualizarEndereco(idEndereco: Int, enderecoDTO: EnderecoDTO): Endereco
}

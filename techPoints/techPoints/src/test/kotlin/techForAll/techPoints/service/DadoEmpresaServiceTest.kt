package techForAll.techPoints.service

import techForAll.techPoints.TestObjects.dadoEmpresaDTOTeste1
import techForAll.techPoints.TestObjects.empresaTeste1
import techForAll.techPoints.TestObjects.usuarioTeste1
import techForAll.techPoints.domain.DadosEmpresa
import techForAll.techPoints.dto.DadosEmpresaDTOAtt
import techForAll.techPoints.repository.DadosEmpresaRepository
import techForAll.techPoints.repository.UsuarioRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.util.*

class DadoEmpresaServiceTest {

    private val dadoEmpresaRepository = mock(DadosEmpresaRepository::class.java)
    private val usuarioRepository = mock(UsuarioRepository::class.java)
    private val dadoEmpresaService = DadoEmpresaService(dadoEmpresaRepository, usuarioRepository)

    @Test
    fun `test cadastrarEmpresa`() {

        val dadoEmpresaDTO = dadoEmpresaDTOTeste1

        val usuario = usuarioTeste1

        `when`(usuarioRepository.findById(dadoEmpresaDTO.idUsuario)).thenReturn(Optional.of(usuario))

        val dadoEmpresaSalvo = DadosEmpresa(
            idEmpresa = dadoEmpresaDTO.idEmpresa,
            nomeEmpresa = dadoEmpresaDTO.nomeEmpresa,
            setorIndustria = dadoEmpresaDTO.setorIndustria,
            cargoUsuario = dadoEmpresaDTO.cargoUsuario,
            emailCorporativo = dadoEmpresaDTO.emailCorporativo,
            telefoneContato = dadoEmpresaDTO.telefoneContato,
            cnpj = dadoEmpresaDTO.cnpj,
            dataAtualizacao = null,
            fkUsuario = usuario
        )

        `when`(dadoEmpresaRepository.save(any())).thenReturn(dadoEmpresaSalvo)

        val resultado = dadoEmpresaService.cadastrarEmpresa(dadoEmpresaDTO)

        assertEquals(dadoEmpresaDTO, resultado)
    }

    @Test
    fun `test obterEmpresaPorId - Empresa encontrada`() {

        val idEmpresa = 1
        val empresaEncontrada = empresaTeste1

        `when`(dadoEmpresaRepository.findById(idEmpresa)).thenReturn(Optional.of(empresaEncontrada))

        val resultado = dadoEmpresaService.obterEmpresaPorId(idEmpresa)

        assertEquals(empresaEncontrada.idEmpresa, resultado.idEmpresa)
        assertEquals(empresaEncontrada.nomeEmpresa, resultado.nomeEmpresa)
        assertEquals(empresaEncontrada.setorIndustria, resultado.setorIndustria)
        assertEquals(empresaEncontrada.cargoUsuario, resultado.cargoUsuario)
        assertEquals(empresaEncontrada.emailCorporativo, resultado.emailCorporativo)
        assertEquals(empresaEncontrada.telefoneContato, resultado.telefoneContato)
        assertEquals(empresaEncontrada.cnpj, resultado.cnpj)
        assertEquals(empresaEncontrada.dataAtualizacao, resultado.dataAtualizacao)
    }

    @Test
    fun `test obterEmpresaPorId - Empresa não encontrada`() {
        val idEmpresa = 99

        `when`(dadoEmpresaRepository.findById(idEmpresa)).thenReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            dadoEmpresaService.obterEmpresaPorId(idEmpresa)
        }
    }

    @Test
    fun `test atualizarEmpresa apenas nome e setor de industria`() {

        val idEmpresa = 1
        val empresaExistente = empresaTeste1

        val novoNome = "Umbrella Corporation"
        val novoSetor = "Tecnologia"

        val empresaDTO = DadosEmpresaDTOAtt(
            idEmpresa = null,
            nomeEmpresa = novoNome,
            setorIndustria = novoSetor,
            cargoUsuario = null,
            emailCorporativo = null,
            telefoneContato = null,
            cnpj = null,
            dataAtualizacao = null,
            idUsuario = 1
        )

        // Mock dos métodos do repositório para retornar os dados existentes e salvar os novos
        `when`(dadoEmpresaRepository.findById(idEmpresa)).thenReturn(Optional.of(empresaExistente))
        `when`(dadoEmpresaRepository.save(any())).then { invocation ->
            invocation.getArgument<DadosEmpresa>(0).apply {
                assertEquals(novoNome, nomeEmpresa)
                assertEquals(novoSetor, setorIndustria)

                assertEquals(empresaExistente.cargoUsuario, cargoUsuario)
                assertEquals(empresaExistente.emailCorporativo, emailCorporativo)
                assertEquals(empresaExistente.telefoneContato, telefoneContato)
                assertEquals(empresaExistente.cnpj, cnpj)

                assertEquals(empresaExistente.dataAtualizacao, dataAtualizacao)
            }
        }
        //invocation.getArgument<DadosEmpresa>(0): recupera o primeiro argumento que foi passado para o método save
        //.apply { ... }: executa um bloco de código no objeto recuperado

        val empresaAtualizada = dadoEmpresaService.atualizarEmpresa(idEmpresa, empresaDTO)

        assertEquals(novoNome, empresaAtualizada.nomeEmpresa)
        assertEquals(novoSetor, empresaAtualizada.setorIndustria)
    }

}
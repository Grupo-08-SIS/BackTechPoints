import techForAll.techPoints.dominio.Endereco
import techForAll.techPoints.dto.EnderecoDTO
import techForAll.techPoints.repository.EnderecoRepository
import techForAll.techPoints.service.EnderecoService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*
import java.time.LocalDateTime
import java.util.*

class EnderecoServiceTest {

    private val repository: EnderecoRepository = mock(EnderecoRepository::class.java)
    private val service = EnderecoService(repository)

    @Test
    fun `test cadastrarEndereco`() {
        val novoEndereco =  Endereco(
            id = 1,
            cep = "12345678",
            cidade = "São Paulo",
            estado = "SP",
            rua = "Haddock Lobo",
            dataAtualizacao = LocalDateTime.now()
        )
        `when`(repository.save(novoEndereco)).thenReturn(novoEndereco)

        val enderecoCadastrado = service.cadastrarEndereco(novoEndereco)

        assertEquals(novoEndereco, enderecoCadastrado)
    }

    @Test
    fun `test listarEnderecos quando existem endereços`() {
        val endereco1 = Endereco(
            id = 1,
            cep = "12345678",
            cidade = "São Paulo",
            estado = "SP",
            rua = "Haddock Lobo",
            dataAtualizacao = LocalDateTime.now()
        )
        val endereco2 = Endereco(
            id = 2,
            cep = "87654321",
            cidade = "Rio de Janeiro",
            estado = "RJ",
            rua = "ipanema",
            dataAtualizacao = LocalDateTime.now()
        )
        val enderecos = listOf(endereco1, endereco2)

        `when`(repository.findAll()).thenReturn(enderecos)

        val listaEnderecos = service.listarEnderecos()

        assertEquals(enderecos, listaEnderecos)
    }

    @Test
    fun `test listarEnderecos quando não existem endereços`() {
        `when`(repository.findAll()).thenReturn(emptyList())

        assertThrows(NoSuchElementException::class.java) {
            service.listarEnderecos()
        }
    }

    @Test
    fun `test buscarEnderecoPorId quando o endereço existe`() {
        val idEndereco = 1
        val endereco = Endereco(
            id = 1,
            cep = "12345678",
            cidade = "São Paulo",
            estado = "SP",
            rua = "Haddock Lobo",
            dataAtualizacao = LocalDateTime.now()
        )
        `when`(repository.findById(idEndereco)).thenReturn(Optional.of(endereco))

        val enderecoEncontrado = service.buscarEnderecoPorId(idEndereco)

        assertEquals(endereco, enderecoEncontrado)
    }

    @Test
    fun `test buscarEnderecoPorId quando o endereço não existe`() {
        val idEndereco = 1
        `when`(repository.findById(idEndereco)).thenReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            service.buscarEnderecoPorId(idEndereco)
        }
    }

    @Test
    fun `test atualizarEndereco apenas cep e estado`() {
        val idEndereco = 1
        val enderecoExistente = Endereco(
            id = 1,
            cep = "12345678",
            cidade = "São Paulo",
            estado = "SP",
            rua = "Haddock Lobo",
            dataAtualizacao = LocalDateTime.now()
        )
        val novoCep = "87654321"
        val novoEstado = "RJ"
        val enderecoDTO = EnderecoDTO(
            cep = novoCep,
            estado = novoEstado,
            cidade = "São Paulo",
            rua = "Haddock Lobo"
        )
        `when`(repository.findById(idEndereco)).thenReturn(Optional.of(enderecoExistente))
        `when`(repository.save(enderecoExistente)).thenReturn(enderecoExistente.copy(
            cep = enderecoDTO.cep!!,
            estado = enderecoDTO.estado!!,
            cidade = enderecoDTO.cidade!!,
            rua = enderecoDTO.rua!!
        ))

        val enderecoAtualizado = service.atualizarEndereco(idEndereco, enderecoDTO)

        assertEquals(novoCep, enderecoAtualizado.cep)
        assertEquals(novoEstado, enderecoAtualizado.estado)
        assertEquals(enderecoExistente.cidade, enderecoAtualizado.cidade)
        assertEquals(enderecoExistente.rua, enderecoAtualizado.rua)
    }
}
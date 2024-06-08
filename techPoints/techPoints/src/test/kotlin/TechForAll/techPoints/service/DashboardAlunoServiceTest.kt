package TechForAll.techPoints.service

import TechForAll.techPoints.TestObjects
import TechForAll.techPoints.dto.*
import TechForAll.techPoints.repository.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.*

class DashboardAlunoServiceTest {

    @Mock
    private lateinit var graficoColunaRepository: GraficoColunaRepository

    @Mock
    private lateinit var graficoLinhaRepository: GraficoLinhaRepository

    @Mock
    private lateinit var pontosSemanaRepository: PontosSemanaRepository

    @Mock
    private lateinit var atividadesUsuarioRepository: AtividadesUsuarioRepository

    @InjectMocks
    private lateinit var dashboardAlunoService: DashboardAlunoService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `teste getPontosAoLongoDoTempo`() {
        val resultadosMock = listOf(TestObjects.pontoAoLongoDoTempoTeste1)
        `when`(graficoLinhaRepository.findPontosAoLongoDoTempo(1)).thenReturn(resultadosMock as List<Array<Any>>?)

        val esperado = listOf(
            PontosAoLongoDoTempoDTO("2023-06-08 12:00:00", "Curso A", 100)
        )

        val atual = dashboardAlunoService.getPontosAoLongoDoTempo(1)
        assertEquals(esperado, atual)
    }

    @Test
    fun `teste getPontosAoLongoDoTempo com dados adicionais`() {
        val resultadosMock = listOf(TestObjects.pontoAoLongoDoTempoTeste1, TestObjects.pontoAoLongoDoTempoTeste2)
        `when`(graficoLinhaRepository.findPontosAoLongoDoTempo(1)).thenReturn(resultadosMock as List<Array<Any>>?)

        val esperado = listOf(
            PontosAoLongoDoTempoDTO("2023-06-08 12:00:00", "Curso A", 100),
            PontosAoLongoDoTempoDTO("2023-07-08 12:00:00", "Curso B", 150)
        )

        val atual = dashboardAlunoService.getPontosAoLongoDoTempo(1)
        assertEquals(esperado, atual)
    }

    @Test
    fun `teste getPontosAoLongoDoTempo com lista vazia`() {
        `when`(graficoLinhaRepository.findPontosAoLongoDoTempo(1)).thenReturn(emptyList())

        val atual = dashboardAlunoService.getPontosAoLongoDoTempo(1)
        assertTrue(atual.isEmpty())
    }

    @Test
    fun `teste getPontosPorCursoAoMes`() {
        val resultadosMock = listOf(TestObjects.pontoPorCursoAoMesTeste1)
        `when`(graficoColunaRepository.findPontosPorCursoAoMes(1)).thenReturn(resultadosMock as List<Array<Any>>?)

        val esperado = listOf(
            PontosPorCursoAoMesDTO("6", "Curso B", 200)
        )

        val atual = dashboardAlunoService.getPontosPorCursoAoMes(1)
        assertEquals(esperado, atual)
    }

    @Test
    fun `teste getPontosPorCursoAoMes com dados adicionais`() {
        val resultadosMock = listOf(TestObjects.pontoPorCursoAoMesTeste1, TestObjects.pontoPorCursoAoMesTeste2)
        `when`(graficoColunaRepository.findPontosPorCursoAoMes(1)).thenReturn(resultadosMock as List<Array<Any>>?)

        val esperado = listOf(
            PontosPorCursoAoMesDTO("6", "Curso B", 200),
            PontosPorCursoAoMesDTO("7", "Curso C", 250)
        )

        val atual = dashboardAlunoService.getPontosPorCursoAoMes(1)
        assertEquals(esperado, atual)
    }

    @Test
    fun `teste getPontosPorCursoAoMes com lista vazia`() {
        `when`(graficoColunaRepository.findPontosPorCursoAoMes(1)).thenReturn(emptyList())

        val atual = dashboardAlunoService.getPontosPorCursoAoMes(1)
        assertTrue(atual.isEmpty())
    }

    @Test
    fun `teste getPontosSemana`() {
        val resultadoMock = TestObjects.pontoSemanaTeste1
        `when`(pontosSemanaRepository.findPontosSemana(1)).thenReturn(listOf(resultadoMock) as List<Array<Any>>?)

        val esperado = PontosSemanaDTO(1, "Usuario A", 50, 30, 20)

        val atual = dashboardAlunoService.getPontosSemana(1)
        assertEquals(esperado, atual)
    }

    @Test
    fun `teste getPontosSemana com dados adicionais`() {
        val resultadoMock = TestObjects.pontoSemanaTeste2
        `when`(pontosSemanaRepository.findPontosSemana(2)).thenReturn(listOf(resultadoMock) as List<Array<Any>>?)

        val esperado = PontosSemanaDTO(2, "Usuario B", 60, 40, 20)

        val atual = dashboardAlunoService.getPontosSemana(2)
        assertEquals(esperado, atual)
    }

    @Test
    fun `teste getPontosSemana lanca excecao quando nao ha dados`() {
        `when`(pontosSemanaRepository.findPontosSemana(1)).thenReturn(emptyList())

        assertThrows(NoSuchElementException::class.java) {
            dashboardAlunoService.getPontosSemana(1)
        }
    }

    @Test
    fun `teste getAtividadesUsuario`() {
        val resultadosMock = listOf(TestObjects.atividadesUsuarioTeste1)
        `when`(atividadesUsuarioRepository.findAtividadesUsuario(1)).thenReturn(resultadosMock as List<Array<Any>>?)

        val esperado = listOf(
            AtividadesUsuarioDTO(1, "Usuario B", "Curso C", "Modulo D", 10, 8)
        )

        val atual = dashboardAlunoService.getAtividadesUsuario(1)
        assertEquals(esperado, atual)
    }

    @Test
    fun `teste getAtividadesUsuario com dados adicionais`() {
        val resultadosMock = listOf(TestObjects.atividadesUsuarioTeste1, TestObjects.atividadesUsuarioTeste2)
        `when`(atividadesUsuarioRepository.findAtividadesUsuario(1)).thenReturn(resultadosMock as List<Array<Any>>?)

        val esperado = listOf(
            AtividadesUsuarioDTO(1, "Usuario B", "Curso C", "Modulo D", 10, 8),
            AtividadesUsuarioDTO(2, "Usuario C", "Curso D", "Modulo E", 12, 10)
        )

        val atual = dashboardAlunoService.getAtividadesUsuario(1)
        assertEquals(esperado, atual)
    }

    @Test
    fun `teste getAtividadesUsuario com lista vazia`() {
        `when`(atividadesUsuarioRepository.findAtividadesUsuario(1)).thenReturn(emptyList())

        val atual = dashboardAlunoService.getAtividadesUsuario(1)
        assertTrue(atual.isEmpty())
    }
}

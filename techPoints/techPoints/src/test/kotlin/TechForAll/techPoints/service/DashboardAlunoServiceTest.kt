package TechForAll.techPoints.service

import TechForAll.techPoints.dto.*
import TechForAll.techPoints.repository.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.sql.Date
import java.time.LocalDate

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
        val resultadosMock = listOf(
            arrayOf("Curso A" as Any, Date.valueOf(LocalDate.of(2023, 6, 8)) as Any, 100 as Any, 1 as Any)
        )
        `when`(graficoLinhaRepository.findPontosPorDiaECurso(1)).thenReturn(resultadosMock)

        val esperado = listOf(
            PontosAoLongoDoTempoDTO(
                nome = "Curso A",
                data_pontuacao = "2023-06-08",
                pontos = 100,
                usuario = 1
            )
        )

        val atual = dashboardAlunoService.getPontosAoLongoDoTempo(1)
        assertEquals(esperado, atual)
    }

    @Test
    fun `teste getPontosAoLongoDoTempo com dados adicionais`() {
        val resultadosMock = listOf(
            arrayOf("Curso A" as Any, Date.valueOf(LocalDate.of(2023, 6, 8)) as Any, 100 as Any, 1 as Any),
            arrayOf("Curso B" as Any, Date.valueOf(LocalDate.of(2023, 7, 8)) as Any, 150 as Any, 1 as Any)
        )
        `when`(graficoLinhaRepository.findPontosPorDiaECurso(1)).thenReturn(resultadosMock)

        val esperado = listOf(
            PontosAoLongoDoTempoDTO(
                nome = "Curso A",
                data_pontuacao = "2023-06-08",
                pontos = 100,
                usuario = 1
            ),
            PontosAoLongoDoTempoDTO(
                nome = "Curso B",
                data_pontuacao = "2023-07-08",
                pontos = 150,
                usuario = 1
            )
        )

        val atual = dashboardAlunoService.getPontosAoLongoDoTempo(1)
        assertEquals(esperado, atual)
    }

    @Test
    fun `teste getPontosAoLongoDoTempo com lista vazia`() {
        `when`(graficoLinhaRepository.findPontosPorDiaECurso(1)).thenReturn(emptyList())

        val atual = dashboardAlunoService.getPontosAoLongoDoTempo(1)
        assertTrue(atual.isEmpty())
    }

    @Test
    fun `teste getPontosPorCursoAoMes`() {
        val resultadosMock = listOf(
            arrayOf("6" as Any, "Curso B" as Any, 200L as Any)
        )
        `when`(graficoColunaRepository.findPontosPorCursoAoMes(1)).thenReturn(resultadosMock)

        val esperado = listOf(
            PontosPorCursoAoMesDTO(
                mes = "6",
                nome = "Curso B",
                pontos = 200
            )
        )

        val atual = dashboardAlunoService.getPontosPorCursoAoMes(1)
        assertEquals(esperado, atual)
    }

    @Test
    fun `teste getPontosPorCursoAoMes com dados adicionais`() {
        val resultadosMock = listOf(
            arrayOf("6" as Any, "Curso B" as Any, 200L as Any),
            arrayOf("7" as Any, "Curso C" as Any, 250L as Any)
        )
        `when`(graficoColunaRepository.findPontosPorCursoAoMes(1)).thenReturn(resultadosMock)

        val esperado = listOf(
            PontosPorCursoAoMesDTO(
                mes = "6",
                nome = "Curso B",
                pontos = 200
            ),
            PontosPorCursoAoMesDTO(
                mes = "7",
                nome = "Curso C",
                pontos = 250
            )
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
        val resultadoMock = listOf(
            arrayOf(1 as Any, "Usuario A" as Any, 50 as Any, 30 as Any, 20 as Any)
        )
        `when`(pontosSemanaRepository.findPontosSemana(1)).thenReturn(resultadoMock)

        val esperado = PontosSemanaDTO(
            idUsuario = 1,
            nomeUsuario = "Usuario A",
            pontosSemanaAtual = 50,
            pontosSemanaPassada = 30,
            diferencaPontos = 20
        )

        val atual = dashboardAlunoService.getPontosSemana(1)
        assertEquals(esperado, atual)
    }

    @Test
    fun `teste getPontosSemana com dados adicionais`() {
        val resultadoMock = listOf(
            arrayOf(2 as Any, "Usuario B" as Any, 60 as Any, 40 as Any, 20 as Any)
        )
        `when`(pontosSemanaRepository.findPontosSemana(2)).thenReturn(resultadoMock)

        val esperado = PontosSemanaDTO(
            idUsuario = 2,
            nomeUsuario = "Usuario B",
            pontosSemanaAtual = 60,
            pontosSemanaPassada = 40,
            diferencaPontos = 20
        )

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
    fun `teste getAtividadesDoUsuario`() {
        val totalAtividadesMock = listOf(
            arrayOf(1 as Any, 20 as Any),
            arrayOf(2 as Any, 15 as Any)
        )
        val atividadesPorUsuarioMock = listOf(
            arrayOf(1 as Any, "Curso 1" as Any, 15 as Any),
            arrayOf(2 as Any, "Curso 2" as Any, 10 as Any)
        )

        `when`(atividadesUsuarioRepository.findTotalAtividadesPorCurso()).thenReturn(totalAtividadesMock)
        `when`(atividadesUsuarioRepository.findAtividadesPorCursoEUsuario(1)).thenReturn(atividadesPorUsuarioMock)

        val esperado = listOf(
            AtividadesUsuarioDTO(
                idCurso = 1,
                nomeCurso = "Curso 1",
                totalQtdAtividades = 20,
                totalAtividadesUsuario = 15
            ),
            AtividadesUsuarioDTO(
                idCurso = 2,
                nomeCurso = "Curso 2",
                totalQtdAtividades = 15,
                totalAtividadesUsuario = 10
            )
        )

        val atual = dashboardAlunoService.getAtividadesDoUsuario(1)
        assertEquals(esperado, atual)
    }

    @Test
    fun `teste getAtividadesDoUsuario com dados adicionais`() {
        val totalAtividadesMock = listOf(
            arrayOf(1 as Any, 20 as Any),
            arrayOf(2 as Any, 15 as Any)
        )
        val atividadesPorUsuarioMock = listOf(
            arrayOf(1 as Any, "Curso 1" as Any, 15 as Any),
            arrayOf(2 as Any, "Curso 2" as Any, 10 as Any)
        )

        `when`(atividadesUsuarioRepository.findTotalAtividadesPorCurso()).thenReturn(totalAtividadesMock)
        `when`(atividadesUsuarioRepository.findAtividadesPorCursoEUsuario(1)).thenReturn(atividadesPorUsuarioMock)

        val esperado = listOf(
            AtividadesUsuarioDTO(
                idCurso = 1,
                nomeCurso = "Curso 1",
                totalQtdAtividades = 20,
                totalAtividadesUsuario = 15
            ),
            AtividadesUsuarioDTO(
                idCurso = 2,
                nomeCurso = "Curso 2",
                totalQtdAtividades = 15,
                totalAtividadesUsuario = 10
            )
        )

        val atual = dashboardAlunoService.getAtividadesDoUsuario(1)
        assertEquals(esperado, atual)
    }

    @Test
    fun `teste getAtividadesDoUsuario com lista vazia`() {
        `when`(atividadesUsuarioRepository.findTotalAtividadesPorCurso()).thenReturn(emptyList())
        `when`(atividadesUsuarioRepository.findAtividadesPorCursoEUsuario(1)).thenReturn(emptyList())

        val atual = dashboardAlunoService.getAtividadesDoUsuario(1)
        assertTrue(atual.isEmpty())
    }
}

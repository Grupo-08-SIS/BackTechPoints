import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when` // Usando `when` em vez de `thenReturn`
import org.mockito.junit.jupiter.MockitoExtension
import TechForAll.techPoints.dto.PontosAoLongoDoTempoDTO
import TechForAll.techPoints.dto.PontosPorCursoAoMesDTO
import TechForAll.techPoints.repository.PontuacaoRepository
import TechForAll.techPoints.service.DashboardAlunoService
import org.mockito.ArgumentMatchers.anyInt

@ExtendWith(MockitoExtension::class)
class DashboardAlunoServiceTest {

    @Mock
    private lateinit var pontuacaoRepository: PontuacaoRepository

    @InjectMocks
    private lateinit var dashboardAlunoService: DashboardAlunoService

    @Test
    fun testGetPontosAoLongoDoTempo() {

        val mockPontosAoLongoDoTempo = listOf(
            PontosAoLongoDoTempoDTO("2024-06", "Curso A", 100),
            PontosAoLongoDoTempoDTO("2024-07", "Curso B", 150)
        )

        `when`(pontuacaoRepository.findPontosAoLongoDoTempo(anyInt())).thenReturn(mockPontosAoLongoDoTempo)


        val result = dashboardAlunoService.getPontosAoLongoDoTempo(1)


        assertEquals(mockPontosAoLongoDoTempo, result)
    }

    @Test
    fun testGetPontosPorCursoAoMes() {

        val mockPontosPorCursoAoMes = listOf(
            PontosPorCursoAoMesDTO("2024-06", "Curso A", 100),
            PontosPorCursoAoMesDTO("2024-06", "Curso B", 150)
        )

        `when`(pontuacaoRepository.findPontosPorCursoAoMes(anyInt())).thenReturn(mockPontosPorCursoAoMes)

        val result = dashboardAlunoService.getPontosPorCursoAoMes(1)

        assertEquals(mockPontosPorCursoAoMes, result)
    }
}

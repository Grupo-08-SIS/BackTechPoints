package TechForAll.techPoints.service

import TechForAll.techPoints.dto.AlunoDTO
import TechForAll.techPoints.repository.DashboardRhRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*

class DashboardRhServiceTest {

    private lateinit var dashboardRhRepository: DashboardRhRepository
    private lateinit var dashboardRhService: DashboardRhService

    private val dadosExemplo = listOf(
        arrayOf(1, "user1", "User", "One", "user1@example.com", "Curso1", "Cidade1"),
        arrayOf(2, "user2", "User", "Two", "user2@example.com", "Curso2", "Cidade2")
    )

    @BeforeEach
    fun setUp() {
        dashboardRhRepository = mock(DashboardRhRepository::class.java)
        dashboardRhService = DashboardRhService(dashboardRhRepository)
        `when`(dashboardRhRepository.findAlunosByCursoAndCidade(null, null)).thenReturn(dadosExemplo)
    }

    @Test
    fun `getUsuariosPorCursoEMunicipio deve retornar lista de AlunoDTO`() {
        val resultado = dashboardRhService.getUsuariosPorCursoEMunicipio(null, null)

        assertEquals(2, resultado.size)
        assertEquals(AlunoDTO(1, "user1", "User", "One", "user1@example.com", "Curso1", "Cidade1"), resultado[0])
        assertEquals(AlunoDTO(2, "user2", "User", "Two", "user2@example.com", "Curso2", "Cidade2"), resultado[1])
    }

    @Test
    fun `getUsuariosPorCursoEMunicipio deve retornar lista vazia quando nao ha dados`() {
        `when`(dashboardRhRepository.findAlunosByCursoAndCidade(null, null)).thenReturn(emptyList())
        val resultado = dashboardRhService.getUsuariosPorCursoEMunicipio(null, null)

        assertTrue(resultado.isEmpty())
    }

    @Test
    fun `getUsuariosPorCursoEMunicipio deve filtrar por curso`() {
        `when`(dashboardRhRepository.findAlunosByCursoAndCidade("Curso1", null)).thenReturn(
            listOf(arrayOf(1, "user1", "User", "One", "user1@example.com", "Curso1", "Cidade1"))
        )
        val resultado = dashboardRhService.getUsuariosPorCursoEMunicipio("Curso1", null)

        assertEquals(1, resultado.size)
        assertEquals(AlunoDTO(1, "user1", "User", "One", "user1@example.com", "Curso1", "Cidade1"), resultado[0])
    }

    @Test
    fun `getUsuariosPorCursoEMunicipio deve filtrar por cidade`() {
        `when`(dashboardRhRepository.findAlunosByCursoAndCidade(null, "Cidade2")).thenReturn(
            listOf(arrayOf(2, "user2", "User", "Two", "user2@example.com", "Curso2", "Cidade2"))
        )
        val resultado = dashboardRhService.getUsuariosPorCursoEMunicipio(null, "Cidade2")

        assertEquals(1, resultado.size)
        assertEquals(AlunoDTO(2, "user2", "User", "Two", "user2@example.com", "Curso2", "Cidade2"), resultado[0])
    }

    @Test
    fun `getUsuariosPorCursoEMunicipio deve filtrar por curso e cidade`() {
        `when`(dashboardRhRepository.findAlunosByCursoAndCidade("Curso1", "Cidade1")).thenReturn(
            listOf(arrayOf(1, "user1", "User", "One", "user1@example.com", "Curso1", "Cidade1"))
        )
        val resultado = dashboardRhService.getUsuariosPorCursoEMunicipio("Curso1", "Cidade1")

        assertEquals(1, resultado.size)
        assertEquals(AlunoDTO(1, "user1", "User", "One", "user1@example.com", "Curso1", "Cidade1"), resultado[0])
    }
}

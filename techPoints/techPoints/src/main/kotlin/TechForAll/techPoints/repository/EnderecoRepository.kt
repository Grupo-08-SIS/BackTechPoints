package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Endereco

import org.springframework.data.jpa.repository.JpaRepository

interface EnderecoRepository : JpaRepository<Endereco, Int> {
}
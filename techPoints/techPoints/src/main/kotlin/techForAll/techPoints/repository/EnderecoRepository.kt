package techForAll.techPoints.repository

import techForAll.techPoints.domain.Endereco

import org.springframework.data.jpa.repository.JpaRepository

interface EnderecoRepository : JpaRepository<Endereco, Int> {
}
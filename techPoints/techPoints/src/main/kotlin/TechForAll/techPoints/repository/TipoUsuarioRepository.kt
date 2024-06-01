package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.TipoUsuario
import org.springframework.data.jpa.repository.JpaRepository

interface TipoUsuarioRepository : JpaRepository<TipoUsuario, Int> {

}
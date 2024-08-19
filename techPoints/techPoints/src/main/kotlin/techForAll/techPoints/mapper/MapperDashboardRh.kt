package techForAll.techPoints.mapper

import techForAll.techPoints.dominio.Usuario
import techForAll.techPoints.dto.rhdashboard.AlunoEspecificoDto

class MapperDashboardRh {

    fun usuarioToAlunoEspecificoDto (usuario: Usuario): AlunoEspecificoDto {

        return AlunoEspecificoDto (
            nome = usuario.nomeUsuario,
            imagemPerfil = usuario.imagemPerfil,
            municipio = usuario.endereco?.cidade,
            email = usuario.email,
          //  escolaridade = usuario.escolaridade, TODO
            emblemas = usuario.inscricoes,
          //  descricaoAluno = usuario.descricao TODO
        )
    }
}
package techForAll.techPoints.mapper.impl

import techForAll.techPoints.dominio.Usuario
import techForAll.techPoints.dto.RhAlunoCursoDto
import techForAll.techPoints.dto.RhAlunoCursoDtoOrdenado
import techForAll.techPoints.dto.rhdashboard.AlunoEspecificoDto
import techForAll.techPoints.mapper.MapperDashboardRh

class MapperDashboardRhImpl : MapperDashboardRh {

    override fun mapToAlunosCursoDto(alunos: Map<Pair<Int, String>, List<RhAlunoCursoDto>>): List<RhAlunoCursoDtoOrdenado> {

        return alunos.map { (cursoIdNome, alunos) ->
            RhAlunoCursoDtoOrdenado(
                idCurso = cursoIdNome.first,
                nomeCurso = cursoIdNome.second,
                alunosFiltrados = alunos.map {
                    RhAlunoCursoDto(
                        idCurso = cursoIdNome.first,
                        nomeCurso = cursoIdNome.second,
                        idUsuario = it.idUsuario,
                        nomeUsuario = it.nomeUsuario,
                        municipio = it.municipio,
                        email = it.email,
                        imagemPerfil = it.imagemPerfil,
                        inscricoes = it.inscricoes
                    )
                }

            )
        }
    }

    override fun usuarioToAlunoEspecificoDto(usuario: RhAlunoCursoDto): AlunoEspecificoDto {

        return AlunoEspecificoDto(
            nome = usuario.nomeUsuario,
            imagemPerfil = usuario.imagemPerfil,
            municipio = usuario.municipio,
            email = usuario.email,
            //  escolaridade = usuario.escolaridade, TODO
            emblemas = usuario.inscricoes,
            //  descricaoAluno = usuario.descricao TODO
        )
    }
}
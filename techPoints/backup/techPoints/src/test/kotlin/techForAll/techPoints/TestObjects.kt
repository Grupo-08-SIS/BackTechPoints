package techForAll.techPoints

import techForAll.techPoints.domain.DadosEmpresa
import techForAll.techPoints.domain.Endereco
import techForAll.techPoints.domain.Usuario
import techForAll.techPoints.dominio.*
import techForAll.techPoints.dto.DadoEmpresaDTO
import techForAll.techPoints.dto.DadosEmpresaDTOAtt
import java.time.LocalDateTime

object TestObjects {

    val enderecoTeste1 = Endereco(
            id = 1,
            cep = "12345678",
            cidade = "São Paulo",
            estado = "SP",
            rua = "Haddock Lobo",
            dataAtualizacao = LocalDateTime.now()
    )

    val tipoUsuarioTeste3 = TipoUsuario(
            idTipoUsuario = 3,
            nome = NomeTipoUsuario.RH
    )

    val usuarioTeste1 = Usuario(
            idUsuario = 1,
            autenticado = true,
            cpf = "12345678901",
            dataDeletado = null,
            email = "claire.redfield@racooncitypd.com",
            imagemPerfil = null,
            nomeUsuario = "claire",
            primeiroNome = "Claire",
            senha = "Redfield123@",
            sobrenome = "Redfield",
            endereco = enderecoTeste1,
            tipoUsuario = tipoUsuarioTeste3,
            telefone = "(00) 00000-0000"

    )

    val dadoEmpresaDTOTeste1 = DadoEmpresaDTO(
            idEmpresa = 1,
            nomeEmpresa = "Empresa Teste",
            setorIndustria = "Tecnologia",
            cargoUsuario = "CEO",
            emailCorporativo = "empresa@teste.com",
            telefoneContato = "123456789",
            cnpj = "12345678901234",
            dataAtualizacao = null,
            idUsuario = 1
    )

    val empresaTeste1 = DadosEmpresa(
            idEmpresa = 2,
            nomeEmpresa = "umbrella",
            setorIndustria = "farmaceutica",
            cargoUsuario = "rh",
            emailCorporativo = "rh_umbrela@email.com",
            telefoneContato = "12945005812",
            cnpj = "1234567890123",
            dataAtualizacao = null,
            fkUsuario = usuarioTeste1
    )

    val empresaAttTeste1 = DadosEmpresaDTOAtt(
            idEmpresa = null,
            nomeEmpresa = "umbrella 2.0",
            setorIndustria = "super farmaceutica",
            cargoUsuario = "agente de rh",
            emailCorporativo = null,
            telefoneContato = null,
            cnpj = null,
            dataAtualizacao = null,
            idUsuario = 2
    )

    val pontoAoLongoDoTempoTeste1 = arrayOf(
            LocalDateTime.of(2023, 6, 8, 12, 0, 0), "Curso A", 100L
    )

    val pontoPorCursoAoMesTeste1 = arrayOf(
            "6", "Curso B", 200L
    )

    val pontoSemanaTeste1 = arrayOf(
            1, "Usuario A", 50, 30, 20
    )

    val atividadesUsuarioTeste1 = arrayOf(
            1, "Usuario B", "Curso C", "Modulo D", 10, 8
    )

    val pontoAoLongoDoTempoTeste2 = arrayOf(
            LocalDateTime.of(2023, 7, 8, 12, 0, 0), "Curso B", 150L
    )

    val pontoPorCursoAoMesTeste2 = arrayOf(
            "7", "Curso C", 250L
    )

    val pontoSemanaTeste2 = arrayOf(
            2, "Usuario B", 60, 40, 20
    )

    val atividadesUsuarioTeste2 = arrayOf(
            2, "Usuario C", "Curso D", "Modulo E", 12, 10
    )

}
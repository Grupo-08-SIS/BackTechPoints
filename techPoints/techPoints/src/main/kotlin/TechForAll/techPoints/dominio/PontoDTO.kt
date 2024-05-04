package TechForAll.techPoints.dominio

data class PontoDTO(
        var idPonto: Int,
        var qtdPonto: Int,
        var tipoPonto: TipoPonto?
) {
}
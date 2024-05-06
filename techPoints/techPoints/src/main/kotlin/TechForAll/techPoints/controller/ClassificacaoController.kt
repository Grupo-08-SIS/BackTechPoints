package TechForAll.techPoints.controller

import TechForAll.techPoints.dominio.Classificacao
import TechForAll.techPoints.repository.ClassificacaoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/classificacao")
class ClassificacaoController {

    @Autowired
    lateinit var classificacaoRepository: ClassificacaoRepository

    @GetMapping
    fun buscarClassificacao(): ResponseEntity<List<Classificacao>> {
        val ranking = classificacaoRepository.buscarRankingGeral()
        return ResponseEntity.status(200).body(ranking)
    }

}
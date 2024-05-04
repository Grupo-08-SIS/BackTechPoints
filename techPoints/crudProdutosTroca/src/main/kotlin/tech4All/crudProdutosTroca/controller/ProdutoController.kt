package tech4All.crudProdutosTroca.controller

import jakarta.validation.Valid
import org.apache.coyote.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech4All.crudProdutosTroca.domain.Produto
import tech4All.crudProdutosTroca.repository.ProdutoRepository

@RestController
@RequestMapping("/produtos")
class ProdutoController (
    val produtoRepository: ProdutoRepository
) {
    @PostMapping
    fun cadastrarProduto(@RequestBody @Valid novoProduto: Produto): ResponseEntity<Produto> {
        val produtoSalvo = produtoRepository.save(novoProduto);

        return ResponseEntity.status(201).body(produtoSalvo);
    }

    @GetMapping
    fun listarProdutos(): ResponseEntity<List<Produto>> {
        val listaProdutos = produtoRepository.findAll();

        if (listaProdutos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(listaProdutos);
    }
}
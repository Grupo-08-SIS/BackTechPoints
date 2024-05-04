package tech4All.crudProdutosTroca.controller

import jakarta.validation.Valid
import org.apache.coyote.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tech4All.crudProdutosTroca.domain.CategoriaProduto
import tech4All.crudProdutosTroca.repository.CategoriaProdutoRepository

@RestController
@RequestMapping("/categoriaProdutos")
class CategoriaProdutoController (
    val categoriaProdutoRepository: CategoriaProdutoRepository
) {
    @PostMapping
    fun cadastrarCategoriaProduto(@RequestBody @Valid novaCategoria: CategoriaProduto): ResponseEntity<CategoriaProduto> {
        val categoriaProdutoSalva = categoriaProdutoRepository.save(novaCategoria);

        return ResponseEntity.status(201).body(novaCategoria);
    }

    @GetMapping
    fun listarCategoriaProduto(): ResponseEntity<List<CategoriaProduto>> {
        val categoriaProdutos = categoriaProdutoRepository.findAll();

        if (categoriaProdutos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(categoriaProdutos);
    }
}
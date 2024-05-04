package tech4All.crudProdutosTroca.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tech4All.crudProdutosTroca.domain.Produto
import tech4All.crudProdutosTroca.repository.ProdutoRepository

@RestController
@RequestMapping("/produtos")
class ProdutoController (
    val produtoRepository: ProdutoRepository,
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

    @PutMapping("/atualizar-categoria-produto/{id}")
    fun atualizarProduto(@PathVariable id:Int, @RequestBody @Valid produtoNovo: Produto): ResponseEntity<Produto> {

        val produtoAtualizado = Produto (
            id = id,
            nome = produtoNovo.nome,
            valorPontos = produtoNovo.valorPontos,
            descricao = produtoNovo.descricao,
            quantidade = produtoNovo.quantidade,
            disponivel = produtoNovo.disponivel,
            categoriaProduto = produtoNovo.categoriaProduto
        )
        produtoRepository.save(produtoAtualizado);

        return ResponseEntity.status(200).body(produtoAtualizado);
    }

    @DeleteMapping("/deletar/{id}")
    fun deletarProduto(@PathVariable id: Int):ResponseEntity<Void> {

        produtoRepository.deleteById(id);

        return ResponseEntity.status(200).build();
    }
}
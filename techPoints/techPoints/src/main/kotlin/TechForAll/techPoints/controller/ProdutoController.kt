package TechForAll.techPoints.controller

import TechForAll.techPoints.dominio.Produto
import TechForAll.techPoints.repository.ProdutoRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/produtos")
class ProdutoController (
    val produtoRepository: ProdutoRepository,
) {

    @Operation(summary = "Cadastrar um novo produto", description = "Cadastra um novo produto na base de dados")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso")
        ]
    )
    @PostMapping
    fun cadastrarProduto(@RequestBody @Valid novoProduto: Produto): ResponseEntity<Produto> {
        val produtoSalvo = produtoRepository.save(novoProduto);

        return ResponseEntity.status(201).body(produtoSalvo);
    }

    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista de todos os produtos cadastrados")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"),
            ApiResponse(responseCode = "204", description = "Nenhum produto encontrado")
        ]
    )
    @GetMapping
    fun listarProdutos(): ResponseEntity<List<Produto>> {
        val listaProdutos = produtoRepository.findAll();

        if (listaProdutos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(listaProdutos);
    }

    @Operation(summary = "Atualizar a categoria de um produto", description = "Atualiza a categoria de um produto pelo ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso")
        ]
    )
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

    @Operation(summary = "Deletar um produto", description = "Remove um produto da base de dados pelo ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Produto deletado com sucesso")
        ]
    )
    @DeleteMapping("/deletar/{id}")
    fun deletarProduto(@PathVariable id: Int):ResponseEntity<Void> {

        produtoRepository.deleteById(id);

        return ResponseEntity.status(200).build();
    }
}
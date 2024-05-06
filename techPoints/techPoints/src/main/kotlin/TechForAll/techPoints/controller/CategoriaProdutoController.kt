package TechForAll.techPoints.controller

import TechForAll.techPoints.dominio.CategoriaProduto
import TechForAll.techPoints.repository.CategoriaProdutoRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/categoriaProdutos")
class CategoriaProdutoController (
    val categoriaProdutoRepository: CategoriaProdutoRepository
) {
    @Operation(summary = "Cadastrar uma nova categoria de produto", description = "Cadastra uma nova categoria de produto na base de dados")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Categoria de produto cadastrada com sucesso")
        ]
    )
    @PostMapping
    fun cadastrarCategoriaProduto(@RequestBody @Valid novaCategoria: CategoriaProduto): ResponseEntity<CategoriaProduto> {
        val categoriaProdutoSalva = categoriaProdutoRepository.save(novaCategoria);

        return ResponseEntity.status(201).body(novaCategoria);
    }

    @Operation(summary = "Listar todas as categorias de produto", description = "Retorna uma lista de todas as categorias de produto cadastradas")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Lista de categorias de produto retornada com sucesso"),
            ApiResponse(responseCode = "204", description = "Nenhuma categoria de produto encontrada")
        ]
    )
    @GetMapping
    fun listarCategoriaProduto(): ResponseEntity<List<CategoriaProduto>> {
        val categoriaProdutos = categoriaProdutoRepository.findAll();

        if (categoriaProdutos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(categoriaProdutos);
    }

    @Operation(summary = "Atualizar uma categoria de produto", description = "Atualiza uma categoria de produto pelo ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Categoria de produto atualizada com sucesso"),
            ApiResponse(responseCode = "404", description = "Categoria de produto não encontrada")
        ]
    )
    @PatchMapping("/atualizar/{id}")
    fun atualizarCategoriaProduto(@PathVariable id: Int, @RequestBody categoriaAtualizada: CategoriaProduto): ResponseEntity<CategoriaProduto> {
        if (categoriaProdutoRepository.existsById(id)) {
            val categoriaExistente = categoriaProdutoRepository.findById(id).get()

            categoriaAtualizada.nome?.let { categoriaExistente.nome = it }
            categoriaAtualizada.descricaoCategoria?.let { categoriaExistente.descricaoCategoria = it }

            val categoriaAtualizadaSalva = categoriaProdutoRepository.save(categoriaExistente)
            return ResponseEntity.status(200).body(categoriaAtualizadaSalva)
        } else {
            return ResponseEntity.status(404).build()
        }
    }


}
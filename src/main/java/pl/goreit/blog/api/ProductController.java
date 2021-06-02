package pl.goreit.blog.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pl.goreit.api.generated.ProductViewDetails;
import pl.goreit.api.generated.product_api.CreateProductRequest;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping()
    @ApiOperation(value = "pobiera  produkty po regex", notes = "pobiera produkt po regex")
    public Page<ProductViewDetails> getProductsByTitleRegex(@RequestParam(defaultValue = "", required = false) String title,
                                                            @RequestParam(defaultValue = "0") Integer page,
                                                            @RequestParam(defaultValue = "10") Integer size) throws DomainException {
        return productService.findAllByRegexpTitle(title, PageRequest.of(page, size));
    }

    @GetMapping("/{title}")
    @ApiOperation(value = "pobiera jeden produkt", notes = "pobiera 1 produkt")
    public ProductViewDetails getProduct(@PathVariable String title) throws DomainException {
        return productService.findByTitle(title);
    }

    @PostMapping("/add")
    @ApiOperation(value = "dodaje 1 produkt", notes = "dodaje produkt")
    public ProductViewDetails addProduct(@RequestBody CreateProductRequest request) {
        return productService.add(request);
    }

    @PostMapping("/comment")
    @ApiOperation(value = "Dodaje komentarz do produktu", notes = "dodaje komentarz")
    public ProductViewDetails addComment(@RequestParam("userId") String userId,
                                         @RequestParam("title") String productTitle,
                                         @RequestParam("text") String text) throws DomainException {
        return productService.addComment(userId, productTitle, text);
    }

}

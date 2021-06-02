package pl.goreit.blog.domain.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.goreit.api.generated.ProductViewDetails;
import pl.goreit.api.generated.product_api.CreateProductRequest;
import pl.goreit.blog.domain.DomainException;

import java.util.List;

public interface ProductService {


    ProductViewDetails findByTitle(String id) throws DomainException;

    List<ProductViewDetails> findAll() throws DomainException;

    Page<ProductViewDetails> findAllByRegexpTitle(String title, Pageable pageable) throws DomainException;

    List<ProductViewDetails> findAllByTitleIn(List<String> titles) throws DomainException;

    ProductViewDetails add(CreateProductRequest createProductRequest);

    ProductViewDetails addComment(String userId, String productTitle, String text) throws DomainException;

}

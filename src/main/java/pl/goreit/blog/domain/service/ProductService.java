package pl.goreit.blog.domain.service;

import pl.goreit.api.generated.OrderResponse;
import pl.goreit.api.generated.ProductView;
import pl.goreit.api.generated.ProductViewDetails;
import pl.goreit.api.generated.product_api.CreateProductRequest;
import pl.goreit.blog.domain.DomainException;

import java.util.List;

public interface ProductService {

    ProductViewDetails findByTitle(String id) throws DomainException;

    ProductViewDetails add(CreateProductRequest createProductRequest);

    ProductViewDetails addComment(String userId, String productTitle, String text) throws DomainException;

    void updateAfterOrdered(OrderResponse orderResponse);
}

package pl.goreit.blog.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.goreit.api.generated.ProductViewDetails;
import pl.goreit.api.generated.product_api.CreateProductRequest;
import pl.goreit.blog.domain.DomainException;
import pl.goreit.blog.domain.ExceptionCode;
import pl.goreit.blog.domain.model.Comment;
import pl.goreit.blog.domain.model.Product;
import pl.goreit.blog.domain.service.ProductService;
import pl.goreit.blog.infrastructure.mongo.ProductRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ConversionService sellConversionService;

    @Autowired
    private ProductRepo productRepo;



    @Override
    public ProductViewDetails findByTitle(String title) throws DomainException {
        Product product = productRepo.findByTitle(title).orElseThrow(() -> new DomainException(ExceptionCode.GOREIT_01));
        return sellConversionService.convert(product, ProductViewDetails.class);
    }

    @Override
    public List<ProductViewDetails> findAllByTitleIn(List<String> titles) throws DomainException {
        List<Product> allByTitle = productRepo.findByTitleIn(titles);
        return  allByTitle.stream().
                map(product -> sellConversionService.convert(product, ProductViewDetails.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductViewDetails add(CreateProductRequest createProductRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sellerId = authentication.getName();

        //@FIXME allow add photo album to product
        Product product = new Product(
                sellerId,
                createProductRequest.getTitle(),
                createProductRequest.getText(),
                createProductRequest.getPrice(),
                null);
        productRepo.save(product);
        return sellConversionService.convert(product, ProductViewDetails.class);
    }

    @Override
    public ProductViewDetails addComment(String userId, String productTitle, String text) throws DomainException {
        Product product = productRepo.findByTitle(productTitle).orElseThrow(() -> new DomainException(ExceptionCode.GOREIT_01));

        Integer sequenceNo = 0;
        if (product.getComments().size() > 0) {
            sequenceNo = product.getComments().get(product.getComments().size() - 1).getSequenceNo();
        }

        product.addComment(new Comment(sequenceNo + 1, userId, text));
        Product saved = productRepo.save(product);
        return sellConversionService.convert(saved, ProductViewDetails.class);
    }

}

package pl.goreit.blog.domain.converter;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.CommentView;
import pl.goreit.api.generated.ProductView;
import pl.goreit.blog.domain.model.Product;

import java.util.stream.Collectors;

@Component
public class ProductToProductViewConverter implements Converter<Product, ProductView> {

    private final ConversionService conversionService;

    @Lazy
    public ProductToProductViewConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public ProductView convert(Product product) {
        return new ProductView(product.getSellerId(),
                product.getTitle(), product.getText(),
                product.getPrice().toString(),
                product.getComments().stream()
                        .map(comment -> conversionService.convert(comment, CommentView.class))
                        .collect(Collectors.toList()),
                product.getBoughtByList(),
                product.getStatus().name());
    }
}

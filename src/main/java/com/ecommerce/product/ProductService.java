package com.ecommerce.product;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.model.Product;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(Product product){
        String slug = Slugify.builder().transliterator(true).lowerCase(true).build()
                .slugify(product.getTitle());
        product.setSlug(slug);
        return productRepository.save(product);
    }
    private Product findProductById(Long id){
        Product p = productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The product with id [%s] not exists"
                        .formatted(id)));
        return p;
    }
    public Product getProduct(Long id){
        Product p = findProductById(id);
        if(!p.isDeleted())
            return p;
        throw new ResourceNotFoundException("The product with id [%s] not exists"
                .formatted(id));
    }
    public String deleteProductById(Long id){
        Product p = productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The product with id [%s] not exists"
                        .formatted(id)));
        p.setDeleted(true);
        productRepository.save(p);
        return "Product have been deleted";
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Product updateProductById(Long id, Product p){
        String slug = Slugify.builder().transliterator(true).lowerCase(true).build()
                .slugify(p.getTitle());
        p.setSlug(slug);
        p.setId(id);
        return productRepository.save(p);
    }
}

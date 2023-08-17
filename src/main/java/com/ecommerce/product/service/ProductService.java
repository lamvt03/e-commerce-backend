package com.ecommerce.product.service;

import com.ecommerce.common.PaginationService;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.model.dto.ProductDTO;
import com.ecommerce.product.model.entity.Rating;
import com.ecommerce.product.model.request.AddToWishlistRequest;
import com.ecommerce.product.model.request.RatingRequest;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.model.dto.FilterDTO;
import com.ecommerce.common.PaginationDTO;
import com.ecommerce.product.model.entity.Product;
import com.ecommerce.product.repository.RatingRepository;
import com.ecommerce.user.model.User;
import com.ecommerce.user.model.UserDTO;
import com.ecommerce.user.service.UserMapper;
import com.ecommerce.user.service.UserService;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final PaginationService paginationService;
    private final UserMapper userMapper;
    private final UserService userService;
    private final RatingRepository ratingRepository;
    private final ProductMapper productMapper;

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
        Product oldProduct = findProductById(id);
        String slug = Slugify.builder().transliterator(true).lowerCase(true).build()
                .slugify(p.getTitle());
        p.setSlug(slug);
        p.setId(id);
        p.setCreatedBy(oldProduct.getCreatedBy());
        p.setCreatedAt(oldProduct.getCreatedAt());
        return productRepository.save(p);
    }

    public List<Product> filterProducts(FilterDTO filter, PaginationDTO pagination){
        Pageable pageable = paginationService.getPageable(pagination);
        return productRepository.findWithFilter(filter, pageable);
    }


    public UserDTO addToWishlist(Long userId, AddToWishlistRequest request) {
        Product product = findProductById(request.productId());
        User user = userService.findUserById(userId);
        Set<Product> wishlist = user.getWishlist();
        boolean alreadyExist =  wishlist.stream()
                .filter(p -> p.getId().equals(request.productId()))
                .findFirst()
                .isPresent();
        if(alreadyExist){
            wishlist.remove(product);
        }else{
            wishlist.add(product);
        }
        return userMapper.toDto(userService.saveUser(user));
    }

    public ProductDTO ratingProduct(Long userId, RatingRequest request) {
        Product product = findProductById(request.productId());
        Set<Rating> ratings = product.getRatings();

        //check whether user already rated product
        boolean alreadyRated = ratings.stream()
                .filter(rating -> rating.getPostedBy().getId().equals(userId))
                .findFirst()
                .isPresent();

        if(alreadyRated){
            Rating oldRating = ratings.stream()
                    .filter(rating -> rating.getPostedBy().getId().equals(userId))
                    .findFirst()
                    .orElseThrow();
            oldRating.setStar(request.star());
            oldRating.setComment(request.comment());
        }else{
            User postedBy = userService.findUserById(userId);
            Rating rating = ratingRepository.save(
                    new Rating(
                            request.star(),
                            request.comment(),
                            postedBy,
                            product
                    )
            );
            ratings.add(rating);
        }
        int ratingSum = ratings.stream()
                .mapToInt(Rating::getStar)
                .reduce(0, (curr, next) -> curr + next);
        int ratingCount = ratings.size();
        float ratingPoint = (float) (1.0 * ratingSum / ratingCount);
        product.setRatingPoint(ratingPoint);
        return productMapper.toDto(productRepository.save(product));
    }
}

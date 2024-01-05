package com.ecommerce.product;

import com.ecommerce.product.image.PImage;
import com.ecommerce.product.image.PImageRepository;
import com.ecommerce.product.model.request.ProductCreateRequest;
import com.ecommerce.util.PaginationService;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.model.ProductDTO;
import com.ecommerce.product.rating.Rating;
import com.ecommerce.product.model.request.AddWishlistRequest;
import com.ecommerce.product.model.request.RatingRequest;
import com.ecommerce.util.model.FilterDTO;
import com.ecommerce.util.model.PaginationDTO;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.rating.RatingRepository;
import com.ecommerce.user.model.User;
import com.ecommerce.user.model.UserDTO;
import com.ecommerce.user.service.UserMapper;
import com.ecommerce.user.service.UserService;
import com.ecommerce.util.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final UserMapper userMapper;
    private final UserService userService;

    private final RatingRepository ratingRepository;
    private final PImageRepository pImageRepository;
    private final PaginationService paginationService;
    private final ImageService imageService;

    public ProductDTO createProduct(ProductCreateRequest request){
        Product productEntity = productMapper.toEntity(request);
        return productMapper.toDto(
                productRepository.save(productEntity)
        );
    }
    public Product findProductById(Long id){
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The product with id [%s] not exists"
                        .formatted(id)));
    }
    public ProductDTO getProductById(Long id){
        Product p = findProductById(id);
        if(!p.isDeleted())
            return productMapper.toDto(p);
        throw new ResourceNotFoundException("The product with id [%s] not exists"
                .formatted(id));
    }
    public void deleteProductById(Long id){
        Product p = findProductById(id);
        p.setDeleted(true);
        productRepository.save(p);
    }

    public List<ProductDTO> findProducts(PaginationDTO paginationDTO){
        Pageable pageable = paginationService.getPageable(paginationDTO);
        return productRepository.findAll(pageable).stream()
                .map(productMapper::toDto)
                .toList();
    }

    public ProductDTO updateProductById(Long id, ProductCreateRequest request){
        Product product = findProductById(id);
        return productMapper.toDto(
                productRepository.save(
                        productMapper.toEntity(product, request)
                )
        );
    }

    public List<ProductDTO> filterProducts(FilterDTO filter, PaginationDTO pagination){
        Pageable pageable = paginationService.getPageable(pagination);
        return productRepository.findWithFilter(filter, pageable).stream()
                .map(productMapper::toDto)
                .toList();
    }


    public UserDTO addToWishlist(Long userId, AddWishlistRequest request) {
        Product product = findProductById(request.productId());
        User user = userService.findUserById(userId);
        Set<Product> wishlist = user.getWishlist();
        boolean alreadyExist = wishlist.stream()
                .anyMatch(p -> p.getId().equals(request.productId()));
        if(alreadyExist){
            wishlist.remove(product);
        }else{
            wishlist.add(product);
        }
        return userMapper.toDto(
                userService.saveUser(user)
        );
    }

    public ProductDTO ratingProduct(User user, RatingRequest request) {
        Product product = findProductById(request.productId());
        Rating existRating = ratingRepository.findByUser_IdAndProduct_Id(user.getId(), request.productId())
                .orElse(null);

        //check whether user already rated product
        if(existRating != null){
            existRating.setStar(request.star());
            existRating.setComment(request.comment());
            ratingRepository.save(existRating);
        }else{
            Rating rating = new Rating(
                            request.star(),
                            request.comment(),
                            user,
                            product);
            ratingRepository.save(rating);


        }

//            update rating point
        List<Rating> ratings = ratingRepository.findAllByProduct_Id(request.productId());
        int ratingSum = ratings.stream()
                .mapToInt(Rating::getStar)
                .reduce(0, Integer::sum);
        int ratingCount = ratings.size();
        float ratingPoint = (float) (1.0 * ratingSum / ratingCount);
        product.setRatingPoint(ratingPoint);

        return productMapper.toDto(
                productRepository.save(product)
        );
    }

    public ProductDTO uploadProductImages(Long prodId, MultipartFile[] images){
        Product product =  findProductById(prodId);
        imageService.uploadProductImages(images, product);
        return productMapper.toDto(
                productRepository.save(product)
        );
    }

    public void deleteProductImage(Long prodId, String publicId) {
        PImage pImage = pImageRepository.findByProduct_IdAndPublicId(prodId, publicId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Product Image with public id [%s] and product id [%s] not found".formatted(publicId, prodId))
                        );
        pImageRepository.delete(pImage);
        imageService.deleteProductImage(publicId);
    }

    public List<ProductDTO> findProductByCategory(String code, PaginationDTO paginationDTO) {
        Pageable pageable = paginationService.getPageable(paginationDTO);
        return productRepository.findByCategory_Code(code, pageable).stream()
                .map(productMapper::toDto)
                .toList();
    }

    public List<ProductDTO> findProductByBrand(String code, PaginationDTO paginationDTO) {
        Pageable pageable = paginationService.getPageable(paginationDTO);
        return productRepository.findByBrand_Code(code, pageable).stream()
                .map(productMapper::toDto)
                .toList();
    }
}

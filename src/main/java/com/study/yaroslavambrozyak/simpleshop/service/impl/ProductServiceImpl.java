package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.dto.ProductDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.Image;
import com.study.yaroslavambrozyak.simpleshop.entity.Product;
import com.study.yaroslavambrozyak.simpleshop.exception.NotEnoughException;
import com.study.yaroslavambrozyak.simpleshop.exception.NotFoundException;
import com.study.yaroslavambrozyak.simpleshop.repository.ImageRepository;
import com.study.yaroslavambrozyak.simpleshop.repository.ProductRepository;
import com.study.yaroslavambrozyak.simpleshop.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,ModelMapper modelMapper,ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.imageRepository = imageRepository;
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
    @Override
    public Product getProductForOrder(Long id, Integer quantity) throws NotEnoughException {
        Product productForOrder = productRepository.findProductById(id);
        if (productForOrder.getQuantity()<quantity){
            throw new NotEnoughException();
        }
        return productForOrder;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Product> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findAllByCategoryId(categoryId,pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Product> getFilteredProductsByCategory(Specification<Product> specification, Pageable pageable) {
        return productRepository.findAll(specification,pageable);
    }

    @Transactional
    @Override
    public void subtractQuantity(Product product, int quantity) {
        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
    }

    @Transactional
    @Override
    public void addProduct(ProductDTO productDTO, List<String> imagePaths) {
        Product product = modelMapper.map(productDTO,Product.class);
        List<Image> productImages = new ArrayList<>();
        imagePaths.forEach(imagePath->{
            Image image = new Image();
            image.setPath(imagePath);
            image.setProduct(product);
            productImages.add(image);
        });
        productRepository.save(product);
        imageRepository.saveAll(productImages);
    }
}

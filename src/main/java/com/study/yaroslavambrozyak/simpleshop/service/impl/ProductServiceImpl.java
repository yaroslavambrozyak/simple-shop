package com.study.yaroslavambrozyak.simpleshop.service.impl;

import com.study.yaroslavambrozyak.simpleshop.dto.ProductDTO;
import com.study.yaroslavambrozyak.simpleshop.entity.Image;
import com.study.yaroslavambrozyak.simpleshop.entity.Product;
import com.study.yaroslavambrozyak.simpleshop.exception.NotEnoughException;
import com.study.yaroslavambrozyak.simpleshop.exception.NotFoundException;
import com.study.yaroslavambrozyak.simpleshop.repository.ImageRepository;
import com.study.yaroslavambrozyak.simpleshop.repository.ProductRepository;
import com.study.yaroslavambrozyak.simpleshop.service.ProductService;
import com.study.yaroslavambrozyak.simpleshop.util.ImageUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ModelMapper modelMapper;
    private ImageRepository imageRepository;
    private ImageUtil imageUtil;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper
            , ImageRepository imageRepository, ImageUtil imageUtil) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.imageRepository = imageRepository;
        this.imageUtil = imageUtil;
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    @Override
    public Product getProductForOrder(Long id, Integer quantity) throws NotEnoughException {
        Product productForOrder = productRepository.findProductById(id).orElseThrow(NotFoundException::new);
        if (productForOrder.getQuantity() < quantity) {
            logger.error("Not Enough " + productForOrder.getName());
            throw new NotEnoughException();
        }
        return productForOrder;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Product> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findAllByCategoryId(categoryId, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Product> getFilteredProductsByCategory(Specification<Product> specification, Pageable pageable) {
        return productRepository.findAll(specification, pageable);
    }

    @Transactional
    @Override
    public void subtractQuantity(Product product, int quantity) {
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

    @Transactional
    @Override
    public void addProduct(ProductDTO productDTO, CommonsMultipartFile[] images) {
        Product product = modelMapper.map(productDTO, Product.class);
        List<Image> productImages = new ArrayList<>();
        List<CommonsMultipartFile> commonsMultipartFiles = Arrays.asList(images);
        commonsMultipartFiles.parallelStream().forEach(image -> {
            try {
                String path = imageUtil.saveImage(image);
                Image imageData = new Image();
                imageData.setPath(path);
                imageData.setProduct(product);
                imageData.setPosition(commonsMultipartFiles.indexOf(image));
                productImages.add(imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        productRepository.save(product);
        imageRepository.saveAll(productImages);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

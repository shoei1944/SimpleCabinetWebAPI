package pro.gravit.simplecabinet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.gravit.simplecabinet.web.dto.ProductDto;
import pro.gravit.simplecabinet.web.exception.EntityNotFoundException;
import pro.gravit.simplecabinet.web.model.ProductEntity;
import pro.gravit.simplecabinet.web.service.ProductService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/id/{id}")
    public ProductDto getById(@PathVariable long id) {
        var optional = service.findById(id);
        if(optional.isEmpty()) {
            throw new EntityNotFoundException("Product not found");
        }
        return new ProductDto(optional.get());
    }

    @GetMapping("/page/{pageId}")
    public Iterable<ProductDto> getPage(@PathVariable int pageId) {
        var list = service.findAll(PageRequest.of(pageId, 10));
        return list.stream().map(ProductDto::new).collect(Collectors.toList());
    }
}

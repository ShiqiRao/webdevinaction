package ff.letsgo.smr.controller;

import ff.letsgo.smr.dal.mapper.ProductMapper;
import ff.letsgo.smr.dal.po.Product;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductMapper productMapper;

    @GetMapping("/{id}")
    public Product getProductInfo(@PathVariable long id) {
        return productMapper.select(id);
    }

    @PostMapping("/")
    public Product createProductInfo(Product product) {
        productMapper.insert(product);
        return product;
    }

    @PutMapping("/{id}")
    public Product updateProductInfo(@PathVariable long id, Product newProduct) throws NotFoundException {
        Product product = productMapper.select(id);
        if (product == null) throw new NotFoundException("不存在ID为" + id + "的产品");
        product.setName(newProduct.getName());
        product.setPrice(newProduct.getPrice());
        productMapper.update(product);
        return product;
    }

    @DeleteMapping("/{id}")
    public Product deleteProductInfo(@PathVariable long id) {
        Product product = productMapper.select(id);
        if (product != null) productMapper.delete(id);
        return product;
    }
}

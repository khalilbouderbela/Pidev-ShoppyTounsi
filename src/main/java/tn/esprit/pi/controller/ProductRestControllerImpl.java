package tn.esprit.pi.controller;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.pi.entities.CategoryEnum;
import tn.esprit.pi.entities.Product;

import tn.esprit.pi.service.IProductService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController

@RequestMapping("/product")

public class ProductRestControllerImpl {

	@Autowired
    private  IProductService iProductService;


    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody Product product)  {
       
        String result=iProductService.addProduct(product);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("productById/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable String id) {
        
        Optional<Product> product = iProductService.getById(Long.parseLong(id));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Product>> getAllProductsByPage(@RequestParam(required = false) CategoryEnum categoryType,
                                                              Pageable pageable) {
        
        Page products = null;
        if (categoryType != null) {
            long count = iProductService.getProductsByCategory(categoryType, pageable).getTotalElements();
            products = PageableExecutionUtils.getPage(
                    iProductService.getProductsByCategory(categoryType, pageable).getContent(), pageable, () -> count);
        } else {
            long count = iProductService.getProducts(pageable).getTotalElements();
            products = PageableExecutionUtils.getPage(iProductService.getProducts(pageable).getContent(), pageable,
                    () -> count);
        }
        if (products.getTotalElements() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable String id) {
        
        iProductService.deleteById(Long.parseLong(id));
        return  ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/addImage", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("multipartFile") MultipartFile multipartFile,
                                             @RequestParam("productId") String productId) throws IOException {

        File convertImage = new File("C:\\test\\" + multipartFile.getOriginalFilename());
        convertImage.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertImage);
        fout.write(multipartFile.getBytes());
        fout.close();
        this.iProductService.affecteImageToProduct(multipartFile.getOriginalFilename(), Long.parseLong(productId));
        return new ResponseEntity<>("Image is uploaded successfuly  " + multipartFile.getOriginalFilename(),
                HttpStatus.OK);
    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> allProducts() {
        List<Product> products = this.iProductService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/productByName/{name}")
    public ResponseEntity<Optional<Product>> productByName(@PathVariable String name) {
        Optional<Product> prod = this.iProductService.getByName(name);
        return new ResponseEntity<>(prod, HttpStatus.OK);

    }


    @GetMapping("gain")
    public ResponseEntity<Map<String, String>> calculateGain() throws ParseException {
        return ResponseEntity.ok(iProductService.GainLastOrdersPerMonth());
    }


    @PostMapping("generate-pdf")
    public ResponseEntity<String> generatePDF() throws FileNotFoundException, DocumentException, ParseException {
        return ResponseEntity.ok(iProductService.generatePDF());
    }
    // percentage selled product per orderline
    @GetMapping("product")
    public ResponseEntity<Map<String, Float>> countSelledProduct() {
        Map<String, Float> selledProducts = this.iProductService.countSelledProduct();
        return new ResponseEntity<>(selledProducts, HttpStatus.OK);
    }

    // percentage selled category per orderline
    @GetMapping("category")
    public ResponseEntity<Map<String, Float>> countSelledProductPerCategory() {
        Map<String, Float> selledProducts = this.iProductService.countSelledProductPerCategory();
        return new ResponseEntity<>(selledProducts, HttpStatus.OK);
    }
    
   


}

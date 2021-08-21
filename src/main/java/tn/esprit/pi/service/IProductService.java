package tn.esprit.pi.service;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.itextpdf.text.DocumentException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import tn.esprit.pi.entities.CategoryEnum;
import tn.esprit.pi.entities.Product;

@Repository
public interface IProductService {
	
	Map<String, Float> countSelledProductPerCategory();
	
	Map<String, Float> countSelledProduct();

	public List<Product> getAllProducts();

	public Product updateProduct(Product product);

	Optional<Product> getByName(String name);

	public void affecteImageToProduct(String Image, Long ProductId);

	String addProduct(Product product) ;

	Optional<Product> getById(Long id);

	Page<Product> getProducts(Pageable pageable);

	void deleteById(Long id);

	Page<Product> getProductsByCategory(CategoryEnum categoryType, Pageable pageable);
	
	String generatePDF() throws FileNotFoundException, DocumentException,ParseException;
	
	Map<String, String> GainLastOrdersPerMonth();
}

package tn.esprit.pi.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tn.esprit.pi.entities.CategoryEnum;
import tn.esprit.pi.entities.OrderLine;
import tn.esprit.pi.entities.Orders;
import tn.esprit.pi.entities.Product;
import tn.esprit.pi.repository.OrderLineRepository;
import tn.esprit.pi.repository.OrdersRepository;
import tn.esprit.pi.repository.ProductRepository;

@Service

public class ProductService implements IProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderLineRepository orderLineRepository;
	@Autowired
	private ICategoryService iCategoryService;
	@Autowired
	private OrdersRepository ordersRepository;

	@Override
	public String addProduct(Product product) {
		if (!String.valueOf(product.getCode()).startsWith("619")) {
			return "Product not tunisian";
		}else{
			productRepository.save(product);
			return "Product is Saved";
		
		}
	}

	@Override
	public Optional<Product> getById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public Page<Product> getProducts(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public Page<Product> getProductsByCategory(CategoryEnum categoryType, Pageable pageable) {
		return productRepository.getByCategory(iCategoryService.findByCategoryType(categoryType), pageable);
	}

	@Override
	public List<Product> getAllProducts() {

		return this.productRepository.findAll();
	}

	@Override
	public Product updateProduct(Product product) {

		return this.productRepository.save(product);
	}

	@Override
	public void affecteImageToProduct(String Image, Long ProductId) {
		this.productRepository.affectImageToProduct(Image, ProductId);

	}

	@Override
	public Optional<Product> getByName(String name) {

		return this.productRepository.findByName(name);
	}

	@Override
	public Map<String, Float> countSelledProduct() {
		Map<String, Float> selledProducts = new HashMap<>();
		float tot=Float.valueOf(total());
		System.out.println(tot);
		this.productRepository.findAll().stream().forEach(e -> {
			float count = Float.valueOf(count(e));
			Float res=  (count /tot);
			System.out.println(res);
			selledProducts.put(e.getName(), res);
		});
		return selledProducts;
	}

	@Override
	public Map<String, Float> countSelledProductPerCategory() {
		Map<String, Float> selledCategories = new HashMap<>();
        
		this.productRepository.findAll().stream().forEach(p -> {
			float count = count(p);

			if (selledCategories.containsKey(p.getCategory().getCategoryType().toString())) {
				String key = p.getCategory().getCategoryType().toString();
				float value = selledCategories.get(key);
				selledCategories.put(key, value + (count)/total());
			} else {
				selledCategories.put(p.getCategory().getCategoryType().toString(), count/total());
			}
		});
		return selledCategories;
	}

	private int total() {
		List<OrderLine> orderLines = new ArrayList<>();
		List<Orders> listOrders = ordersRepository.findAll();
	     int total = 0;
		for (Orders orders : listOrders) {

			this.ordersRepository.selectOrderLigneIds(orders.getOrderId()).forEach(id -> {
				orderLines.add(this.orderLineRepository.findById(id).get());
			});
			
			
		}
		for (OrderLine orderl : orderLines) {
			
			total += orderl.getQuantity();
			

		}
		
		return total;
	}

	private int count(Product product) {
		List<OrderLine> orderLines = new ArrayList<>();
		List<Orders> listOrders = ordersRepository.findAll();
		int count = 0;
		for (Orders orders : listOrders) {

			this.ordersRepository.selectOrderLigneIds(orders.getOrderId()).forEach(id -> {
				orderLines.add(this.orderLineRepository.findById(id).get());
			});
			;
			System.out.println(orders.toString() + "\n");
		}
		for (OrderLine orderl : orderLines) {
			if (orderl.getProduct().getProductId() == product.getProductId()) {
				count += orderl.getQuantity();
			}

		}

		return count;
	}

	@Override
	public Map<String, String> GainLastOrdersPerMonth() {
		List<Orders> listOrders = ordersRepository.findAll();
		Map<String, String> totalGains = new HashMap<>();
		LocalDate dateLocal = LocalDate.now().minusMonths(1);
		Date date = Date.from(dateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<OrderLine> orderLine = new ArrayList<>();
		for (Orders orders : listOrders) {
			if (orders.getOrderDate().after(date)) {
				this.ordersRepository.selectOrderLigneIds(orders.getOrderId()).forEach(id -> {
					orderLine.add(this.orderLineRepository.findById(id).get());
				});
				;
				System.out.println(orders.toString() + "\n");
			}
		}
		System.out.println(orderLine.toString());

		orderLine.forEach(o -> {

			if (totalGains.containsKey(o.getProduct().getName().toString())) {

				String key = o.getProduct().getName().toString();
				Float value = Float.valueOf(totalGains.get(key));
				Float prevValue = Float.valueOf(totalGains.get(key));
				Float tot = value + prevValue;
				totalGains.put(key, String.valueOf(tot));
			} else {

				Float gain = (float) o.getPrice();
				totalGains.put(o.getProduct().getName(), String.valueOf(gain));
			}
		});

		float total = 0f;
		for (Map.Entry<String, String> entry : totalGains.entrySet()) {
			total += Float.parseFloat(entry.getValue());
			entry.setValue(entry.getValue().concat(" TND"));

		}
		totalGains.put("total", String.valueOf(total).concat(" TND"));
		return totalGains;
	}

	@Override
	public String generatePDF() throws FileNotFoundException, DocumentException {
		Document document = new Document(PageSize.A4);
		String pdfFileName = "ConsommiTounsi "
				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm")) + ".pdf";
		String pdfPath = "C:\\pdf\\" + pdfFileName;

		PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Paragraph p = new Paragraph("Gain", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		PdfPTable table = new PdfPTable(2);
		table.setSpacingBefore(10);

		writeTableHeader(table);
		Map<String, String> data = GainLastOrdersPerMonth();

		writeTableData(table, data);
		document.add(table);

		// write the total
		Chunk chunk = new Chunk("total : " + data.get("total"), font);
		
		document.add(chunk);
		document.close();
		return "PDF was generated successfuly";
	}

	private void writeTableData(PdfPTable table, Map<String, String> data) {
		for (Map.Entry<String, String> line : data.entrySet()) {
			if (!line.getKey().equals("total")) {
				table.addCell(line.getKey());
				table.addCell(line.getValue());
			}
		}
	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.BLUE);
		cell.setPadding(5);
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.WHITE);
		cell.setPhrase(new Phrase("Product", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Gain", font));
		table.addCell(cell);
	}

}

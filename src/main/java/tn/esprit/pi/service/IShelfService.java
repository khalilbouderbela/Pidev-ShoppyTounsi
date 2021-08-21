package tn.esprit.pi.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import tn.esprit.pi.entities.Category;
import tn.esprit.pi.entities.OrderLine;
import tn.esprit.pi.entities.Orders;
import tn.esprit.pi.entities.Product;
import tn.esprit.pi.entities.Shelf;
import tn.esprit.pi.entities.ShelfRating;
import tn.esprit.pi.entities.ShelfType;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.payload.CategoryRevenulastThreeDays;
import tn.esprit.pi.payload.ShelfRevenu;

public interface IShelfService {

	public String addShelf(Shelf shelf);

	public Shelf updateShelf(Shelf shelf);

	public String DeleteShelfById(long shelfId);

	public List<Shelf> getAllShelfs();

	public Shelf getShelfById(long shelf);

	public int getNombreShelf();

	public List<Shelf> getShelfByType(ShelfType type);

	public String affecterCategoryShelf(long categoryId, long shelfId);

	public String daffecterCategoryShelf(long categoryId, long shelfId);

	public List<String> getAllCategoriesNameByShelfId(long shelfId);

	public List<Category> getAllCategoryByShelfJPQL(long Shelfid);

	public List<String> getAllProductByShelfJPQL(long Shelfid);

	public void deleteRating(long userId ,long shelfId);

	public ShelfRating getRatingbyId(long rating_id);

	public List<ShelfRating> getAllRating();

	public List<User> getUSersByShelf(long shelfId);

	public ShelfRating saveOrUpdateRating(Authentication auth, long Shelfid, int rating);

	public List<Shelf> getShelfs(Authentication auth);

	public List<Product> getOders(Authentication auth);

	public void UpdateReductin(int red ,long shelfId);

	public List<Product> getOrdersByShelf(long shelfId);

	public List<ShelfRevenu> getShelfsRevenu();

	List<Orders> getOrdersLastThreeDays();

	List<CategoryRevenulastThreeDays> getCategoryLastThreeDays();



	List<Product> getOrdersByCategory(long categoryId);

	public void deleteShelfByDate();

   
   
}

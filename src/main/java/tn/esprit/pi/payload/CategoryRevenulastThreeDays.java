package tn.esprit.pi.payload;

import tn.esprit.pi.entities.Category;

public class CategoryRevenulastThreeDays implements Comparable<CategoryRevenulastThreeDays> {
	
	String categoryName;
	float revenu ;
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public float getRevenu() {
		return revenu;
	}
	public void setRevenu(float revenu) {
		this.revenu = revenu;
	}
	@Override
	public int compareTo(CategoryRevenulastThreeDays o) {
		return (int) -(this.revenu-o.revenu);
		
	}
	

}

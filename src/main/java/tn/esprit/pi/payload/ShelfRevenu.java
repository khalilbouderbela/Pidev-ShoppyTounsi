package tn.esprit.pi.payload;

import tn.esprit.pi.entities.Shelf;

public class ShelfRevenu implements Comparable<ShelfRevenu> {
	
	
	private String shelfName;
	private float revenu;
	public String getShelf() {
		return shelfName;
	}
	public void setShelf(String shelf) {
		this.shelfName = shelf;
	}
	public float getRevenu() {
		return revenu;
	}
	public void setRevenu(float revenu) {
		this.revenu = revenu;
	}
	@Override
	public int compareTo(ShelfRevenu o) {
		return (int) -(this.revenu-o.revenu);
	}
	
	
	

}

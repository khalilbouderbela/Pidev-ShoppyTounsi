package tn.esprit.pi.payload;

import java.util.Comparator;

import tn.esprit.pi.entities.Shelf;

public class ShelfOcc implements Comparable<ShelfOcc>{
	private Shelf shelf;
	private int occ;
	public ShelfOcc() {
		super();
	}
	public int getOcc() {
		return occ;
	}
	public void setOcc(int occ) {
		this.occ = occ;
	}
	public Shelf getShelf() {
		return shelf;
	}
	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}
	
	@Override
	public int compareTo(ShelfOcc o) {
		// TODO Auto-generated method stub
		return -(this.occ-o.occ);
	}
	
	

}

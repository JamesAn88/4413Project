package com.foodrus.bean;

import java.util.Comparator;

import com.foodrus.bean.vo.Item;

public class ShoppingItem implements DomainBean, Comparator<ShoppingItem> {
	// *** Serial Number
	private static final long serialVersionUID = -7514811715857864868L;
	
	private Item item;
	private String qty;
	
	public ShoppingItem() {
		super();
	}
	public ShoppingItem(Item item, String qty) {
		super();
		this.item = item;
		this.qty = qty;
	}

	public ShoppingItem(Item item) {
		super();
		this.item = item;
	}

	public ShoppingItem(String qty) {
		super();
		this.qty = qty;
	}


	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public double getPriceBeforeTax() {
		double priceBeforeTax = 0d;
		try{
			priceBeforeTax = item.getPrice() * Integer.valueOf(qty);
		} catch(Exception e){
			System.err.println("Could not compute cost in ShoppingItem");
		}
		return priceBeforeTax;
	}

	@Override
	public int compare(ShoppingItem obj1, ShoppingItem obj2) {
		return obj1.item.getNumber().compareToIgnoreCase(obj2.item.getNumber());
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean equals = true;
		if (obj == null) {
			equals = false;
		} else if (getClass() != obj.getClass()) {
			equals = false;
		} else {
			ShoppingItem other = (ShoppingItem) obj;
			if ((this.item == null) ? (other.item != null) : 
				!this.item.getNumber().equals(other.item.getNumber())) {
				equals = false;
			}
		}
		return equals;
	}
	
	@Override
	public int hashCode() {
	    int hash = 3;
	    hash = 53 * hash + ((this.item != null && this.item.getNumber() != null) ? 
	    		this.item.getNumber().hashCode() : 0);
	    return hash;
	}
	
	/**
	 * add more quantity to this shopping item
	 * @param addedQty
	 */
	public void addQty(String addedQty) {
		try{
			int currentQtyAsInt = Integer.valueOf(this.qty);
			int addedQtyAsInt = Integer.valueOf(addedQty);
			int newQtyAsInt = currentQtyAsInt + addedQtyAsInt;
			this.qty = String.valueOf(newQtyAsInt);
		} catch(Exception e){
			System.err.println("Could not add quantity ["+addedQty+"] for shopping item["+this+"]");
		}
	}
	
	@Override
	public String toString() {
		return "ShoppingItem [item=" + item + ", qty=" + qty + "]";
	}
}

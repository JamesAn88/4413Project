package com.foodrus.bean;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.foodrus.bean.vo.Item;

public class ShoppingCart implements DomainBean{
	// *** Serial number
	private static final long serialVersionUID = 7421082833333095713L;

	private Set<ShoppingItem> items;
	private int itemCount;
	private double subTotal;
	
	public ShoppingCart() {
		super();
		items = new HashSet<>();
	}
	
	public void addItem(ShoppingItem shoppingItem) {
		ShoppingItem existingItem = this.containsItem(shoppingItem);
		if(existingItem != null){
			existingItem.addQty(shoppingItem.getQty());
		} else if(shoppingItem != null){
			this.items.add(shoppingItem);
		}
		itemCount = getTotalItems();
		subTotal = getTotalPriceBeforeTax();
	}

	public void updateItem(ShoppingItem shoppingItem){
		if(shoppingItem != null){
			removeItem(shoppingItem);
			this.items.add(shoppingItem);
			itemCount = getTotalItems();
			subTotal = getTotalPriceBeforeTax();
		}
	}

	public void removeItem(ShoppingItem shoppingItem) {
		if(shoppingItem != null){
			this.items.remove(shoppingItem);
			itemCount = getTotalItems();
			subTotal = getTotalPriceBeforeTax();
		}
	}

	public void removeItem(Item item) {
		if(item !=null){
			this.removeItem(new ShoppingItem(item));
			itemCount = getTotalItems();
			subTotal = getTotalPriceBeforeTax();
		}
	}

	public void removeItem(String itemId) {
		if(itemId != null){
			Item item = new Item();
			item.setNumber(itemId);
			this.removeItem(item);
			itemCount = getTotalItems();
			subTotal = getTotalPriceBeforeTax();
		}
	}

	public void emptyCart(){
		items.clear();
		itemCount = getTotalItems();
		subTotal = getTotalPriceBeforeTax();
	}
	
	public Set<ShoppingItem> getItems() {
		return Collections.unmodifiableSet(items);
	}
	
	public double getTotalPriceBeforeTax() {
		double totalPriceBeforeTax = 0d;
		for(ShoppingItem shoppingItem : items){
			totalPriceBeforeTax += shoppingItem.getPriceBeforeTax();
		}
		return totalPriceBeforeTax;
	}
	
	public int getTotalItems() {
		int totalItems = 0;
		try{
			for(ShoppingItem shoppingItem : items){
				int shoppingItemQty = Integer.valueOf(shoppingItem.getQty());
				totalItems += shoppingItemQty;
			}
		} catch(Exception e){
			System.err.println("Exception was thrown trying to compute totalItems in ShoppingCart");
		}
		return totalItems;
	}
	
	private ShoppingItem containsItem(ShoppingItem shoppingItem) {
		ShoppingItem existingItem = null;
		Iterator<ShoppingItem> it = items.iterator();
		boolean found = false;
		while(!found && it.hasNext()){
			ShoppingItem temp = it.next();
			found = temp.equals(shoppingItem);
			if(found){
				existingItem = temp;
			}
		}
		return existingItem;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("ShoppingCart [items=");
		for(Iterator<ShoppingItem>it=items.iterator(); it.hasNext() ;){
			sb.append(it.next().toString());
		}
		sb.append("]");
		return  sb.toString();
	}

	public int getItemCount() {
		return itemCount;
	}

	public double getSubTotal() {
		return subTotal;
	}

}

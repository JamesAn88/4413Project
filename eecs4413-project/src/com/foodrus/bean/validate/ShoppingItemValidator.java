package com.foodrus.bean.validate;

import java.util.ArrayList;
import java.util.List;

import com.foodrus.bean.DomainBean;
import com.foodrus.bean.ShoppingItem;
import com.foodrus.bean.vo.Item;

public class ShoppingItemValidator implements Validator{

	public ShoppingItemValidator() {
		super();
	}

	@Override
	public List<String> validate(DomainBean form) {
		ShoppingItem shoppingItem = (ShoppingItem) form;
		List<String> errors = null;
		if(shoppingItem != null){
			errors = new ArrayList<>();
			this.validateQuantity(shoppingItem, errors);
			if(errors.isEmpty()){
				this.validateItem(shoppingItem, errors);
			}
		}
		return errors;
	}

	private void validateItem(ShoppingItem shoppingItem, List<String> errors) {
		Item item = shoppingItem.getItem();
		int purchaseQty = Integer.valueOf(shoppingItem.getQty());
		if(item != null){
			int avilableQty = Integer.valueOf(item.getQty());
			if(purchaseQty > avilableQty){
//				errors.add("Quantity requested ["+purchaseQty+"] is more than avilable ["+avilableQty+"]");
			}
		} 
	}

	private void validateQuantity(ShoppingItem shoppingItem, List<String> errors) {
		String qty = shoppingItem.getQty();
		if(qty == null || qty.trim().isEmpty()){
			errors.add("Quantity can not be empty!");
		} else {
			int qtyAsInt;
			try{
				qtyAsInt = Integer.valueOf(qty);
				if(qtyAsInt <= 0){
					errors.add("Quantity ["+qty+"] must be positive number!");
				}
			} catch(Exception e){
				errors.add("Quantity ["+qty+"] must be a number!");
			}
		}
	}
}
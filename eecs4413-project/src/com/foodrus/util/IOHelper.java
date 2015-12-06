package com.foodrus.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import com.foodrus.bean.vo.Category;

public final class IOHelper {

	private IOHelper() {
	}
	

	/**
	 * deSerialize String to object 
	 * @param str to be deSerialize
	 * @return an object of the deSerialized String 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deSerialize(String str) throws IOException ,
		ClassNotFoundException {
		byte [] data = Base64.getDecoder().decode(str);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
		Object obj  = ois.readObject();
		ois.close();
		return obj;
	}

	/**
	 * serialize object to String
	 * @param obj to be serialized
	 * @return String serialization.
	 * @throws IOException
	 */
	public static String serialize (Object obj) throws IOException {
		if(!(obj instanceof Serializable)){
			throw new IOException("Object is not Serializable");
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		oos.close();
		return Base64.getEncoder().encodeToString(baos.toByteArray()); 
	}

	/**
	 * convert a list of String to a String
	 * @param list to be converted to string
	 * @return the list as String
	 */
	public static String listToString (List<String> list) {
		StringBuffer sb = new StringBuffer();
		if(list != null){
			for(String str : list){
				sb.append(str);
			}
		}
		return sb.toString(); 
	}
	
	/**
	 * write the pictures of Categories to local storage
	 * then release the picture and by setting to null
	 * @param outputFolder
	 * @param cats
	 * @throws IOException
	 */
	public static void resolvePictures(String outputFolder, List<Category> cats) throws IOException {
		if(cats != null){
			for(Category cat : cats){
				Path path = Paths.get(outputFolder, String.valueOf(cat.getId()));
				OutputStream out = new BufferedOutputStream(new FileOutputStream(path.toFile()));
				out.write(cat.getPicture());
				out.close();
				// release the picture bytes to save memory
				cat.setPicture(null); 
			}
		}
	}
	
	public static void resolvePurchaseOrders(){
		
	}
}

package com.foodrus.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.List;

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

}

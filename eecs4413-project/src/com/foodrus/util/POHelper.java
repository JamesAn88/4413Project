package com.foodrus.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.foodrus.util.Constants.POConst;

public class POHelper {
	
	private static File getPOFile(File userDir) throws IOException{
		File file = null;
		try{
			String filePath = POConst.PREFIX;
			int count = userDir.list().length;
			if (count < 10){
				filePath = filePath + "0";
			}
			filePath = filePath + Integer.toString(count++);
			filePath = filePath + POConst.SUFFIX;
			file = Files.createFile(userDir.toPath().resolve(filePath)).toFile();
		} catch(Exception ex) {
			throw new IOException("Could not create PO file in user dir ["+userDir+"]", ex);
		}
		return file;
	}
	
	public static File getUserDir(String user, String poDir) throws IOException{
		File file = null;
		try{
			if(!Files.exists(Paths.get(poDir, user))){
				file = Files.createDirectory(Paths.get(poDir, user)).toFile();
			}
		} catch (Exception ex){
			throw new IOException("Could not create user dir for user ["+user+"], poDir["+poDir+"]", ex);
		}
		return file; 
	}
	
	public static File createPO(OrderType order, String poDir) throws IOException{
		File poFile = null;
		try {
			JAXBContext jc = JAXBContext.newInstance(order.getClass());
			Marshaller marsh = jc.createMarshaller();
			marsh.setProperty("com.sun.xml.internal.bind.xmlHeaders", 
					"<?xml-stylesheet type='text/xsl' href='XML/style.xsl'?>");
			marsh.setProperty(Marshaller.JAXB_FRAGMENT, true);
			File userDir = getUserDir(order.getCustomer().getAccount(), poDir);
			poFile = getPOFile(userDir);
			marsh.marshal(order, poFile);
		} catch (JAXBException e) {
			throw new IOException("Could not marshall file for order ["+order+"]"
					+ " and poDir ["+poDir+"]", e);
		}
		return poFile;
	}
}

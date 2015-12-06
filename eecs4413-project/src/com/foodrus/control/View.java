package com.foodrus.control;

public class View {
	public static final int INCLUDE = 0;
	public static final int FORWARD = 1;
	public static final int REDIRECT= 2;
	
	private int dispatchType;
	private String path;
	
	public View() {
		super();
	}

	public View(int dispatchType, String path) {
		super();
		this.dispatchType = dispatchType;
		this.path = path;
	}

	public View(String path, int dispatchType) {
		super();
		this.dispatchType = dispatchType;
		this.path = path;
	}

	public int getDispatchType() {
		return dispatchType;
	}

	public void setDispatchType(int dispatchType) {
		this.dispatchType = dispatchType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}

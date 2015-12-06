package com.foodrus.tags;

import java.io.IOException;

import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Math extends SimpleTagSupport{

	// *** attributes
	private double operand1;
	private double operand2;
	private String operation;
	
	public Math() {
		super();
	}

	@Override
	public void doTag() throws IOException{
		this.getJspContext().getOut().print(doTheMath());
	}

	private double doTheMath() {
		double result;
		switch(operation.trim().toLowerCase()){
			case "add":
				result = operand1 + operand2;
			break;

			case "mult":
				result = operand1 * operand2;
			break;

			case "div":
				result = operand1 / operand2;
			break;

			case "sub":
				result = operand1 - operand2;
			break;

			default:
				result = 0;
			break;
		}
		return result;
	}

	public double getOperand1() {
		return operand1;
	}
	public void setOperand1(double operand1) {
		this.operand1 = operand1;
	}
	public double getOperand2() {
		return operand2;
	}
	public void setOperand2(double operand2) {
		this.operand2 = operand2;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
}

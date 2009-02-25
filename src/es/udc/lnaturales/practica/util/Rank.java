package es.udc.lnaturales.practica.util;

public class Rank {
	
	String first="";
	String second="";
	String third="";
	
	public void setFirst(String first) {
		this.first = first;
	}
	public void setSecond(String second) {
		this.second = second;
	}
	public void setThird(String third) {
		this.third = third;
	}
	
	public String toString(){
		return first + "\n" + second + "\n" + third;
	}

}

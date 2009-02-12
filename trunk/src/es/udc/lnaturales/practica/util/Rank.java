package es.udc.lnaturales.practica.util;

public class Rank {
	
	private Result first = new Result("null", 0);
	private Result second = new Result("null", 0);
	private Result third = new Result("null", 0);
	
	public void setFirst(Result first) {
		this.first = first;
	}
	public void setSecond(Result second) {
		this.second = second;
	}
	public void setThird(Result third) {
		this.third = third;
	}
	
	public String toString(){
		return first + "\n" + second + "\n" + third;
	}

}

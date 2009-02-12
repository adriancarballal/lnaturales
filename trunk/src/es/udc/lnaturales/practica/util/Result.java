package es.udc.lnaturales.practica.util;

public class Result {
	
	private String result;
	private int probability;
	
	public Result(String result, float probability){
		this.result = result;
		probability = probability*100;
		this.probability = java.lang.Math.round(probability);

		
	}
	
	public String toString(){
		return result + " (" + probability + "%)";
	}

}

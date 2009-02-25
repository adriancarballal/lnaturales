package es.udc.lnaturales.practica.util;

import java.util.ArrayList;
import java.util.List;

public class Appearances {
	
	//HashMap<String, Integer> app;
	List<String> result;
	List<String> docId;
	List<Integer> numAppearances;
	
	public Appearances(){
		 this.result = new ArrayList<String>();
		 this.docId = new ArrayList<String>();
		 this.numAppearances = new ArrayList<Integer>();
	}
	
	public void addAppearance(String key, String docid){
		boolean existe=false;
		int posicion = -1;
		for (int i=0;i<result.size();i++) {
			if (result.get(i).equals(key)) {
				existe=true;
				posicion=i;
			}
		}
		if (existe) {
			int temp= this.numAppearances.get(posicion)+1;
			this.numAppearances.set(posicion, (Integer) temp);
		}
		else {
			this.docId.add(docid);
			this.result.add(key);
			this.numAppearances.add(Integer.valueOf(1));
		}
			
	}
	
	public String toString(){
		
		Rank rank = new Rank();
		String e="ex";
		String respuesta;
		
		int pos = -1; int val = 0;
		for (int i = 0; i < this.numAppearances.size(); i++) {
			if(this.numAppearances.get(i)>val){
				val=this.numAppearances.get(i);
				pos=i;
			}
		}
		String primero;
		if (pos==-1) {
			primero = "1 plnaex031ms NIL";
			rank.setFirst(primero);
		}
		else {
			respuesta = this.result.get(pos);
			if (respuesta.contains("_")) {
				respuesta=this.result.get(pos).replaceAll("_", " ");
				e="st";
			}
			primero = "1 "+ "plna"+e+"031ms " +this.docId.get(pos) +" "+ this.numAppearances.get(pos)+ " "+ respuesta; 
			rank.setFirst(primero);
			this.result.remove(pos);
			this.docId.remove(pos);
			this.numAppearances.remove(pos);
		}
		pos = -1;val = 0;
		for (int i = 0; i < this.numAppearances.size(); i++) {
			if(this.numAppearances.get(i)>val){
				val=this.numAppearances.get(i);
				pos=i;
			}
		}
		String segundo;
		e="ex";
		if (pos==-1) {
			segundo = "2 plnaex031ms NIL";
			rank.setSecond(segundo);
		}
		else {
			respuesta = this.result.get(pos);
			if (respuesta.contains("_")) {
				respuesta=this.result.get(pos).replaceAll("_", " ");
				e="st";
			}
			
			segundo = "2 "+ "plna"+e+"031ms " + this.docId.get(pos) +" "+ this.numAppearances.get(pos)+ " "+ respuesta; 
			rank.setSecond(segundo);
			this.result.remove(pos);
			this.docId.remove(pos);
			this.numAppearances.remove(pos);
		}
		pos = -1;val = 0;
		for (int i = 0; i < this.numAppearances.size(); i++) {
			if(this.numAppearances.get(i)>val){
				val=this.numAppearances.get(i);
				pos=i;
			}
		}
		String tercero;
		e="ex";
		if (pos==-1) {
			tercero = "3 plnaex031ms NIL";
			rank.setThird(tercero);
		}
		else {
			respuesta = this.result.get(pos);
			if (respuesta.contains("_")) {
				respuesta=this.result.get(pos).replaceAll("_", " ");
				e="st";
			}
			tercero = "3 "+ "plna"+e+"031ms " + this.docId.get(pos) +" "+ this.numAppearances.get(pos)+" "+ respuesta; 
			rank.setThird(tercero);
			this.result.remove(pos);
			this.docId.remove(pos);
			this.numAppearances.remove(pos);
		}

		return rank.toString();		
	}
	
	

}

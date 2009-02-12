package es.udc.lnaturales.practica.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Appearances {
	
	HashMap<String, Integer> app;
	
	public Appearances(){
		this.app = new HashMap<String, Integer>();
	}
	
	public void addAppearance(String key){
		if(app.containsKey(key)){
			int i = app.get(key).intValue();
			app.remove(key);
			app.put(key, i+1);
		}
		else app.put(key, 1);		
	}
	
	public String toString(){
		
		Rank rank = new Rank();
		
		Object[] keys = (app.keySet().toArray());
		List<String> k = new ArrayList<String>();
		for (int i = 0; i < keys.length; i++) k.add((String)keys[i]);
			
		Object[] values = app.values().toArray();
		List<Integer> t = new ArrayList<Integer>();
		for (int i = 0; i < values.length; i++) t.add((Integer)values[i]);
		
		int total = 0; int pos = -1; int val = 0;
		for (int i = 0; i < t.size(); i++) {
			if(t.get(i)>val){
				val=t.get(i);
				pos=i;
			}
			if(t.get(i)>1) total+=t.get(i);
		}
		if(pos!=-1){
			rank.setFirst(new Result(k.get(pos), (float)((t.get(pos)))/(float)(total)));
			t.remove(pos); k.remove(pos);
			pos = -1; val = 0;
			for (int i = 0; i < t.size(); i++) {
				if(t.get(i)>val){
					val=t.get(i);
					pos=i;
				}
				if(t.get(i)>1) total+=t.get(i);
			}
			if(pos!=-1){
				rank.setSecond(new Result(k.get(pos), (float)((t.get(pos)))/(float)(total)));
				t.remove(pos); k.remove(pos);
				pos = -1; val = 0;
				for (int i = 0; i < t.size(); i++) {
					if(t.get(i)>val){
						val=t.get(i);
						pos=i;
					}
					if(t.get(i)>1) total+=t.get(i);
				}
				if(pos!=-1){
					rank.setThird(new Result(k.get(pos), (float)((t.get(pos)))/(float)(total)));
				}
			}
		}
		return rank.toString();		
	}
	
	

}

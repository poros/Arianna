package fr.eurecom.mobserv.arianna.mapobject;

import com.google.gson.annotations.Expose;

public class GPoint {
	@Expose
	private int x;
	@Expose
	private int y;
	
	public GPoint(){
		
	}
	
	
	public GPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
}

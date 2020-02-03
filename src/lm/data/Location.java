/*
 * Class that defines a named location in the game
 */

package lm.data;

import org.eclipse.swt.graphics.RGB;

public class Location {
	private int x, y = 0;
	private String name = "";
	private RGB color = new RGB(0,0,0);
	
	public Location(int x, int y, String name, RGB color) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.color = color;
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public RGB getColor() {
		return this.color;
	}
	
	public void setColor(RGB col) {
		this.color = col;
	}
}

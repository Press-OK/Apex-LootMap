/*
 * Class that defines an item in the game
 */

package lm.data;

import java.io.Serializable;

public class LootItem implements Serializable{
	
	private String name = "";
	private String quality = "MISS";
	private long timeFound = 0;
	private int locationX = 0;
	private int locationY = 0;
	private String locationName = "";
	private double distanceFromStart = 0;
	private double distanceFromEnd = 0;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public long getTimeFound() {
		return timeFound;
	}
	public void setTimeFound(long timeFound) {
		this.timeFound = timeFound;
	}
	public int getLocationX() {
		return locationX;
	}
	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}
	public int getLocationY() {
		return locationY;
	}
	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public double getDistanceFromStart() {
		return distanceFromStart;
	}
	public void setDistanceFromStart(double distanceFromStart) {
		this.distanceFromStart = distanceFromStart;
	}
	public double getDistanceFromEnd() {
		return distanceFromEnd;
	}
	public void setDistanceFromEnd(double distanceFromEnd) {
		this.distanceFromEnd = distanceFromEnd;
	}
}

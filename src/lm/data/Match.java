/*
 * Class that holds all of the data for a single match
 */

package lm.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Match implements Serializable{
		private String day = "";
		private int date = 0;
		private int shipStartX = 0;
		private int shipStartY = 0;
		private int shipEndX = 0;
		private int shipEndY = 0;
		private String dropLocation = "";
		private int numItems = 0;
		private int numArmor = 0;
		private int numMed = 0;
		private int numBag = 0;
		private int numMod = 0;
		private int numBlue = 0;
		private int numPurple = 0;
		private int numGold = 0;
		private double avgDistFromStartAll = 0;
		private double avgDistFromEndAll = 0;
		private double avgDistFromStartBlue = 0;
		private double avgDistFromEndBlue = 0;
		private double avgDistFromStartPurple = 0;
		private double avgDistFromEndPurple = 0;
		private double avgDistFromStartGold = 0;
		private double avgDistFromEndGold = 0;
		private int lvlPlayer1 = 0;
		private int lvlPlayer2 = 0;
		private int lvlPlayer3 = 0;
		private int ownKillsLast = 0;
		private int sqdKillsLast = 0;
		private long matchLength = 0;
		private String lastMatchLength = "";
		private ArrayList<LootItem> lootItems = new ArrayList<LootItem>();
		
		public String getDay() {
			return day;
		}
		public void setDay(String day) {
			this.day = day;
		}
		public int getDate() {
			return date;
		}
		public void setDate(int date) {
			this.date = date;
		}
		public int getShipStartX() {
			return shipStartX;
		}
		public void setShipStartX(int shipStartX) {
			this.shipStartX = shipStartX;
		}
		public int getShipStartY() {
			return shipStartY;
		}
		public void setShipStartY(int shipStartY) {
			this.shipStartY = shipStartY;
		}
		public int getShipEndX() {
			return shipEndX;
		}
		public void setShipEndX(int shipEndX) {
			this.shipEndX = shipEndX;
		}
		public int getShipEndY() {
			return shipEndY;
		}
		public void setShipEndY(int shipEndY) {
			this.shipEndY = shipEndY;
		}
		public String getDropLocation() {
			return dropLocation;
		}
		public void setDropLocation(String dropLocation) {
			this.dropLocation = dropLocation;
		}
		public int getNumItems() {
			return numItems;
		}
		public void setNumItems(int numItems) {
			this.numItems = numItems;
		}
		public int getNumArmor() {
			return numArmor;
		}
		public void setNumArmor(int numArmor) {
			this.numArmor = numArmor;
		}
		public int getNumMed() {
			return numMed;
		}
		public void setNumMed(int numMed) {
			this.numMed = numMed;
		}
		public int getNumBag() {
			return numBag;
		}
		public void setNumBag(int numBag) {
			this.numBag = numBag;
		}
		public int getNumMod() {
			return numMod;
		}
		public void setNumMod(int numMod) {
			this.numMod = numMod;
		}
		public double getAvgDistFromStartAll() {
			return avgDistFromStartAll;
		}
		public void setAvgDistFromStartAll(double avgDistFromStartAll) {
			this.avgDistFromStartAll = avgDistFromStartAll;
		}
		public double getAvgDistFromEndAll() {
			return avgDistFromEndAll;
		}
		public void setAvgDistFromEndAll(double avgDistFromEndAll) {
			this.avgDistFromEndAll = avgDistFromEndAll;
		}
		public double getAvgDistFromStartBlue() {
			return avgDistFromStartBlue;
		}
		public void setAvgDistFromStartBlue(double avgDistFromStartBlue) {
			this.avgDistFromStartBlue = avgDistFromStartBlue;
		}
		public double getAvgDistFromEndBlue() {
			return avgDistFromEndBlue;
		}
		public void setAvgDistFromEndBlue(double avgDistFromEndBlue) {
			this.avgDistFromEndBlue = avgDistFromEndBlue;
		}
		public double getAvgDistFromStartPurple() {
			return avgDistFromStartPurple;
		}
		public void setAvgDistFromStartPurple(double avgDistFromStartPurple) {
			this.avgDistFromStartPurple = avgDistFromStartPurple;
		}
		public double getAvgDistFromEndPurple() {
			return avgDistFromEndPurple;
		}
		public void setAvgDistFromEndPurple(double avgDistFromEndPurple) {
			this.avgDistFromEndPurple = avgDistFromEndPurple;
		}
		public double getAvgDistFromStartGold() {
			return avgDistFromStartGold;
		}
		public void setAvgDistFromStartGold(double avgDistFromStartGold) {
			this.avgDistFromStartGold = avgDistFromStartGold;
		}
		public double getAvgDistFromEndGold() {
			return avgDistFromEndGold;
		}
		public void setAvgDistFromEndGold(double avgDistFromEndGold) {
			this.avgDistFromEndGold = avgDistFromEndGold;
		}
		public int getLvlPlayer1() {
			return lvlPlayer1;
		}
		public void setLvlPlayer1(int lvlPlayer1) {
			this.lvlPlayer1 = lvlPlayer1;
		}
		public int getLvlPlayer2() {
			return lvlPlayer2;
		}
		public void setLvlPlayer2(int lvlPlayer2) {
			this.lvlPlayer2 = lvlPlayer2;
		}
		public int getLvlPlayer3() {
			return lvlPlayer3;
		}
		public void setLvlPlayer3(int lvlPlayer3) {
			this.lvlPlayer3 = lvlPlayer3;
		}
		public int getOwnKillsLast() {
			return ownKillsLast;
		}
		public void setOwnKillsLast(int ownKillsLast) {
			this.ownKillsLast = ownKillsLast;
		}
		public int getSqdKillsLast() {
			return sqdKillsLast;
		}
		public void setSqdKillsLast(int sqdKillsLast) {
			this.sqdKillsLast = sqdKillsLast;
		}
		public long getMatchLength() {
			return matchLength;
		}
		public void setMatchLength(long matchLength) {
			this.matchLength = matchLength;
		}
		public String getLastMatchLength() {
			return lastMatchLength;
		}
		public void setLastMatchLength(String lastMatchLength) {
			this.lastMatchLength = lastMatchLength;
		}
		public ArrayList<LootItem> getLootItems() {
			return lootItems;
		}
		public void setLootItems(ArrayList<LootItem> lootItems) {
			this.lootItems = lootItems;
		}
		public int getNumBlue() {
			return numBlue;
		}
		public void setNumBlue(int numBlue) {
			this.numBlue = numBlue;
		}
		public int getNumPurple() {
			return numPurple;
		}
		public void setNumPurple(int numPurple) {
			this.numPurple = numPurple;
		}
		public int getNumGold() {
			return numGold;
		}
		public void setNumGold(int numGold) {
			this.numGold = numGold;
		}
}

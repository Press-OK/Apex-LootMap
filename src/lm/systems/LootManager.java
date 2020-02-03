/*
 * Manages a set of acquired loot per game.
 */

package lm.systems;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import lm.data.LootItem;

public class LootManager implements Serializable{
	private ArrayList<LootItem> lootItems = new ArrayList<LootItem>();
	
//	// Debug print a loot file...
//	public LootManager() {
//		
////		loadLootSet("out/loot/190405_23-47.loot");
//		loadLootSet("out/loot/MASTER.loot");
//		double distStart = 0;
//		double distEnd = 0;
//		int numitems = 0;
//		int numblue = 0;
//		int numpurp = 0;
//		int numgold = 0;
//		int numarm = 0;
//		int nummed = 0;
//		int numbag = 0;
//		int nummod = 0;
//		for (LootItem l : lootItems) {
//			numitems += 1;
//			distStart += l.getDistanceFromStart();
//			distEnd += l.getDistanceFromEnd();
//			if (l.getQuality().equals("Blue")) { numblue += 1; }
//			if (l.getQuality().equals("Purple")) { numpurp += 1; }
//			if (l.getQuality().equals("Gold")) { numgold += 1; }
//			if (l.getName().equals("Armor")) { numarm += 1; }
//			if (l.getName().equals("Med")) { nummed += 1; }
//			if (l.getName().equals("Bag")) { numbag += 1; }
//			if (l.getName().equals("Mod")) { nummod += 1; }
//		}
//		distStart = distStart / numitems;
//		distEnd = distEnd / numitems;
//		System.out.println("# Items blue or above: " + numitems + "\nAverage dist from ship start: "+distStart+
//				"\nAverage dist from ship end: "+distEnd+"\nBlue: "+numblue+"  Purp: "+numpurp+"  Gold: "+numgold+
//				"\nArmor: "+numarm+"  Med: "+nummed+"  Bag: "+numbag+"  Mod: "+nummod);
//	}
	
	public void addItem(LootItem li) {
		this.lootItems.add(li);
	}
	
	public ArrayList<LootItem> getItems() {
		return this.lootItems;
	}
	
	@SuppressWarnings("unchecked")
	public void loadLootSet(String path) {
		try {
		    FileInputStream fileInputStream = new FileInputStream(path);
		    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		    this.lootItems = (ArrayList<LootItem>) objectInputStream.readObject();
		    objectInputStream.close();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	
	public void saveLootSet(String path) {
		try {
		    FileOutputStream fileOutputStream = new FileOutputStream(path);
		    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		    objectOutputStream.writeObject(lootItems);
		    objectOutputStream.flush();
		    objectOutputStream.close();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}

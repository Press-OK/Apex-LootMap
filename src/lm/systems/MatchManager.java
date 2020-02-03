/*
 * Manages match data for multiple matches.
 */

package lm.systems;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import lm.data.Match;

public class MatchManager implements Serializable{
	private ArrayList<Match> matches = new ArrayList<Match>();

	public void addMatch(Match m) {
		this.matches.add(m);
	}
	
	public ArrayList<Match> getMatches() {
		return this.matches;
	}
	
	@SuppressWarnings("unchecked")
	public void loadMatchSet(String path) {
		try {
		    FileInputStream fileInputStream = new FileInputStream(path);
		    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		    this.matches = (ArrayList<Match>) objectInputStream.readObject();
		    objectInputStream.close();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	
	public void saveMatchSet(String path) {
		try {
		    FileOutputStream fileOutputStream = new FileOutputStream(path);
		    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		    objectOutputStream.writeObject(matches);
		    objectOutputStream.flush();
		    objectOutputStream.close();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}
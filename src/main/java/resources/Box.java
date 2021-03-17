package resources;

import java.util.ArrayList;

import org.bson.types.ObjectId;

public class Box {
	public ObjectId _id;
	public String boxName, email, passHash;
	
	public ArrayList<String> users;
	public ArrayList<String> timeSegments;
	
	@Override
	public String toString() {
		return boxName;
	}
}

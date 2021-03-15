package resources;

import java.util.ArrayList;

import org.bson.types.ObjectId;

public class Userr {
	public ObjectId _id;
	public String email,
			passHash,
			firstName,
			lastName;
	public ArrayList<String> boxes;
	public ArrayList<String> timeSegments;

	@Override
	public String toString() {
		String str = "User: " + firstName + " " + lastName + " :: " + email + " >> " + _id;
		return str;
	}
}

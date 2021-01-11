package resources;

import java.util.ArrayList;

import org.bson.types.ObjectId;

public class Userr {
	public ObjectId _id;
	public String email,
			passHash,
			contact;
	public ArrayList<String> timeSegments;
	@Override
	public String toString() {
		return email;
	}
}


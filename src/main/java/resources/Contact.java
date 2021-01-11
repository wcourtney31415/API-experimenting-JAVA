package resources;

import org.bson.types.ObjectId;

public class Contact {
	public ObjectId _id;
	public String firstName,
			lastName,
			phoneNumber,
			email;

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}

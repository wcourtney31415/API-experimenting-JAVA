package resources;

import org.bson.types.ObjectId;

public class Company {
	public ObjectId _id;
	public String name,
			address,
			email,
			passHash,
			primaryContact,
			secondaryContact;

	@Override
	public String toString() {
		return name;
	}
}

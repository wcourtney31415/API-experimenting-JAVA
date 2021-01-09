package resources;
import org.bson.types.ObjectId;

public class Userr {
	public ObjectId _id;
	public String firstName;
	public String lastName;
	public String email;
	public String username;
	public String passHash;

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}

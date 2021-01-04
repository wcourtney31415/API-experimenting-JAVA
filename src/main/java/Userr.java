import org.bson.types.ObjectId;

public class Userr {
	public ObjectId _id;
	public String firstName;
	public String lastName;

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}

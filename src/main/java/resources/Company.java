package resources;
import org.bson.types.ObjectId;

public class Company {
	public ObjectId _id;
	public String name;
	
	@Override
	public String toString() {
		return name;
	}
}

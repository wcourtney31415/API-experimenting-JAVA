package mongoDb;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoCollection;

import resources.Userr;

public class MongoUser extends MongoResource<Userr> {

	MongoCollection<Userr> userCollection = getCollection("User", Userr.class);

	@Override
	public Userr get(String idString) {
		return userCollection
				.find()
				.filter(withId(idString))
				.first();
	}

	@Override
	public List<Userr> get() {
		return userCollection
				.find()
				.into(new ArrayList<Userr>());
	}

	@Override
	public void add(Userr user) {
		userCollection.insertOne(user);
	}

	@Override
	public void update(String idString, Userr user) {
		userCollection.replaceOne(withId(idString), user);
	}

	@Override
	public void remove(String idString) {
		userCollection.deleteOne(withId(idString));
	}

}

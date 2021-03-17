package mongoDb;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoCollection;

import resources.Box;

public class MongoBox extends MongoResource<Box> {

	MongoCollection<Box> companyCollection = getCollection("Company", Box.class);

	@Override
	public Box get(String idString) {
		return companyCollection
				.find()
				.filter(withId(idString))
				.first();
	}

	@Override
	public List<Box> get() {
		return companyCollection
				.find()
				.into(new ArrayList<Box>());
	}

	@Override
	public void add(Box comapny) {
		companyCollection.insertOne(comapny);
	}

	@Override
	public void update(String idString, Box company) {
		companyCollection.replaceOne(withId(idString), company);
	}

	@Override
	public void remove(String idString) {
		companyCollection.deleteOne(withId(idString));
	}

}

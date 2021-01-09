package mongoDb;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoCollection;

import resources.Company;

public class MongoCompany extends MongoResource<Company> {

	MongoCollection<Company> companyCollection = getCollection("Company", Company.class);

	@Override
	public Company get(String idString) {
		return companyCollection
				.find()
				.filter(withId(idString))
				.first();
	}

	@Override
	public List<Company> get() {
		return companyCollection
				.find()
				.into(new ArrayList<Company>());
	}

	@Override
	public void add(Company comapny) {
		companyCollection.insertOne(comapny);
	}

	@Override
	public void update(String idString, Company company) {
		companyCollection.replaceOne(withId(idString), company);
	}

	@Override
	public void remove(String idString) {
		companyCollection.deleteOne(withId(idString));
	}

}

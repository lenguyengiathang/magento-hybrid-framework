package utilities;

import java.util.Locale;

import com.github.javafaker.Faker;

public class DataHelper {

	private Locale locale = new Locale("en");
	private Faker faker = new Faker(locale);

	public static DataHelper getDataHelper() {
		return new DataHelper();
	}

	public String getFirstName() {
		return faker.address().firstName();
	}

	public String getLastName() {
		return faker.address().lastName();
	}

	public String getEmailAddress() {
		return faker.internet().emailAddress();
	}

	public String getPassword() {
		return faker.internet().password(6, 10, true, true);
	}

	public String getPhoneNumber() {
		return faker.phoneNumber().phoneNumber();
	}

	public String getSocialSecurityNumber() {
		return faker.idNumber().ssnValid();
	}

	public String getAddress() {
		return faker.address().fullAddress();
	}

	public String getCity() {
		return faker.address().city();
	}

	public String getState() {
		return faker.address().state();
	}

	public String getCountry() {
		return faker.address().country();
	}

	public String getZipCode() {
		return faker.address().zipCode();
	}

}

package utilities;

import java.util.Locale;

import com.github.javafaker.Faker;

public class FakeDataUtils {

	private Locale locale = new Locale("en");
	private Faker faker = new Faker(locale);

	public static FakeDataUtils getDataHelper() {
		return new FakeDataUtils();
	}

	public String getFirstName() {
		return faker.address().firstName();
	}

	public String getLastName() {
		return faker.address().lastName();
	}

	public String getEmailDomain() {
		return faker.internet().domainName();
	}

	public String getEmailAddress() {
		return faker.internet().emailAddress();
	}

	public String getPassword() {
		return faker.internet().password(8, 12, true, true, true);
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

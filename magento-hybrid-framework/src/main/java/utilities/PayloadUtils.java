package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class PayloadUtils {

	public static String createDynamicPayload(String fileName, Map<String, String> values) throws IOException {
		String payload = new String(Files.readAllBytes(Paths.get("src/test/resources/payloads/" + fileName)));

		for (Map.Entry<String, String> entry : values.entrySet()) {
			payload = payload.replace("${" + entry.getKey() + "}", entry.getValue());
		}

		return payload;
	}

	public static Map<String, String> createCustomerAccountData(String email, String password, String firstName,
			String lastName, String address, String city, String state, int zipCode, String phoneNumber) {
		Map<String, String> data = new HashMap<>();
		data.put("email", email);
		data.put("password", password);
		data.put("firstName", firstName);
		data.put("lastName", lastName);
		data.put("address", address);
		data.put("city", city);
		data.put("state", state);
		data.put("zipCode", String.valueOf(zipCode));
		data.put("phoneNumber", phoneNumber);
		return data;
	}

	public static Map<String, String> createCustomerTokenData(String email, String password) {
		Map<String, String> data = new HashMap<>();
		data.put("email", email);
		data.put("password", password);
		return data;
	}

}

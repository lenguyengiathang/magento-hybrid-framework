package utilities;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	public static String getJsonValue(String fileName, String key) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(new File("src/test/resources/" + fileName));
			return jsonNode.get(key).asText();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

package utilities;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtils {

	private static ObjectMapper objectMapper = new ObjectMapper();
	private static Random random = new Random();

	private static JsonNode readJsonFile(String fileName) {
		try {
			return objectMapper.readTree(new File("src/test/resources/" + fileName));
		} catch (IOException e) {
			System.err.println("Error reading JSON file: " + fileName);
			return null;
		}
	}

	public static String getJsonValue(String fileName, String key) {
		try {
			JsonNode jsonNode = objectMapper.readTree(new File("src/test/resources/" + fileName));

			String[] keys = key.split("\\.");
			for (String k : keys) {
				if (jsonNode != null) {
					jsonNode = jsonNode.get(k);
				} else {
					return null;
				}
			}
			return jsonNode != null ? jsonNode.asText() : null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JsonNode getRandomProductNoOptions(String fileName) {
		JsonNode rootNode = readJsonFile(fileName);
		if (rootNode == null)
			return null;

		JsonNode categories = rootNode.get("gear_products");
		if (categories == null || !categories.isArray())
			return null;

		JsonNode category = categories.get(random.nextInt(categories.size()));
		JsonNode products = category.get("products");

		if (products == null || !products.isArray())
			return null;

		JsonNode product = products.get(random.nextInt(products.size()));

		ObjectNode result = objectMapper.createObjectNode();
		result.put("category", category.get("category").asText());
		result.set("product", product);

		return result;
	}

	public static JsonNode getRandomProductWithOptions(String fileName) {
		JsonNode rootNode = readJsonFile(fileName);
		if (rootNode == null)
			return null;

		JsonNode categories = rootNode.get("categories");
		if (categories == null || !categories.isArray())
			return null;

		JsonNode category = categories.get(random.nextInt(categories.size()));
		JsonNode subcategories = category.get("subcategories");
		if (subcategories == null || !subcategories.isArray())
			return null;

		JsonNode subcategory = subcategories.get(random.nextInt(subcategories.size()));
		JsonNode products = subcategory.get("products");
		if (products == null || !products.isArray())
			return null;

		JsonNode product = products.get(random.nextInt(products.size()));
		JsonNode sizes = product.get("sizes");
		String size = (sizes != null && sizes.isArray() && sizes.size() > 0)
				? sizes.get(random.nextInt(sizes.size())).asText()
				: null;

		JsonNode colors = product.get("colors");
		String color = (colors != null && colors.isArray() && colors.size() > 0)
				? colors.get(random.nextInt(colors.size())).asText()
				: null;

		ObjectNode result = objectMapper.createObjectNode();
		result.put("category", category.get("category").asText());
		result.put("subcategory", subcategory.get("subcategory").asText());
		result.set("product", product);
		result.put("size", size);
		result.put("color", color);

		return result;
	}
}

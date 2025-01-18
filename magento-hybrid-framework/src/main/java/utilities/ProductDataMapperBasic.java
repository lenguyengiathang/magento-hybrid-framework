package utilities;

import java.io.File;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductDataMapperBasic {

	private Random random;

	public static class Product {
		@JsonProperty("product_name")
		private String productName;

		@JsonProperty("price")
		private String price;

		public String getProductName() {
			return productName;
		}

		public String getPrice() {
			return price;
		}
	}

	public static class Category {
		@JsonProperty("category")
		private String categoryName;

		@JsonProperty("products")
		private List<Product> products;

		public String getCategoryName() {
			return categoryName;
		}

		public List<Product> getProducts() {
			return products;
		}
	}

	@JsonProperty("gear_products")
	public List<Category> categories;

	public List<Category> getCategories() {
		return categories;
	}

	public static ProductDataMapperBasic loadProductData(String fileName) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			String filePath = System.getProperty("user.dir") + "/src/test/resources/" + fileName;
			return mapper.readValue(new File(filePath), ProductDataMapperBasic.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Category getRandomCategory() {
		random = new Random();
		return categories.get(random.nextInt(categories.size()));
	}

	public Product getRandomProductFromCategory(Category category) {
		random = new Random();
		return category.getProducts().get(random.nextInt(category.getProducts().size()));
	}
}

package utilities;

import java.io.File;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductDataMapperAdvanced {
	private static final Random random = new Random();

	public static class Product {
		@JsonProperty("product_name")
		private String productName;

		@JsonProperty("price")
		private String price;

		@JsonProperty("sizes")
		private List<String> sizes;

		@JsonProperty("colors")
		private List<String> colors;

		public String getProductName() {
			return productName;
		}

		public String getPrice() {
			return price;
		}

		public List<String> getSizes() {
			return sizes;
		}

		public List<String> getColors() {
			return colors;
		}
	}

	public static class Subcategory {
		@JsonProperty("subcategory")
		private String subcategoryName;

		@JsonProperty("products")
		private List<Product> products;

		public String getSubcategoryName() {
			return subcategoryName;
		}

		public List<Product> getProducts() {
			return products;
		}
	}

	public static class Category {
		@JsonProperty("category")
		private String categoryName;

		@JsonProperty("subcategories")
		private List<Subcategory> subcategories;

		public String getCategoryName() {
			return categoryName;
		}

		public List<Subcategory> getSubcategories() {
			return subcategories;
		}
	}

	@JsonProperty("categories")
	public List<Category> categories;

	public List<Category> getCategories() {
		return categories;
	}

	public static ProductDataMapperAdvanced loadProductData(String fileName) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			String filePath = System.getProperty("user.dir") + "/src/test/resources/" + fileName;
			return mapper.readValue(new File(filePath), ProductDataMapperAdvanced.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Category getRandomCategory() {
		return categories.get(random.nextInt(categories.size()));
	}

	public Subcategory getRandomSubcategory(Category category) {
		return category.getSubcategories().get(random.nextInt(category.getSubcategories().size()));
	}

	public Product getRandomProductFromSubcategory(Subcategory subcategory) {
		return subcategory.getProducts().get(random.nextInt(subcategory.getProducts().size()));
	}

	public String getRandomSize(Product product) {
		List<String> sizes = product.getSizes();
		return sizes.get(random.nextInt(sizes.size()));
	}

	public String getRandomColor(Product product) {
		List<String> colors = product.getColors();
		return colors.get(random.nextInt(colors.size()));
	}
}

package utilities;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductDataMapperAdvanced {

	public static class Product {
		@JsonProperty("product_name")
		private String productName;

		@JsonProperty("price")
		private String price;

		@JsonProperty("size")
		private String size;

		@JsonProperty("color")
		private String color;

		public String getProductName() {
			return productName;
		}

		public String getPrice() {
			return price;
		}

		public String getSize() {
			return size;
		}

		public String getColor() {
			return color;
		}
	}

	public static class ProductType {
		@JsonProperty("type")
		private String type;

		@JsonProperty("items")
		private List<Product> items;

		public String getType() {
			return type;
		}

		public List<Product> getItems() {
			return items;
		}
	}

	public static class Category {
		@JsonProperty("category")
		private String category;

		@JsonProperty("products")
		private List<ProductType> products;

		public String getCategory() {
			return category;
		}

		public List<ProductType> getProducts() {
			return products;
		}
	}

	@JsonProperty("men_products")
	private List<Category> menProducts;

	@JsonProperty("women_products")
	private List<Category> womenProducts;

	public List<Category> getMenProducts() {
		return menProducts;
	}

	public List<Category> getWomenProducts() {
		return womenProducts;
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
}

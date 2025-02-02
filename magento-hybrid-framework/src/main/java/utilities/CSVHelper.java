package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CSVHelper {
	public static String[] getRandomRowFromCSVFile(String filePath) throws IOException {
		List<String[]> data = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			boolean skipHeader = true;
			while ((line = br.readLine()) != null) {
				if (skipHeader) {
					skipHeader = false;
					continue;
				}
				data.add(line.split(","));
			}
		}
		Random random = new Random();
		return data.get(random.nextInt(data.size()));
	}
}

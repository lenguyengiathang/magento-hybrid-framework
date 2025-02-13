package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CSVUtils {
	public static String[] getRandomRowFromCSVFile(String filePath) throws IOException {
		List<String[]> data = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			br.readLine();

			String line;
			while ((line = br.readLine()) != null) {
				data.add(line.split(","));
			}
		}

		if (data.isEmpty()) {
			throw new IOException("CSV file is empty or only contains headers.");
		}

		Random random = new Random();
		return data.get(random.nextInt(data.size()));
	}
}

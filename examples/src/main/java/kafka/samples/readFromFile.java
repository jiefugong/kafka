import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class readFromFile {

	public static void main(String[] args) {
		String fileName = "/Users/jigong/desktop/work/kafka_source/kafka/examples/src/main/java/kafka/samples/files/firstFile.txt";
		String currline;

		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);

			while ((currline = br.readLine()) != null) {
				System.out.println(currline);
			}

			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't open the file!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
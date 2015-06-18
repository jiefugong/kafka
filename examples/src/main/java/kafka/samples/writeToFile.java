import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class writeToFile {

	public static void main (String[] args) {
		try {
			// String content = "We're going to write this into the file NEW.\n";
			String fileName = "/Users/jigong/desktop/work/kafka_source/kafka/examples/src/main/java/kafka/samples/files/firstFile.txt";
			File file = new File(fileName);

			if (!file.exists()) {
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				String content;

				for (int i = 0; i < 10; i += 1) {
					content = "Currently writing: " + i + "\n";
					bw.write(content);
				}

				bw.close();
			} else {
				FileWriter fw = new FileWriter(fileName, true);
				fw.write("Just appended this!\n");
				fw.close();
			}

			System.out.println("Execution completed!");

		} catch (IOException e) {
			System.out.println("Error'd when trying to write to file.");
			e.printStackTrace();
		}
	}
}
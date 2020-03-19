package solution;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.nio.file.Files;

public class Printer {

  public void exportToFile(List<String> stringList, String fileName) throws IOException {

    Path path = Paths.get(fileName);
    String str = String.join(System.lineSeparator(), stringList);

    Files.write(path, str.getBytes());
  }
}

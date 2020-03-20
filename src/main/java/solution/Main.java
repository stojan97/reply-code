package solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {

  private static List<String> matrix;
  private static List<Developer> developers;
  private static List<Manager> managers;

  public static void main(String[] args) throws IOException {

    String[] files = new String[] { "a_solar.txt", "b_dream.txt", "c_soup.txt", "d_maelstrom.txt", "e_igloos.txt", "f_glitch.txt" };
    for (String f : files) {
      solve(f);
    }
  }

  private static void solve(String fileName) throws IOException {

    InputStream resourceAsStream = Main.class.getClassLoader().getResourceAsStream(fileName);
    InputStreamReader streamReader = new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8);
    BufferedReader reader = new BufferedReader(streamReader);
    List<String> input = new ArrayList<>();

    for (String line; (line = reader.readLine()) != null; ) {
      input.add(line);
    }

    String parse[] = input.get(0).split(" ");
    int h = Integer.parseInt(parse[0]);
    int w = Integer.parseInt(parse[1]);

    matrix = new ArrayList<>();
    int i = 1;
    // System.out.println(w);
    for (int c = 0; c < w; c++) {
      matrix.add(input.get(i));
      i++;
    }

    // for (String c : matrix) {
    //     System.out.println(c);
    // }

    int replyerId = 0;

    int devs = Integer.parseInt(input.get(i++));
    developers = new ArrayList<>();
    for (int c = 0; c < devs; c++) {
      String devProps[] = input.get(i).split(" ");
      String company = devProps[0];
      int bonus = Integer.parseInt(devProps[1]);
      int skillsSize = Integer.parseInt(devProps[2]);

      List<String> skills = new ArrayList<>();

      for (int k = 3; k < devProps.length; k++) {
        skills.add(devProps[k]);
      }
      Developer d = new Developer(replyerId++, company, bonus, skillsSize, skills);
      developers.add(d);
      // System.out.println(d);
      i++;
    }

    int managers_size = Integer.parseInt(input.get(i++));
    managers = new ArrayList<>();
    for (int c = 0; c < managers_size; c++) {
      String managerProps[] = input.get(i).split(" ");
      String company = managerProps[0];
      int bonus = Integer.parseInt(managerProps[1]);

      Manager manager = new Manager(replyerId++, company, bonus);
      managers.add(manager);
      // System.out.println(manager);
      i++;
    }

    Solver solver = new Solver(fileName, matrix, developers, managers);
    solver.solve();
  }

}

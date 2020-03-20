package solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Solver {

  private String fileName;
  private List<String> matrix;
  private List<Developer> developers;
  private List<Manager> managers;
  private int rows;
  private int cols;
  Set<Replyer> visitedReplyers;
  Replyer[][] visited;
  Map<Replyer, Pair> positions;
  long total;

  int[] dirR = { 1, -1, 0, 0 };
  int[] dirC = { 0, 0, 1, -1 };

  public Solver(
    String fileName, List<String> matrix, List<Developer> developers, List<Manager> managers) {

    this.fileName = fileName + "_" + this.getClass().getSimpleName();
    this.matrix = matrix;
    this.developers = developers;
    this.managers = managers;
    this.rows = matrix.size();
    this.cols = matrix.get(0).length();
    visitedReplyers = new HashSet<>();
    visited = new Replyer[rows][cols];
    positions = new HashMap<>();
  }

  public void solve() throws IOException {

    List<Pair> cells = getAvailableCells();
    Collections.sort(cells, Collections.reverseOrder());

    for (Pair p : cells) {
      if (visited[p.i][p.j] == null) {
        bfs(p);
      }
    }
    System.out.println("Total points: " + total);
    List<String> coords = new ArrayList<>();
    for (Replyer r : developers) {
      Pair p = positions.get(r);
      if (p == null) {
        coords.add("X");
      } else {
        coords.add(p.j + " " + p.i); // first horizontal coordinate(colon) and then vertical(row)
      }
    }
    for (Replyer r : managers) {
      Pair p = positions.get(r);
      if (p == null) {
        coords.add("X");
      } else {
        coords.add(p.j + " " + p.i); // first horizontal coordinate(colon) and then vertical(row)
      }
    }
    new Printer().exportToFile(coords, fileName + "_sol");
    new Printer().exportToFile(List.of(String.valueOf(total)), fileName + "_points");
  }

  private void bfs(Pair p) {

    Queue<Pair> q = new LinkedList<>();
    q.add(p);
    Replyer r = getBestReplyer(p);
    if (r == null) {
      return;
    }
    visitedReplyers.add(r);
    visited[p.i][p.j] = r;
    positions.put(r, p);

    while (!q.isEmpty()) {
      Pair curr = q.poll();
      Replyer currReplyer = visited[curr.i][curr.j];
      for (int i = 0; i < 4; i++) {
        Pair newPair = new Pair(curr.i + dirR[i], curr.j + dirC[i], 0);
        if (!isValid(newPair.i, newPair.j) || visited[newPair.i][newPair.j] != null) {
          continue;
        }

        Replyer newReplyer = getBestReplyer(newPair, currReplyer);
        if (newReplyer != null) {
          visitedReplyers.add(newReplyer);
          visited[newPair.i][newPair.j] = newReplyer;
          positions.put(newReplyer, newPair);
          q.offer(newPair);
        }
      }
    }
  }

  private Replyer getBestReplyer(Pair p, Replyer currReplyer) {

    char type = matrix.get(p.i).charAt(p.j);
    List<? extends Replyer> replyers = type == 'M' ? managers : developers;
    long maxTotalPoints = 0;
    Replyer result = null;
    for (Replyer r : replyers) {
      if (r.equals(currReplyer) || visitedReplyers.contains(r)) {
        continue;
      }
      List<Replyer> neigh = getNeighborReplyers(p);
      long score = calculateScore(r, neigh);
      if (score >= maxTotalPoints) {
        result = r;
        maxTotalPoints = score;
      }
    }

    total += maxTotalPoints;
    return result;
  }

  private Replyer getBestReplyer(Pair p) {

    if (matrix.get(p.i).charAt(p.j) == 'M') {
      int maxBonusPoints = 0;
      Replyer result = null;
      for (Replyer r : managers) {
        if (!visitedReplyers.contains(r) && r.bonusPoints >= maxBonusPoints) {
          result = r;
          maxBonusPoints = r.bonusPoints;
        }
      }
      return result;
    } else {
      for (Replyer r : developers) {
        if (!visitedReplyers.contains(r)) {
          return r;
        }
      }
      return null;
    }
  }

  private List<Pair> getAvailableCells() {

    List<Pair> result = new ArrayList<>();

    for (int i = 0; i < matrix.size(); i++) {
      for (int j = 0; j < matrix.get(i).length(); j++) {
        if (matrix.get(i).charAt(j) != '#') {
          int countNeightbors = getNeighbors(i, j).size();
          result.add(new Pair(i, j, countNeightbors));
        }
      }
    }
    return result;
  }

  private long calculateScore(Replyer r, List<Replyer> neigh) {

    if(neigh.size() == 0) {
      return r.bonusPoints;
    }

    long result = 0;
    for(Replyer n : neigh) {
      result += (Calculator.wp(r, n) + Calculator.bp(r, n));
    }
    return result;
  }

  private List<Replyer> getNeighborReplyers(Pair p) {

    List<Replyer> result = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      int nr = p.i + dirR[i];
      int nc = p.j + dirC[i];
      if (isValid(nr, nc) && visited[nr][nc] != null) {
        result.add(visited[nr][nc]);
      }
    }
    return result;
  }

  private List<Pair> getNeighbors(int r, int c) {

    List<Pair> result = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      int nr = r + dirR[i];
      int nc = c + dirC[i];
      if (isValid(nr, nc)) {
        result.add(new Pair(nr, nc, 0));
      }
    }
    return result;
  }

  private boolean isValid(int r, int c) {

    return r >= 0 && r < rows && c >= 0 && c < cols && matrix.get(r).charAt(c) != '#';
  }

}

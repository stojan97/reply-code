package solution;

public class Pair implements Comparable<Pair> {

  int i, j;
  int countNeighbors;

  public Pair(int i, int j, int countNeighbors) {

    this.i = i;
    this.j = j;
    this.countNeighbors = countNeighbors;
  }

  @Override
  public int compareTo(Pair o) {

    return Integer.compare(countNeighbors, o.countNeighbors);
  }

  @Override
  public String toString() {

    return "reply.code.challenge.Pair{" + "i=" + i + ", j=" + j + ", countNeighbors=" + countNeighbors + '}';
  }
}

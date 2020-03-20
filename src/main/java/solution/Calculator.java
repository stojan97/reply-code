package solution;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Calculator {

  static long wp(Replyer a, Replyer b) {

    Set<String> sa = new HashSet<>(a.skills);
    Set<String> sb = new HashSet<>(b.skills);

    Set<String> sac = new HashSet<>(sa);
    sac.addAll(sb);
    long union = sac.size();

    sa.retainAll(sb);
    long intersection = sa.size();
    return intersection * (union - intersection);
  }

  static long bp(Replyer a, Replyer b) {

    if (Objects.equals(a.company, b.company)) {
      return a.bonusPoints * b.bonusPoints;
    }
    return 0;
  }

}

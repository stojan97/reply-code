package solution;

import java.util.Collections;

public class Manager extends Replyer {

  public Manager(int id, String company, int bonusPoints) {

    super(id, company, bonusPoints, Collections.emptyList());
  }

  @Override
  public String toString() {

    return "reply.code.challenge.Manager{" + "company='" + company + '\'' + ", bonusPoints=" + bonusPoints + '}';
  }
}

package solution;

import java.util.List;

public abstract class Replyer {

  public String company;
  public int bonusPoints;
  public List<String> skills;
  int id;

  public Replyer(int id, String company, int bonusPoints, List<String> skills) {

    this.id = id;
    this.skills = skills;
    this.company = company;
    this.bonusPoints = bonusPoints;
  }

  @Override
  public int hashCode() {

    return id;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Replyer replyer = (Replyer) o;
    return id == replyer.id;
  }
}

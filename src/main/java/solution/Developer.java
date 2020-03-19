package solution;

import java.util.List;

public class Developer extends Replyer {

  public int skillsSize;

  public Developer(int id, String company, int bonusPoints, int skillsSize, List<String> skills) {

    super(id, company, bonusPoints, skills);
    this.skillsSize = skillsSize;
    this.skills = skills;
  }

  @Override
  public String toString() {

    return "reply.code.challenge.Developer{"
           + "company='"
           + company
           + '\''
           + ", bonusPoints="
           + bonusPoints
           + ", skillsSize="
           + skillsSize
           + ", skills="
           + skills
           + '}';
  }
}

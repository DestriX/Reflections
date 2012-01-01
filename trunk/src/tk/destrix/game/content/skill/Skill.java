package tk.destrix.game.content.skill;

import tk.destrix.game.model.Player;

/**
 * Handles skill related actions.
 * @author DestriX ("http://www.destrix.tk")
 */
public class Skill {
	
	private Player player;
	
	public Skill(Player player) {
		this.player = player;
	}
	
	public static final int SKILL_COUNT = 22;
	public static final double MAXIMUM_EXP = 200000000;
	
	private int[] skills = new int[SKILL_COUNT];
	private double[] experience = new double[SKILL_COUNT];
	
	/**
	 * Sets the skill level.
	 * 
	 * @param skillID
	 *            the skill ID
	 * @param level
	 *            the level
	 */
	public void setSkill(int skillID, int level) {
		skills[skillID] = level;
		player.getActionSender().sendSkill(skillID, skills[skillID], experience[skillID]);
	}

	/**
	 * Adds skill experience.
	 * 
	 * @param skillID
	 *            the skill ID
	 * @param exp
	 *            the experience to add
	 */
	public void addSkillExp(int skillID, int exp) {
		experience[skillID] += exp;
		player.getActionSender().sendSkill(skillID, skills[skillID], experience[skillID]);
	}

	/**
	 * Removes skill experience.
	 * 
	 * @param skillID
	 *            the skill ID
	 * @param exp
	 *            the experience to add
	 */
	public void removeSkillExp(int skillID, int exp) {
		experience[skillID] -= exp;
		player.getActionSender().sendSkill(skillID, skills[skillID], experience[skillID]);
	}

	public int[] getSkills() {
		return skills;
	}

	public double[] getExperience() {
		return experience;
	}

	public void setExperience(double[] experience) {
		this.experience = experience;
	}

	public void setSkills(int[] skills) {
		this.skills = skills;
	}

	public interface SkillHandler {
		public void executeSkill(Player player, Skill skill);
	}
	
}

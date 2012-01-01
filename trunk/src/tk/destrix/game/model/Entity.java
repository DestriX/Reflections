package tk.destrix.game.model;

import tk.destrix.game.Position;

/**
 * Represents entities.
 * @author DestriX ("http://www.destrix.tk")
 */
public abstract class Entity {
	
	private int index;
	private Entity interactingEntity;
	private boolean isAttacking;
	private boolean isDead;
	private boolean instigatingAttack;
	private boolean attackTickRunning;
	private boolean endAttackTick;
	private boolean combatTickRunning;
	private int attackSpeed = 5;
	private int damage;
	private int hitType;
	private int combatTimer;
	private int attackTimer;
	private int combatType;
	
	private Position position;
	
	
	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setInteractingEntity(Entity interactingEntity) {
		this.interactingEntity = interactingEntity;
	}

	public Entity getInteractingEntity() {
		return interactingEntity;
	}

	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}

	public boolean isAttacking() {
		return isAttacking;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}

	public Position getPosition() {
		return position;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public boolean isDead() {
		return isDead;
	}
	
	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	
	public int getAttackSpeed() {
		return attackSpeed;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getCombatType() {
		return combatType;
	}
	
	public void setCombatType(int combatType) {
		this.combatType = combatType;
	}
	
	public void setHitType(int hitType) {
		this.hitType = hitType;
	}
	
	public int getHitType() {
		return hitType;
	}
	
	public void setCombatTimer(int combatTimer) {
		this.combatTimer = combatTimer;
	}
	
	public int getCombatTimer() {
		return combatTimer;
	}
	
	public void setAttackTimer(int attackTimer) {
		this.attackTimer = attackTimer;
	}
	
	public int getAttackTimer() {
		return attackTimer;
	}
	
	public boolean isInstigatingAttack() {
		return instigatingAttack;
	}
	
	public void setInstigatingAttack(boolean instigatingAttack) {
		this.instigatingAttack = instigatingAttack;
	}
	
	public boolean endAttackTick() {
		return endAttackTick;
	}
	
	public void setEndAttackTick(boolean endAttackTick) {
		this.endAttackTick = endAttackTick;
	}
	
	public boolean isCombatTickRunning() {
		return combatTickRunning;
	}
	
	public void setCombatTickRunning(boolean combatTickRunning) {
		this.combatTickRunning = combatTickRunning;
	}
	
	public boolean isAttackTickRunning() {
		return attackTickRunning;
	}
	
	public void setAttackTickRunning(boolean attackTickRunning) {
		this.attackTickRunning = attackTickRunning;
	}

}

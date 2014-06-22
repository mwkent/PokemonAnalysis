/*
 * The details kept on each individual pokemon.
 */

package pokemon.data_initialization;

class Pokemon implements Comparable<Pokemon> {
	private final String name;
	private String type1 = "";
	private String type2 = "";
	private final int number;
	private final int hp;
	private final int attack;
	private final int defense;
	private final int spAttack;
	private final int spDefense;
	private final int speed;
	private final int BST; // Base stat total

	public Pokemon(String name, int number, int hp, int attack, int defense,
			int spAttack, int spDefense, int speed, int BST) {
		this.name = name;
		this.number = number;
		this.hp = hp;
		this.attack = attack;
		this.defense = defense;
		this.spAttack = spAttack;
		this.spDefense = spDefense;
		this.speed = speed;
		this.BST = BST;
	}

	public Pokemon(String name, String type1, String type2, int number, int hp,
			int attack, int defense, int spAttack, int spDefense, int speed,
			int BST) {
		this.name = name;
		this.type1 = type1;
		this.type2 = type2;
		this.number = number;
		this.hp = hp;
		this.attack = attack;
		this.defense = defense;
		this.spAttack = spAttack;
		this.spDefense = spDefense;
		this.speed = speed;
		this.BST = BST;
	}

	public String getName() {
		return name;
	}

	public int getNumber() {
		return number;
	}

	public int getHp() {
		return hp;
	}

	public int getAttack() {
		return attack;
	}

	public int getDefense() {
		return defense;
	}

	public int getSpAttack() {
		return spAttack;
	}

	public int getSpDefense() {
		return spDefense;
	}

	public int getSpeed() {
		return speed;
	}

	public int getBST() {
		return BST;
	}

	public void setType1(String type) {
		this.type1 = type;
	}

	public String getType1() {
		return type1;
	}

	public void setType2(String type) {
		this.type2 = type;
	}

	public String getType2() {
		return type2;
	}

	@Override
	public String toString() {
		return number + " " + name + " " + hp + " " + attack + " " + defense
				+ " " + spAttack + " " + spDefense + " " + speed + " " + BST
				+ " " + type1 + " " + type2;
	}

	@Override
	public int compareTo(Pokemon other) {
		return number - other.getNumber();
	}
}

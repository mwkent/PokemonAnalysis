/*
 * The TypeAdvantages class holds all of the essential information on types.
 * It keeps a list of the types and knows type effectiveness for each type,
 * whether you are attacking or defending with a type.
 */

package pokemon.data_initialization;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

class TypeAdvantages {
	private static final List<String> TYPES = Arrays.asList("normal", "fire",
			"water", "electric", "grass", "ice", "fighting", "poison",
			"ground", "flying", "psychic", "bug", "rock", "ghost", "dragon",
			"dark", "steel", "fairy");
	private static final HashMap<String, List<List<String>>> attackingTypeAdvantages = makeAttackingTA();
	private static final HashMap<String, List<List<String>>> defendingTypeAdvantages = makeDefendingTA();

	TypeAdvantages() {
	}

	public static List<List<String>> getAttackingTA(String type) {
		return attackingTypeAdvantages.get(type);
	}

	public static List<List<String>> getDefendingTA(String type) {
		return defendingTypeAdvantages.get(type);
	}

	public static boolean isType(String type) {
		return TYPES.contains(type);
	}

	private static HashMap<String, List<List<String>>> makeAttackingTA() {
		HashMap<String, List<List<String>>> attackingTA = new HashMap<String, List<List<String>>>();

		// The attacking type does not affect types in the first list,
		// does half damage to types in the second list,
		// does normal damage to types in the third list,
		// and does double damage to types in the fourth list.
		List<List<String>> normalAttack = Arrays.asList(Arrays.asList("ghost"),
				Arrays.asList("rock", "steel"), new ArrayList<String>(0));
		List<List<String>> fireAttack = Arrays.asList(new ArrayList<String>(0),
				Arrays.asList("fire", "water", "rock", "dragon"),
				Arrays.asList("grass", "ice", "bug", "steel"));
		List<List<String>> waterAttack = Arrays.asList(
				new ArrayList<String>(0),
				Arrays.asList("water", "grass", "dragon"),
				Arrays.asList("fire", "ground", "rock"));
		List<List<String>> electricAttack = Arrays.asList(
				Arrays.asList("ground"),
				Arrays.asList("electric", "grass", "dragon"),
				Arrays.asList("water", "flying"));
		List<List<String>> grassAttack = Arrays.asList(
				new ArrayList<String>(0), Arrays.asList("fire", "grass",
						"poison", "flying", "bug", "dragon", "steel"), Arrays
						.asList("water", "ground", "rock"));
		List<List<String>> iceAttack = Arrays.asList(new ArrayList<String>(0),
				Arrays.asList("fire", "water", "ice", "steel"),
				Arrays.asList("grass", "ground", "flying", "dragon"));
		List<List<String>> fightingAttack = Arrays.asList(
				Arrays.asList("ghost"),
				Arrays.asList("flying", "psychic", "bug", "fairy"),
				Arrays.asList("normal", "ice", "rock", "dark", "steel"));
		List<List<String>> poisonAttack = Arrays.asList(Arrays.asList("steel"),
				Arrays.asList("poison", "ground", "rock", "ghost"),
				Arrays.asList("grass", "fairy"));
		List<List<String>> groundAttack = Arrays.asList(
				Arrays.asList("flying"), Arrays.asList("grass", "bug"),
				Arrays.asList("fire", "electric", "posion", "rock", "steel"));
		List<List<String>> flyingAttack = Arrays.asList(
				new ArrayList<String>(0),
				Arrays.asList("electric", "rock", "steel"),
				Arrays.asList("grass", "fighting", "bug"));
		List<List<String>> psychicAttack = Arrays.asList(Arrays.asList("dark"),
				Arrays.asList("psychic", "steel"),
				Arrays.asList("fighting", "poison"));
		List<List<String>> bugAttack = Arrays.asList(new ArrayList<String>(0),
				Arrays.asList("fire", "fighting", "poison", "flying", "ghost",
						"steel", "fairy"), Arrays.asList("grass", "psychic",
						"dark"));
		List<List<String>> rockAttack = Arrays.asList(new ArrayList<String>(0),
				Arrays.asList("fighting", "ground", "steel"),
				Arrays.asList("fire", "ice", "flying", "bug"));
		List<List<String>> ghostAttack = Arrays.asList(Arrays.asList("normal"),
				Arrays.asList("dark"), Arrays.asList("psychic", "ghost"));
		List<List<String>> dragonAttack = Arrays.asList(Arrays.asList("fairy"),
				Arrays.asList("steel"), Arrays.asList("dragon"));
		List<List<String>> darkAttack = Arrays.asList(new ArrayList<String>(0),
				Arrays.asList("fighting", "dark", "fairy"),
				Arrays.asList("psychic", "ghost"));
		List<List<String>> steelAttack = Arrays.asList(
				new ArrayList<String>(0),
				Arrays.asList("fire", "water", "electric", "steel"),
				Arrays.asList("ice", "rock", "fairy"));
		List<List<String>> fairyAttack = Arrays.asList(
				new ArrayList<String>(0),
				Arrays.asList("fire", "poison", "steel"),
				Arrays.asList("fighting", "dragon", "dark"));

		attackingTA.put("normal", normalAttack);
		attackingTA.put("fire", fireAttack);
		attackingTA.put("water", waterAttack);
		attackingTA.put("electric", electricAttack);
		attackingTA.put("grass", grassAttack);
		attackingTA.put("ice", iceAttack);
		attackingTA.put("fighting", fightingAttack);
		attackingTA.put("poison", poisonAttack);
		attackingTA.put("ground", groundAttack);
		attackingTA.put("flying", flyingAttack);
		attackingTA.put("psychic", psychicAttack);
		attackingTA.put("bug", bugAttack);
		attackingTA.put("rock", rockAttack);
		attackingTA.put("ghost", ghostAttack);
		attackingTA.put("dragon", dragonAttack);
		attackingTA.put("dark", darkAttack);
		attackingTA.put("steel", steelAttack);
		attackingTA.put("fairy", fairyAttack);

		Iterator<String> attackingTAKeys = attackingTA.keySet().iterator();
		while (attackingTAKeys.hasNext()) {
			String attackingTAKey = attackingTAKeys.next();
			List<String> normalDamage = new ArrayList<String>(TYPES);
			List<List<String>> attackingTAValues = attackingTA.get(attackingTAKey);
			for (List<String> TA : attackingTAValues) {
				for (String type : TA) {
					normalDamage.remove(type);
				}
			}
			List<List<String>> valuesCopy = new ArrayList<List<String>>(attackingTAValues);
			valuesCopy.add(2, normalDamage);
			attackingTA.put(attackingTAKey, valuesCopy);
		}

		return attackingTA;
	}

	private static HashMap<String, List<List<String>>> makeDefendingTA() {
		HashMap<String, List<List<String>>> defendingTA = new HashMap<String, List<List<String>>>();

		for (String defendingType : TYPES) {
			List<List<String>> defendWith = new ArrayList<List<String>>();
			List<String> noDamageAttacks = new ArrayList<String>();
			List<String> halfDamageAttacks = new ArrayList<String>();
			List<String> normalDamageAttacks = new ArrayList<String>();
			List<String> doubleDamageAttacks = new ArrayList<String>();
			for (String type : TYPES) {
				List<List<String>> typeDamage = attackingTypeAdvantages
						.get(type);
				boolean doesNoDamage = typeDamage.get(0)
						.contains(defendingType);
				boolean doesHalfDamage = typeDamage.get(1).contains(
						defendingType);
				boolean doesNormalDamage = typeDamage.get(2).contains(
						defendingType);
				boolean doesDoubleDamage = typeDamage.get(3).contains(
						defendingType);
				if (doesNoDamage) {
					noDamageAttacks.add(type);
				} else if (doesHalfDamage) {
					halfDamageAttacks.add(type);
				} else if (doesNormalDamage) {
					normalDamageAttacks.add(type);
				} else if (doesDoubleDamage) {
					doubleDamageAttacks.add(type);
				}
			}
			defendWith.add(noDamageAttacks);
			defendWith.add(halfDamageAttacks);
			defendWith.add(normalDamageAttacks);
			defendWith.add(doubleDamageAttacks);
			defendingTA.put(defendingType, defendWith);
		}

		return defendingTA;
	}

	private static void writeToFile() throws IOException {
		// location of the file where the information is stored
		File dataFile = new File(
				"/Users/mattkent/pokemanzzz/Pokemon Website/data/typeInfo");
		Emitter emitter = new Emitter(dataFile);
		for (String type : TYPES) {
			List<List<String>> attackingData = attackingTypeAdvantages
					.get(type);
			List<String> noDamage = attackingData.get(0);
			List<String> halfDamage = attackingData.get(1);
			List<String> normalDamage = attackingData.get(2);
			List<String> doubleDamage = attackingData.get(3);
			emitter.emit("Attacking with " + type + ": no damage - " + noDamage
					+ ", half damage - " + halfDamage + ", normal damage - "
					+ normalDamage + ", double damage - " + doubleDamage);
		}

		emitter.emit("");

		for (String type : TYPES) {
			List<List<String>> defendingData = defendingTypeAdvantages
					.get(type);
			List<String> noDamage = defendingData.get(0);
			List<String> halfDamage = defendingData.get(1);
			List<String> normalDamage = defendingData.get(2);
			List<String> doubleDamage = defendingData.get(3);
			emitter.emit("Defending with " + type + ": no damage - " + noDamage
					+ ", half damage - " + halfDamage + ", normal damage - "
					+ normalDamage + ", double damage - " + doubleDamage);
		}
		emitter.close();
	}

	public static void main(String[] args) {
		try {
			writeToFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Couldn't write to typeInfo file");
			e.printStackTrace();
		}
	}
}

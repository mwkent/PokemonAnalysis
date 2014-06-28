/* 
 * This class currently can find the weaknesses of a single pokemon or a team of pokemon, 
 * using the findWeaknesses and findTeamWeaknesses methods respectively. findAttackAdvantages
 * takes a list of attack types and returns what types the given list will be super effective
 * against.
 */

package pokemon.analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pokemon.data_initialization.Data;

public class Analysis {
	private final Data data;

	public Analysis() throws IOException {
		data = new Data();
	}

	public String printAttackAdvantages(List<String> attackTypes) {
		String result;
		for (String attackType : attackTypes) {
			if (!data.isType(attackType)) {
				result = attackType + " is not a type";
				System.out.println(result);
				return result;
			}
		}
		List<String> attackAdvantages = findAttackAdvantages(attackTypes);
		int numAdvantages = attackAdvantages.size();
		if (numAdvantages == 0) {
			result = "No advantages";
		} else {
			result = attackAdvantages.size() + " advantages to ";
			for (String attackAdvantage : attackAdvantages) {
				result += attackAdvantage + ", ";
			}
			result = result.substring(0, result.length() - 2);
		}
		System.out.println(result);
		return result;
	}

	public String printTeamWeaknesses(List<String> pokemons) {
		List<Map<String,Integer>> teamWeaknesses = findTeamWeaknesses(pokemons);
		Map<String,Integer> resistanceCount = teamWeaknesses.get(0);
		List<String> mostResistances = new ArrayList<String>();
		int numResistances = 0;
		Iterator<String> types = resistanceCount.keySet().iterator();
		while (types.hasNext()) {
			String type = types.next();
			Integer count = resistanceCount.get(type);
			if (count > numResistances) {
				mostResistances = new ArrayList<String>();
				mostResistances.add(type);
				numResistances = count;
			} else if (count == numResistances) {
				mostResistances.add(type);
			}
		}
		Map<String,Integer> weaknessCount = teamWeaknesses.get(1);
		List<String> mostWeaknesses = new ArrayList<String>();
		int numWeaknesses = 0;
		types = weaknessCount.keySet().iterator();
		while (types.hasNext()) {
			String type = types.next();
			Integer count = weaknessCount.get(type);
			if (count > numWeaknesses) {
				mostWeaknesses = new ArrayList<String>();
				mostWeaknesses.add(type);
				numWeaknesses = count;
			} else if (count == numWeaknesses) {
				mostWeaknesses.add(type);
			}
		}
		int numPokemon = pokemons.size();
		String result = "Team - ";
		for (int i = 0; i < numPokemon; i++) {
			String pokemon = pokemons.get(i);
			if (i == numPokemon - 1) {
				result += pokemon + "\n";
			} else {
				result += pokemon + ", ";
			}
		}
		result += numResistances + " resistances to ";
		for (String type : mostResistances) {
			result += type + " ";
		}
		result += "\n" + numWeaknesses + " weaknesses to ";
		for (String type : mostWeaknesses) {
			result += type + " ";
		}
		System.out.println(result);
		return result;
	}

	public String printWeaknesses(String pokemon) {
		List<List<String>> weaknesses = findWeaknesses(pokemon);
		if (weaknesses == null || weaknesses.size() < 5) {
			String result = pokemon + " is not a pokemon";
			System.out.println(result);
			return result;
		} else {
			String result = pokemon + "\n";
			List<String> noDamage = weaknesses.get(0);
			List<String> quarterDamage = weaknesses.get(1);
			List<String> halfDamage = weaknesses.get(2);
			List<String> normalDamage = weaknesses.get(3);
			List<String> doubleDamage = weaknesses.get(4);
			List<String> quadrupleDamage = weaknesses.get(5);
			if (noDamage.size() > 0)
				result += " 0x   - " + noDamage + "\n";
			if (quarterDamage.size() > 0)
				result += " 1/4x - " + quarterDamage + "\n";
			if (halfDamage.size() > 0)
				result += " 1/2x - " + halfDamage + "\n";
			if (normalDamage.size() > 0)
				result += " 1x   - " + normalDamage + "\n";
			if (doubleDamage.size() > 0)
				result += " 2x   - " + doubleDamage + "\n";
			if (quadrupleDamage.size() > 0)
				result += " 4x   - " + quadrupleDamage + "\n";
			System.out.print(result);
			return result;
		}
	}

	//////////////////
	// Private methods
	//////////////////

	private List<String> findAttackAdvantages(List<String> attackTypes) {
		List<String> attackAdvantages = new ArrayList<String>();
		for (String attackType : attackTypes) {
			List<String> superEffectiveTypes = data.getAttackingTA(attackType)
					.get(3);
			for (String superEffectiveType : superEffectiveTypes) {
				if (!attackAdvantages.contains(superEffectiveType)) {
					attackAdvantages.add(superEffectiveType);
				}
			}
		}
		return attackAdvantages;
	}

	// Returns the types that the team resists and is weak to and how many pokemon
	// on the team that resist and are weak to those types
	private List<Map<String,Integer>> findTeamWeaknesses(List<String> pokemons) {
		//
		List<Map<String, Integer>> damageCounts = new ArrayList<Map<String, Integer>>();
		for (int i = 0; i < 6; i++) {
			damageCounts.add(new HashMap<String, Integer>());
		}
		for (String pokemon : pokemons) {
			List<List<String>> weaknesses = findWeaknesses(pokemon);
			for (int i = 0; i < 6; i++) {
				List<String> someDamage = weaknesses.get(i);
				Map<String, Integer> damageCount = damageCounts.get(i);
				for (String type : someDamage) {
					Integer count = damageCount.get(type);
					if (count == null) {
						damageCount.put(type, 1);
					} else {
						damageCount.put(type, count + 1);
					}
				}
			}
		}
		Map<String, Integer> resistanceCount = new HashMap<String, Integer>();
		Map<String, Integer> weaknessCount = new HashMap<String, Integer>();
		for (int i = 0; i < 6; i++) {
			if (i < 3) {
				Iterator<Entry<String, Integer>> someResistanceCount = damageCounts
						.get(i).entrySet().iterator();
				while (someResistanceCount.hasNext()) {
					Entry<String, Integer> entry = someResistanceCount.next();
					String type = entry.getKey();
					Integer specificCount = entry.getValue();
					Integer count = resistanceCount.get(type);
					count = (count == null) ? specificCount : count
							+ specificCount;
					resistanceCount.put(type, count);
				}
			} else if (i > 3) {
				Iterator<Entry<String, Integer>> someWeaknessCount = damageCounts
						.get(i).entrySet().iterator();
				while (someWeaknessCount.hasNext()) {
					Entry<String, Integer> entry = someWeaknessCount.next();
					String type = entry.getKey();
					Integer specificCount = entry.getValue();
					Integer count = weaknessCount.get(type);
					count = (count == null) ? specificCount : count
							+ specificCount;
					weaknessCount.put(type, count);
				}
			}
		}
		List<Map<String,Integer>> teamWeaknesses = new ArrayList<Map<String,Integer>>();
		teamWeaknesses.add(resistanceCount);
		teamWeaknesses.add(weaknessCount);
		return teamWeaknesses;
	}

	// Gives the types that do 0x, 1/4x, 1/2x, 2x, and 4x damage to the given
	// pokemon
	private List<List<String>> findWeaknesses(String pokemon) {
		List<List<String>> weaknesses = new ArrayList<List<String>>();
		List<String> noDamage = new ArrayList<String>();
		List<String> quarterDamage = new ArrayList<String>();
		List<String> halfDamage = new ArrayList<String>();
		List<String> normalDamage = new ArrayList<String>();
		List<String> doubleDamage = new ArrayList<String>();
		List<String> quadrupleDamage = new ArrayList<String>();
		String type1 = data.getType1(pokemon);
		String type2 = data.getType2(pokemon);
		// type advantages when defending with type1
		List<List<String>> defendingTAT1 = data.getDefendingTA(type1);

		// type1 does not exist, meaning the pokemon does not exist
		if (type1.equals("")) {
			return null;
		}

		// type2 does not exist
		if (type2.equals("")) {
			noDamage = defendingTAT1.get(0);
			halfDamage = defendingTAT1.get(1);
			normalDamage = defendingTAT1.get(2);
			doubleDamage = defendingTAT1.get(3);
			weaknesses.add(noDamage);
			weaknesses.add(quarterDamage);
			weaknesses.add(halfDamage);
			weaknesses.add(normalDamage);
			weaknesses.add(doubleDamage);
			weaknesses.add(quadrupleDamage);
			return weaknesses;
		}

		// type2 exists
		List<List<String>> defendingTAT2 = data.getDefendingTA(type2);
		List<String> noDamageToT1 = defendingTAT1.get(0);
		List<String> noDamageToT2 = defendingTAT2.get(0);
		List<String> halfDamageToT1 = defendingTAT1.get(1);
		List<String> halfDamageToT2 = defendingTAT2.get(1);
		List<String> normalDamageToT1 = defendingTAT1.get(2);
		List<String> normalDamageToT2 = defendingTAT2.get(2);
		List<String> doubleDamageToT1 = defendingTAT1.get(3);
		List<String> doubleDamageToT2 = defendingTAT2.get(3);
		noDamage.addAll(noDamageToT1);
		noDamage.addAll(noDamageToT2);
		for (String type : halfDamageToT1) {
			if (halfDamageToT2.contains(type)) {
				quarterDamage.add(type);
			}
			else if (normalDamageToT2.contains(type)) {
				halfDamage.add(type);
			}
			else if (doubleDamageToT2.contains(type)) {
				normalDamage.add(type);
			}
		}
		for (String type : normalDamageToT1) {
			if (halfDamageToT2.contains(type)) {
				halfDamage.add(type);
			}
			else if (normalDamageToT2.contains(type)) {
				normalDamage.add(type);
			}
			else if (doubleDamageToT2.contains(type)) {
				doubleDamage.add(type);
			}
		}
		for (String type : doubleDamageToT1) {
			if (halfDamageToT2.contains(type)) {
				normalDamage.add(type);
			}
			else if (normalDamageToT2.contains(type)) {
				doubleDamage.add(type);
			}
			else if (doubleDamageToT2.contains(type)) {
				quadrupleDamage.add(type);
			}
		}

		weaknesses.add(noDamage);
		weaknesses.add(quarterDamage);
		weaknesses.add(halfDamage);
		weaknesses.add(normalDamage);
		weaknesses.add(doubleDamage);
		weaknesses.add(quadrupleDamage);
		return weaknesses;
	}

	// Main method

	public static void main(String[] args) throws IOException {
		Analysis analysis = new Analysis();
		analysis.printWeaknesses("noivern");
		List<String> attackTypes = new ArrayList<String>();
		attackTypes.add("dragon");
		attackTypes.add("flying");
		attackTypes.add("fire");
		attackTypes.add("dark");
		analysis.printAttackAdvantages(attackTypes);
//		List<String> team = new ArrayList<String>();
//		team.add("landorus (therian forme)");
//		team.add("thundurus (incarnate forme)");
//		team.add("talonflame");
//		team.add("tyranitar");
//		team.add("clefable");
//		team.add("aegislash (blade forme)");
//		analysis.printTeamWeaknesses(team);
	}
}

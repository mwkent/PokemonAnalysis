package pokemon.data_initialization;

import java.io.IOException;
import java.util.List;

public class Data implements DataInterface {
	private final PokemonData pokemonData;
	
	public Data() throws IOException {
		pokemonData = new PokemonData();
	}

	public String getType1(String pokemon) {
		return pokemonData.getType1(pokemon);
	}
	
	public String getType2(String pokemon) {
		return pokemonData.getType2(pokemon);
	}

	// Attacking type advantages
	public List<List<String>> getAttackingTA(String type) {
		return TypeAdvantages.getAttackingTA(type);
	}
	
	// Defending type advantages
	public List<List<String>> getDefendingTA(String type) {
		return TypeAdvantages.getDefendingTA(type);
	}
	
	public boolean isType(String type) {
		return TypeAdvantages.isType(type);
	}
}

package pokemon.data_initialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

class PokemonData {
	// maps a pokemon's name to the pokemon
	private final HashMap<String, Pokemon> data;

	public PokemonData() throws FileNotFoundException {
		data = parseData();
	}

	private HashMap<String, Pokemon> parseData() throws FileNotFoundException {
		HashMap<String, Pokemon> pokeData = new HashMap<String, Pokemon>();
		File dataFile = new File("/Users/mattkent/pokemanzzz/Pokemon Website/data/pokemonInfo");
		Scanner scanner = new Scanner(dataFile);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			lineScanner.useDelimiter(" ");
			int number = lineScanner.nextInt();
			String name = lineScanner.next();
			while (!lineScanner.hasNextInt()) {
				name += " " + lineScanner.next();
			}
			int hp = lineScanner.nextInt();
			int attack = lineScanner.nextInt();
			int defense = lineScanner.nextInt();
			int spAttack = lineScanner.nextInt();
			int spDefense = lineScanner.nextInt();
			int speed = lineScanner.nextInt();
			int BST = lineScanner.nextInt();
			String type1 = lineScanner.next();
			String type2 = lineScanner.hasNext() ? lineScanner.next() : "";
			Pokemon p = new Pokemon(name, type1, type2, number, hp, attack,
					defense, spAttack, spDefense, speed, BST);
			pokeData.put(name, p);
			lineScanner.close();
		}
		scanner.close();
		return pokeData;
	}

	public String getType1(String pokemon) {
		Pokemon p = data.get(pokemon);
		if (p == null) {
			return "";
		}
		return p.getType1();
	}

	public String getType2(String pokemon) {
		Pokemon p = data.get(pokemon);
		if (p == null) {
			return "";
		}
		return p.getType2();
	}
}

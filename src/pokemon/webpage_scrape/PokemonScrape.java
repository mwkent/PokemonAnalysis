/*
 * Scrapes webpages and writes the pokemon data to the data/pokemonInfo.txt file.
 */
package pokemon.webpage_scrape;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pokemon.data_initialization.Emitter;
import pokemon.data_initialization.Pokemon;

class PokemonScrape {
	private static final String STATS_URL = "http://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_base_stats_(Generation_VI-present)";
	private static final String TYPES_URL = "http://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_National_Pok%C3%A9dex_number";
	private final HashMap<String, Pokemon> data;
	private final List<Pokemon> sortedData;
	private static final File DATA_FILE = new File("data", "pokemonInfo");

	public PokemonScrape() throws IOException {
		data = getData();
		setTypes();
		sortedData = sortData();
		writeToFile(DATA_FILE);
	}

	private HashMap<String, Pokemon> getData() throws IOException {
		Document doc = Jsoup.connect(STATS_URL).get();
		Elements pokemonLines = doc.select("tr");

		HashMap<String, Pokemon> map = new HashMap<String, Pokemon>();
		for (Element pokemonLine : pokemonLines) {
			String pokemonText = pokemonLine.text();
			// System.out.println(pokemonText);

			// 3 digits followed by anything
			if (pokemonText.matches("\\d{3}.*")) {

				// Gets number and stats of the pokemon
				Pattern statPattern = Pattern.compile("(\\d{1,}(\\S\\d{1,})?)");
				Matcher statMatcher = statPattern.matcher(pokemonText);
				List<Integer> stats = new ArrayList<Integer>();
				for (int i = 0; i < 8; i++) {
					if (statMatcher.find()) {
						stats.add(Integer.parseInt(statMatcher.group()));
					}
				}

				// At least one non-digit followed by a digit
				// This will find the name of the pokemon
				Pattern namePattern = Pattern.compile("\\D+");
				Matcher nameMatcher = namePattern.matcher(pokemonText);
				String name = "";
				if (nameMatcher.find()) {
					String fullName = nameMatcher.group().toLowerCase();
					int endIndex = fullName.length();
					int startOfExtra = fullName.indexOf('(');
					if (fullName.substring(0, 2).equals("m ")
							&& startOfExtra != -1) {
						name = "mega "
								+ fullName.substring(2, startOfExtra - 1);
					} else if (fullName.substring(0, 3).equals("mx ")
							&& startOfExtra != -1) {
						name = "mega "
								+ fullName.substring(3, startOfExtra - 1)
								+ " x";
					} else if (fullName.substring(0, 3).equals("my ")
							&& startOfExtra != -1) {
						name = "mega "
								+ fullName.substring(3, startOfExtra - 1)
								+ " y";
					} else {
						name = fullName.substring(1, endIndex - 1);
					}
					// Fix name
					if (stats.get(0) == 29) {
						name = "nidoran female";
					} else if (stats.get(0) == 32) {
						name = "nidoran male";
					} else if (name.contains("flab")) {
						name = "flabebe";
					}

				}
				// System.out.println(name);

				if (stats.size() < 8 || name.equals("")) {
					System.out.println("Problem parsing pokemon data for "
							+ name);
					System.out.println(pokemonText);
					continue;
				}

				Pokemon pokemon = new Pokemon(name, stats.get(0), stats.get(1),
						stats.get(2), stats.get(3), stats.get(4), stats.get(5),
						stats.get(6), stats.get(7));
				map.put(name, pokemon);
			}
		}
		return map;
	}

	private void setTypes() throws IOException {
		Document doc = Jsoup.connect(TYPES_URL).get();
		Elements pokemonLines = doc.select("tr");

		for (Element pokemonLine : pokemonLines) {
			String pokemonText = pokemonLine.text();
			// System.out.println(pokemonText);

			// #ddd #ddd words
			if (pokemonText.matches("#\\d{3}\\s#\\d{3}\\s[a-zA-Z].*")) {
				int number = -1;
				Pattern numberPattern = Pattern.compile("#\\d{3} #\\d{3}");
				Matcher numberMatcher = numberPattern.matcher(pokemonText);
				if (numberMatcher.find()) {
					number = Integer.parseInt(numberMatcher.group()
							.substring(6));
				}

				// Consecutive group of letters
				// This will find the name of the pokemon
				Pattern namePattern = Pattern.compile("[a-zA-Z]+");
				Matcher nameMatcher = namePattern.matcher(pokemonText);
				String name = "";
				if (nameMatcher.find()) {
					name = nameMatcher.group().toLowerCase();
					// Fix pokemon with special names
					if (number == 29) {
						name = "nidoran female";
					} else if (number == 32) {
						name = "nidoran male";
					} else if (name.equals("flab")) {
						name = "flabebe";
					} else if (name.equals("mr")) {
						name = "mr. mime";
					} else if (name.equals("mime")) {
						name = "mime jr.";
					} else if (name.equals("farfetch")) {
						name = "farfetch'd";
					} else if (name.equals("ho")) {
						name = "ho-oh";
					}
				}

				Pattern typePattern = Pattern
						.compile("Normal|Fighting|Flying|Poison|Ground|Rock|Bug|Ghost|Steel|Fire|Water|Grass|Electric|Psychic|Ice|Dragon|Dark|Fairy");
				Matcher typeMatcher = typePattern.matcher(pokemonText);
				String type1 = "";
				String type2 = "";
				if (typeMatcher.find()) {
					type1 = typeMatcher.group().toLowerCase();
				}
				if (typeMatcher.find()) {
					type2 = typeMatcher.group().toLowerCase();
				}
				Pokemon p = data.get(name);
				if (p != null) {
					p.setType1(type1);
					p.setType2(type2);
					String megaPName = "mega " + name;
					String megaPXName = "mega " + name + " x";
					String megaPYName = "mega " + name + " y";
					Pokemon megaP = data.get(megaPName);
					Pokemon megaPX = data.get(megaPXName);
					Pokemon megaPY = data.get(megaPYName);
					if (megaP != null) {
						megaP.setType1(type1);
						megaP.setType2(type2);
					}
					if (megaPX != null) {
						megaPX.setType1(type1);
						megaPX.setType2(type2);
					}
					if (megaPY != null) {
						megaPY.setType1(type1);
						megaPY.setType2(type2);
					}
					if (megaPXName.equals("mega charizard x")) {
						megaPX.setType1(type1);
						megaPX.setType2("dragon");
					} else if (megaPName.equals("mega pinsir")) {
						megaP.setType1(type1);
						megaP.setType2("flying");
					} else if (megaPName.equals("mega gyarados")) {
						megaP.setType1(type1);
						megaP.setType2("dark");
					} else if (megaPName.equals("mega ampharos")) {
						megaP.setType1(type1);
						megaP.setType2("dragon");
					} else if (megaPName.equals("mega aggron")) {
						megaP.setType1(type1);
						megaP.setType2("");
					}
				} else {
					if (name.equals("meloetta")) {
						if (type2.equals("psychic")) {
							name = "meloetta (aria forme)";
						} else {
							name = "meloetta (pirouette forme)";
						}
						p = data.get(name);
						p.setType1(type1);
						p.setType2(type2);
					} else if (name.equals("darmanitan")) {
						if (type2.equals("")) {
							name = "darmanitan (standard mode)";
						} else {
							name = "darmanitan (zen mode)";
						}
						p = data.get(name);
						p.setType1(type1);
						p.setType2(type2);
					} else if (name.equals("rotom")) {
						if (type2.equals("ghost")) {
							name = "rotom (normal rotom)";
						} else if (type2.equals("water")) {
							name = "rotom (wash rotom)";
						} else if (type2.equals("flying")) {
							name = "rotom (fan rotom)";
						} else if (type2.equals("grass")) {
							name = "rotom (mow rotom)";
						} else if (type2.equals("ice")) {
							name = "rotom (frost rotom)";
						} else {
							name = "rotom (heat rotom)";
						}
						p = data.get(name);
						p.setType1(type1);
						p.setType2(type2);
					} else if (name.equals("wormadam")) {
						if (type2.equals("grass")) {
							name = "wormadam (plant cloak)";
						} else if (type2.equals("ground")) {
							name = "wormadam (sandy cloak)";
						} else {
							name = "wormadam (trash cloak)";
						}
						p = data.get(name);
						p.setType1(type1);
						p.setType2(type2);
					} else {
						Iterator<String> names = data.keySet().iterator();
						while (names.hasNext()) {
							String storedName = names.next();
							if (storedName.contains(name)) {
								p = data.get(storedName);
								p.setType1(type1);
								p.setType2(type2);
							}
						}
						// System.out.println("Couldn't find " + name
						// + " in type data");
						// System.out.println(pokemonText);
						// System.out.println(type1);
						// System.out.println(type2);
					}

				}
				if (type1.equals("") && type2.equals("")) {
					System.out.println("No types found for " + name);
				}
			}
		}
	}

	private List<Pokemon> sortData() {
		List<Pokemon> sortedData = new ArrayList<Pokemon>();
		Iterator<Pokemon> values = data.values().iterator();
		while (values.hasNext()) {
			sortedData.add(values.next());
		}
		Collections.sort(sortedData);
		return sortedData;
	}

	private void writeToFile(File file) throws IOException {
		Emitter emitter = new Emitter(file);
		Iterator<Pokemon> pokemans = sortedData.iterator();
		while (pokemans.hasNext()) {
			Pokemon pokemon = pokemans.next();
			emitter.emit(pokemon);
		}
		emitter.close();
	}

	public static void main(String[] args) throws IOException {
		new PokemonScrape();
	}
}

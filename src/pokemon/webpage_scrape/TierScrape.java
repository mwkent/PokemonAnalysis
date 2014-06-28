/*
 * The TierScrape class scrapes data on the pokemon tiers from the provided 
 * url and writes the data to the tierInfo file in the data directory. 
 */

package pokemon.webpage_scrape;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pokemon.data_initialization.Emitter;

class TierScrape {
	private static final String TIER_URL = "http://pokemon.neoseeker.com/wiki/Tier_listings";
	private static final File TIER_FILE = new File("data", "tierInfo");
	private final List<List<String>> tiers;
	private static final List<String> TIER_NAMES = Arrays.asList("Uber", "OU",
			"BL", "UU", "BL2", "RU", "BL3", "NU", "LC", "NFE");

	TierScrape() throws IOException {
		tiers = scrapeTiers();
		writeToFile(TIER_FILE);
	}

	private List<List<String>> scrapeTiers() throws IOException {
		List<List<String>> tiers = new ArrayList<List<String>>();
		Document doc = Jsoup.connect(TIER_URL).get();
		Elements pokemonLines = doc.select("ul");
		int numLines = pokemonLines.size();
		int startIndex = 0;
		while (startIndex < numLines) {
			Element pokemonLine = pokemonLines.get(startIndex);
			// Value of a 1 indicates the next lines are the pokemon tiers
			// However, this is susceptible to change
			boolean startsWith1 = pokemonLine.text().substring(0, 2)
					.equals("1 ");
			startIndex++;
			if (startsWith1) {
				break;
			}
		}
		// numLines-1 hardcoded for the site
		while (startIndex < numLines - 1) {
			List<String> tier = new ArrayList<String>();
			Element pokemonLine = pokemonLines.get(startIndex);
			String pokemonText = pokemonLine.text();
			String[] pokemons = pokemonText.split(" ");
			for (String pokemon : pokemons) {
				tier.add(pokemon);
			}
			tiers.add(tier);
			startIndex++;
		}
		return tiers;
	}

	private void writeToFile(File file) throws IOException {
		Emitter emitter = new Emitter(file);
		int numTiers = tiers.size();
		for (int i = 0; i < numTiers; i++) {
			List<String> tier = tiers.get(i);
			String tierName = TIER_NAMES.get(i);
			emitter.emit(tierName + " Tier");
			for (String pokemon : tier) {
				emitter.emit(pokemon);
			}
			emitter.emit("");
		}
		emitter.close();
	}

	public static void main(String[] args) throws IOException {
		new TierScrape();
	}
}

package pokemon.data_initialization;

import java.util.List;

public interface DataInterface {
	public String getType1(String pokemon);

	public String getType2(String pokemon);

	public List<List<String>> getAttackingTA(String type);

	public List<List<String>> getDefendingTA(String type);

	public boolean isType(String type);
}

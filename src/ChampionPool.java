import java.util.HashMap;
import java.util.Map;

public class ChampionPool {

    private Map<String, Champion> pool = new HashMap<>();

    public void addChampion(Champion champion) {
        pool.put(champion.getName(), champion);
    }

    public Champion get(String name) {
        return pool.get(name);
    }

    public boolean exists(String name) {
        return pool.containsKey(name);
    }
}
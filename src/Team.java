import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Team<T extends Champion>{
    private final Iff side;
    private List<T> members = new ArrayList<>();

    public Team(Iff side) {
        this.side = side;
    }
    public Iff getSide() {
        return side;
    }
    public void addMember(T champion) {
        champion.iff = side;
        members.add(champion);
        System.out.println(champion.getName() + "이(가) " + side + "팀에 합류!");
    }
    public List<T> getMembers() {
        return members;
    }
    public boolean isResurrectAvailable() {
        return members.stream().anyMatch(champion -> champion.isResurrectAvailable);
    }
    public boolean hasAlive() {
        return members.stream().anyMatch(champion -> champion.isAlive());
    }
    public List<T> aliveMembers() {
        return members.stream().filter(Champion::isAlive).toList();
    }
    public T randomAlive(Random random) {
        List<T> alive = aliveMembers();
        if(alive.isEmpty()) return null;
        return alive.get(random.nextInt(alive.size()));
    }
    public void cleanupDead() {
//        members.removeIf(c -> !c.isAlive());
        members.removeIf(c -> !c.isResurrectAvailable);
    }
}

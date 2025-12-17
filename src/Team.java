import java.util.ArrayList;
import java.util.List;

public class BlackTeam<T extends Champion>{
    private String name;
    private List<T> members = new ArrayList<>();

    public BlackTeam(String name) {
        this.name = name;
    }
}

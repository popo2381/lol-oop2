import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLog {
    // 로그 저장소 (게임 전역)
    private static final List<LogEntry> entries = new ArrayList<>();

    private GameLog() {}

    public static void add(String message) {
        entries.add(new LogEntry(message));
    }
    public static void printAll() {
        for (LogEntry entry : entries) {
        entry.print();
        }
    }
    public static void clear() {
        entries.clear();
    }

    public static List<LogEntry> snapshot() {
        return Collections.unmodifiableList(new ArrayList<>(entries));
    }

    public static class LogEntry {
        private final String message;

        public LogEntry(String message) {
            this.message = message;
        }

        public void print() {
            System.out.println("[LOG] " + message);
        }

        public String getMessage() {
            return message;
        }
    }
}

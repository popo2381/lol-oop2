public class Sona {
    private final String name;
    private int level;

    public Sona(String name) {
        this.name = name;
        this.level = 1;
    }

    public String getName() {
        return name;
    }

    public void heal(final Champion target) {
        target.setHp(target.getHp() + 100);
    }

    public void example() {
        final int baseDamage = 50;
        // baseDamage = 60; // 컴파일 에러
    }
}

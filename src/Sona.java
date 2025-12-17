public final class Sona extends Champion{
    private int level;

    public Sona(String name) {
        super(name, GameConstants.DEFAULT_HP, GameConstants.DEFAULT_ATK, GameConstants.DEFAULT_DEF);
    }
    public Sona(String name, Iff iff) {
        super(name, GameConstants.DEFAULT_HP, GameConstants.DEFAULT_ATK, GameConstants.DEFAULT_DEF, iff);
    }

    // --------스킬--------
    @Override
    public void useQ(Champion target) {
        //애쉬만의 q스킬
        deal(target, 25, "용맹의 찬가(Q)!");
    }public void useW(Champion target) {
        //애쉬만의 w스킬
        deal(target, 60, "인내의 아리아(W)!"); // 힐
    }public void useE(Champion target) {
        //애쉬만의 e스킬
        deal(target, 70, "기민함의 노래(E)!");
    }public void useR(Champion target) {
        //애쉬만의 r스킬
        deal(target, 90, "크레센도(R)!");
    }
    public void heal(final Champion target) {
        target.setHp(target.getHp() + 100);
    }

    public void example() {
        final int baseDamage = 50;
        // baseDamage = 60; // 컴파일 에러
    }
}

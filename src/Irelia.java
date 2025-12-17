public final class Irelia extends Champion {
    private int level;

    public Irelia(String name) {
        super(name, GameConstants.DEFAULT_HP, GameConstants.DEFAULT_ATK, GameConstants.DEFAULT_DEF);
    }
    public Irelia(String name, Iff iff) {
        super(name, GameConstants.DEFAULT_HP, GameConstants.DEFAULT_ATK, GameConstants.DEFAULT_DEF, iff);
    }

    // --------스킬--------
    @Override
    public void useQ(Champion target) {
        //애쉬만의 q스킬
        deal(target, 25, "칼날 쇄도(Q)!"); // 공격 + 회복
    }public void useW(Champion target) {
        //애쉬만의 w스킬
        deal(target, 60, "저항의 춤(W)!");
    }public void useE(Champion target) {
        //애쉬만의 e스킬
        deal(target, 70, "쌍검 협무(E)!");
    }public void useR(Champion target) {
        //애쉬만의 r스킬
        deal(target, 90, "선봉진격검(R)!");
    }
}
    
    
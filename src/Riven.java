public final class Riven extends Champion {
    private int level;

    public Riven(String name) {
        super(name, GameConstants.DEFAULT_HP, GameConstants.DEFAULT_ATK, GameConstants.DEFAULT_DEF);
    }
    public Riven(String name, Iff iff) {
        super(name, GameConstants.DEFAULT_HP, GameConstants.DEFAULT_ATK, GameConstants.DEFAULT_DEF, iff);
    }

    // --------스킬--------
    @Override
    public void useQ(Champion target) {
        //애쉬만의 q스킬
        deal(target, 25, "부러진 날개(Q)!");
    }public void useW(Champion target) {
        //애쉬만의 w스킬
        deal(target, 60, "기 폭발(W)!");
    }public void useE(Champion target) {
        //애쉬만의 e스킬
        deal(target, 70, "용맹(E)!"); // DEF 증가
    }public void useR(Champion target) {
        //애쉬만의 r스킬
        deal(target, 90, "추방자의 검(R)!"); // 공격력 증가 + 공격
    }
}
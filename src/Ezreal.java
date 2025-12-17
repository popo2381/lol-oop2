public final class Ezreal extends Champion {
    private int level;

    public Ezreal(String name) {
        super(name, GameConstants.DEFAULT_HP, GameConstants.DEFAULT_ATK, GameConstants.DEFAULT_DEF);
    }
    public Ezreal(String name, Iff iff) {
        super(name, GameConstants.DEFAULT_HP, GameConstants.DEFAULT_ATK, GameConstants.DEFAULT_DEF, iff);
    }

    // --------스킬--------
    @Override
    public void useQ(Champion target) {
        //애쉬만의 q스킬
        deal(target, 25, "주문의 힘(Q)!");
    }public void useW(Champion target) {
        //애쉬만의 w스킬
        deal(target, 60, "신비한 화살(W)!");
    }public void useE(Champion target) {
        //애쉬만의 e스킬
        deal(target, 70, "비전 이동(E)!");
    }public void useR(Champion target) {
        //애쉬만의 r스킬
        deal(target, 90, "정조준 일격(R)!");
    }
}
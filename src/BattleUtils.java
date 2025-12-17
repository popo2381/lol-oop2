public class BattleUtils {
    public static int battleCount =0;
    public static Champion pickHigherHp(Champion a, Champion b) {
        if (a.getHp() > b.getHp()) {
            return a;
        } else {
            return b;
        }
    }
}

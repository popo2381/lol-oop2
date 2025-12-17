import java.util.Random;

public abstract class Champion {
    //모든 챔피언이 공유하는 카운트
    private static int createdCount = 0;
    protected final String name;
    protected Iff iff;
    private int hp,atk,def;
    private int maxHp=GameConstants.DEFAULT_HP, maxAtk=GameConstants.DEFAULT_ATK, maxDef=GameConstants.DEFAULT_DEF;
    protected int level;
    private boolean alive=true;
    private int exp = 0;
    Random random = new Random();
    static final int MAX_RESURRECT_COUNT = 5;
    private int resurrectCount = 0;
    protected boolean isResurrectAvailable = true;
    // 생성자
    public Champion(String name, int hp, int atk, int def) {
        this.name = name;
        this.level = 1;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        createdCount++; // 챔피언이 생성될 때마다 +1
    }
    public Champion(String name, int hp, int atk, int def, Iff iff) {
        this.name = name;
        this.level = 1;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.iff = iff;
        createdCount++; // 챔피언이 생성될 때마다 +1
    }
    // ==== 레벨업 ====
    public void killExp() {
        exp += 50;
        if(exp >= 100) {
            exp -= 100;
            levelUp();
        }
    }
    public void defaultExp() {
        exp += 10;
    }
    public final void levelUp() {
        if (this.level >= GameConstants.MAX_LEVEL) {
            System.out.println("이미 최고 레벨입니다!");
        }
        level++;
        setHp(+100);
        this.hp += 100;
        setAtk(+50);
        setDef(+50);
        System.out.println(iffName() + "레벨업! 현재 레벨: [" + level + "]  ATK +50 HP +100만큼 상승했습니다!" );
    }
    // ==== 공격 ====
    public void deal(Champion target, int damage, String skillSound) {
        int skillDamage = atk + damage;
        System.out.println(iffName() + " -> " + target.iffName() + " " + skillSound);
        target.takeDamage(skillDamage, this);
        BattleUtils.battleCount++;
    }

    public void attack(Champion target) {
        int skill = random.nextInt(5);
        randomSkill(skill, target);
    }
    // ==== 피해 ====
    public void takeDamage(int skillDamage, Champion attacker) {
        if(!alive) return;
        int totalDamage = skillDamage - def;
        if(totalDamage < 0) totalDamage = 0;
        attacker.exp += 10;
        hp -= totalDamage;
        System.out.println(iffName() + "이(가) " + totalDamage + " 피해를 받았습니다.");

        if(hp <= 0) {
            hp = 0;
            alive = false;
            System.out.println(iffName() + "이(가) 사망했습니다.");
            attacker.killExp();
            if(GameConstants.GAME_MODE.equals("PERSONAL") || GameConstants.GAME_MODE.equals("TEAM")) {
                setAlive();
            }
        }else System.out.println(iffName() + "의 남은 체력: " + hp);
    }
    // ==== 부활 ====
    public final void resurrect() {
        resurrectCount++;
        if(resurrectCount > MAX_RESURRECT_COUNT) {
            isResurrectAvailable = false;
            return;
        }
        alive = true;
        setHp(-50);
        this.hp = maxHp;
        setAtk(+20);
    }

    /// ======= skill ======= ///
    // ==== 기본 공격 ====
    public void basicAttack(Champion target) {
        System.out.println(iffName() + " -> " + target.iffName() + " 평타 공격! " );
        target.takeDamage((GameConstants.DEFAULT_ATK+10), this);
        BattleUtils.battleCount++;
        /*if(target.isAlive()) {
        sleep(2000);
        basicAttack(target);
        }*/
    }
    // ==== QWER스킬 ====
    public abstract void useQ(Champion target);
    public abstract void useW(Champion target);
    public abstract void useE(Champion target);
    public abstract void useR(Champion target);

    // 스킬 랜덤 로직
    public void randomSkill(int skill, Champion target) {
        switch(skill) {
            case 0 -> basicAttack(target);
            case 1 -> useQ(target);
            case 2 -> useW(target);
            case 3 -> useE(target);
            case 4 -> useR(target);
        }
    }
    public boolean getAlive() {
        return alive;
    }
    public void setAlive() {
        resurrect();
        if(!isResurrectAvailable) {
            System.out.println(iffName() + "는 최대 부활횟수를 초과하여 더이상 부활할 수 없습니다. (부활 횟수: " +
                    (resurrectCount-1) + ")");
            return;
        }
        if(GameConstants.GAME_MODE.equals("NORMAL")) {
            System.out.println("2초 후 리스폰 됩니다.");
            sleep(2000);
        }
//        else if(GameConstants.GAME_MODE.equals("PERSONAL")
//                || GameConstants.GAME_MODE.equals("TEAM"))
//
        System.out.println(iffName() + "이(가) 부활했습니다. ");
        System.out.println("[현재 HP: " + getHp() + " | ATK: " + getAtk()
        + " | 부활 횟수: " + resurrectCount + "]");
    }

    public String iffName() {
        return "[" + iff + "/" + name + "]";
    }
    public static int getCreatedCount() {
        return createdCount;
    }
    public String getName() {
        return name;
    }
    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        maxHp += hp;
    }
    public int getAtk() {
        return atk;
    }
    public void setAtk(int atk) {
        maxAtk += atk;
    }
    public int getDef() {
        return def;
    }
    public void setDef(int def) {
        maxDef += def;
    }
    public String toString() {
        return this.name;
    }
    public boolean isAlive() {
        return alive;
    }
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public Enum getIff() {
        return iff;
    }
}

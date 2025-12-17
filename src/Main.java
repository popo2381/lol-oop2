import java.util.*;
import java.util.stream.IntStream;

public class Main {
    private static Random random = new Random();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("========================================");
        System.out.println("        League of Legends - GAME MENU   ");
        System.out.println("========================================");
        System.out.println(" 1. 일반 게임 (Normal Game)");
        System.out.println(" 2. 개인전 모드 (Personal Game)");
        System.out.println(" 3. 팀전 모드 (Team Game)");
        System.out.println("----------------------------------------");
        System.out.println(" 4. 챔피언 정보 조회"); // 보류
        System.out.println(" 5. 게임 정보 조회"); // 보류
        System.out.println("----------------------------------------");
        System.out.println(" 0. 게임 종료"); // 보류
        System.out.println("========================================");
        System.out.print("메뉴 번호를 선택하세요: ");
        int number = scanner.nextInt();
        switch (number) {
            case 1:
                GameConstants.GAME_MODE = "NORMAL";
                normalGame();
                break;
            case 2:
                GameConstants.GAME_MODE = "PERSONAL";
                personalGame();
                break;
            case 3:
                GameConstants.GAME_MODE = "TEAM";
                teamGame();
                break;
        }
    }
    /// ========= normal Game =========
    public static void normalGame() {
        boolean battle = true, match = true;
        int round = 1;
        Scanner sc = new Scanner(System.in);
        System.out.println("========================================");
        System.out.println("    NORMAL MODE는 일대일 전투 모드 입니다.    ");
        System.out.println("========================================");
        sleep(1000);
        System.out.println("              챔 피 언 목 록               ");
        System.out.println("========================================");
        sleep(500);
        System.out.println("  1.Irelia  2.Riven  3.Ezreal  4.Sona   ");
        System.out.println("========================================");
        Champion myChamp = selectChampion(sc, "내 챔피언을 선택하세요 : ", Iff.MY);
        Champion enemyChamp = selectChampion(sc, "상대 챔피언을 선택하세요 : ", Iff.ENEMY);
        while(battle) {
            System.out.println("\n===== " + round + "라운드 =====\n");
            while(match) {
                if (random.nextBoolean()) {
                    myChamp.attack(enemyChamp);
                    sleep(100);
                    enemyChamp.attack(myChamp);
                    sleep(100);
                }
                if (!myChamp.isAlive()) {
                    myChamp.setAlive();
                    round++;
                    break;
                } else if (!enemyChamp.isAlive()) {
                    enemyChamp.setAlive();
                    round++;
                    break;
                }
            }
            if (!myChamp.isResurrectAvailable || !enemyChamp.isResurrectAvailable) {
                battle = false;
            }
        }
        if(myChamp.isAlive()) {
            System.out.println("[결과] 승리: " + myChamp.iffName());
        }
        else
            System.out.println("[결과] 승리: " + enemyChamp.iffName());
    }
    /// ========= personal Game =========
    public static void personalGame() {
        Scanner sc = new Scanner(System.in);
        System.out.println("========================================");
        System.out.println("    PERSONAL MODE는 개인전 모드 입니다.      ");
        System.out.println("========================================");
        sleep(1000);
        System.out.println("         랜덤으로 진행되는 챔피언 목록         ");
        System.out.println("========================================");
        sleep(500);
        System.out.println("  1.Irelia  2.Riven  3.Ezreal  4.Sona   ");
        System.out.println("========================================");
        /// ========= 챔피언 소환 =========
        List<Champion> champions = summonChampion(sc, "챔피언 인원수를 입력해주세요 (최대 8인): ");
        System.out.println("나의 챔피언: " +
                champions.stream()
                        .filter(i -> i.getIff() == Iff.MY )
                        .map(Champion::getName)
                        .toList());
        sc.nextLine();
        /// ========= battle start =========
        int round = 1;
        boolean running = true;
        System.out.println("현재 참가중인 챔피언: " + champions);
        GameLog.LogEntry entry = new GameLog.LogEntry("전투 시작");
        entry.print();
        while (running) {
            Champion attacker = champions.get(random.nextInt(champions.size()));
            Champion defender = champions.get(random.nextInt(champions.size()));

            while(true) {
                if(attacker == defender) {
                    defender = champions.get(random.nextInt(champions.size()));
                }else break;
            }
            attacker.attack(defender);
            sleep(300);
            champions.removeIf(c -> !c.isResurrectAvailable);
            if (champions.size() < 2) {
                System.out.println("전투가 종료되었습니다.");
                running = false;
            }
        }
        System.out.println("");
        System.out.println("[결과] 우승: " + champions.get(0).iffName());
//        System.out.println("살아남은 챔피언: " + champions + " 총 " + champions.size() + "명" );
        System.out.println("총 생성된 챔피언 수: " + Champion.getCreatedCount());
        System.out.println("총 싸움 횟수: " + BattleUtils.battleCount);
//        Champion winner = BattleUtils.pickHigherHp(irelia, riven);
    }
     ///========= team Game =========
    public static void teamGame() {
        Scanner sc = new Scanner(System.in);
        System.out.println("========================================");
        System.out.println("   TEAM MODE는 팀대팀 ONE HIT 모드 입니다.   ");
        System.out.println("========================================");
        sleep(1000);
        System.out.println("        RED팀과 BLUE팀으로 진행됩니다.        ");
        System.out.println("========================================");
        sleep(500);
        System.out.println("  1.Irelia  2.Riven  3.Ezreal  4.Sona   ");
        System.out.println("========================================");
        // 챔피언 Pool 구성
        ChampionPool pool = new ChampionPool();
        pool.addChampion(new Irelia("irelia"));
        pool.addChampion(new Riven("riven"));
        pool.addChampion(new Ezreal("ezreal"));
        pool.addChampion(new Sona("sona"));

        // 팀 생성
        Team<Champion> blue = new Team<>(Iff.BLUE);
        Team<Champion> red = new Team<>(Iff.RED);

        // 팀 배치
//        blue.addMember(pool.get("irelia"));
//        blue.addMember(pool.get("riven"));
//
//        red.addMember(pool.get("ezreal"));
//        red.addMember(pool.get("sona"));
        Map<Integer, String> menu = Map.of (
                1, "irelia",
                2, "riven",
                3, "ezreal",
                4, "sona"
        );
        System.out.print("팀을 선택하세요 (1.RED 2.BLUE) : ");
        int teamChoice = sc.nextInt();
        Team<Champion> myTeam=null, enemyTeam=null;
        if(teamChoice == 1) {
            myTeam = red;
            enemyTeam = blue;
        } else if (teamChoice == 2) {
            myTeam = blue;
            enemyTeam = red;
        }

        Set<Integer> myPick = new HashSet<>();
        System.out.println("");
        System.out.println("1.irelia 2.riven. 3.ezreal. 4.sona");
        System.out.print("내 팀에서 싸우게 될 챔피언 2명을 선택해주세요 (예:1 3) : ");
        while(myPick.size() < 2) {
            int pick = sc.nextInt();
            if(!menu.containsKey(pick)) {
                System.out.println("잘못된 번호입니다.");
                continue;
            }
            if(myPick.contains(pick)) {
                System.out.println("이미 선택한 챔피언입니다.");
                continue;
            }
            myPick.add(pick);
        }
        for (int pick : myPick) {
            myTeam.addMember(pool.get(menu.get(pick)));
        }
        System.out.println("=======================");
        for(int i = 1; i <= 4; i++) {
            if (!myPick.contains(i)) {
                enemyTeam.addMember(pool.get(menu.get(i)));
            }
        }
        System.out.println("=======================");
        System.out.println("내 팀(" + myTeam.getSide() + "): " +
                myTeam.getMembers());
        System.out.println("=======================");
        System.out.println("상대 팀(" + enemyTeam.getSide() + "): " +
                enemyTeam.getMembers());

        battle(blue, red);
    }
    ///  ========= view champ info =========
    public static void viewChamp() {

    }

    ///  ========= view game info =========
    ///  ========= exit  =========
    public static Champion selectChampion(Scanner sc, String output, Iff iff) {
        System.out.print(output);
        int champ = sc.nextInt();

        switch (champ) {
            case 1:
                return new Irelia("irelia", iff);
            case 2:
                return new Riven("riven", iff);
            case 3:
                return new Ezreal("ezreal", iff);
            case 4:
                return new Sona("sona", iff);
        }
        return null;
    }
    public static List<Champion> summonChampion(Scanner sc, String output) {
        System.out.print(output);
        int player = sc.nextInt();
        List<Champion> champions = new ArrayList<>(
                IntStream.range(0, player)
                        .mapToObj(i -> {
                            Iff iff = (i == 0) ? Iff.MY : Iff.ENEMY;
                            int champ = random.nextInt(4);
                            return switch (champ) {
                                case 0 -> new Irelia("irelia", iff);
                                case 1 -> new Riven("riven", iff);
                                case 2 -> new Ezreal("ezreal", iff);
                                case 3 -> new Sona("sona", iff);
                                default -> throw new IllegalStateException("Unexpected value: " + champ);
                            };
                        })
                        .toList());

        return champions;
    }
    public static void battle(Team<? extends Champion > aChamp , Team<? extends Champion> bChamp) {
        int round = 1;
        boolean running = true;
        System.out.println("=== " + aChamp.getSide() + " vs " +  bChamp.getSide() + " START ===");
        GameLog.LogEntry entry = new GameLog.LogEntry("전투 시작");
        while (running) {
            if(random.nextBoolean()) {
                fight(aChamp, bChamp);
                sleep(300);
            } else {
                fight(bChamp, aChamp);
                sleep(300);
            }
            aChamp.cleanupDead();
            bChamp.cleanupDead();
            if(aChamp.getMembers().size() == 0) {
                System.out.println("");
                System.out.println(bChamp.getSide() + "에서 살아남은 챔피언: " + bChamp.getMembers() + "(" + bChamp.getMembers().size() + ")");
                running = false;
            } else if(bChamp.getMembers().size() == 0) {
                System.out.println("");
                System.out.println(aChamp.getSide() + "에서 살아남은 챔피언: " + aChamp.getMembers() + "(" + aChamp.getMembers().size() + ")");
                running = false;
            }
        }
        Iff winner = aChamp.hasAlive() ? aChamp.getSide() : bChamp.getSide();
        System.out.println("=== Winner: " + winner + " ===");

    }
    private static void fight(Team<? extends Champion> atkSide, Team<? extends Champion> defSide) {
        Champion attacker = atkSide.randomAlive(random);
        Champion defender = defSide.randomAlive(random);
        if (attacker == null || defender == null)
            return;
        attacker.attack(defender);
    }
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


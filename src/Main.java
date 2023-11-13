import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 250, 200, 400, 200, 300, 150};
    public static int[] heroesDamage = {10, 30, 20, 0, 5, 0, 0, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "MEDIC", "GOLEM", "lUCKY", "WITCHER", "THOR"};
    public static int roundNumber;


    public static void thorAttack() {
        Random random = new Random();
        boolean isStunSuccessful = random.nextBoolean();

        if (isStunSuccessful) {
            System.out.println("Thor оглушил босса!");
            bossDefence = null;
        } else {
            System.out.println("Thor не смог оглушить босса.");
        }
    }

    public static void witcherHeroes() {
        for (int r = 0; r < heroesHealth.length; r++) {
            if (heroesHealth[6] > 0 && bossHealth > 0) {
                if (heroesHealth[r] <= 0) {
                    heroesHealth[r] = heroesHealth[6];
                    heroesHealth[6] = 0;
                    System.out.println("Witcher спас жизнь " + heroesAttackType[r]);
                }
            }

        }
    }

    public static void lucky() {
        Random random = new Random();
        int luckys = random.nextInt();
        if (luckys < 1) {
            System.out.println("Лаки уклонился от боса");

        } else {
            heroesHealth[5] -= bossDamage;
            if (heroesHealth[5] < 0) {
                heroesHealth[5] = 0;
            }
        }
    }

    public static void golemAttacs() {
        if (heroesHealth[4] > 0 && bossHealth > 0) {
            int damageOfGolem = bossDamage / 5;
            heroesHealth[4] = heroesHealth[4] - damageOfGolem;
            bossDamage -= damageOfGolem;
            System.out.println(heroesHealth[4] + " GOLEM 1/5 или 20% урона исходящий от Боса по другим союзником");

        }
    }

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void helpMedic() {
        int help = 50;
        boolean onlyOneHeal = false;
        for (int t = 0; t < heroesHealth.length; t++) {
            if (heroesHealth[3] > 0 && bossHealth >= 0) {
                if (heroesHealth[t] > 0 && heroesHealth[t] < 100) {
                    if (!onlyOneHeal) {
                        if (heroesAttackType[t] != "MEDIC") {
                            onlyOneHeal = true;
                            heroesHealth[t] += help;
                            System.out.println(heroesAttackType[3] + " Вылечел  героя: " + heroesAttackType[t]);
                        }
                    }
                }
            }
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttack();
        heroesAttack();
        golemAttacs();
        helpMedic();
        lucky();
        witcherHeroes();


        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void bossAttack() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("ROUND " + roundNumber + " -------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage +
                " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] +
                    " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }
}

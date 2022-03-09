package com.company;

import java.util.Random;

public class Main {
    public static String bossDefenceType;
    public static int bossDamage = 100;
    public static int bossHealth = 1500;
    public static String[] heroesAttackType = {"Treat", "Physical", "Magical", "Kinetic", "Energetic", "Luck", "Resistance", "Stun"};
    public static int[] heroesDamage = {0, 25, 20, 15, 10, 20, 30, 50};
    public static int[] heroesHealth = {290, 280, 270, 250, 400, 240, 300, 310,};
    public static int roundNumber = 0;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        roundNumber++;
        bossDefenceType = chooseDefence();
        bossHits();
        heroesHit();
        medic();
        golem();
        lucky();
        berserk();
        thor();
        printStatistics();
    }

    public static String chooseDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        System.out.println("Boss chose " + heroesAttackType[randomIndex]);
        return heroesAttackType[randomIndex];
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (bossDefenceType == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2;
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] + " x "
                            + coeff + " = " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static void medic(){
        for (int i = 0; i < heroesHealth.length; i++) {
            if (i == 0){
                continue;
            }
            if (heroesHealth[i] <= 100 && heroesHealth[i] > 0 && heroesHealth[0] > 0){
                heroesHealth[i] = heroesHealth[i] + 100;
                System.out.println("Медик излечил: " + heroesAttackType[i]);
                break;
            }
        }
    }

    public static void golem(){
        int damageBoss = bossDamage * 1/5;
        int uron = 0;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (i == 4) {
                continue;
            }
            else if (heroesHealth[i] > 0 && heroesHealth[4] > 0) {
                uron++;
                heroesHealth[i] += damageBoss;
            }

        }
        heroesHealth[4] -= damageBoss * uron;
        System.out.println("Голем поглотил урона: " + damageBoss * uron);
    }

    public static void lucky(){
        Random random = new Random();
        boolean chanse = random.nextBoolean();
        if (heroesHealth[5] > 0){
            if (chanse){
                heroesHealth[5] += 50;
            }
            System.out.println("Счастливчику повезло");
        }
    }

    public static void berserk(){
        int hitBoss = bossDamage / 2;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[6] > 0){
                heroesHealth[6] += hitBoss;
                bossHealth -= hitBoss;
                System.out.println("Берсерк поглотил половину урона Босса");
                break;
            }

        }
    }

    public static void thor(){
        Random random = new Random();
        boolean chance = random.nextBoolean();
        if (heroesHealth[7] > 0){
            if (chance){
                bossDamage = 0;
                System.out.println("Тор оглушил Босса");
            }
            else {
                bossDamage = 50;
            }
        }

    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        int totalHealth = 0;
        for (int health : heroesHealth) {
            totalHealth += health;
        }
        if (totalHealth <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void printStatistics() {
        System.out.println(roundNumber + " ROUND _________________");
        System.out.println("Boss health: " + bossHealth + " (" + bossDamage + ")");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " +
                    heroesHealth[i] + " (" + heroesDamage[i] + ")");
        }
    }
}

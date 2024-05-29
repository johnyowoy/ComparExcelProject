package Test;

import java.util.Scanner;
class CashCard {
    private String number;
    private int balance;
    private int bonus;
    CashCard(String number, int balance, int bonus) {
        this.number = number;
        this.balance = balance;
        this.bonus = bonus;
    }
    String getNumber() {
        return number;
    }
    int getBalance() {
        return balance;
    }
    int getBonus() {
        return bonus;
    }
    void store(int money) {
        if (money > 0) {
            this.balance += money;
            if (money >= 1000) {
                this.bonus++;
            }
        } else {
            System.out.println("請輸入正數");
        }
    }
    void charge(int money) {
        if (money > 0) {
            if (money <= this.balance) {
                this.balance -= money;
            }
        } else {
            System.out.println("餘額不足，請儲值");
        }
    }
    int exchange(int bonus) {
        if (bonus > 0) {
            this.bonus -= bonus;
        }
        return this.bonus;
    }
}

public class CashApp {
    public static void main(String[] args) {
        /*
        CashCard[] cards = {
                new CashCard("A001", 500, 0),
                new CashCard("A002", 300, 0),
                new CashCard("A003", 1000, 1)
        };

        var console = new Scanner(System.in);
        for (var card : cards) {
            System.out.printf("為(%s, %d, %d) 儲值", card.number, card.balance, card.bonus);
            card.store(console.nextInt());
            System.out.printf("明細(%s, %d, %d)%n", card.number, card.balance, card.bonus);
        }
        */
        var console = new Scanner(System.in);
        var card1 = new CashCard("A001", 500, 0);

        System.out.printf("明細(%s, %d, %d)%n", card1.getNumber(), card1.getBalance(), card1.getBonus());
    }
}

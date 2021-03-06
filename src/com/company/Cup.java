package com.company;

import java.util.ArrayList;
import java.util.List;

public class Cup {
    List<Die> dice = new ArrayList<>();
    public static int numOfDice;

    public Cup () {
        while (dice.size() < numOfDice) {
            dice.add(new Die());
        }
    }

    public void roll() {
        for (Die die : dice) {
            die.roll();
        }
    }

    public String displayHand() {
        String hand = "";
        for (Die die : dice) {
            hand += die.faceValue + " ";
        }
        return hand;
    }


}

package com.company;

import java.util.Random;

public class Die {
    public int numberOfSides;
    public int faceValue;

    public Die() {
        numberOfSides = 6;
    }

    public void roll() {
        Random dieValue = new Random();
        int maxSideValue = 6;
        int minSideValue = 1;
        faceValue = dieValue.nextInt(maxSideValue) + minSideValue;
    }
}

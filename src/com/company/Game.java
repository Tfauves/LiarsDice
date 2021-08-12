package com.company;
import java.util.*;

public class Game {
    public Scanner scanner = new Scanner(System.in);
    public List<Player> playerList = new ArrayList<>();
    public Map<Integer, Integer> diceOnTable = new HashMap<>();

    public final byte MAX_PLAYERS = 8;
    public final byte MIN_PLAYERS = 1;
    public boolean isLieCalled = false;
    public boolean isActiveRound = false;
    public boolean isStartingRoundPlayer = true;
    public boolean isValidBid = false;

    public int previousBidQty;
    public int previousBidFaceValue;
    public int currentBidDieQty;
    public int currentBidDieFaceValue;



    public Game() {
        System.out.println("Enter number of players: ");
        int numberOfPlayers;
        do {
            numberOfPlayers = scanner.nextByte();
            scanner.nextLine();
        } while (numberOfPlayers < MIN_PLAYERS || numberOfPlayers > MAX_PLAYERS);

        while (playerList.size() < numberOfPlayers) {
            System.out.println("Enter player name: ");
            playerList.add(new Player((scanner.nextLine()).trim()));
        }
    }

    public void play() {
        round();
    }

    public void round() {
        isStartingRoundPlayer = true;
        isActiveRound = true;
        rollAll();
        spaces();
        while (isActiveRound) {
        turn();
        }

    }

    public void rollAll() {
        for (Player activePlayer : playerList) {
            activePlayer.cup.roll();
            //System.out.println(activePlayer.cup.displayHand());
            setDiceOnTable(activePlayer.cup.dice);
        }
        //System.out.println(diceOnTable);
    }

    public void setDiceOnTable(List<Die> dice) {
        for (Die die : dice) {
            if (diceOnTable.containsKey(die.faceValue)) {
                diceOnTable.put(die.faceValue, diceOnTable.get(die.faceValue) + 1);
            } else {
                diceOnTable.put(die.faceValue, 1);
            }
        }

    }

    public void turn() {
        for (Player activePlayer : playerList) {
            if (isStartingRoundPlayer) {
                startingRoundBid(activePlayer);
                isStartingRoundPlayer = false;
                isValidBid = false;
            } else {
                bid(activePlayer);
                spaces();
               isValidBid = false;
            }
        }
    }

    public void startingRoundBid(Player activePlayer) {
        do {
            System.out.println("Player " + activePlayer.playerName + " start it off.");
            System.out.println("Your hand is " + activePlayer.cup.displayHand());
            System.out.println("Make your bid.");
            System.out.println("Enter Qty: ");
            currentBidDieQty = scanner.nextInt();
            System.out.println("Enter die face value: ");
            currentBidDieFaceValue = scanner.nextInt();
            if (currentBidDieQty == 0 || currentBidDieFaceValue == 0) {
                System.out.println("Invalid Bid! Try Again.");
            }
            scanner.nextLine();
            isValidBid = currentBidDieQty != 0 && currentBidDieFaceValue != 0;
        } while(!isValidBid);

        spaces();
    }

    public void bid(Player activePlayer) {
        previousBidQty = currentBidDieQty;
        previousBidFaceValue = currentBidDieFaceValue;
        do {
            System.out.println("Previous bid: " + previousBidQty + "x " + previousBidFaceValue);
            System.out.println("Player " + activePlayer.playerName + "'s turn.");
            System.out.println("Your hand is " + activePlayer.cup.displayHand());

            System.out.println("Type (b) to bid or (l) to call lie: ");
            String bidOrCall = scanner.nextLine();
            if (bidOrCall.equals("b")) {
                System.out.println("Make your bid.");
                System.out.println("Enter Qty: ");
                currentBidDieQty = scanner.nextInt();
                System.out.println("Enter die face value: ");
                currentBidDieFaceValue = scanner.nextInt();
                scanner.nextLine();

            } else if (bidOrCall.equals("l")) {
//            System.out.println("you called lie");
                isLieCalled = true;

            }
            if (currentBidDieFaceValue > previousBidFaceValue) {
                isValidBid = true;
                System.out.println("Valid Bid2...");
            } else if (currentBidDieFaceValue == previousBidFaceValue && currentBidDieQty > previousBidQty) {
                isValidBid = true;
                System.out.println("Valid Bid2...");
            } else {
                isValidBid = false;
                System.out.println("Invalid Bid2!!!");
            }
        } while (!isValidBid);


    }


    public void spaces() {
        int spaceCounter = 0;
        while (spaceCounter < 20) {
            System.out.println();
            spaceCounter++;
        }
    }






}

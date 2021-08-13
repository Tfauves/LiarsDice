package com.company;
import java.util.*;

public class Game {
    public Scanner scanner = new Scanner(System.in);
    public List<Player> playerList = new ArrayList<>();
    public Map<Integer, Integer> diceOnTable = new HashMap<>();

    public final byte MAX_PLAYERS = 8;
    public final byte MIN_PLAYERS = 1;

    public boolean isActiveGame = false;
    public boolean isALie = false;
    public boolean isActiveRound = false;
    public boolean isStartingRoundPlayer = true;
    public boolean isValidBid = false;
    public boolean lieCalled = false;

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
        isActiveGame = true;
        round();


    }

    // TODO: 8/11/2021 get round to reset and start with new rolls and clear map.
    public void round() {
        isActiveRound = true;
        isStartingRoundPlayer = true;
        lieCalled = false;
        rollAll();
        //spaces();
        System.out.println("-------------------------------------------");
        System.out.println("Begin Round");
        System.out.println("-------------------------------------------");
        while (isActiveRound) {
            turn();
        }
        play();
    }

    public void rollAll() {
        for (Player activePlayer : playerList) {
            activePlayer.cup.roll();
            setDiceOnTable(activePlayer.cup.dice);
        }
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
                    }
                    if (lieCalled) {
                        showHands();
                        checkLie(activePlayer);
                        remove();
                        break;
                    }
            }
            declareWinner();
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
            System.out.println("-------------------------------------------");
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

            } else if (bidOrCall.equals("l")) {
                lieCalled = true;
                isActiveRound = false;
                isValidBid = true;
            }
        } while (!isValidBid);

    }

    public void checkLie(Player activePlayer) {

        isALie = !diceOnTable.containsKey(previousBidFaceValue) || diceOnTable.get(previousBidFaceValue) < previousBidQty;
        if (isALie) {
            System.out.println("bid was a lie");
            if (playerList.indexOf(activePlayer) == 0) {
                System.out.println(playerList.get(playerList.size() - 1).playerName + " loses a die.");
                playerList.get(playerList.size() - 1).cup.dice.remove(0);


            } else {
            System.out.println(playerList.get(playerList.indexOf(activePlayer) - 1).playerName + " loses a die.");
            playerList.get(playerList.indexOf(activePlayer) - 1).cup.dice.remove(0);
            }
        } else if (!isALie) {
            System.out.println("Bid was not a lie you lose a die");
            playerList.get(playerList.indexOf(activePlayer)).cup.dice.remove(0);
        }
    }

    public void remove() {
        playerList.removeIf(player -> player.cup.dice.size() == 0);

    }

    public void declareWinner() {
        for (Player players : playerList) {
            if (playerList.size() == 1) {
                System.out.println(players.playerName + " is the winner, Game Over.");
                isActiveGame = false;
                System.exit(0);
            }
        }
    }

    public void showHands() {
        for (Player players : playerList) {
            System.out.println(players.playerName + "'s Hand " + players.cup.displayHand());
            isStartingRoundPlayer = true;
        }
            System.out.println("The dice on the table are: " + diceOnTable);
    }

    public void spaces() {
        int spaceCounter = 0;
        while (spaceCounter < 33) {
            System.out.println();
            spaceCounter++;
        }
    }

}

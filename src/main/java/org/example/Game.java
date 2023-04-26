package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game {
    private static final int FIELD_SIZE = 10;
    char[][] battleField = new char[FIELD_SIZE][FIELD_SIZE];
    Map<Ship.ShipType, Boolean> placedShips = new HashMap<>();
    private final char OCCUPY_CHAR = 'O';
    private boolean playerTookAShot = false;

    Game() {
        System.out.println("Welcome to Battleship!");
        for (int i = 0; i < FIELD_SIZE; i++) {
            Arrays.fill(battleField[i], '~');
        }
        for (Ship.ShipType type: Ship.ShipType.values()) {
            placedShips.put(type, false);
        }
    }

    /**
     * place a ship on the battlefield
     * @param ship the ship to be placed
     */
    void placeShip(Ship ship) {
        checkNeighboring(ship);
        checkCrossing(ship);
        if (ship.layout == Ship.Layout.H) {
            for(int i = ship.startColumn; i < ship.startColumn + ship.size; i++) {
                battleField[ship.startRow][i] = OCCUPY_CHAR;
            }
        } else {
            // vertical
            for(int i = ship.startRow; i < ship.startRow + ship.size; i++) {
                battleField[i][ship.startColumn] = OCCUPY_CHAR;
            }
        }
        this.placedShips.put(ship.type , true);
    }

    /**
     * check if the ship is crossing any other ship on the battlefield
     * @param ship the ship to be checked
     */
    void checkCrossing(Ship ship) throws IllegalArgumentException {
        if (ship.layout == Ship.Layout.H) {
            for(int i = ship.startColumn; i < ship.startColumn + ship.size; i++) {
                if (locationIsOccupied(ship.startRow, i)) {
                    throw new IllegalArgumentException("Error! Wrong location");
                }
            }
        } else {
            // vertical
            for(int i = ship.startRow; i < ship.startRow + ship.size; i++) {
                if (locationIsOccupied(i, ship.startColumn)) {
                    throw new IllegalArgumentException("Error! Wrong location");
                }
            }
        }
    }

    // whether the location defined by these coordinates is occupied
    private boolean locationIsOccupied(int row, int column) {
        return battleField[row][column] == OCCUPY_CHAR;
    }

    // make sure that ship is not touching any other ships
    private void checkNeighboring(Ship ship) {
        int rowStart  = Math.max( ship.startRow - 1, 0);
        int rowFinish = Math.min( ship.endRow + 1, Game.FIELD_SIZE - 1);
        int colStart  = Math.max( ship.startColumn - 1, 0);
        int colFinish = Math.min( ship.endColumn + 1, Game.FIELD_SIZE - 1);
        for ( int curRow = rowStart; curRow <= rowFinish; curRow++ ) {
            for ( int curCol = colStart; curCol <= colFinish; curCol++ ) {
                if (battleField[curRow][curCol] == OCCUPY_CHAR) {
                    throw new IllegalArgumentException("Error! Wrong location");
                }
            }
        }
    }

    // start the phase of placing the ships on the battlefield
    public void startPlacingShipsPhase() {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        for (Ship.ShipType type: Ship.ShipType.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):%n", type.name, type.size);
            while (!placedShips.get(type)) {
                try {
                    userInput = scanner.nextLine();
                    placeShip(new Ship(type, userInput));
                    printBattleField();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void startGame() {
        System.out.println("The game has started!");
        printBattleField();
        System.out.println("Take a shot!");
        Scanner scanner = new Scanner(System.in);
        String userInput;
        while (!playerTookAShot) {
            userInput = scanner.next();
            shoot(userInput);
        }
    }

    /**
     * shoot a location on the battlefield
     * @param shootUserInput coordinates of the battlefield to be shot
     */
    public void shoot(String shootUserInput) {
        String[] parsedShootUserInput = ParsingUtils.parseShootUserInput(shootUserInput);
        int shootRow;
        int shootColumn;
        try {
            shootRow = ParsingUtils.rowCharToInt(parsedShootUserInput[0].charAt(0));
            shootColumn = ParsingUtils.columnStrToInt(parsedShootUserInput[1]);
            if (!isValidRow(shootRow) || !isValidColumn(shootColumn)) {
                throw new IllegalArgumentException("Error! Invalid Row");
            }

        } catch (Exception exception) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                return;
        }

        if (locationIsOccupied(shootRow, shootColumn)) {
            setAHit(shootRow, shootColumn);
            printBattleField();
            System.out.println("You hit a ship!");
        } else {
            setAMiss(shootRow, shootColumn);
            printBattleField();
            System.out.println("You missed!");
        }
        playerTookAShot = true;
    }

    private boolean isValidRow(int row) {
        return row >= 0 && row < FIELD_SIZE;
    }

    private boolean isValidColumn(int column) {
        return column >= 0 && column < FIELD_SIZE;
    }

    private void setAHit(int row, int column) {
        battleField[row][column] = 'X';
    }

    private void setAMiss(int row, int column) {
        battleField[row][column] = 'M';
    }
    public void printBattleField() {
        System.out.print(" ");
        for (int i = 0; i < Game.FIELD_SIZE; i++) {

            System.out.printf(" %d", i + 1);
        }
        System.out.println();
        for (int i = 0; i < Game.FIELD_SIZE; i++) {
            System.out.print((char) (65 + i));
            for (int j = 0; j < Game.FIELD_SIZE; j++) {
                System.out.printf(" %s", battleField[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}

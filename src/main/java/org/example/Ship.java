package org.example;

import static org.example.ParsingUtils.columnStrToInt;
import static org.example.ParsingUtils.rowCharToInt;

public class Ship {
    int size;
    Layout layout;
    // start row of the ship location
    int startRow;
    int endRow;
    int startColumn;
    int endColumn;
    ShipType type;

    Ship(ShipType type, String shipLocationUserInput) {
        String[] parsedInput = ParsingUtils.parseShipLocationUserInput(shipLocationUserInput);
        this.layout = calcLayout(parsedInput);
        this.type = type;
        this.size = this.type.size;
        this.setPosition(parsedInput);
        validateSize();
    }

    enum Layout {
        H, V
    }

    public enum ShipType {
        Aircraft(5, "Aircraft Carrier"),
        Battleship(4, "Battleship"),
        Submarine(3, "Submarine"),
        Cruiser(3, "Cruiser"),
        Destroyer(2, "Destroyer");
        final int size;
        final String name;
        ShipType(int size, String name) {
            this.size = size;
            this.name = name;
        }
    }

    // if the layout is vertical of horizontal based on the coordinates
    private Layout calcLayout(String[] parsedInput) {
        if (parsedInput[0].equals(parsedInput[2])) {
            return Layout.H;
        } else if (parsedInput[1].equals(parsedInput[3])) {
            return Layout.V;
        } else {
            throw new IllegalArgumentException("Error! Invalid Layout");
        }
    }

    // set ship position based on user input (coordinates)
    private void setPosition(String[] userInput) {
        if (layout == Layout.H) {
            this.startRow = rowCharToInt(userInput[0].charAt(0));
            this.endRow = this.startRow;
            String startColumn;
            String endColumn;
            if (Integer.parseInt(userInput[1]) < Integer.parseInt(userInput[3])) {
                startColumn = userInput[1];
                endColumn = userInput[3];
            } else {
                startColumn = userInput[3];
                endColumn = userInput[1];
            }
            this.startColumn = columnStrToInt(startColumn);
            this.endColumn = columnStrToInt(endColumn);
        } else {
            // V
            char startRow;
            char endRow;
            if (userInput[0].charAt(0) < userInput[2].charAt(0)) {
                startRow = userInput[0].charAt(0);
                endRow = userInput[2].charAt(0);
            } else {
                startRow = userInput[2].charAt(0);
                endRow = userInput[0].charAt(0);
            }
            this.startRow = rowCharToInt(startRow);
            this.endRow = rowCharToInt(endRow);
            this.startColumn = columnStrToInt(userInput[1]);
            this.endColumn = this.startColumn;
        }
    }

    private void validateSize() {
        if (layout == Layout.H) {
            if (endColumn - startColumn != size - 1) {
                throw new IllegalArgumentException("Error! wrong size");
            }
        } else {
            // V
            if (endRow - startRow != size - 1) {
                throw new IllegalArgumentException("Error! wrong size");
            }
        }
    }

    @Override
    public String toString() {
        return "Ship{" +
                "size=" + size +
                ", layout=" + layout +
                ", startRow=" + startRow +
                ", startColumn=" + startColumn +
                ", type=" + type +
                '}';
    }
}

package org.example;

public class ParsingUtils {
    public static String[] parseShipLocationUserInput(String userInput) {
        String[] userInputArr = userInput.split(" ");
        if (userInputArr.length < 2 || userInputArr[0].length() < 2 || userInputArr[1].length() < 2) {
            throw new IllegalArgumentException("Error! invalid input");
        }
        return new String[]{
                // row is one char
                userInputArr[0].substring(0,1),
                userInputArr[0].substring(1),
                userInputArr[1].substring(0,1),
                userInputArr[1].substring(1)
        };
    }

    public static String[] parseShootUserInput(String userInput) {
        if (userInput.length() < 2 || userInput.length() > 3) {
            throw new IllegalArgumentException("Error! invalid input");
        }
        return new String[] {
                // row is one char
                userInput.substring(0,1),
                userInput.substring(1),
        };
    }

    public static int rowCharToInt(char rowChar) {
        // valid chars are A -> J
        if (rowChar >= 65 && rowChar <= 74) {
            return rowChar - 65;
        }
        throw new IllegalArgumentException("Error! Invalid Row");
    }

    public static int columnStrToInt(String columnStr) {
        // starts from 0
        return Integer.parseInt(columnStr) - 1;
    }
}

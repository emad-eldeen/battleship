# Battleship
Implementation of the famous [Battleship](https://en.wikipedia.org/wiki/Battleship_(game)) game (not the full implementation though)

## Technical Requirements
- Create a 10x10 game field
- Let the player place the ships on the field by entering the coordinates. Handle any wrong input exceptions. The ships can be placed horizontally or vertically but not diagonally across the grid spaces; the ships should not cross or touch each other
- Let the player specify a position to shoot on the field. If the shell misses the target and falls in the water, this cell should be marked with an M, and a successful strike is marked by an X

## How to use
- You have 5 ships: **Aircraft Carrier** is 5 cells, **Battleship** is 4 cells, **Submarine** is 3 cells, **Cruiser** is also 3 cells, and **Destroyer** is 2 cells. Start placing your ships with the largest one. 
- To place a ship, enter two coordinates: the beginning and the end of the ship.
- The symbol `~` denotes the fog of war, `O` denotes a cell with your ship, `X` denotes that the ship was hit, and `M` signifies a miss.
- Take a shot at a prepared game field. You need to indicate the coordinates of the target, and the program will then display a message about a hit or a miss

## Knowledge used

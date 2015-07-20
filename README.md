# colorPuzzle

Implemented a heuristic search to play the color puzzle game. The color puzzle is a type sliding
puzzle played with 14 chips of different colors on a 3x5 board. Initially, the player shakes the 14 chips together in
his/her hand and places them on a flat surface arranged in 3 rows and 5 columns.

The goal of the game is to rearrange the chips into a symmetrical arrangement by sliding them one at a time into the
empty space. Only a chip directly next to the empty space, not including diagonally, can be moved into it.

A symmetrical arrangement (a goal state) is one which is balanced around the middle row. In other words, the top row
must be identical to the bottom row, and there are no constraints on the middle row.

# How to run it
1. Have the input file in the desktop named "input.txt". The input file should not contain any empty line.
2. Then run the source file.
3. Then the output file will be in created in the desktop named "output.txt".

# Sample Input

b b r r w b r b r b b w r r e
r b b r w b b b r b r e r w r
b b r r b w r b r w b b e r r
r r b b w r r b r b e r b w b
b b r w r r b b b e r r r w b
b w b b r r b b r b w r r e r
b b r b w r b w r r b b e r r
r b b w e r r r r b b r b w b
w r b r r b w e b b r b b r r
r b e b w r b b b r r w b r r
r e r r b r r b b w b b w r b
w e b b b r r b b r b r w r r
r b w w b r r r b r b b r e b
b r r b w r b r r e b w b b r
b r b w b b r r r b b r w e r
r r r b r w b b e r b r w b b
r b w r r b e b b w r r r b b
w r b b r b r r r e b b w r b

Each letter indicates the first letter of the color. (eg: w - white)

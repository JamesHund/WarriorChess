Breakdown of what each input aims to test:
Previous samples
1  different warrior types displayed on grid correctly and correct statistics output
2  warrior movement
3  warrior loop around to start of moves list
4  warrior piece board wrapping
5 water (glider)
6 warrior health improved by water present in neighbourhood
7 warrior battle one warrior per cell
8 warrior loss from battle
9 special ability water
10 neighbourhood wrap corner
11  offense strength bound

// Please note that the following could result in program termination
12 begin with 1 warrior on board
13 output for one warrior victor
14 Exception for more than 10 warriors move on a cell

New samples
15  restorer pieces
16  neighbourhood doesn't include current piece
17  trance causing potion
18  trance healing potion
19 pick up and drop of weapons FIFO
20 magic crystal with correct configuration
21 peacemaker piece
22 invisibility potion
23 invisibility potion and then another with different iteration value
24 multiple invisibility options, use one with longest effects
25 multiple other pieces per cell (x)
26 order priority

// Please note that the following could result in program termination
27 magic crystal with incorrect configuration1
28 complex grid run till end
29 Exception more than 1 magic crystal on grid
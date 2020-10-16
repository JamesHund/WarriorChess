Here is a breakdown of what the test cases test:
1	different warrior types displayed on grid correctly and correct statistics output
2	warrior movement
3	warrior loop around to start of moves list
4	warrior overlapping
5	warrior piece board wrapping
6	water (glider)
7	warrior health improved by water present in neighbourhood
8	water warrior offense increased by water present in neighbourhood
9	warrior battle one warrior per cell
10	warrior battle multiple warriors per cell
11	warrior loss from battle
12	special ability water
13	special ability stone
14	special ability flame
15	special ability air
16	age tests
17	neighbourhood wrap corner
18	water fountain
19  offense strength bound
20  defense strength increase due to overlapping
// Note that the following test cases should trigger a System.exit() and therefore cannot be tested by TestProject.java (You should check these by hand).
21	begin with 1 warrior on board
22	begin with 0 warrior on board
23	start game with warrior 0 health
24	output for one warrior victor
25	Exception for more than 10 warriors move on a cell


Additional test cases for final handin
26	pick up of weapons during game
27	pick up and drop of weapons FIFO
28	magic crystal with correct configuration
29	warrior with highest offense gets weapon
// Note that the following test cases should trigger a System.exit() and therefore cannot be tested by TestProject.java (You should check these by hand).
30	magic crystal with incorrect configuration1
31	output for no warriors left on grid
32	complex grid run till end
33	Exception more than 1 magic crystal on grid




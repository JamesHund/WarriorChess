#!/bin/bash

cd bin;
FAILED_CASES="Failed Cases: ";
echo -e "\n******************************NEW TEST********************************\n";
for i in {1..8}
do
  OUTPUT=$(java Game_24129429 "../samples3/input0$i.txt" "1" | diff - ../samples3/outputVisualPlain/output0$i.txt --color);
  if [ ${#OUTPUT} != 0 ]
  then
  	echo -e "\nTesting test case $i\n";
	java Game_24129429 "../samples3/input0$i.txt" "1" | diff - ../samples3/outputVisualPlain/output0$i.txt --color;
	FAILED_CASES+=" "$i" ";
  fi
done
echo $FAILED_CASES

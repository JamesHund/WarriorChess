#!/bin/bash
cd bin;
FAILED_CASES="Failed Cases: ";
echo -e "\n******************************NEW TEST********************************\n";
for i in {1..9}
do
  OUTPUT=$(java Game_24129429 "../samples/samples2/input0$i.txt" "1" | diff - ../samples/samples2/outputVisual/plain/output0$i.txt --color);
  if [ ${#OUTPUT} != 0 ]
  then
  	echo -e "\nTesting test case $i\n";
	java Game_24129429 "../samples/samples2/input0$i.txt" "1" | diff - ../samples/samples2/outputVisual/plain/output0$i.txt --color;
	FAILED_CASES+=" "$i" ";
  fi
done
for i in {10..33}
do

  OUTPUT=$(java Game_24129429 "../samples/samples2/input$i.txt" "1" | diff - ../samples/samples2/outputVisual/plain/output$i.txt --color);
  if [ ${#OUTPUT} != 0 ]
  then
    echo -e "\nTesting test case $i\n";
    java Game_24129429 ../samples/samples2/input$i.txt 1 | diff - ../samples/samples2/outputVisual/plain/output$i.txt --color;
    FAILED_CASES+=" "$i" ";
  fi
done
echo -e "\n************************TESTING BONUS FEAUTURES**************************\n";
for i in {1..8}
do
  OUTPUT=$(java Game_24129429 "../samples/samples3/input0$i.txt" "1" | diff - ../samples/samples3/outputVisualPlain/output0$i.txt --color);
  if [ ${#OUTPUT} != 0 ]
  then
  	echo -e "\nTesting test case $i\n";
	java Game_24129429 "../samples/samples3/input0$i.txt" "1" | diff - ../samples/samples3/outputVisualPlain/output0$i.txt --color;
	FAILED_CASES+=" "$i" ";
  fi
done
if [ "$FAILED_CASES" == "Failed Cases: " ]
then
  echo All tests passed.
else
  echo $FAILED_CASES
fi

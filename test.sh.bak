#!/bin/bash
empty=$("");
cd bin;
for i in {1..9}
do
  OUTPUT=$(java Game_24129429 "../samples1/input0$i.txt" "1" | diff - ../samples1/outputVisualPlain/output0$i.txt --color);
  echo $OUTPUT > file.txt
  if [ $OUTPUT != $empty ]
  then
  	echo -e "\nTesting test case $i\n";
	echo $OUTPUT;
  fi
done
for i in {10..23}
do
  echo -e "\nTesting test case $i\n";
  java Game_24129429 ../samples1/input$i.txt 1 | diff - ../samples1/outputVisualPlain/output$i.txt --color;
done
echo -e "\n______________\nperforming demo tests\n______________\n";
for i in {1..9}
do
  echo -e "\nDemo test case $i\n";
  java Game_24129429 ../testsFirstDemo/input0$i.txt 1 | diff - ../testsFirstDemo/outputVisual/plain/output0$i.txt --color;
done
for i in {10..25}
do
  echo -e "\nDemo test case $i\n";
  java Game_24129429 ../testsFirstDemo/input$i.txt 1 | diff - ../testsFirstDemo/outputVisual/plain/output$i.txt --color;
done

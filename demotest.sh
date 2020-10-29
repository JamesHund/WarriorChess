#!/bin/bash
cd bin;
echo -e "\n______________\nperforming demo tests\n______________\n";
for i in {1..9}
do
  echo -e "\nDemo test case $i\n";
  java Game_24129429 ../samples/testsFirstDemo/input0$i.txt 1 | diff - ../samples/testsFirstDemo/outputVisual/plain/output0$i.txt --color;
done
for i in {10..25}
do
  echo -e "\nDemo test case $i\n";
  java Game_24129429 ../samples/testsFirstDemo/input$i.txt 1 | diff - ../samples/testsFirstDemo/outputVisual/plain/output$i.txt --color;
done

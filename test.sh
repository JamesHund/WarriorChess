#!bin/sh
cd bin;
for i in {1..9}
do
  echo -e "\nTesting test case $i\n";
  java Game_24129429 "../samples1/input0$i.txt" "1" | diff -s - ../samples1/outputVisualPlain/output0$i.txt --color;
done
for i in {10..18}
do
  echo -e "\nTesting test case $i\n";
  java Game_24129429 ../samples1/input$i.txt 1 | diff -s - ../samples1/outputVisualPlain/output$i.txt --color;
done
echo "\n______________\nperforming demo tests\n______________\n";
for i in {1..9}
do
  echo -e "\nTesting test case $i\n";
  java Game_24129429 ../testsFirstDemo/input0$i.txt 1 | diff -s - ../testsFirstDemo/outputVisual/plain/output0$i.txt --color;
done
for i in {10..25}
do
  echo -e "\nTesting test case $i\n";
  java Game_24129429 ../testsFirstDemo/input$i.txt 1 | diff -s - ../testsFirstDemo/outputVisual/plain/output$i.txt --color;
done

#!/bin/sh
echo "Compiling..."
javac ./main.java
echo "Packing..."
jar cfvm ./lottogen.jar ./META-INF/MANIFEST.MF ./main.class
echo "DONE"

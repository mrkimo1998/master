@echo off
echo Compiling...
javac .\me\mrkimo\kimotools\*.java -classpath .\me\mrkimo\kimotools\bukkit-1.11-R0.1-SNAPSHOT-shaded.jar -Xlint:deprecation
echo Packing...
jar cvf .\KimoTools.jar .\me\mrkimo\kimotools\kimotools.class
jar uvf .\KimoTools.jar .\me\mrkimo\kimotools\WarpManager.class
jar uvf .\KimoTools.jar .\me\mrkimo\kimotools\ComManager.class
jar uvf .\KimoTools.jar .\me\mrkimo\kimotools\HomeManager.class
jar uvf .\KimoTools.jar .\plugin.yml
echo Exporting to Testserver...
echo Please manually copy
echo DONE!

#!/bin/sh
echo 'STARTING'
javac -d ./bin ./src/me/mrkimo/game/*.java
cd ./bin
jar cvfm Game.jar ../manifest.txt ./me/mrkimo/game/Game.class
jar uvf Game.jar ./me/mrkimo/game/Spritesheet.class
jar uvf Game.jar ./me/mrkimo/game/Screen.class
jar uvf Game.jar ./me/mrkimo/game/InputHandler.class
jar uvf Game.jar ./me/mrkimo/game/Key.class
jar uvf Game.jar ./me/mrkimo/game/Colors.class
jar uvf Game.jar ./me/mrkimo/game/Font.class
jar uvf Game.jar ./me/mrkimo/game/Level.class
jar uvf Game.jar ./me/mrkimo/game/Tile.class
jar uvf Game.jar ./me/mrkimo/game/BasicTile.class
jar uvf Game.jar ./me/mrkimo/game/Entity.class
jar uvf Game.jar ./me/mrkimo/game/Mob.class
jar uvf Game.jar ./me/mrkimo/game/Player.class
jar uvf Game.jar ../res/spritesheet.png
mv Game.jar ../
echo 'DONE'

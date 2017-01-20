# Labrynt
This was a 2D maze-type game controlled using speech recognition that I developed for my final year Software Dissertation Project.  

This project was focused on producing a working two dimensional game compatible with devices running the android operating system 4.0 and later. It inherits the rule mechanics of checkers and Pac-man and offers intuitive gameplay.

The player will be placed in a maze that is floored with black and white tiles each imprinted with a number. The idea is to have the player move towards a tile by calling out the character it is labeled with. Movement will be restricted to two tiles ahead and players will have to take detours in order to obtain different ‘upgradables’; some of which increases or removes the tile movement restriction.   

The game is based in the java programming language and constructed in the eclipse IDE using the LibGDX framework. LibGDX is a cross-platform game development framework; meaning it allows programmers to write code once and deploy it to multiple platforms without needing to make modification to the original code (Introduction to LibGDX, 2014).   ‘Labrynt’ uses a ‘tilemap’ generator which creates unique maps from a limited amount of resources. Of course the amount of levels allowed in the first build of Labrynt will be capped at 5, as this will only be for alpha testing. The game has the usual game states; a splash screen, menu, instruction screen and a game over state. Also according to the core app quality document, the game graphics will be programmed to cater for different screen resolutions.


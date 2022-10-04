# Java-Solitaire
A solitaire game written in Java

As of the current moment we are complete part 7. I have made as many changes as I could and have noted as many in this area as possible.

The changes are as follows:

Class Name changes-
Original name -> new Name

Solitaire -> Main
GamePanel -> Background
Deck -> StockPile
Waste -> TalonPile

Measurments-

StockPile Location-
500, 20 -> 650, 15
Card Size-
76, 96 -> 84, 112
Window Size-
Original Window Size -> 750, 600

Many Methods have been renamed so please look in the necessary class to find the name.
The most notable are 
Pile.java-
isEmpty() -> noCard()
Card.java-
getFoundationBase() -> getFoundation()
getCardBack() -> getBack()

Furthermore, many variable names have been changed and some reverse rebasing has been done. Please do not change this back, it will help us in the end.

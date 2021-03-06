import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.File;
import java.lang.Comparable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/*
 * Subject: CST338 
 * Team: Cubed Expresso
 *       Chris Smith
 *       Clarence Mitchell
 *       Daniel Cadwell
 * Assignment: 6 - Timed High-Card Game
 * ClassName: Assig6.java
 * 
 *
 * Description
 * 
 * 
 */

//**********************************//
//                                  //
//   Base Class Assig6 Definition   //
//                                  //
//**********************************//

public class Assig6 
{
   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;
   static int numPacksPerDeck = 1;
   static int numJokersPerPack = 0;
   static int numUnusedCardsPerPack = 0;
   static Card[] unusedCardsPerPack = null;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
   static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];
   static Card[] player1Winnings = new Card[NUM_CARDS_PER_HAND*2];
   static Card[] player2Winnings = new Card[NUM_CARDS_PER_HAND*2];
   static int[] cardPointValues = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
   static JLabel statusText = new JLabel("");
   static CardListener listener = new CardListener();
   static ButtonListener btnListener  = new ButtonListener();;
   static CardGameFramework highCardGame;
   static CardTable table;
   static final String PLAYER1_TEXT = "Computer",
         PLAYER2_TEXT = "You";
   static final String START_TIMER = "Start Timer", 
         STOP_TIMER = "Stop Timer",
         RESET_TIMER = "Reset Timer";
   // ------------- NEW CODE ---------------------------------------
   static JLabel timerText = new JLabel("");
   static JButton timerStartBttn = new JButton(START_TIMER);
   static JButton timerStopBttn = new JButton(STOP_TIMER);
   static final int PAUSE = 1000; // milliseconds
   static Counter gameCounter = new Counter();
   static Timer gameTimer = new Timer(); 
   //static Assig6.Timer gameTimer = new Assig6.Timer(); 
   //static TimeListener timerEar = new TimeListener();
   // ------------- END NEW CODE -----------------------------------


   public static void main(String[] args)
   {
     
      //gameCounter.
      //Timer gameTimer = new Timer(gameCounter);
      table = new CardTable("High Card Game", NUM_CARDS_PER_HAND, NUM_PLAYERS);

      initGame();
      table.setVisible(true);
   } // end main()
   
   public static void initGame()
   {
      player1Winnings = new Card[NUM_CARDS_PER_HAND * 2];
      player2Winnings = new Card[NUM_CARDS_PER_HAND * 2];
      
   // ----------------- NEW CODE --------------------
     // Counter masterCounter = new Counter();
    //  Timer[] threads = new Timer[30000];
       //Timer gameTimer = new Timer(gameCounter, timerText);
      String myString;
      //gameTimer = new Timer();
      //btnListener = new ButtonListener();
      
/*      ActionListener timerEar = new ActionListener()
      {
         int i;
         
         @SuppressWarnings("deprecation")
         public void actionPerformed(ActionEvent e)
         {
           if ( e.getActionCommand() == START_TIMER)
           {
              gameTimer = new Timer(gameCounter, timerText);
             gameCounter.resetCounter();
             gameTimer.reset();
             gameTimer.start();
           } //end if
           else 
           {
              gameTimer.kill();
              try
            {
               gameTimer.join(0);
            } catch (InterruptedException e1)
            {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
              
            } // end if 

          } // end 
      }; // Action Listener
*/      //myString = String.format("%d:%d", masterCounter.mins, masterCounter.secs);
     // --------------------- END NEW CODE ------------------
      
      //
      //   Reset the entire table to default settings
      //
      table.pnlComputerHand.removeAll();
      table.pnlPlayedCards.removeAll();
      table.pnlPlayerText.removeAll();
      table.pnlStatusText.removeAll();
      table.pnlHumanHand.removeAll();
      
      //
      //   Create the framework for the High Card game
      //
      highCardGame = new CardGameFramework(numPacksPerDeck, numJokersPerPack,
         numUnusedCardsPerPack, unusedCardsPerPack, NUM_PLAYERS,
         NUM_CARDS_PER_HAND);
      
      highCardGame.deal();
      highCardGame.getHand(1).sort();
      
      for (int i = 0; i < NUM_CARDS_PER_HAND; i++)
      {
         //
         //   Get and set Icons for computer hand and add to JLabel
         //
         computerLabels[i] = new JLabel();
         computerLabels[i].setIcon(GUICard.getBackCardIcon());
         
         //
         //   Get and set Icons for player hand and add to JLabel
         //
         humanLabels[i] = new JLabel();
         humanLabels[i].setIcon(GUICard.getIcon(highCardGame.getHand(1)
            .inspectCard(i)));
         humanLabels[i].setMaximumSize(new Dimension(0, 0));
         humanLabels[i].addMouseListener(listener);
         
         //
         //   Add the computer and player hands (JLabels) to the table
         //
         table.pnlComputerHand.add(computerLabels[i]);
         table.pnlHumanHand.add(humanLabels[i]);
      } // end for loop
      
      //
      //   Create, add and format text to the playing area of the table
      //
      playLabelText[0] = new JLabel(PLAYER1_TEXT + ": 0");
      playLabelText[1] = new JLabel(PLAYER2_TEXT + ": 0");
      playLabelText[0].setHorizontalAlignment(JLabel.CENTER);
      playLabelText[0].setVerticalAlignment(JLabel.TOP);
      playLabelText[1].setHorizontalAlignment(JLabel.CENTER);
      playLabelText[1].setVerticalAlignment(JLabel.TOP);
      table.pnlPlayerText.add(playLabelText[0]);
      table.pnlPlayerText.add(playLabelText[1]);
      statusText.setHorizontalAlignment(JLabel.CENTER);
      table.pnlStatusText.add(statusText);
      statusText.removeMouseListener(listener);
      statusText.setText("");
      statusText.setBorder(null);
      
      // ----------------- NEW CODE ---------------------------
      timerText.setVerticalAlignment(JLabel.BOTTOM);
      timerText.setHorizontalAlignment(JLabel.CENTER);
      timerText.setText(gameCounter.toString());
      table.pnlTimerText.add(timerText);
      //timerStartBttn.setBackground(Color.GREEN);
      //timerStartBttn.setBorderPainted(false);
      //timerStartBttn.setOpaque(true);
      timerStartBttn.addActionListener(btnListener);
      table.pnlTimerSubBttn.add(timerStartBttn);
      //timerStopBttn.setBackground(Color.RED);
      //timerStopBttn.setBorderPainted(false);
      //timerStopBttn.setOpaque(true);
      timerStopBttn.addActionListener(btnListener);
      table.pnlTimerSubBttn.add(timerStopBttn);
      // ----------------- END NEW CODE ------------------------
      
      table.pnlHumanHand.revalidate();
      table.pnlHumanHand.repaint();
      table.pnlComputerHand.revalidate();
      table.pnlComputerHand.repaint();
      table.pnlPlayArea.revalidate();
      table.pnlPlayArea.repaint();
   } // end initGame()
   
   static int getCardPointValue(Card card)
   {
      String values = new String(Card.validCardValues);
      
      if(card.errorFlag)
         return -1;
      
      return cardPointValues[values.indexOf(card.getValue())];
   } // end getCardPointValue()
   
   static int getComputerCard(Card playerCard)
   {
      Card possibleCard = null;
      int cardPosition = 0;
      boolean hasHigherCard = false;
      
      for (int i = 0; i < highCardGame.getHand(0).getNumCards(); i++)
      {
         if (playerCard.compareTo(highCardGame.getHand(0).inspectCard(i)) < 0)
         {
            //The computer has a higher card.
            if (possibleCard != null)
            {
               //If this card is lower than the possible card, but can still
               //beat the player, then replace possible card.
               if (possibleCard.compareTo(highCardGame.getHand(0).
                  inspectCard(i)) > 0)
               {
                  possibleCard = new Card(highCardGame.getHand(0).
                     inspectCard(i));
                  cardPosition = i;
               } // end if possibleCard.compareTo
            }
            else
            {
               possibleCard = new Card(highCardGame.getHand(0).inspectCard(i));
               hasHigherCard = true;
               cardPosition = i;
            } // end if-else possibleCard
         } // end if playerCard
      } // end for loop
      
      if (!hasHigherCard)
      {
         //If the computer does not have a card that can beat the player, then
         //feed the lowest card to the player.
         for (int i = 0; i < highCardGame.getHand(0).getNumCards(); i++)
         {
            if(playerCard.compareTo(highCardGame.getHand(0).
               inspectCard(i)) >= 0)
            {
               if(possibleCard != null)
               {
                  if(possibleCard.compareTo(highCardGame.getHand(0).
                     inspectCard(i)) > 0)
                  {
                     possibleCard = new Card(highCardGame.getHand(0).
                        inspectCard(i));
                     cardPosition = i;
                  } // end if possibleCard.compareTo
               }
               else
               {
                  possibleCard = highCardGame.getHand(0).inspectCard(i);
                  cardPosition = i;
               } // end if-else possibleCard
            }
         } // end for loop
      } // end if !hasHigherCard

      return cardPosition;
   } // end getComputerCard()
   
   static int calculateScore(Card[] winnings)
   {
      int score = 0;
      
      for (Card card : winnings)
         if(card != null)
            score++;
         else
            break;
      
      return score;
   } // end calculateScore()
   
   static void removeLabel(JLabel[] labels, JLabel label)
   {
      boolean moveBack = false;
      
      for (int i = 0; i < labels.length; i++)
      {
         if (labels[i] == label)
         {
            labels[i] = null;
            moveBack = true;
         }
         else if (moveBack)
         {
            labels[i - 1] = labels[i];
            labels[i] = null;
         } // end if-else
      } // end for loop
   } // end removeLabel()
   
   static void addToWinnings(Card[] winnings, Card... cards)
   {
      for (int i = 0; i < cards.length; i++)
         for(int j = 0; j < winnings.length; j++)
            if(winnings[j] == null)
            {
               winnings[j] = new Card(cards[i]);
               break;
            } // end if
   } // end addToWinnings()
   
   static void playCard(int handPosition)
   {
      //Clear out the previous play
      table.pnlPlayedCards.removeAll();

      Card playerCard = highCardGame.getHand(1).inspectCard(handPosition);
      int computerHandPosition = getComputerCard(playerCard);
      Card computerCard = highCardGame.getHand(0).
         inspectCard(computerHandPosition);
      
      JLabel computerCardLabel = new JLabel();
      computerCardLabel.setIcon(GUICard.getIcon(computerCard));
      computerCardLabel.setHorizontalAlignment(JLabel.CENTER);
      computerCardLabel.setVerticalAlignment(JLabel.BOTTOM);

      table.pnlHumanHand.remove(humanLabels[handPosition]);
      table.pnlComputerHand.remove(computerLabels[computerHandPosition]);
      highCardGame.getHand(0).playCard(computerHandPosition);
      highCardGame.getHand(1).playCard(handPosition);

      computerLabels[0].setHorizontalAlignment(JLabel.CENTER);
      humanLabels[handPosition].setHorizontalAlignment(JLabel.CENTER);
      humanLabels[handPosition].setVerticalAlignment((JLabel.BOTTOM));

      table.pnlPlayedCards.add(computerCardLabel);
      table.pnlPlayedCards.add(humanLabels[handPosition]);
      humanLabels[handPosition].removeMouseListener(listener);
      humanLabels[handPosition].setBorder(null);
      
      removeLabel(humanLabels, humanLabels[handPosition]);
      removeLabel(computerLabels, computerLabels[computerHandPosition]);

      if (playerCard.compareTo(computerCard) < 0)
      {
         addToWinnings(player1Winnings, computerCard, playerCard);
         statusText.setText("Computer wins...");
      }
      else if (playerCard.compareTo(computerCard) > 0)
      {
         addToWinnings(player2Winnings, computerCard, playerCard);
         statusText.setText("You win!");
      }
      else
         statusText.setText("Draw! The cards have been discarded.");
      
      playLabelText[0].setText(PLAYER1_TEXT + ": "
         + calculateScore(player1Winnings));
      playLabelText[1].setText(PLAYER2_TEXT + ":"
         + calculateScore(player2Winnings));

      if (highCardGame.getHand(0).getNumCards() == 0)
      {
         //The game is over.
         if (calculateScore(player1Winnings) > calculateScore(player2Winnings))
         {
            statusText.setText("Computer wins the game...");
         }
         else if (calculateScore(player1Winnings) <
            calculateScore(player2Winnings))
         {
            statusText.setText("You win the game!");
         }
         else
         {
            statusText.setText("The game ended in a draw.");
         }
         
         statusText.setText(statusText.getText()
            + " Click here to play again!");
         statusText.addMouseListener(listener);
      } // end if highCardGame

      table.pnlHumanHand.revalidate();
      table.pnlHumanHand.repaint();
      table.pnlComputerHand.revalidate();
      table.pnlComputerHand.repaint();
      table.pnlPlayArea.revalidate();
      table.pnlPlayArea.repaint();
   } // end playCard()
   
   
 //****************************//
// //
//Timer Class Definition   //
//...extends Thread     //
// //
//****************************//
   public static class Timer extends Thread
   {
         // private Counter counterObject;
      //private JLabel updateLabel;
      private boolean doProcess = true;
      public static final int PAUSE = 1000; //  milliseconds
      
      
      public Timer()
      {
      doProcess = true;
      //counterObject = ctr;
      //updateLabel = lbl;
      }
      
      public void run()
      {
         //countObject.increment();
         while (doProcess)
         {
            
            gameCounter.increment();
            System.out.println(gameCounter.toString());

            timerText.setText( gameCounter.toString());
            this.repaintTimer();
            doNothing(PAUSE);
         }
         System.out.println(gameCounter.toString());
      
      }
      
      public void repaintTimer()
      {
         timerText.repaint();
         table.pnlTimerText.repaint();

      }
      
      public void reset()
      {
         doProcess = true;
      }
      
      public void kill()
      {
         doProcess = false;
         this.repaintTimer();
      }
      
      public  void doNothing(int milliseconds)
      {
         try
         {
            Thread.sleep(milliseconds);
         }
         catch(InterruptedException e)
         {
            System.out.println("Unexpected interrupt");
            System.exit(0);
         }
      }
   }

   public static class Counter
   {
      public static int secs;
      public static int mins;
      public static int hrs;
      
      public Counter()
      {
         resetCounter();
      
      }
  
      public static int getHrs()
      {
         return hrs;
      }

      
      public int getSecs()
      {
         return secs;
      }
      
      public int getMins()
      {
         return mins;
      }
      
      public void resetCounter()
      {
         secs = 0;
         mins = 0;
         hrs = 0;
      }
      public void increment()
      {

         if (++secs > 59)
         {
            ++mins;
            secs = 0;
         }
         
         if (mins > 59)
         {
            ++hrs;
            mins = 0;
         }
      
      }
   
      public String toString()
      {
      String rtnHrs = String.format("%02d", hrs);
      String rtnMins = String.format("%02d", mins);
      String rtnSecs = String.format("%02d", secs);
      
      return (rtnHrs + ":" + rtnMins + ":" + rtnSecs);
      }
   }

} // end base class Assig6

//***********************************//
//                                 //
// ButtonListener Class Definition  //
//                                 //
//***********************************//
class ButtonListener implements ActionListener
{
   //Counter btnCounter;
   //Timer btnTimer;
   
   //public ButtonListener(Counter gameCounter, Timer gameTimer)
  // {
  //    btnCounter =  gameCounter;
  //    btnTimer = gameTimer;
  // }
   
   public void actionPerformed(ActionEvent e)
   {
      JButton source = (JButton)e.getSource();
      System.out.println(source.getActionCommand());

      if ( source.getActionCommand() == Assig6.START_TIMER)
      {

        Assig6.gameCounter.resetCounter();
        
        Assig6.gameTimer = new Assig6.Timer();
        Assig6.gameTimer.start();
        source.setText( Assig6.RESET_TIMER);

      }
      else
      {
         Assig6.gameTimer.kill();
         Assig6.gameCounter.resetCounter(); 
         
         try
         {
            Assig6.gameTimer.join();
         } catch (InterruptedException e1)
         {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         } finally
         {
            
            if ( source.getActionCommand() == Assig6.RESET_TIMER)
            {               
               Assig6.gameTimer =  new Assig6.Timer();
               Assig6.gameTimer.start();
               source.setText( Assig6.RESET_TIMER);

            }
            else
            {
               Assig6.timerStartBttn.setText(Assig6.START_TIMER);
               Assig6.gameTimer.repaintTimer();
 
            }

         }
         
        
      }
         


   }
}



//***********************************//
//                                   //
//   CardListener Class Definition   //
//                                   //
//***********************************//
class CardListener implements MouseListener
{
   public void mouseEntered(MouseEvent e)
   {
      JLabel source = (JLabel)e.getSource();
      LineBorder border = new LineBorder(new Color(0, 0, 255), 2);
      source.setBorder(border);
   } // end mouseEntered()
   
   public void mouseExited(MouseEvent e)
   {
      JLabel source = (JLabel)e.getSource();
      source.setBorder(null);
   } // end mouseExited()
   
   public void mouseClicked(MouseEvent e)
   {
      JLabel source = (JLabel)e.getSource();
      
      for (int i = 0; i < Assig6.humanLabels.length; i++)
         if (Assig6.humanLabels[i] == source)
         {
            Assig6.playCard(i);
            break;
         }
      
      if (Assig6.statusText == source)
      {
         Assig6.initGame();
      }
   } // end mouseClicked()
   
   public void mouseReleased(MouseEvent e)
   {

   } // end mouseReleased()
   
   public void mousePressed(MouseEvent e)
   {

   } // end mousePressed()
} // end class CardListener


//****************************************//
//                                        //
//   CardGameFramework Class Definition   //
//                                        //
//****************************************//
class CardGameFramework
{
   private static final int MAX_PLAYERS = 50;

   private int numPlayers;
   private int numPacks;              // # standard 52-card packs per deck
                                      // ignoring jokers or unused cards
   private int numJokersPerPack;      // if 2 per pack & 3 packs per deck, get 6
   private int numUnusedCardsPerPack; // # cards removed from each pack
   private int numCardsPerHand;       // # cards to deal each player
   private Deck deck;                 // holds the initial full deck and gets
                                      // smaller (usually) during play
   private Hand[] hand;               // one Hand for each player
   private Card[] unusedCardsPerPack; // an array holding the cards not used
                                      // in the game.  e.g. pinnacle does not
                                      // use cards 2-8 of any suit

   public CardGameFramework( int numPacks, int numJokersPerPack,
      int numUnusedCardsPerPack,  Card[] unusedCardsPerPack,
      int numPlayers, int numCardsPerHand)
   {
      int k;

      // filter bad values
      if (numPacks < 1 || numPacks > 6)
         numPacks = 1;
      if (numJokersPerPack < 0 || numJokersPerPack > 4)
         numJokersPerPack = 0;
      if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) //  > 1 card
         numUnusedCardsPerPack = 0;
      if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
         numPlayers = 4;
      // one of many ways to assure at least one full deal to all players
      if (numCardsPerHand < 1 ||
         numCardsPerHand >  numPacks * (52 - numUnusedCardsPerPack)
         / numPlayers )
         numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;

      // allocate
      this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
      this.hand = new Hand[numPlayers];
      for (k = 0; k < numPlayers; k++)
         this.hand[k] = new Hand();
      deck = new Deck(numPacks);

      // assign to members
      this.numPacks = numPacks;
      this.numJokersPerPack = numJokersPerPack;
      this.numUnusedCardsPerPack = numUnusedCardsPerPack;
      this.numPlayers = numPlayers;
      this.numCardsPerHand = numCardsPerHand;
      for (k = 0; k < numUnusedCardsPerPack; k++)
         this.unusedCardsPerPack[k] = unusedCardsPerPack[k];

      // prepare deck and shuffle
      newGame();
   } // end overloaded CardGameFramework()

   // constructor default for game like bridge
   public CardGameFramework()
   {
      this(1, 0, 0, null, 4, 13);
   } // end CardGameFramework()

   public Hand getHand(int k)
   {
      // hands start from 0 like arrays

      // on error return automatic empty hand
      if (k < 0 || k >= numPlayers)
         return new Hand();

      return hand[k];
   } // end getHand()

   public Card getCardFromDeck()
   {
      return deck.dealCard();
   } // end getCardFromDeck()

   public int getNumCardsRemainingInDeck()
   {
      return deck.getNumCards();
   } // end getNumCardsRemainingInDeck()

   public void newGame()
   {
      int k, j;

      // clear the hands
      for (k = 0; k < numPlayers; k++)
         hand[k].resetHand();

      // restock the deck
      deck.init(numPacks);

      // remove unused cards
      for (k = 0; k < numUnusedCardsPerPack; k++)
         deck.removeCard(unusedCardsPerPack[k]);

      // add jokers
      for (k = 0; k < numPacks; k++)
         for ( j = 0; j < numJokersPerPack; j++)
            deck.addCard(new Card('X', Card.Suit.values()[j]));

      // shuffle the cards
      deck.shuffle();
   } // end newGame()

   public boolean deal()
   {
      // returns false if not enough cards, but deals what it can
      int k, j;
      boolean enoughCards;

      // clear all hands
      for (j = 0; j < numPlayers; j++)
         hand[j].resetHand();

      enoughCards = true;
      
      for (k = 0; k < numCardsPerHand && enoughCards ; k++)
      {
         for (j = 0; j < numPlayers; j++)
            if (deck.getNumCards() > 0)
               hand[j].takeCard(deck.dealCard());
            else
            {
               enoughCards = false;
               break;
            } // end if-else
      } // end for loop 

      return enoughCards;
   } // end deal()

   void sortHands()
   {
      int k;

      for (k = 0; k < numPlayers; k++)
         hand[k].sort();
   } // end sortHands()
} // end class CardGameFramework


//******************************//
//                              //
//   GUICard Class Definition   //
//                              //
//******************************//
class GUICard
{
   private static Icon[][] iconCards = new ImageIcon[14][4];
   private static Icon iconBack;
   static boolean iconsLoaded = false;
   static final char[] VALID_SUITS = {'C', 'D', 'H', 'S'};
   private static String iconFolderPath = "./images";
   
   public static Icon getIcon(Card card)
   {
      if(!GUICard.iconsLoaded)
         GUICard.loadCardIcons();
      
      return iconCards[valueAsInt(card)][suitAsInt(card)];
   } // end getIcon()
   
   private static void loadCardIcons()
   {
      if(!(new File(GUICard.iconFolderPath).exists()))
      {
         JOptionPane.showMessageDialog(null, "By deafult ../images/ is used to"
            + " store card icon images, but ../images/ does not exist. Press OK"
            + " to select the folder where card icon images are stored. Press"
            + " cancel in the forthcoming dialog window to exit this program.");
         JFileChooser chooser = new JFileChooser(".");
         chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
         chooser.setMultiSelectionEnabled(false);
         chooser.showDialog(null, "Select");
         File selectedFile = chooser.getSelectedFile();
         if(selectedFile == null)
            System.exit(0);
         GUICard.iconFolderPath = selectedFile.getPath();
         System.out.println(iconFolderPath);
      } // end if
      for (int i = 0; i < Card.validCardValues.length; i++)
         for (int j = 0; j < VALID_SUITS.length; j++)
         {
            if (!new File(iconFolderPath + "/" + Card.validCardValues[i]
               + VALID_SUITS[j] + ".gif").exists())
            {
               JOptionPane.showMessageDialog(null, Card.validCardValues[i]
                  + VALID_SUITS[j] + ".gif could not be found in the icon"
                  + " folder. Program execution will now stop.");
               System.exit(0);
            } // end if
            
            iconCards[i][j] = new ImageIcon(iconFolderPath + "/"
               + Card.validCardValues[i] + VALID_SUITS[j] + ".gif");
         } // end for loop j
      
      iconBack = new ImageIcon(iconFolderPath + "/BK.gif");
      GUICard.iconsLoaded = true;
   } // end loadCardIcons()
   
   private static int valueAsInt(Card card)
   {
      String values = new String(Card.validCardValues);
      
      return values.indexOf(card.getValue());
   } // end valueAsInt()
   
   private static int suitAsInt(Card card)
   {
      return card.getSuit().ordinal();
   } // end suitAsInt()
   
   public static Icon getBackCardIcon()
   {
      if(!GUICard.iconsLoaded)
         GUICard.loadCardIcons();
      
      return GUICard.iconBack;
   } // end getBackCardIcon()
} // end class GUICard


//********************************//
//                                //
//   CardTable Class Definition   //
//                                //
//********************************//
class CardTable extends JFrame
{
   static final int MAX_CARDS_PER_HAND = 56;
   static final int MAX_PLAYERS = 2; // for now, we only allow 2 person games
   private int numCardsPerHand;
   private int numPlayers;

   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea, pnlPlayedCards,
      pnlPlayerText, pnlStatusText, pnlTimer, pnlTimerText, pnlTimerButton,
      pnlTimerSubBttn;
                                  // -------- NEW CODE -------------------
   //
   //   Default constructor
   //
   public CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      super();

      if(numCardsPerHand < 0 || numCardsPerHand > CardTable.MAX_CARDS_PER_HAND)
         this.numCardsPerHand = 20;

      this.numCardsPerHand = numCardsPerHand;
      if(numPlayers < 2 || numPlayers > CardTable.MAX_PLAYERS)
         this.numPlayers = numPlayers;
      if(title == null)
         title = "";
      
      //
      //   Establish main frame in which program will run
      //
      this.setTitle(title);
      this.setSize(800, 600);
      this.setMinimumSize(new Dimension(800, 600));
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      BorderLayout layout = new BorderLayout();
      this.setLayout(layout);

      //
      //   Creates and formats JPanel for computer's hand
      //
      FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
      TitledBorder border = new TitledBorder("Computer Hand");
      pnlComputerHand = new JPanel();
      pnlComputerHand.setLayout(flowLayout);
      pnlComputerHand.setPreferredSize(new Dimension((int)this.getMinimumSize().
         getWidth()-50, 105));
      JScrollPane scrollComputerHand = new JScrollPane(pnlComputerHand);
      scrollComputerHand.setVerticalScrollBarPolicy(JScrollPane.
         VERTICAL_SCROLLBAR_NEVER);
      scrollComputerHand.setBorder(border);
      this.add(scrollComputerHand, BorderLayout.NORTH);

      //
      //   Creates and formats JPanel for playing area
      //
      border = new TitledBorder("Playing Area");
      GridLayout gridLayoutCardsArea = new GridLayout(1, 2);
      GridLayout gridLayoutStatusArea = new GridLayout(1, 1);
      pnlPlayArea = new JPanel();
      pnlPlayArea.setBorder(border);
      layout = new BorderLayout();
      pnlPlayArea.setLayout(layout);
      pnlPlayedCards = new JPanel();
      pnlPlayedCards.setLayout(gridLayoutCardsArea);
      pnlPlayerText = new JPanel();
      pnlPlayerText.setLayout(gridLayoutCardsArea);
      pnlStatusText = new JPanel();
      pnlStatusText.setLayout(gridLayoutStatusArea);
      pnlPlayedCards.setPreferredSize(new Dimension((int)this.getMinimumSize().
         getWidth()-50, 150));
      pnlPlayerText.setPreferredSize(new Dimension(100, 30));
      pnlStatusText.setPreferredSize(new Dimension(100, 30));
      pnlPlayArea.add(pnlPlayedCards, BorderLayout.NORTH);
      pnlPlayArea.add(pnlPlayerText, BorderLayout.CENTER);
      pnlPlayArea.add(pnlStatusText, BorderLayout.SOUTH);
      this.add(pnlPlayArea, BorderLayout.CENTER);
      
      // ------------------- NEW CODE -------------------------------
      //
      //   Creates and formats JPanel for timer
      //
      border = new TitledBorder("Game Timer");
      GridLayout gridLayoutTimer = new GridLayout(1, 2);
      GridLayout subGridLayoutTimer = new GridLayout(2, 1);
      pnlTimer = new JPanel();
      pnlTimer.setBorder(border);
      layout = new BorderLayout();
      pnlTimer.setLayout(layout);
      pnlTimerText = new JPanel();
      pnlTimerText.setLayout(gridLayoutTimer);
      pnlTimerButton = new JPanel();
      pnlTimerButton.setLayout(gridLayoutTimer);
      pnlTimerSubBttn = new JPanel();
      pnlTimerSubBttn.setLayout(subGridLayoutTimer);
      pnlTimerText.setPreferredSize(new Dimension(150, 150));
      pnlTimerButton.setPreferredSize(new Dimension(150, 50));
      pnlTimerSubBttn.setPreferredSize(new Dimension(150, 50));
      pnlTimer.add(pnlTimerText, BorderLayout.NORTH);
      pnlTimer.add(pnlTimerButton, BorderLayout.SOUTH);
      pnlTimerButton.add(pnlTimerSubBttn);
      this.add(pnlTimer, BorderLayout.EAST);
      // -------------------- END NEW CODE -------------------------
      
      //
      //   Creates and formats JPanel for human's hand
      //
      border = new TitledBorder("Human Hand");
      pnlHumanHand = new JPanel();
      pnlHumanHand.setLayout(flowLayout);
      pnlHumanHand.setPreferredSize(new Dimension((int)this.getMinimumSize().
         getWidth()-50, 105));
      JScrollPane scrollHumanHand = new JScrollPane(pnlHumanHand);
      scrollHumanHand.setVerticalScrollBarPolicy(JScrollPane.
         VERTICAL_SCROLLBAR_NEVER);
      scrollHumanHand.setBorder(border);
      this.add(scrollHumanHand, BorderLayout.SOUTH);
   } // end CardTable()
} // end class CardTable


//***************************//
//                           //
//   Card Class Definition   //
//                           //
//***************************//
class Card implements Comparable
{
   //The four standard suits are supported.
   public enum Suit{clubs, diamonds, hearts, spades};
   private char value;
   private Suit suit;
   
   //errorFlag is set to true if the user tries to create or set a card's value
   //to one that is not in the validCardValues array. This will cause the card's
   //toString() method to indicate that the card is invalid.
   boolean errorFlag;
   
   //validCardValues holds values that a card is allowed to be.
   public static char[] validCardValues = {'A', '2', '3', '4', '5', '6', '7',
      '8', '9', 'T', 'J', 'Q', 'K', 'X'};
   public static char[] validCardSuits = {'C', 'D', 'H', 'S'};
   public static char[] valueRanks = validCardValues;
   
   public int compareTo(Object t)
   {
      Card c = (Card)t;
      String strRanks = new String(valueRanks);
      
      if (t.getClass() != this.getClass())
         return 1;
      if (strRanks.indexOf(c.getValue()) < 0)
         return 1;
      if (strRanks.indexOf(c.getValue()) < strRanks.indexOf(this.getValue()))
         return 1;
      if (strRanks.indexOf(c.getValue()) == strRanks.indexOf(this.getValue()))
         return 0;
      if (strRanks.indexOf(c.getValue()) > strRanks.indexOf(this.getValue()))
         return -1;
      
      return 1;
   } // end compareTo()
   
   static void arraySort(Card[] cards, int arraySize)
   {
      boolean swapped = false;
      
      do
      {
         swapped = false;
         
         for(int i = 1; i < arraySize; i++)
         {
            if(cards[i-1].compareTo(cards[i]) > 0)
            {
               Card tmpCard = new Card(cards[i-1]);
               cards[i-1] = new Card(cards[i]);
               cards[i] = new Card(tmpCard);
               swapped = true;
            } // end if
         } // end for loop
      } // end do
      while (swapped);
   } // end arraySort()

   /* Card(char, Suit)
      In: A char representing the card's value, and a Suit representing the 
          card's suit.
      Out: Nothing
      Description: This is a constructor that takes a value and a suit for a
                   card. This will create a card of the specified value & suit.
   */
   public Card(char value, Suit suit)
   {
      this.set(value, suit);
   } // end overloaded Card(char, Suit)
   
   /* Card()
      In: Nothing
      Out: Nothing
      Description: This is a default constructor that takes no values. It will
                   create an Ace of Spades.
   */
   public Card()
   {
      this.set('A', Suit.spades);
   } // end Card()
   
   /* Card(Card)
      In: A Card object
      Out: Nothing
      Description: This is a copy constructor that returns a NEW card with the
                   same values as the card passed into it.
   */
   public Card(Card card)
   {
      this.set(card.value, card.suit);
   } // end overloaded Card(Card)
   
   /* boolean set(char, Suit)
      In: A char representing the card's value and a Suit representing the
          card's suit.
      Out: True if the value and suit are valid, false if otherwise.
      Description: This set's the card's suit and value, if they are valid.
                   Otherwise, it sets the card's errorFlag to true.
   */
   public boolean set(char value, Suit suit)
   {
      if (Card.isValid(value, suit))
      {
         this.errorFlag = false;
         this.value = value;
         this.suit = suit;
         return true;
      }
      else
      {
         this.errorFlag = true;
         return false;
      } // end if-else
   } // end set()
   
   /* boolean isValid(char, Suit)
      In: A char representing the card's value and a Suit representing its suit.
      Out: True if the value is valid, false if otherwise.
      Description: This function determines whether the value passed to it is a
                   valid value for a card. It checks the value against the valid
                   values stored in Card.validCardValues.
   */
   private static boolean isValid(char value, Suit suit)
   {
      for (char validValue : Card.validCardValues)
         if (String.valueOf(validValue).toLowerCase().equals(String.
            valueOf(value).toLowerCase()))
            return true;
      
      return false;
   } // end isValid()
   
   /* char getValue()
      In: Nothing
      Out: A char holding the card's value.
      Description: This is an accessor for the card's value.
   */
   public char getValue()
   {
      return value;
   } // end getValue()
   
   /* Suit getSuit()
      In: Nothing
      Out: The card's suit type.
      Description: This is an accessor for the card's suit.
   */
   public Suit getSuit()
   {
      return this.suit;
   } // end getSuit()
   
   /* String toString()
      In: Nothing
      Out: A String object containing the value and suit of the card, or
           [INVALID CARD] if the errorFlag is set to true.
      Description: This returns the card's value to the caller in String form.
   */
   public String toString()
   {
      if (this.errorFlag == true)
         return "[INVALID CARD]";
      else
         return this.value + " of " + suit.toString();
   } // end toString()
   
   public boolean equals(Card c)
   {
      if (this.getValue() == c.getValue() && this.getSuit() == c.getSuit())
         return true;
      return false;
   } // end equals()
} // end class Card


//***************************//
//                           //
//   Hand Class Definition   //
//                           //
//***************************//
class Hand
{
   public static final int MAX_CARDS = 50;
   private Card[] myCards = new Card[MAX_CARDS];
   int numCards = 0;
   
   /* Hand()
      In: Nothing
      Out: Nothing
      Description: The default constructor for Hand does not actually do
                   anything.
   */
   public Hand()
   {
   
   } // end Hand()

   void sort()
   {
      Card.arraySort(this.myCards, numCards);
   } // end sort()

   /* void resetHand()
      In: Nothing
      Out: Nothing
      Description: This sets the hand to its default state, containing no cards.
   */
   public void resetHand()
   {
      this.myCards = new Card[MAX_CARDS];
      this.numCards = 0;
   } // end resetHand()
   
   /* boolean takeCard(Card)
      In: A Card object
      Out: True if there is room in the hand for the card, false if otherwise
      Description: This takes a Card object and places a copy of that object
                   into the hand.
   */
   public boolean takeCard(Card card)
   {
      if (this.numCards >= MAX_CARDS)
         return false;
      else
      {
         this.myCards[numCards] = new Card(card);
         this.numCards++;
         
         return true;
      }
   } // end takeCard()
   
   /* Card playCard()
      In: Nothing
      Out: A Card object with the same values as the card on the top of the hand
      Description: This creates a copy of the first card in the hand and returns
                   it to the caller.
   */
   public Card playCard()
   {
      Card card = this.myCards[this.numCards-1];
      this.myCards[this.numCards-1] = null;
      this.numCards--;
      
      return card;
   } // end playCard()
   
   /* String toString()
      In: Nothing
      Out: A String object containing the cards in the hand.
      Description: This will provide a textual representation of the data
                   contained in hand to the caller.
   */
   public String toString()
   {
      String handString = "( ";
      for (int i = 0; i <  this.numCards; i++)
      {
         handString += this.myCards[i].toString();
         
         if (i != this.numCards - 1)
            handString += ", ";
      } //  end for loop
      handString += " )";
      
      return handString;
   } // end toString()
   
   /* int getNumCards()
      In: Nothing
      Out: An integer whose value is the number of cards in the hand.
      Description: This is a basic accessor function.
   */
   public int getNumCards()
   {
      return this.numCards;
   } // end getNumCards()
   
   /* Card inspectCard(int)
      In: An integer representing the position of the card to be inspected.
      Out: A copy of the card at the specified position, or an invalid card if
           there is no card in that position.
      Description: This function returns a Card object whose values are equal to
                   the card in the specified position.
   */
   public Card inspectCard(int k)
   {
      if (k >= this.numCards || k < 0)
         return new Card('0', Card.Suit.spades);
      else
         return new Card(this.myCards[k]);
   } // end inspectCard()

   public Card playCard(int k)
   {
      if (k >= this.numCards || k < 0)
         return new Card('0', Card.Suit.spades);
      else
      {
         Card card = new Card(this.myCards[k]);
         
         for(int i = k+1; i < this.numCards; i++)
         {
            this.myCards[i - 1] = this.myCards[i];
            this.myCards[i] = null;
         } // end for loop
         this.numCards--;
         
         return card;
      } // end if-else
   } // end playCard()
} // end class Hand


//***************************//
//                           //
//   Deck Class Definition   //
//                           //
//***************************//
class Deck
{
   public static final short MAX_CARDS_IN_PACK = 56;
   public static final short MAX_PACKS = 6;
   public static final short MAX_CARDS = MAX_PACKS * MAX_CARDS_IN_PACK;
   
   //The masterPack is a pack of cards that the cards in the deck are built off
   //of. It contains one card for each value/suit combination. This is static,
   //as it does not change per object instantiated.
   private static Card[] masterPack = new Card[MAX_CARDS_IN_PACK];
   private Card[] cards; //The cards in the object's deck. Not static, as each
                         //deck object can have different cards.
   private int topCard;  //The position of the card on the top of the deck.
   private int numPacks; //The deck can consist of multiple packs of cards.

   /* Deck(int)
      In: An integer specifying the number of packs to build the deck from.
      Out: Nothing
      Description: This is a constructor that will build a deck composed of the
                   specified number of packs.
   */
   public Deck(int numPacks)
   {
      //Build the master pack.
      this.allocateMasterPack();
      //If the user wants more packs than are available, give them the max.
      if (numPacks > Deck.MAX_PACKS)
         this.init(Deck.MAX_PACKS);
      //If the user wants 0 or less packs, give them one.
      else if (numPacks < 1)
         this.init(1);
      else
         //Otherwise, build the deck with the specified number of packs.
         this.init(numPacks);
   } // end overloaded Deck()
   
   /* Deck()
      In: None
      Out: Nothing
      Description: This default constructor builds a deck with one pack.
   */
   public Deck()
   {
      this.allocateMasterPack();
      this.init(1);
   } // end Deck()
   
   /* void init(int)
      In: An integer whose value is the number of packs to build the deck from.
      Out: Nothing
      Description: This will initialize the cards array data member to a
                   complete deck built from the specified number of packs.
   */
   public void init(int numPacks)
   {
      //Initialize the cards array.
      this.cards = new Card[numPacks * Deck.MAX_CARDS_IN_PACK];
      //Until the total number of cards are reached, keep adding cards from the
      //master pack.
      for (int i = 0; i < numPacks * Deck.MAX_CARDS_IN_PACK; i++)
      {
         this.cards[i] = this.masterPack[i % Deck.MAX_CARDS_IN_PACK];
      }
      //Set the top card to the last card allocated.
      this.topCard = numPacks * Deck.MAX_CARDS_IN_PACK;
   } // end init()
   
   /* void shuffle()
      In: Nothing
      Out: Nothing
      Description: This uses a Fisher-Yates shuffle to shuffle all of the cards
                   in the deck.
   */
   public void shuffle()
   {
      //Beginning with the top card, decrement i until i is 0.
      for (int i = this.topCard - 1; i >= 0; i--)
      {
         Card tmpCard = this.cards[i]; //Store the card at i, since it will be
                                       //overwritten.
         //Choose a random card position from within the deck.
         int randomPosition = (int) (Math.random() * (this.topCard - 1));
         //Take the card from the random position and store it in the ith
         //position.
         this.cards[i] = this.cards[randomPosition];
         //Take the card from the ith position, and put it into the randomly
         //chosen position.
         this.cards[randomPosition] = tmpCard;
         
         //The cards have now been swapped.
      } // end for loop
   } // end shuffle()
   
   /* Card dealCard()
      In: Nothing
      Out: A copy of the Card object on the top of the deck.
      Description: This function makes a copy of the card on the top of the
                   deck, removes that card from the deck, and returns the copy
                   to the caller.
   */
   public Card dealCard()
   {
      //Return an invalid card if there are no cards in the deck.
      if (this.topCard < 0)
         return new Card('0', Card.Suit.spades);
      else
      {
         //Create a copy of the card on the top of the deck.
         Card card = new Card(this.cards[this.topCard - 1]);
         //Set the actual card on the top of the deck to null, to destroy it.
         this.cards[this.topCard - 1] = null;
         //The topCard is now one less than it was.
         this.topCard--;
         //return the copy.
         return card;
      } // end if-else
   } // end dealCard()
   
   /* int getTopCard()
      In: Nothing
      Out: An integer whose value is the position of the top card in the deck.
      Description: This is a basic accessor function.
   */
   public int getTopCard()
   {
      return this.topCard;
   } // end getTopCard()
   
   /* Card inspectCard(int)
      In: An integer representing the position of the card to be inspected.
      Out: A copy of the card at the specified position, or an invalid card if
           there is no card in that position.
      Description: This function returns a Card object whose values are equal to
                   the card in the specified position.
   */
   public Card inspectCard(int k)
   {
      //If k is invalid, return an invalid card.
      if (k >= this.topCard || k < 0)
         return new Card('0', Card.Suit.spades);
      else
         //Otherwise, return a copy of the card in position k.
         return new Card(this.cards[k]);
   } // end inspectCard()
   
   /* void allocateMasterPack()
      In: Nothing
      Out: Nothing
      Description: This function fills the masterPack if it is not already
                   filled. It fills the pack with valid card values.
   */
   private static void allocateMasterPack()
   {
      //If Deck.masterPack is null, then it needs to be filled, otherwise,
      //nothing needs to be done.
      if (Deck.masterPack != null)
      {
         //For each suit, fill the masterPack with each valid card value from
         //that suit.
         for (int i = 0; i < Card.Suit.values().length; i++)
         {
            for (int j = 0; j < Card.validCardValues.length; j++)
            {
               Deck.masterPack[i * Card.validCardValues.length + j] =
                  new Card(Card.validCardValues[j], Card.Suit.values()[i]);
            } // end for loop j
         } // end for loop i
      } // end if
   } // end allocateMasterPack()

   public boolean addCard(Card card)
   {
      int cardCount = 0;
      
      for (Card cardInDeck : this.cards)
         if (cardInDeck.equals(card))
            cardCount++;
      if (cardCount >= this.numPacks || this.topCard >= this.MAX_CARDS)
         return false;
      this.topCard++;
      this.cards[topCard - 1] = new Card(card);
      return true;
   } // end addCard()
   
   public int getNumCards()
   {
      return this.topCard;
   } // end getNumCards()
   
   public boolean removeCard(Card card)
   {
      for (int i = 0; i < this.cards.length; i++)
         if (this.cards[i].equals(card))
         {
            this.cards[i] = new Card(this.cards[topCard - 1]);
            this.topCard--;
            return true;
         } // end if
      
      return false;
   } // end removeCard()
   
   public void sort()
   {
      Card.arraySort(this.cards, this.topCard);
   } // end sort()
} // end class Deck


// ------------------------- NEW CODE ------------------------------------


// ----------------------- END NEW CODE ------------------------------------

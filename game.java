import java.util.*;
import java.lang.Math.*;
import java.lang.*;

public class game{
	
	int enableUnicode = 0; //for users who may not have support for unicode characters
	
	public class card
	{ //card class, with a suit and rank
		String suit;
		int value;
	}
	public class cardPile{	//class for pile of cards, each new game started makes a new pile of cards
		int pileNumber;//for keeping track of pile numbers when entering eternal mode
		ArrayList<card> cards;
	}	
    // A person class to keep the name, an array of card, traits of cards in hand
	public class person
	{	//person class with a name and hand
		String name;
		ArrayList<card> hand;
		int hasFolded;	//all these variables are used in evaluating the person's hand and in calculating the final winner
		int hasFullHouse;
		int fullHouseRank; //for comparing against other players' fullhouses
		int straightRank; //for comparing against any other players' flushes
		int hasRoyalFlush;
		int hasAllSameSuit;
		int hasStraightFlush;
		int hasStraight;
		int numberOfPairs;
		int pairRank; //for comparing against any other players' pairs
		int numberOfTriplets;
		int tripletRank; //for comparing against any other players' triplets
		int numberOfQuadruples;
		int quadrupleRank;
		int gameRank;
	}

	public class highScorer{
		String title;
		int score;
	}
	ArrayList<highScorer> scorers = new ArrayList<highScorer>();

	public highScorer makehighScorer(String s, int x){;
		highScorer hh = new highScorer();
		hh.title = s;
		hh.score = x;
		return hh;
	}

	public void setScoreBoard(){
		for(int i = 0; i < 8; i++){
			highScorer hs = new highScorer();
			hs.title = "????????";
			hs.score = 0;
			scorers.add(hs);
		}
	}
	public void evaluateScore(String s, int rank){
		//ArrayList<highScorer> replacementSet = new ArrayList<highScorer>();
		int isNewHighScore = 0;
		highScorer hs = makehighScorer(s,rank);
		for(int i = 0; i < scorers.size(); i++){
			if(rank > scorers.get(i).score){
				isNewHighScore = 1;
				break;				
			}	
		}
	
		if(isNewHighScore == 1){scorers.remove(scorers.size()-1); scorers.add(hs); BubbleSortScores(scorers); //System.out.println("Added score"); 	
		}

	}
	public void printHighScores(){
		int i = 0;
			System.out.println("Rank:	Name:		Score:");
			System.out.println("*  ***************************************");
		while(i < scorers.size()){
			System.out.println(i+1 + ":  " + scorers.get(i).title + ":		" + scorers.get(i).score);
			i++;
		}
		
	}
	/*This method is used to deal cards to a person
	 *It takes a String and create a person through getPerson() method
	 *It ensures five random cards from the card pile dealt to the person after calling this method
	 *And remove the dealt cards from the pile
	 *Random cards are generated using the Math.random()
	 */
	public void shuffleCards(){ //shuffles cards before we deal the first hand
		
		for(int i = 0; i < piles.get(piles.size()-1).cards.size(); i++){ // go through each index
			int random = (int)(Math.random() * ((piles.get(piles.size()-1).cards.size()-1)+1)); //and swap with a random
			if(random != i){								    //index in the deck
				card temp = piles.get(piles.size()-1).cards.get(i);
				card tempR = piles.get(piles.size()-1).cards.get(random);
				piles.get(piles.size()-1).cards.set(i, tempR);
				piles.get(piles.size()-1).cards.set(random, temp);

			}
		}
		System.out.println("-- Cards have been shuffled! --");
	}

	public void dealCards(String n){ //get the person whose name matches the parameter and deal cards to them until they have 5 in hand
		//System.out.println(cards.size());
		person x = getPerson(n);
			if(x.hand.size() != 0) System.out.println(x.name + " discarded " + (5-x.hand.size()) + " cards.");


		if(x.name == playername && x.hand.size() == 0) shuffleCards(); //before we deal any cards, shuffle the deck.  player always
		//System.out.println(x.hand.size());				goes first
		for(int i = x.hand.size(); i < 5; i++){
		//Random r = new Random(System.currentTimeMillis());
		//Min + (int)(Math.random() * ((Max - Min) + 1))
		//int random = (int)(Math.random() * ((piles.get(piles.size()-1).cards.size()-1)+1)); 
			card c = piles.get(piles.size()-1).cards.get(piles.get(piles.size()-1).cards.size()-1);
			x.hand.add(c);
			//remove card from deck after it has been dealt
			piles.get(piles.size()-1).cards.remove(c);
		}
		System.out.println(n + " was dealt some cards.");
	}	
	/* 
	 * It takes a String and an int and as the input
	 * And matches the String with the card.suit and card.value in the ArrayList<card> piles
	 * If they match, return this card
	 * else return null
	 */
	public card getCard(String suite, int rank){ //return a card object matching the description in parameters
		for(card c : piles.get(piles.size()-1).cards){
			if(c.suit.equalsIgnoreCase(suite) && c.value == rank)
				return c;
		}	
		return null;
	}
	/*This method is used to create a new person 
	 * It takes a String as the input and creates a person class
	 * This person would take the string as name and a new ArrayList<card> is initialized as hand
	 * The rest of the parameters initialized to 0
	 * In the end of the method we add this person to the ArrayList<person> people
	 */
	public void makePerson(String s){	//create a new person and add them to the list of all people
		person p = new person();
		p.name = s;
		p.hand = new ArrayList<card>();
		p.hasFolded = 0;
		p.hasFullHouse = 0;
		p.hasRoyalFlush = 0;
		p.hasAllSameSuit = 0;
		p.hasStraight = 0;
		p.hasStraightFlush=0;
		p.straightRank = 0;
		p.numberOfPairs = 0;
		p.pairRank = 0;
		p.numberOfTriplets = 0;
		p.tripletRank = 0;
		p.numberOfQuadruples = 0;
		p.quadrupleRank = 0;
		p.gameRank = 0;
		people.add(p);

		System.out.println(s + " has taken a seat at the table.");
	}

	/*This method takes a String and match the name of person in ArrayList<person> people
	 * If it matches a person's name in the list, we return the person with the same name
	 * else we return null
	 */
	public person getPerson(String n)
	{ //return the person whose name matches the string parameter
		for(person p : people){
			if(p.name.equals(n))
				return p;
		}
		return null;
	}
	/*
	 * This method makes cards taking a String and an int as input
	 * It creates a card class baring the String as its suit and the int as its value
	 * Then we add this card to the end of the ArrayList<card> piles
	 */
	public void makeCard(String s, int val){ //make a new card and add it to the appropriate cardpile
		card e = new card();
		e.suit = s;
		e.value = val;
		piles.get(piles.size()-1).cards.add(e);
	}
	/*
	 * This method is used to print the hand in the person's hand
	 * It takes a String as the input
	 * It first finds the person from the ArrayList<person> people
	 * Then we sort the cards in the person's hand and print them in order from high to low
	 * And print the cards in a cardlike way with their suit at top and rank in the middle
	 * _______
	 *| suit  |
	 *| value |
	 *|		  |
	 *|		  |
	 *|_______| 
	 */
	public void printHand(String n){ //print the person's name followed by their cards
		person p = getPerson(n);
		if(p.name.equals(playername))
			System.out.println("Your cards.");				
		else			
			System.out.println(p.name + "'s cards.");
		System.out.println("-----------------");
		BubbleSortCards(p.hand);
				ArrayList<ArrayList<card>> pairs = new ArrayList<ArrayList<card>>();//3 ArrayList<ArrayList<card>> to store pairs,triplets and quadruples
				ArrayList<ArrayList<card>> triplets = new ArrayList<ArrayList<card>>();			
				ArrayList<ArrayList<card>> quadruples = new ArrayList<ArrayList<card>>();					
				processCards(p, pairs, triplets, quadruples);
		reorder(p);
		int i = 0;
		for(card c : p.hand){
		 	if(i == 0) System.out.println("  ______ ");
			i++;
			//System.out.println("|------| \\");
			if(enableUnicode == 1){
				if(c.suit.charAt(0) == 's' || c.suit.charAt(0) == 'S') //unicode characters for suits instead of ascii character
					System.out.println("\\|   "+'\u2660'+"  |\\");
				else if(c.suit.charAt(0) == 'c' || c.suit.charAt(0) == 'C')
					System.out.println("\\|   "+'\u2663'+"  |\\");
				else if(c.suit.charAt(0) == 'h' || c.suit.charAt(0) == 'H')
					System.out.println("\\|   "+'\u2665'+"  |\\");
				else if(c.suit.charAt(0) == 'd' || c.suit.charAt(0) == 'D')
					System.out.println("\\|   "+'\u2666'+"  |\\");
			}
			else{
				System.out.println("\\|   "+c.suit.charAt(0)+"  |\\");
			}
			if(c.value==14) System.out.println("\\|1 A 14|\\");
			else if(c.value == 10) System.out.println("\\|  "+c.value+"  |\\");
			else if(c.value == 11) System.out.println("\\| J-11 |\\");
			else if(c.value == 12) System.out.println("\\| Q-12 |\\");
			else if(c.value == 13) System.out.println("\\| K-13 |\\");			
			else System.out.println("\\|  "+c.value+"   |\\");
			System.out.println("\\|______|\\");			
			
		
		}
	}	
	/*
	 * This method is used to get the hand of a person based on his name
	 * It takes a String and find the person through getPerson method
	 * And return his hand of cards
	 */
	public ArrayList<card> getHand(String n)
	{ //return a person's hand
		person p = getPerson(n);
			return p.hand;		
	}	
	/*This method is used to bubblesort the cards in the person's hand
	 * It takes an ArrayList<card> and sort them based on the rank of the cards
	 * It will sort them in a descending sort based on the requirement i
	 */
	public void BubbleSortCards(ArrayList<card> hand) //sort the card by value from least to most
	{
	     int j;
	     boolean flag = true;   // set flag to true to begin first pass
	     card temp;   //holding variable
	     while ( flag )
	    {
		    flag= false;    //set flag to false awaiting a possible swap
		    for( j=0;  j < hand.size()-1;  j++ )
		    {
			   if ( hand.get(j).value < hand.get(j+1).value )   // change to > for ascending sort
			   {
				   temp = hand.get(j);                //swap elements
				   hand.set(j, hand.get(j+1));
				   hand.set(j+1, temp);
				   flag = true;              //shows a swap occurred 
			  }
		    }
	     }
	}

		public void BubbleSortScores(ArrayList<highScorer> scores) //sort the card by value from least to most
{
	     int j;
	     boolean flag = true;   // set flag to true to begin first pass
	     highScorer temp;   //holding variable
	     while ( flag )
	    {
		    flag= false;    //set flag to false awaiting a possible swap
		    for( j=0;  j < scores.size()-1;  j++ )
		    {
			   if ( scores.get(j).score < scores.get(j+1).score )   // change to > for ascending sort
			   {
				   temp = scores.get(j);                //swap elements
				   scores.set(j, scores.get(j+1));
				   scores.set(j+1, temp);
				   flag = true;              //shows a swap occurred 
			  }
		    }
	     }
	}
	
	/*This method is used to reorder the card in hand for better viewing
	 * It takes a person and based on different hand he has, we put quadruples,triplets,pairs in front so it helps user to change their cards
	*/
	
	public void reorder(person p)
	{
		
			//reorders when quadruple is present
			if(p.numberOfQuadruples==1)
			{
				if (p.hand.get(0).value!=p.quadrupleRank)//when the first card value is not the quadruple value
					swapCards(p);			
			}
			else if (p.hasAllSameSuit==1&&p.hasStraight==0)
			{
				BubbleSortCards(p.hand);
			}
			//reorders when triplet is present
			else if (p.numberOfTriplets==1)//This deals with triplet and full house at the same time
			{
				//BubbleSortCards(p.hand);
				if(p.hand.get(0).value!=p.tripletRank)
				{
					swapCards(p);
				}
				if(p.hand.get(0).value != p.tripletRank) //check again if we have another card out of order
					swapCards(p);
 
				if(!(p.hand.get(3).value >= p.hand.get(4).value)){ ArrayList<card> newHand = new ArrayList<card>();
				newHand.add(p.hand.get(0)); //copy triplet to new hand, and then switch the unordered cards
				newHand.add(p.hand.get(1));
				newHand.add(p.hand.get(2));
				newHand.add(p.hand.get(4));
				newHand.add(p.hand.get(3));
				p.hand.clear(); //clear hand
				p.hand = newHand;} //set to the reordered hand
			}
			//reorders when it's 2 pairs
			else if (p.numberOfPairs==2)
			{
				if (p.hand.get(0).value==p.pairRank)//when the high pair also has highest rank
				{
					if (p.hand.get(2).value!=p.hand.get(3).value)//when the low pair has the lowest rank
					{
						card temp=p.hand.get(2);
						p.hand.remove(2);
						p.hand.add(temp);//we remove the third card and add it to the end
					}
				}
				else//when the card of the highest rank is not in any pair
				{
					swapCards(p);
					//we remove the card and and the card to the end	
				}
			}
			//reorders when it's 1 pair
			else if (p.numberOfPairs==1)
			{
				int i=0;
				
				while (i < 5 && p.hand.get(i).value!=p.pairRank)
					i++;
					if (i==1)
					{
						card temp=p.hand.get(0);
						p.hand.add(3,temp);
						p.hand.remove(0);
					}
					else if (i==2)
					{
						while(i>0)
						{
							card temp=p.hand.get(0);
							p.hand.add(4,temp);
							p.hand.remove(0);
							i--;
						}
						
					}
					else if (i==3)
					{
						while(i>0)
						{
							card temp=p.hand.get(0);
							p.hand.add(5,temp);
							p.hand.remove(0);
							i--;
						}
						
					}
			}
		
	}
 	
			/*
			 * This method is used to make a new person and the number of 1-3 players based on his choice
			 * It first prompts out 
			 *			   ######################
						   # Select a game size #
						   ######################
						   1. One Opponent
						   2. Two Opponents
						   3. Three Opponents
			 * and takes the ascii value between 49-51 (1-3) 
			 * It will create Colonel Hamberg, Rawhide Randall or Ol' Stinky Pete accordingly
			 * or when the input is not in range, print out 
			 * "Tap the 1, 2, or 3 key on your keyboard to select a gamestyle and press enter."
			 * After the player numbers are properly set, it makes a call to playGame() method with number of total players as the input value
			 */
	public void getPlayerCount(){ //second stage of user input and the program, decides how larg to make the game
		Scanner reader = new Scanner(System.in);
		int haveTheyChosenAGamestyleYet = 0;

		while(haveTheyChosenAGamestyleYet == 0){
		System.out.println("######################\n# Select a game size #\n######################\n1. One Opponent\n2. Two Opponents\n3. Three Opponents\n\nIf you don't want to be here, enter 'q'");
		
		String num = reader.nextLine();
		if(num.equalsIgnoreCase("q") || num.equalsIgnoreCase("quit")){System.out.println("So long, and remember - with linux, you WINUX!");  System.exit(0);}
		
		if(num.length() > 0){
			char c = num.charAt(0); // first char in string
			int ascii = (int)c;
				if(num.length() == 1 && ascii >= 49 && ascii <= 51){
					if(Integer.parseInt(num) == 1){
						makePerson("Colonel Hamberg");					
						/*getPerson(playername).hand.add(getCard("Diam",5));
						getPerson(playername).hand.add(getCard("Club",5));
						getPerson(playername).hand.add(getCard("Diam",2));
						getPerson(playername).hand.add(getCard("Diam",3));
						getPerson(playername).hand.add(getCard("Diam",7));
						getPerson("Colonel Hamberg").hand.add(getCard("Club",12));
						getPerson("Colonel Hamberg").hand.add(getCard("Hear",12));
						getPerson("Colonel Hamberg").hand.add(getCard("Club",12));
						getPerson("Colonel Hamberg").hand.add(getCard("Club",9));
						getPerson("Colonel Hamberg").hand.add(getCard("Club",10));*/
						dealCards(playername);
						dealCards("Colonel Hamberg");
						//BubbleSortCards(getPerson("Colonel Hamberg").hand);
						//reorder(getPerson("Colonel Hamberg"));
						//printHand("Colonel Hamberg");
						//printHand(playername);
						haveTheyChosenAGamestyleYet = 1;
					}
					else if(Integer.parseInt(num) == 2){
						makePerson("Colonel Hamberg");
						makePerson("Rawhide Randall");
						dealCards(playername);
						dealCards("Colonel Hamberg");
						dealCards("Rawhide Randall");
						//printHand("Colonel Hamberg");
						//printHand("Rawhide Randall");
						//printHand(playername);
						haveTheyChosenAGamestyleYet = 1;
					}
					else if(Integer.parseInt(num) == 3){
						makePerson("Colonel Hamberg");
						makePerson("Rawhide Randall");
						makePerson("Ol' Stinky Pete");
						dealCards(playername);
						dealCards("Colonel Hamberg");
						dealCards("Rawhide Randall");
						dealCards("Ol' Stinky Pete");
						//printHand("Colonel Hamberg");
						//printHand("Rawhide Randall");
						//printHand("Ol' Stinky Pete");
						//printHand(playername);
						haveTheyChosenAGamestyleYet = 1;
					}
				}
			else
				System.out.println("\nTap the 1, 2, or 3 key on your keyboard to select a gamestyle and press enter.\n");
		}
		else System.out.println("\nTap the 1, 2, or 3 key on your keyboard to select a gamestyle and press enter.\n");
		}
		playGame(people.size()); //call the play game method
	}
	/*
	 * This method is used to process hand in a person currentTurn and evaluate them accordingly
	 * It takes a person, an ArrayList<ArrayList<card>> pairs to store pairs in the hand if any, 
	 * an ArrayList<ArrayList<card>> triplets to store triplets in this hand if any,
	 * and an ArrayList<ArrayList<card>> quadruples to store quadruples if any.
	 * 
	 */
	public void processCards(person currentTurn, ArrayList<ArrayList<card>> pairs, ArrayList<ArrayList<card>> triplets, ArrayList<ArrayList<card>> quadruples){

		reorder(currentTurn);

		currentTurn.hasFolded = 0;
		currentTurn.hasFullHouse = 0;
		currentTurn.hasRoyalFlush = 0;
		currentTurn.hasAllSameSuit = 0;
		currentTurn.hasStraight = 0;
		currentTurn.hasStraightFlush = 0;
		currentTurn.straightRank = 0;
		currentTurn.numberOfPairs = 0;
		currentTurn.pairRank = 0;
		currentTurn.numberOfTriplets = 0;
		currentTurn.tripletRank = 0;
		currentTurn.numberOfQuadruples = 0;
		currentTurn.quadrupleRank = 0;

		currentTurn.gameRank = 0;
				//lets see if the person has a "flush"/all same suit
			int numClubs = 0;
			int numHearts = 0;
			int numSpades = 0;
			int numDiams = 0;

			for(card cc : currentTurn.hand){
				if(cc.suit.equals("Club")) numClubs++;
				else if(cc.suit.equals("Hear")) numHearts++;
				else if(cc.suit.equals("Diam")) numDiams++;
				else if(cc.suit.equals("Spad")) numSpades++;
			}

			if(numClubs == 5 || numHearts == 5 || numSpades == 5 || numDiams == 5) currentTurn.hasAllSameSuit = 1;
			//if the number of x suit in a hand is 5, all cards are of that suit
	
				//printHand(currentTurn.name);
				//first check for pairs
				for(int i = 0; i < currentTurn.hand.size()-1; i++){
					for(int j = i+1; j < currentTurn.hand.size(); j++){
						if(currentTurn.hand.get(i).value == currentTurn.hand.get(j).value)
						{
							ArrayList<card> thePair = new ArrayList<card>();
							thePair.add(currentTurn.hand.get(i));
							thePair.add(currentTurn.hand.get(j));
							pairs.add(thePair);
							if(currentTurn.pairRank < thePair.get(0).value)
								currentTurn.pairRank = thePair.get(0).value;
						}
					}

				}
			currentTurn.numberOfPairs = pairs.size();
			//check for quadruple
			int isQuadruple = 1;
			if(!(pairs.size() == 6))
				isQuadruple = 0;

			if(isQuadruple == 1){ 
				ArrayList<card> theQuadruple = new ArrayList<card>();
				
				if(currentTurn.hand.get(0) == currentTurn.hand.get(1)) //if c0 = c1, low four cards 
					{for(int r = 0; r < currentTurn.hand.size()-1;r++){
						theQuadruple.add(currentTurn.hand.get(r));
						quadruples.add(theQuadruple);						
						}}
				else {for(int r = 1; r < currentTurn.hand.size();r++){ //if c0 =/= c1, high four cards
						theQuadruple.add(currentTurn.hand.get(r));
						quadruples.add(theQuadruple);						
						}}
				currentTurn.numberOfQuadruples = 1;

				if(currentTurn.quadrupleRank < theQuadruple.get(0).value)
								currentTurn.quadrupleRank = theQuadruple.get(0).value;
			}

				//next check for three of a kind
				for(int i = 0; i < currentTurn.hand.size()-2; i++){
					for(int j = i+1; j < currentTurn.hand.size()-1; j++){
						for(int k = j+1; k < currentTurn.hand.size();k++){
							if(currentTurn.hand.get(i).value == currentTurn.hand.get(j).value && currentTurn.hand.get(j).value == currentTurn.hand.get(k).value)
							{
								ArrayList<card> theTriplet = new ArrayList<card>();
								theTriplet.add(currentTurn.hand.get(i));
								theTriplet.add(currentTurn.hand.get(j));
								theTriplet.add(currentTurn.hand.get(k));
								triplets.add(theTriplet);

								if(currentTurn.tripletRank < theTriplet.get(0).value)
									currentTurn.tripletRank = theTriplet.get(0).value;
							}
						}
					}

				}
			currentTurn.numberOfTriplets = triplets.size();
			

			/*	*/
			//look for a full house
			int testFullHouse = 0;
			if(triplets.size() > 0 && pairs.size() > 0)
				{
					for(int ind1 = 0; ind1 < pairs.size(); ind1++){
						for(card gee : pairs.get(ind1)){
							for(int ind2 = 0; ind2 < triplets.size(); ind2++){
								for(card wee : triplets.get(ind2)){
									if(gee.value != wee.value)
										testFullHouse = 1;
								}
							}
						}
					}
			}
			if(testFullHouse == 1){ currentTurn.hasFullHouse = 1; currentTurn.fullHouseRank = triplets.get(0).get(0).value;}
			else currentTurn.hasFullHouse = 0;

			//look for a straight/flush
			BubbleSortCards(currentTurn.hand);
			//reorder(currentTurn);
			//printHand(currentTurn.name);
			if((currentTurn.hand.get(0).value == currentTurn.hand.get(1).value+1 && currentTurn.hand.get(1).value == currentTurn.hand.get(2).value+1 && currentTurn.hand.get(2).value == currentTurn.hand.get(3).value+1 && currentTurn.hand.get(3).value == currentTurn.hand.get(4).value+1) || (currentTurn.hand.get(0).value == 14 && currentTurn.hand.get(1).value == 5 && currentTurn.hand.get(2).value == 4 && currentTurn.hand.get(3).value == 3 && currentTurn.hand.get(4).value == 2))
			{currentTurn.hasStraight = 1; if(currentTurn.hand.get(1).value == 5) currentTurn.straightRank = 5; else currentTurn.straightRank = currentTurn.hand.get(0).value;}
			
			//royal flush?
			 if(currentTurn.straightRank == 14 && currentTurn.hand.get(1).value == 13)
			 {
				 currentTurn.hasRoyalFlush=1;
			 }
			 if (currentTurn.hasStraight==1&&currentTurn.hasAllSameSuit==1)
				 currentTurn.hasStraightFlush=1;

		reorder(currentTurn);
	}


	public void makeDecisions(person currentTurn){ //here is where the ai will decide what cards to keep

	//	int numPlayerDiscarded = 0;
		if(currentTurn.hasRoyalFlush == 1 || currentTurn.hasStraight == 1 || currentTurn.hasFullHouse == 1 || currentTurn.hasAllSameSuit == 1) return; //do nothing in these cases
		if(currentTurn.numberOfPairs == 1){ //if the ai player has a pair, discard all cards not in the pair
			ArrayList<card> newHand = new ArrayList<card>();
			for(card cc : currentTurn.hand){
				if(cc.value == currentTurn.pairRank)
					newHand.add(cc);
			}
			currentTurn.hand.clear();
			for(card xx : newHand){
				currentTurn.hand.add(xx);
			}

			dealCards(currentTurn.name);						
		}
		else if(currentTurn.numberOfPairs == 2){ //if the ai player has two pairs, discard the card not in either pair
			ArrayList<card> newHand = new ArrayList<card>();			
			for(card cc : currentTurn.hand){
				if(cc.value == currentTurn.pairRank)
					newHand.add(cc);
			}
			for(int dd = 0; dd < (currentTurn.hand.size()-1); dd++){
				if((currentTurn.hand.get(dd).value == currentTurn.hand.get(dd+1).value) && currentTurn.hand.get(dd).value != currentTurn.pairRank){newHand.add(currentTurn.hand.get(dd)); newHand.add(currentTurn.hand.get(dd+1)); break;}		
			}
			currentTurn.hand.clear();
			for(card xx : newHand){
				currentTurn.hand.add(xx);
			}

			dealCards(currentTurn.name);	
		}
		else if(currentTurn.numberOfTriplets == 1){ //if he has a triplet, discard the other two cards
			ArrayList<card> newHand = new ArrayList<card>();
			for(card cc : currentTurn.hand){
				if(cc.value == currentTurn.tripletRank){
					newHand.add(cc);
				}	
			}
			currentTurn.hand.clear();
			for(card xx : newHand){currentTurn.hand.add(xx);}
			dealCards(currentTurn.name);
		}
		else if(currentTurn.hasRoyalFlush == 0 && currentTurn.hasStraight == 0 && ((currentTurn.hand.get(4).value - currentTurn.hand.get(1).value) == 3 || (currentTurn.hand.get(3).value - currentTurn.hand.get(0).value) == 3))
		{  //has four cards in ascending order

			card rr = currentTurn.hand.get(0);
			card ss = currentTurn.hand.get(4);

			if( rr.value != (currentTurn.hand.get(1).value -1))
				currentTurn.hand.removeAll(Collections.singleton(rr));
			else if( ss.value != (currentTurn.hand.get(3).value +1))
				currentTurn.hand.removeAll(Collections.singleton(ss));

			dealCards(currentTurn.name);
		}
		else { //if he has nothing good, look to see if he has at least 4 matching suit
			int numClubs = 0;
			int numHearts = 0;
			int numSpades = 0;
			int numDiams = 0;

			for(card cc : currentTurn.hand){
				if(cc.suit.equals("Club")) numClubs++;
				else if(cc.suit.equals("Hear")) numHearts++;
				else if(cc.suit.equals("Diam")) numDiams++;
				else if(cc.suit.equals("Spad")) numSpades++;
			}
			
			if(numClubs == 4){
				int toRemove = 0;
				for(int xx = 0; xx < currentTurn.hand.size(); xx++){
					if(!(currentTurn.hand.get(xx).suit.equals("Club"))){
						toRemove = xx;
						break;
					}
				}
				//System.out.println("Removed " + toRemove);
				currentTurn.hand.remove(toRemove);
			}
			if(numHearts == 4){
				int toRemove = 0;
				
				for(int xx = 0; xx < currentTurn.hand.size(); xx++){
					if(!(currentTurn.hand.get(xx).suit.equals("Hear"))){
						toRemove = xx;
						break;
					}
				}
				currentTurn.hand.remove(toRemove);
			}
			if(numSpades == 4){
				int toRemove = 0;
				for(int xx = 0; xx < currentTurn.hand.size(); xx++){
					if(!(currentTurn.hand.get(xx).suit.equals("Spad"))){
						toRemove = xx;
						break;
					}
				}
				currentTurn.hand.remove(toRemove);
			}
			if(numDiams == 4){
				int toRemove = 0;
				for(int xx = 0; xx < currentTurn.hand.size(); xx++){
					if(!(currentTurn.hand.get(xx).suit.equals("Diam"))){
						toRemove = xx;
						break;
					}
				}
				currentTurn.hand.remove(toRemove);
			}
			else if(currentTurn.hand.get(0).value == 14){ //has an ace, which will be first in list.  keep ace, discard other four
				ArrayList<card> newHand = new ArrayList<card>();
				newHand.add(currentTurn.hand.get(0));
				currentTurn.hand.clear();
				currentTurn.hand.add(newHand.get(0));
				
			}
			else{
				ArrayList<card> newHand = new ArrayList<card>();
				newHand.add(currentTurn.hand.get(0)); //put two highest in
				newHand.add(currentTurn.hand.get(1));	
				currentTurn.hand.clear();
				for(card ee : newHand){
					currentTurn.hand.add(ee);
				}
			}

			dealCards(currentTurn.name);
		}
	}
	

	//This method remove the cards from ith position and put it to the end
	public void swapCards(person p)
	{
		card temp = p.hand.get(0);
		p.hand.remove(0);
		p.hand.add(temp);
	}
	public void swap(ArrayList<person> pp, int i, int j)
    {
        
        person temp = pp.get(i);                //swap elements
        pp.set(i, pp.get(j));
        pp.set(j, temp);
    }
	
	public void determineWinner(){ //assigns a score to each card combo, bonuses for high ranking cards and matchng suits are applied
	//turns out a royal flush needs all matching suit, but a straight can be a regular straight or a FLUSh straight	
		for(person currentTurn : people){		
			//royal flush is not in the rule, but we'd like to keep it. It is the highest straight flush
			if(currentTurn.hasRoyalFlush == 1 && currentTurn.hasAllSameSuit == 1) {System.out.println(currentTurn.name + " has a royal flush!");currentTurn.gameRank = 464;}			
			//straight flush, the first a winning case,when two people both have straight flush, look into the high card rank
			else if(currentTurn.hasStraightFlush==1){ System.out.println(currentTurn.name+" has a straight flush!"); currentTurn.gameRank=450+currentTurn.straightRank;}			
			//four of a kind, the second winning case, when two people both have four of a kind, look into the quadrupleRank. 
			else if(currentTurn.quadrupleRank > 0)
			{ 
				if (currentTurn.quadrupleRank==14)
					System.out.println(currentTurn.name + " has four Aces!"); 
				else
					System.out.println(currentTurn.name + " has four " + currentTurn.quadrupleRank + "s!"); 
				currentTurn.gameRank = (400+currentTurn.quadrupleRank);}
			//full house, the third winning case, when two people both have four of a kind, look into the fullHouseRank
			else if(currentTurn.hasFullHouse == 1){ 
				if (currentTurn.fullHouseRank==14)
					System.out.println(currentTurn.name + " has a full house of rank Ace!");
				else
					System.out.println(currentTurn.name + " has a full house of rank " + currentTurn.fullHouseRank + "!"); 
				currentTurn.gameRank = (350 + currentTurn.fullHouseRank);}
			//flush, the fourth winning case, when two people both have it,they have a and we need to compare the second highest and so on
			else if(currentTurn.hasAllSameSuit==1){System.out.println(currentTurn.name+" has a regular flush!");currentTurn.gameRank=300+currentTurn.hand.get(0).value;}
			//straight,the fifth winning case, when two people both have it, look into the rank of the high card
			else if(currentTurn.hasStraight == 1){ System.out.println(currentTurn.name + " has a straight!"); currentTurn.gameRank = (250 + currentTurn.straightRank);}
			//three of a kind, the sixth winning case, when two people both have it, look into the rank of tripleRank
			else if(currentTurn.tripletRank > 0 && currentTurn.quadrupleRank == 0){ 
				if (currentTurn.tripletRank==14)
					System.out.println(currentTurn.name + " has a triplet of Aces!");
				else
					System.out.println(currentTurn.name + " has a triplet of " + currentTurn.tripletRank + "s!"); 
				currentTurn.gameRank = (200 + currentTurn.tripletRank);} 
			//two pairs, the seventh winning case, when two people both have it, compare the high pair first, then the low pair
			else if(currentTurn.numberOfPairs == 2  && currentTurn.tripletRank == 0 && currentTurn.quadrupleRank == 0)
			{ 
				if (currentTurn.pairRank==14)
					System.out.println(currentTurn.name + " has two pairs with a high rank of Aces!");
				else
					System.out.println(currentTurn.name + " has two pairs with a high rank of " + currentTurn.pairRank + "s!"); 
				currentTurn.gameRank = (150 + currentTurn.pairRank);}
			//1 pair, the eighth winning case, when two people both have it, compare the pair first, then the rest of the cards successively
			else if(currentTurn.numberOfPairs == 1)
			{ 
				if (currentTurn.pairRank==14)
					System.out.println(currentTurn.name + " has a pair of Aces!");
				else
					System.out.println(currentTurn.name + " has a pair of " + currentTurn.pairRank + "s!"); 
				currentTurn.gameRank = (100 + currentTurn.pairRank);}
			//high card, the ninth winning case, when two people both have it, compare the cards from high to low
			else{ 
				if  (currentTurn.hand.get(0).value==14)
					System.out.println(currentTurn.name + " has a high card of rank Ace");
				else
					System.out.println(currentTurn.name + " has a high card of rank " + currentTurn.hand.get(0).value); 
				currentTurn.gameRank += currentTurn.hand.get(0).value;}
		}
	
		int tie=0;
		String winnerName = "";
		int j;
			     boolean flag = true;   // set flag to true to begin first pass
			     person temp;   //holding variable
			     while ( flag )
			     {
				    flag= false;    //set flag to false awaiting a possible swap
				    for( j=0;  j < people.size()-1;  j++ )
				    {
				    	//reorder(people.get(j));
				    	//reorder(people.get(j+1));
					   if ( people.get(j).gameRank > people.get(j+1).gameRank )   // change to > for ascending sort
					   {
                           swap(people,j,j+1);
						   flag = true;//shows a swap occurred
					   }
					   if (people.get(j).gameRank==people.get(j+1).gameRank)
					   {
						   //when there is a tie with straight flush
						   if (people.get(j).hasRoyalFlush==1||people.get(j).hasStraightFlush==1)
							   tie++;                      
						   //when there is a tie in four of a kind,there is no chance of getting a tie!
						   else if (people.get(j).numberOfPairs==6)
							   tie++;
						   //when there is a tie in full house,there is no chance of getting a tie!
						  /* else if (people.get(j).hasFullHouse==1)
							   tie++;*/
						   //when there is a tie in regular flush
						   else if (people.get(j).hasAllSameSuit==1)
						   {
							   //compare the second card
							   if (people.get(j).hand.get(1).value>people.get(j+1).hand.get(1).value)
							   {
                                   swap(people,j,j+1);
                                   flag = true;
							   }
							   //when a tie in 2nd card, compare the third card
							   else if(people.get(j).hand.get(2).value>people.get(j+1).hand.get(2).value)
							   {
                                   swap(people,j,j+1);
								   flag = true; 
							   }
							   //when a tie in 3rd card, compare the fourth card
							   else if (people.get(j).hand.get(3).value>people.get(j+1).hand.get(3).value)
							   {
								   swap(people,j,j+1);
								   flag = true; 
							   }
							   //when a tie in 4th card, compare the last card
							   else if (people.get(j).hand.get(4).value>people.get(j+1).hand.get(4).value)
							   {
                                   swap(people,j,j+1);
								   flag = true; 
							   }
							   else if ((people.get(j).hand.get(1).value==people.get(j+1).hand.get(1).value)&&(people.get(j).hand.get(2).value==people.get(j+1).hand.get(2).value)
                                       &&(people.get(j).hand.get(3).value==people.get(j+1).hand.get(3).value)&&(people.get(j).hand.get(4).value==people.get(j+1).hand.get(4).value))
								   tie++;
                               
						   }
						   //when they have the same game rank in straight, this is a tie
						   else if (people.get(j).hasStraight==1)
                             tie++;						  
						   //when there is a tie in two pairs
						   else if (people.get(j).numberOfPairs==2)
						   {
							   //since we have reordered the card, so we only need to compare the last card
							if (people.get(j).hand.get(4).value>people.get(j+1).hand.get(4).value)
							{
								    swap(people,j,j+1);
                                    flag = true;
							}
							 if (people.get(j).hand.get(4).value==people.get(j+1).hand.get(4).value)
                             {
								 tie++;
								 System.out.println(tie);
                             }
						   }
						   //when there is a tie in the pair
						   else if (people.get(j).numberOfPairs==1)
						   {
							   //compare the 3rd card in hand because we've reordered the card in hand
							   if (people.get(j).hand.get(2).value>people.get(j+1).hand.get(2).value)
							   {
								    swap(people,j,j+1);
                                    flag = true;
							   }
							   //compare the 4th card in hand
							   else if(people.get(j).hand.get(3).value>people.get(j+1).hand.get(3).value)
							   {
                                    swap(people,j,j+1);
                                    flag = true;
							   }
							   //compare the 5th card in hand
							   else if(people.get(j).hand.get(4).value>people.get(j+1).hand.get(4).value)
							   {
                                   swap(people,j,j+1);
								   flag = true; 													   
							   }
							   else if ((people.get(j).hand.get(2).value==people.get(j+1).hand.get(2).value)
                                        &&(people.get(j).hand.get(3).value==people.get(j+1).hand.get(3).value)&&(people.get(j).hand.get(4).value==people.get(j+1).hand.get(4).value))
								   tie++;
						   }
						   //evaluate the high card
						   else
						   {
							   //compare the 2nd card in hand
							   if(people.get(j).hand.get(3).value>people.get(j+1).hand.get(3).value)
							   {
								    swap(people,j,j+1);
                                    flag = true;
                                }
							   //compare the 3rd card in hand because we've reordered the card in hand
							   else if (people.get(j).hand.get(2).value>people.get(j+1).hand.get(2).value)
							   {
								    swap(people,j,j+1);
                                    flag = true;
							   }
							   //compare the 4th card in hand
							   else if(people.get(j).hand.get(1).value>people.get(j+1).hand.get(1).value)
							   {
								    swap(people,j,j+1);
                                    flag = true;
							   }
							   //compare the 5th card in hand
							   else if(people.get(j).hand.get(0).value>people.get(j+1).hand.get(0).value)
							   {
                                   swap(people,j,j+1);
								   flag = true; 													   
							   }
                               
							   else if ((people.get(j).hand.get(1).value==people.get(j+1).hand.get(1).value)&&(people.get(j).hand.get(2).value==people.get(j+1).hand.get(2).value)
                                        &&(people.get(j).hand.get(3).value==people.get(j+1).hand.get(3).value)&&(people.get(j).hand.get(4).value==people.get(j+1).hand.get(4).value))
                                   tie++;
						   }
						 
					   }
				    }
					flag = false;
			      }
		
			     if (tie==0)
			    	System.out.println("\n" + people.get(people.size()-1).name + " wins the game!\n");
			     else if(tie==1)
				    System.out.println("\n" + people.get(people.size()-1).name +" and "+ people.get(people.size()-2).name+ " both win the game!\n");
			     else if (tie==2)
					    System.out.println("\n" + people.get(people.size()-1).name +", "+ people.get(people.size()-2).name + " and "+ people.get(people.size()-3).name+"all win the game!\n");
			     else
					    System.out.println("\n" + people.get(people.size()-1).name +", "+ people.get(people.size()-2).name +", "+ people.get(people.size()-3).name+" and "+ people.get(people.size()-4).name+"all win the game!\n");


        //System.out.println("The number of people in the tie is "+tie);			
	}
	
	public void updateScoreBoard(){
			for(person pp : people){
				evaluateScore(pp.name, pp.gameRank);
			}
	}

	public void playGame(int sizeOfTable)
	{
		int inGame = 1;
		int turn = 0;
		int numPlayerDiscarded = 0;//keeps track of total number of cards discarded
		//BubbleSortCards(getPerson(playername).hand);
		//reorder(getPerson(playername));
		//printHand(playername);
		int isFirst = 1;
		while(inGame ==1)
		{
			
			if(turn == people.size()){ determineWinner(); updateScoreBoard(); break;} //if we have played everyone, go to post-game
			person currentTurn = people.get(turn);			
			if(currentTurn == getPerson(playername)){ //treat the player differently compared to ai
				ArrayList<ArrayList<card>> pairs = new ArrayList<ArrayList<card>>();//3 ArrayList<ArrayList<card>> to store pairs,triplets and quadruples
				ArrayList<ArrayList<card>> triplets = new ArrayList<ArrayList<card>>();			
				ArrayList<ArrayList<card>> quadruples = new ArrayList<ArrayList<card>>();					
				processCards(currentTurn, pairs, triplets, quadruples);
				if(isFirst == 1) printHand(playername);

				isFirst++;

			int discardLimit = 3; //if no ace in hand, can give up to three
			if((currentTurn.hand.get(0).value == 14)||(currentTurn.hand.get(1).value == 14)||(currentTurn.hand.get(2).value == 14)||(currentTurn.hand.get(3).value == 14)||(currentTurn.hand.get(4).value == 14)) discardLimit = 4; //else, up to four cards to discard

			if(discardLimit-numPlayerDiscarded >= 0) System.out.println((discardLimit-numPlayerDiscarded) + " draws left!");
			else System.out.println("No more draws remaining!");

			//processCards(currentTurn, pairs, triplets, quadruples);
			//find proper message to display!
			if(currentTurn.hasRoyalFlush == 1 && currentTurn.hasAllSameSuit == 1) System.out.println("ROYAL FLUSH!");		//1st winning case,highest straight flush
			else if(currentTurn.hasStraightFlush==1) System.out.println("Straight Flush!");//straight flush, 1st winning case
			else if(currentTurn.numberOfQuadruples == 1) //2nd winning case
			{
				if(currentTurn.quadrupleRank==14) System.out.println("You have four Aces!");
				else System.out.println("You have four " + currentTurn.quadrupleRank + "s!");
			}
			else if(currentTurn.hasStraight==1)	System.out.println("You have a straight!");
			else if(currentTurn.hasFullHouse == 1) //3rd winning case
			{
				if (currentTurn.fullHouseRank==14) System.out.println("You have a full house of rank Ace!");
				else System.out.println("You have a full house of rank " + currentTurn.fullHouseRank + "!");
			}
			else if(currentTurn.hasAllSameSuit==1) System.out.println("You have a regular flush!");//4th winning case
			else if(currentTurn.hasStraight == 1) System.out.println("You have a straight!");//5th winning case			
			else if(triplets.size() > 0 && quadruples.size() == 0) //6th winning case
			{
				if (currentTurn.tripletRank==14)	System.out.println("You have a triplet of Aces!");
				else	System.out.println("You have a triplet of " + currentTurn.tripletRank + "s!");
			}
			else if(pairs.size() == 2  && triplets.size() == 0 && quadruples.size() == 0) //7th winning case
			{
			if (currentTurn.pairRank==14) System.out.println("You have two pairs with your highest pair being of Aces!");
			else System.out.println("You have two pairs with your highest pair being of "+currentTurn.pairRank+  "s!");
			}
			else if(pairs.size() == 1  && triplets.size() == 0 && quadruples.size() == 0) //8th winning case
			{
				if (currentTurn.pairRank==14) System.out.println("You have a pair of Aces!");
				else System.out.println("You have a pair of " + currentTurn.pairRank + "s!");
			}
			else System.out.println("You have nothing good so far...");
			System.out.println("What is your move? (Enter Number)\n1. Draw new cards\n2. View your hand\n3. End Turn\n4. View High Scores\n5. Exit");
			Scanner readem = new Scanner(System.in);
			String choice = readem.nextLine();
			if(choice.length() > 0){
					char c = choice.charAt(0); // first char in string
					int ascii = (int)c;
				if(choice.length() == 1 && ascii >= 49 && ascii <= 53){
				if(Integer.parseInt(choice) == 1){
					if(numPlayerDiscarded < discardLimit){
					//crappy way of doing things
					int done = 0;
					while(done == 0 && numPlayerDiscarded < discardLimit){
							System.out.println("Enter the card(s) (up to three per turn) you want to remove by choosing { 1 | 2 | 3 | 4 | 5 } where the top card is 1.\nMultiple cards should be separated by spaces\nIf you don't want to be here, press q");
							
							String toParse = readem.nextLine();
							if(toParse.equalsIgnoreCase("q") || toParse.equalsIgnoreCase("quit")) break;
							String[] selecteds = toParse.split(" ");
						
							int goodString = 1;
							for(int rex = 0; rex < selecteds.length; rex++){
								if(selecteds[rex].length() != 0){								
								char d = selecteds[rex].charAt(0);
								int asciitwo = (int)d;
								//System.out.println(selecteds[rex]);
								if(!(selecteds[rex].length() == 1 && asciitwo >= 49 && asciitwo <= 54) || selecteds.length > (discardLimit-numPlayerDiscarded)) goodString = 0;}
								else goodString = 0;
							}
							if(goodString == 1){
								card placeHolder = new card();
								placeHolder.suit = "temp";
								placeHolder.value = 99;
								person x = getPerson(playername);
								for(int i = 0; i < selecteds.length; i++){ 
									int index = Integer.parseInt(selecteds[i]);

										
										x.hand.set((index-1),placeHolder);
								}
							

								x.hand.removeAll(Collections.singleton(placeHolder));						
								dealCards(playername);
								numPlayerDiscarded += selecteds.length;
								

								
								//BubbleSortCards(getPerson(playername).hand);
								//reorder(getPerson(playername));
								//printHand(playername);
								//System.out.println((3-numPlayerDiscarded) + " draws left!");
								printHand(playername);
								done = 1;
							}
							else {
								if(!(numPlayerDiscarded >= discardLimit)) System.out.println("I didn't quite understand that.");
								else System.out.println("No more draws are left for you!");}
						}
					
					}
					else System.out.println("sorry, no more.");
				}
					else if(Integer.parseInt(choice) == 2){//BubbleSortCards(currentTurn.hand);
					//reorder(currentTurn);
						
						pairs.clear();
						triplets.clear();
						quadruples.clear();
						processCards(currentTurn, pairs, triplets, quadruples);
						//BubbleSortCards(currentTurn.hand);
						printHand(currentTurn.name);}
					else if(Integer.parseInt(choice) == 3){
					
					pairs.clear();
					triplets.clear();
					quadruples.clear();
					
					/*currentTurn.hasFullHouse = 0;
					currentTurn.hasRoyalFlush = 0;
					currentTurn.hasStraight = 0;
					currentTurn.straightRank = 0;
					currentTurn.numberOfPairs = 0;
					currentTurn.pairRank = 0;
					currentTurn.numberOfTriplets = 0;
					currentTurn.tripletRank = 0;
					currentTurn.numberOfQuadruples = 0;
					currentTurn.quadrupleRank = 0;*/

					processCards(currentTurn, pairs, triplets, quadruples);
					//reorder(currentTurn);
					printHand(currentTurn.name); 
					turn++;}
				else if(Integer.parseInt(choice) == 4){printHighScores();}
				/*else if(Integer.parseInt(choice) == 5){  THIS Was going to be a way to restart the game, unfortunately 
				pairs.clear(); 				   we could not get it working in time
				triplets.clear();
				quadruples.clear();
				
				people.clear();
				makePerson(playername);
				piles.get(piles.size()-1).cards.clear();
				//piles.clear();
				for(int i = 2; i < 15; i++){
					makeCard("Club", i);
					makeCard("Hear", i);
					makeCard("Spad", i);
					makeCard("Diam", i);
				}
					getPlayerCount();}*/
				else if(Integer.parseInt(choice) == 5){System.out.println("So long, and remember - with linux, you WINUX!");
System.exit(0);	}	
				else System.out.println("Please choose an option that is programmed.");
			}
				else{System.out.println("Please be choosing a number from 1 - 5.");}
				}
				else System.out.println("I didn't quite understand that.");
			}
			else{ //AI player's turn
				//System.out.println("Not Your Turn");
				ArrayList<ArrayList<card>> pairs = new ArrayList<ArrayList<card>>();
				ArrayList<ArrayList<card>> triplets = new ArrayList<ArrayList<card>>();			
				ArrayList<ArrayList<card>> quadruples = new ArrayList<ArrayList<card>>();
				//pairs.clear();
				//triplets.clear();
				//quadruples.clear();
				//printHand(currentTurn.name);
				processCards(currentTurn, pairs, triplets, quadruples);
				makeDecisions(currentTurn);

				pairs.clear();
				triplets.clear();
				quadruples.clear();
					currentTurn.hasFullHouse = 0; //reset cards
					currentTurn.hasRoyalFlush = 0;
					currentTurn.hasStraight = 0;
					currentTurn.straightRank = 0;
					currentTurn.numberOfPairs = 0;
					currentTurn.pairRank = 0;
					currentTurn.numberOfTriplets = 0;
					currentTurn.tripletRank = 0;
					currentTurn.numberOfQuadruples = 0;
					currentTurn.quadrupleRank = 0;

				processCards(currentTurn, pairs, triplets, quadruples);

				

						/*find proper message to display!
						  Cards have been evaluated, and the person 
						  Object now has various int values
						  that allow us to determine how good of a hand he has*/
			
				if(currentTurn.hasRoyalFlush == 1 && currentTurn.hasAllSameSuit == 1) System.out.println(currentTurn.name + " has a royal flush!");
			else if(currentTurn.hasStraightFlush == 1) System.out.println(currentTurn.name + " has a straight flush!");
			else if(currentTurn.hasStraight == 1) System.out.println(currentTurn.name + " has a straight!");
			else if(currentTurn.hasFullHouse == 1)
				{
					if (currentTurn.fullHouseRank==14) System.out.println(currentTurn.name + " has a full house of rank Ace!");
					else System.out.println(currentTurn.name + " has a full house of rank " + currentTurn.fullHouseRank + "!");
				}
			else if(quadruples.size() > 0)
				{
					if (currentTurn.quadrupleRank==14) System.out.println(currentTurn.name + " has four Aces!");
					else System.out.println(currentTurn.name + "has four "+currentTurn.quadrupleRank+"s!");
				}
			else if(triplets.size() > 0 ) 
				{
					if (currentTurn.tripletRank==14) System.out.println(currentTurn.name + " has a triplet of Aces!");
					else System.out.println(currentTurn.name + " has a triplet of " + currentTurn.tripletRank + "s!");
				}
			else if(pairs.size() == 2)
				{
					if (currentTurn.pairRank==14) System.out.println(currentTurn.name + " has two pairs with a high rank of Aces!");
					else System.out.println(currentTurn.name + " has two pairs with a high rank of " + currentTurn.pairRank + "s!");
				}
			else if(pairs.size() == 1) 
				{
					if (currentTurn.pairRank==14) System.out.println(currentTurn.name + " has a pair of Aces!");
					else System.out.println(currentTurn.name + " has a pair of " + currentTurn.pairRank + "s!");
				}			
			//else if(currentTurn.hasAllSame == 1) System.out.println(currentTurn.name + " has a flush!");
		
			else System.out.println(currentTurn.name + " has nothing good.");
			
			printHand(currentTurn.name);
			
				turn++;
			}
		}
	}

	//ArrayList<card> cards = new ArrayList<card>();
	public void makePile(int n){
		cardPile pileOfCards = new cardPile();
		pileOfCards.pileNumber = n;
		pileOfCards.cards = new ArrayList<card>();
		piles.add(pileOfCards);
	}

	ArrayList<person> people = new ArrayList<person>();
	ArrayList<cardPile> piles = new ArrayList<cardPile>();	


	String playername = "";

	public static void main(String[] args){
		
	int running = 1; //our while loop contains all programtells us to stop we will keep running the game
	while(running == 1){ //until the player 
		game deck = new game(); //create new instance of game, its called the deck because
		deck.setScoreBoard();
		deck.makePile(0);	//the game originally was contained within the card class
		//deck.shuffleCards();
		System.out.println("Does your computer support unicode characters? (enter y/n for answer, or q to quit)");
			int badInput = 1;
			while(badInput == 1){
				Scanner readMe = new Scanner(System.in);
				String rex = readMe.nextLine();
				if(rex.equalsIgnoreCase("y") || rex.equalsIgnoreCase("yes")){
					deck.enableUnicode = 1;
					badInput = 0;
				}
				else if(rex.equalsIgnoreCase("n") || rex.equalsIgnoreCase("no")){
					badInput = 0;
				}
				else if(rex.equalsIgnoreCase("q") || rex.equalsIgnoreCase("quit")){
					System.out.println("See you later, and remember - with linux, you WINUX!");
					System.exit(0);
		
				}
				else if(rex.equalsIgnoreCase("p") || rex.equalsIgnoreCase("prompt")){
					System.out.println("Does your computer support unicode characters? (enter y/n for answer, or q to quit)");
				}
				else System.out.println("Please enter a valid answer.  If you cannot see the prompt anymore, press p");
			}
		
		int validname = 0; //keep us protected form crshes or bad input
		while(validname == 0){
			System.out.print("What is your name? (Enter 'q' or 'quit' to exit)");
			Scanner reader = new Scanner(System.in);
			deck.playername = reader.nextLine();

			if(deck.playername.equalsIgnoreCase("q") || deck.playername.equalsIgnoreCase("quit")){
				System.out.println("So long, and remember - with linux, you WINUX!");
				System.exit(0);
			}
			else if(deck.playername.equalsIgnoreCase("Colonel Hamberg") || deck.playername.equalsIgnoreCase("Rawhide Randall") || deck.playername.equalsIgnoreCase("Ol' Stinky Pete")){System.out.println("The name '" + deck.playername + "' is reserved.");}
			else validname = 1; //avoid confusion by forbidding player to name himself after AIs
		}
		System.out.println(" ");
		deck.piles.get(deck.piles.size()-1).cards.clear();
		for(int i = 2; i < 15; i++){	//make all 13 cards for each suit and add them to "deck" (cardpiles.last in our program)
			deck.makeCard("Club", i);
			deck.makeCard("Hear", i);
			deck.makeCard("Spad", i);
			deck.makeCard("Diam", i);
		}	
		deck.makePerson(deck.playername);
		deck.getPlayerCount();		
		int goodAnswer = 0;
		
		while(goodAnswer == 0){
			System.out.println("Play again? y/n"); //allow the user to play as many times as he or she wants to
		Scanner readText = new Scanner(System.in);
		String answer = readText.nextLine();

			if(answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")){
				deck.people.clear();
				deck.makePerson(deck.playername);
				deck.piles.get(deck.piles.size()-1).cards.clear();
				for(int i = 2; i < 15; i++){
					deck.makeCard("Club", i);
					deck.makeCard("Hear", i);
					deck.makeCard("Spad", i);
					deck.makeCard("Diam", i);
				}
				deck.getPlayerCount();
				//goodAnswer = 1;		
			}		
			else if(answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no")){
				System.out.println("So long, and remember - with linux, you WINUX!");
				System.exit(0);
			}
			else System.out.println("Beg your pardon, I didn't understand that.");

		}
	}	
		//Now to actually start the game
		//Each person has an index in the people array
		//This index is their turn number as well.
		//once everyone has had a turn, we will begin
		//the rounds again on whomever has folded
		//once somebody calls or all but one has folded
		//the rounds are over and a victor is decided
	}
}

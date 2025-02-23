/* HW1. Battle
 * This file contains two classes :
 * 		- Deck represents a pack of cards,
 * 		- Battle represents a battle game.
 */

import java.util.LinkedList;

class Deck { // represents a pack of cards


	LinkedList<Integer> cards;
	// The methods toString, hashCode, equals, and copy are used for
	// display and testing, you should not modify them.

	@Override
	public String toString() {
		return cards.toString();
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		Deck d = (Deck) o;
		return cards.equals(d.cards);
	}

	Deck copy() {
		Deck d = new Deck();
		for (Integer card : this.cards)
			d.cards.addLast(card);
		return d;
	}

	// constructor of an empty deck
	Deck() {
		cards = new LinkedList<Integer>();
	}

	// constructor from field
	Deck(LinkedList<Integer> cards) {
		this.cards = cards;
	}

	// constructor of a complete sorted deck of cards with nbVals values
	Deck(int nbVals) {
		cards = new LinkedList<Integer>();
		for (int j = 1; j <= nbVals; j++)
			for (int i = 0; i < 4; i++)
				cards.add(j);
	}

	// Question 1

	// takes a card from deck d to put it at the end of the current packet
	int pick(Deck d) {
		//	throw new Error("Method pick(Deck d) to complete (Question 1)");
		if (!d.cards.isEmpty()) {
			int x = d.cards.removeFirst();
			cards.addLast(x);
			return x;
		} else {
			return -1;
		}
	}

	// takes all the cards from deck d to put them at the end of the current deck
	void pickAll(Deck d) {
		//	throw new Error("Method pickAll(Deck d) to complete (Question 1)");
		while (!d.cards.isEmpty()) {
			pick(d);
		}
	}

	// checks if the current packet is valid
	boolean isValid(int nbVals) {
		//	throw new Error("Method isValid(int nbVals) to complete (Question 1)");
		int[] numbers = new int[nbVals];
		for (Integer x : cards) {
			if (x < 1 || x > nbVals || numbers	[x - 1] > 3)
				return false;
			numbers[x - 1]++;
		}
		return true;
	}

	// Question 2.1

	// chooses a position for the cut
	int cut() {
		int ans = 0;
		for(int i=0;i<cards.size();i++){
			if(Math.random() > 0.5){
				ans++;
			}
		}
		return ans;
	}

	// cuts the current packet in two at the position given by cut()
	Deck split() {
		int num = cut();
		Deck ans = new Deck();
		for(int i=0;i<num;i++){
			ans.cards.addLast(cards.removeFirst());
		}
		return ans;
	}

	// Question 2.2

	// mixes the current deck and the deck d
	void riffleWith(Deck d) {
		Deck tmp = new Deck();
		while (!d.cards.isEmpty() && !this.cards.isEmpty()) {
			double rand = (double) (this.cards.size())/(d.cards.size()+this.cards.size());
			if(Math.random() < rand){
				tmp.cards.addLast(cards.removeFirst());
			}
			else {
				tmp.cards.addLast(d.cards.removeFirst());
			}
		}
		while(!d.cards.isEmpty()){
			tmp.cards.addLast(d.cards.removeFirst());
		}
		while(!this.cards.isEmpty()){
			tmp.cards.addLast(this.cards.removeFirst());
		}
		this.cards = tmp.cards;
	}

	// Question 2.3

	// shuffles the current deck using the riffle shuffle
	void riffleShuffle(int m) {
		for(int i=0;i<m;i++){
			riffleWith(split());
		}
	}
}

class Battle { // represents a battle game

	Deck player1;
	Deck player2;
	Deck trick;
	boolean turn = true;

	// constructor of a battle without cards
	Battle() {
		player1 = new Deck();
		player2 = new Deck();
		trick = new Deck();
	}

	// constructor from fields
	Battle(Deck player1, Deck player2, Deck trick) {
		this.player1 = player1;
		this.player2 = player2;
		this.trick = trick;
	}

	// copy the battle
	Battle copy() {
		Battle r = new Battle();
		r.player1 = this.player1.copy();
		r.player2 = this.player2.copy();
		r.trick = this.trick.copy();
		return r;
	}

	// string representing the battle
	@Override
	public String toString() {
		return "Player 1 : " + player1.toString() + "\n" + "Player 2 : " + player2.toString() + "\nPli " + trick.toString();
	}

	// Question 3.1

	// constructor of a battle with a deck of cards of nbVals values
	Battle(int nbVals) {
		player1 = new Deck();
		player2 = new Deck();
		trick = new Deck();
		Deck tmp = new Deck(nbVals);
		tmp.riffleShuffle(7);
		while (!tmp.cards.isEmpty()){
			player1.pick(tmp);
			player2.pick(tmp);
		}
	}

	// Question 3.2

	// test if the game is over
	boolean isOver() {
		return player1.cards.isEmpty() ||  player2.cards.isEmpty();
	}

	// effectue un tour de jeu

	boolean oneRound(){
		return oneRound(true);
	}

	boolean oneRound(boolean disableturn){
		if (isOver()) {
			return false;
		}

		int card1 = player1.cards.removeFirst();
		int card2 = player2.cards.removeFirst();

		if(turn || disableturn){
			trick.cards.add(card1);
			trick.cards.add(card2);
		}
		else {
			trick.cards.add(card2);
			trick.cards.add(card1);
		}

		turn = !turn;

		if (card1 > card2) {
			player1.pickAll(trick);
			return true;
		} else if (card1 < card2) {
			player2.pickAll(trick);
			return true;
		} else {
			if (isOver()) {
				return false;
			}

			card1 = player1.cards.removeFirst();
			card2 = player2.cards.removeFirst();

			if(turn || disableturn){
				trick.cards.add(card1);
				trick.cards.add(card2);
			}
			else {
				trick.cards.add(card2);
				trick.cards.add(card1);
			}

			turn = !turn;

			return oneRound(disableturn);
		}
	}


	// Question 3.3

	// returns the winner
	int winner() {
		if(player1.cards.size() > player2.cards.size()){
			return 1;
		}
		else if(player2.cards.size() > player1.cards.size()){
			return 2;
		}
		else {
			return 0;
		}
	}

	int game(){
		return game(true);
	}



	// plays a game with a fixed maximum number of moves
	int game(int turns) {
		for(int i=0;i<turns;i++){
			oneRound();
		}
		return winner();
	}

	// Question 4.1

	// plays a game without limit of moves, but with detection of infinite games
	int game(boolean disableturn) {
		Battle gay = this.copy();
		Battle that = this;
		while(!that.isOver()){
			gay.oneRound(disableturn);
			that.oneRound(disableturn);
			if(that.isOver()){
				break;
			}
			that.oneRound(disableturn);
			if(that.toString().equals(gay.toString())){
				return 3;
			}
		}
		return that.winner();
	}

	// Question 4.2

	// performs statistics on the number of infinite games
	static void stats(int nbVals, int nbGames) {
		int[] ans = new int[4];
		for(int i=0;i<nbGames;i++){
			ans[new Battle(nbVals).game(false)]++;
		}
		System.out.println("Draws: " + ans[0]);
		System.out.println("Player 1 wins: " + ans[1]);
		System.out.println("Player 2 wins: " + ans[2]);
		System.out.println("Infinite games: " + ans[3]);
	}
}

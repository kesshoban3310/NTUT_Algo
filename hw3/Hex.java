/* The Hex game
   https://en.wikipedia.org/wiki/Hex_(board_game)
   desigened by Jean-Christophe Filliâtre

   grid size : n*n

   playable cells : (i,j) with 1 <= i, j <= n

   blue edges (left and right) : i=0 or i=n+1, 1 <= j <= n
    red edges (top and bottom) : 1 <= i <= n, j=0 or j=n+1

      note: the four corners have no color

   adjacence :      i,j-1   i+1,j-1

                 i-1,j    i,j   i+1,j

                    i-1,j+1    i,j+1

*/

import java.util.Vector;

public class Hex {


  enum Player {
    NOONE, BLUE, RED
  }



  Vector<Vector<Player>> a;
  Vector<Vector<Integer>> la;
  Vector<Integer> par = new Vector<Integer>( );
  Vector<Integer> rk = new Vector<Integer>( );



  Player p = Player.RED;
  int n,node;

  // create an empty board of size n*n
  Hex(int n) {
    this.n = n;
    node = n + (n+2)*(n-1) + (n+3);
    a = new Vector<Vector<Player>>(n+2);
    la = new Vector<Vector<Integer>>(n+2);
    for (int i = 0; i < n+2; i++) {
      Vector<Player> x = new Vector<Player>(n+2);
      Vector<Integer> le = new Vector<Integer>(n+2);
      for(int j = 0; j < n+2; j++){
        if(i == 0 || i == n+1){
          x.add(Player.BLUE);
        }
        else if(j == 0 || j == n+1){
          x.add(Player.RED);
        }
        else {
          x.add(Player.NOONE);
        }
        le.add( i + (n+2)*(j-1) + (n+3) );
      }
      a.add(x);
      la.add(le);
    }
    for(int i=0;i<node;i++){
      par.add(i);
      rk.add(i);
    }
  }

  // return the color of cell i,j
  Player get(int i, int j) {
    return a.get(i).get(j);
  }
  private boolean chk(int i, int j,Player c) {
    return a.get(i).get(j-1).equals(c) ||
            a.get(i+1).get(j-1).equals(c) ||
            a.get(i-1).get(j).equals(c) ||
            a.get(i+1).get(j).equals(c) ||
            a.get(i-1).get(j+1).equals(c) ||
            a.get(i).get(j+1).equals(c);
  }
  private void Switch(){
    if(p == Player.RED){
      p = Player.BLUE;
    }
    else if(p == Player.BLUE){
      p = Player.RED;
    }
  }
  // update the board after the player with the trait plays the cell (i, j).
  // Does nothing if the move is illegal.
  // Returns true if and only if the move is legal.
  boolean click(int i, int j) {
    boolean ans = false;
    if(a.get(i).get(j) != Player.NOONE) {
      Switch();
      return ans;
    }
    if(p == Player.RED){
      if((j == 1 || j == 11) && a.get(i).get(j) != Player.BLUE){
        a.get(i).set(j, Player.RED);
        ans = true;
      }
      if(chk(i,j,Player.RED)){
        a.get(i).set(j, Player.RED);
        ans = true;
      }
    }
    else if(p == Player.BLUE){
      if((i == 1 || i == 11) && a.get(i).get(j) != Player.RED){
        a.get(i).set(j, Player.BLUE);
        ans = true;
      }
      if(chk(i,j,Player.BLUE)){
        a.get(i).set(j, Player.BLUE);
        ans = true;
      }
    }
    Switch();
    return ans;
  }

  // return the player with the trait or Player.NOONE if the game is over
  // because of a player's victory.
  Player currentPlayer() {
    return p;
  }


  // return the winning player, or Player.NOONE if no player has won yet
  Player winner() {
    return Player.NOONE;
  }

  int label(int i, int j) {
    return la.get(i).get(j);
  }


  public static void main(String[] args) {
    HexGUI.createAndShowGUI();
  }
}

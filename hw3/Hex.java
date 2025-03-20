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

class Pair<A, B> {
  public final A first;
  public final B second;

  public Pair(A first, B second) {
    this.first = first;
    this.second = second;
  }
}

public class Hex {


  enum Player {
    NOONE, BLUE, RED
  }



  Vector<Vector<Player>> a;
  Vector<Integer> par = new Vector<Integer>( );
  Vector<Integer> rk = new Vector<Integer>( );



  Player p = Player.RED;
  Player winner = Player.NOONE;
  int n,node;

  private int find(int x) {
    if (par.get(x) != x) {
      par.set(x, find(par.get(x)));
    }
    return par.get(x);
  }
  private void union(int x, int y) {
    int fx = find(x);
    int fy = find(y);

    if (fx == fy) return; // 已經在同一集合

    // 根據 rank 合併
    if (rk.get(fx) < rk.get(fy)) {
      par.set(fx, fy);
    } else {
      par.set(fy, fx);
      if (rk.get(fx).equals(rk.get(fy))) {
        rk.set(fx, rk.get(fx) + 1);
      }
    }
  }


  // create an empty board of size n*n
  Hex(int n) {
    this.n = n;
    node = (n+2)*(n*2) ;

    a = new Vector<Vector<Player>>(n+2);
    for (int i = 0; i < n+2; i++) {
      Vector<Player> x = new Vector<Player>(n+2);
      Vector<Integer> le = new Vector<Integer>(n+2);
      for(int j = 0; j < n+2; j++){
        if((i == 0 || i == n+1) && (1<=j && j<=n) ){
          x.add(Player.BLUE);
        }
        else if((j == 0 || j == n+1)&& (1<=i && i<=n) ){
          x.add(Player.RED);
        }
        else {
          x.add(Player.NOONE);
        }
        le.add( i + (n+2)*(j-1) + (n+3) );
      }
      a.add(x);
    }
    for(int i=0;i<=node;i++){
      par.add(i);
      rk.add(0);
    }


    for (int j = 1; j < n; j++) {
      union(label(0, j), label(0, j+1));
    }

    for (int j = 1; j < n; j++) {
      union(label(n+1, j), label(n+1, j+1));
    }

    for (int i = 1; i < n; i++) {
      union(label(i, 0), label(i+1, 0));
    }

    for (int i = 1; i < n; i++) {
      union(label(i, n+1), label(i+1, n+1));
    }

  }

  // return the color of cell i,j
  Player get(int i, int j) {
    return a.get(i).get(j);
  }
  private void chk(int i, int j) {
    // 六個方向偏移量

    a.get(i).set(j,p);

    int[] dx = {0, 1, -1, 1, -1, 0};
    int[] dy = {-1, -1, 0, 0, 1, 1};

    Pair<Boolean, Pair<Integer, Integer>> ans = new Pair<>(false, new Pair<>(-1, -1));

    for (int k = 0; k < 6; k++) {
      int ni = i + dx[k];
      int nj = j + dy[k];
      if (a.get(ni).get(nj).equals(p)) {
        ans = new Pair<>(true, new Pair<>(ni, nj));

        union( label(i ,j ), label(ni, nj) );

      }
    }

    // 沒找到
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
    if(a.get(i).get(j) != Player.NOONE) {
      Switch();
      return false;
    }

    chk(i, j);

    Switch();
    return true;
  }

  // return the player with the trait or Player.NOONE if the game is over
  // because of a player's victory.
  Player currentPlayer() {
    if(winner != Player.NOONE){
      return Player.NOONE;
    }
    return p;
  }


  // return the winning player, or Player.NOONE if no player has won yet
  Player winner() {

    if(winner != Player.NOONE){
      return winner;
    }

    if (find(label(1,0)) == find(label(1,n+1))) {
      winner = Player.RED;
    } else if (find(label(0,1)) == find(label(n+1,1))) {
      winner = Player.BLUE;
    }
    return winner;
  }

  int label(int i, int j) {
    return par.get(i + (n+2)*(j-1) + (n+2));
  }


  public static void main(String[] args) {
    HexGUI.createAndShowGUI();
  }
}

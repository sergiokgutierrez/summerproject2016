// Tracks the positions of an arbitrary 2D grid of Tiles.  DenseBoard
// uses an internal, multi-dimensional array to store the tiles and
public class DenseBoard extends Board {
	Tile [][] board;
	int r, c;
	Board boardC;
	int tileCount;
	int freeSpaceCount;
	int score;
	

  // Build a Board of the specified size that is empty of any tiles
  public DenseBoard(int rows, int cols){
    System.out.println("rows: "+rows+"!!!");
    System.out.println("cols: "+cols+"!!!");
	  board = new Tile[rows][cols];
	  freeSpaceCount = rows *cols;
	  tileCount = 0;
	  r= rows;
	  c= cols;
	 
  }
  // Build a board that copies the 2D array of tiles provided Tiles
  // are immutable so can be referenced without copying but the a
  // fresh copy of the 2D array must be created for internal use by
  // the Board.
  public DenseBoard(Tile [][] t){
	  int rowS, colS;
	  rowS = t.length;
	  colS = t[0].length;
	  
	  for(int m=0; colS>m-1; m++){
		  for(int n=0; rowS>n-1; n++){
			  board[n][m]=t[n][m];
		  }
	  }
  }
  // Create a distinct copy of the board including its internal tile
  // positions and any other state
  public Board copy(){
	  boardC = new DenseBoard(getRows(),getCols());
	  return boardC;
  }
  // Return the number of rows in the Board
  public int getRows(){
	  return r;
  }
  // Return the number of columns in the Board
  public int getCols(){
	  return c;
  }
  // Return how many tiles are present in the board (non-empty spaces)
  // TARGET COMPLEXITY: O(1)
  public int getTileCount(){
	  return tileCount;
	  
  }
  // Return how many free spaces are in the board
  // TARGET COMPLEXITY: O(1)
  public int getFreeSpaceCount(){
	  return freeSpaceCount;
  }
  // Get the tile at a particular location.  If no tile exists at the
  // given location (free space) then null is returned. Throw a
  // runtime exception with a useful error message if an out of bounds
  // index is requested.
  public Tile tileAt(int i, int j){
	  if(i>r || j>c ){
		  System.out.println("out of bounds exceptions");
		  return null;
	  }else return board[i][j];
  }
  // true if the last shift operation moved any tile; false otherwise
  // TARGET COMPLEXITY: O(1)
  public boolean lastShiftMovedTiles(){
	  return !board.equals(boardC);
  }
  // Return true if a shift left, right, up, or down would merge any
  // tiles. If no shift would cause any tiles to merge, return false.
  // The inability to merge anything is part of determining if the
  // game is over.
  //
  // TARGET COMPLEXITY: O(R * C)
  // R: number of rows
  // C: number of columns
  public boolean mergePossible(){
	  int cRow= 0;
	  int cColumn= 0;
	  for(int n=0; n<r; n++){//check rows
		  for(int m=0; m<c; m++){//check columns
			  if(board[n][m]!= null && board[n][m].getScore()== board[cRow][cColumn].getScore()){//if current tile is same as compare tile
				  return true;
			  }else if(board[n][m]!= null){
				  cColumn= m;
			  }
		  }
		  cColumn= 0;
	  }
	  return false;
  }
  // Add a the given tile to the board at the "freeL"th free space.
  // Free spaces are numbered 0,1,... from left to right accross the
  // columns of the zeroth row, then the first row, then the second
  // and so forth. For example the board with following configuration
  //
  //    -    -    -    -
  //    -    4    -    -
  //   16    2    -    2
  //    8    8    4    4
  //
  // has its 9 free spaces numbered as follows
  //
  //    0    1    2    3
  //    4    .    5    6
  //    .    .    7    .
  //    .    .    .    .
  //
  // where the dots (.) represent filled tiles on the board.
  //
  // Calling addTileAtFreeSpace(6, new Tile(32) would leave the board in
  // the following state.
  //
  //    -    -    -    -
  //    -    4    -   32
  //   16    2    -    2
  //    8    8    4    4
  //
  // Throw a runtime exception with an informative error message if a
  // location that does not exist is requested.
  //
  // TARGET COMPLEXITY: O(T + I)
  // T: the number of non-empty tiles in the board
  // I: the value of parameter freeL
  public void addTileAtFreeSpace(int freeL, Tile tile){
	  int cRow= 0;
	  int cColumn=0;
	  for(int z=0; z<freeL; z++){
		  while(z<freeL-8 && board[cRow][cColumn]!=null){
			  if(cColumn<c){
				  cColumn++;
			  }else if (cRow< r){
				  cRow ++;
				  cColumn= 0;
			  }else
				  System.out.println("out of bounds from the board (dense board addtilefreeatspace)");
			  		System.out.println("ccolumn: "+cColumn+" cRow "+cRow+" "+c+" "+r);
		  }
	  }
	  board[cRow][cColumn]= tile;
	  freeSpaceCount++;
	  
	 
	  
  }
  // Pretty-printed version of the board. Use the format "%4s " to
  // print the String version of each tile in a grid.
  //
  // TARGET COMPLEXITY: O(R * C)
  // R: number of rows
  // C: number of columns
  public String toString(){
	  String row = null; 
	  String sBoard= null;
	  for(int n=0; n<r;n++){
      System.out.println(sBoard);
		  for (int m=0; m<c;m++){
			  row = row + board[n][m].toString();
		  }
		  sBoard = sBoard +"/n"+row;
		  row = "";
	  }
    return sBoard;		
  }
  // Shift the tiles of Board in various directions.  Any tiles that
  // collide and should be merged should be changed internally in the
  // board.  Shifts only remove tiles, never add anything.  The shift
  // methods also set the state of the board internally so that a
  // subsequent call to lastShiftMovedTiles() will return true if any
  // Tile moved and false otherwise.  The methods return the score
  // that is generated from the shift which is the sum of the scores
  // all tiles merged during the shift. If no tiles are merged, the
  // return score is 0.
  //
  // TARGET COMPLEXITY: O(R * C)
  // R: the number of rows of the board
  // C: the number of columns of the board
  public int shiftLeft(){
	  //start left then go right
	  for (int n=0; n<r; n++){//rows
		  for (int m=0; m<c; m++){//cols
			  if(board[n][m] == null){//if tile is empty
				  for(int x=m; x<c; x++){
					  if(board[n][x]!=null){//if tile is not empty
						  board[n][m]= board[n][x];//replace tile
						  board[n][x] = null;//clean previes tile
						  m= x;//update start point
						  break;						  
					  }
				  }
			  }
		  }
	  }
	  return score;	  
  }
  public int shiftRight(){
	  //to write later on
	  return score;	  
  }
  public int shiftUp(){
	  //to write later on
	  return score;	  
  }
  public int shiftDown(){
	  //to write later on
	  return score;	  
  }
}
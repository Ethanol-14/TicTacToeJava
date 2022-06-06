import java.util.Scanner;

public class MyClass {
	public static String subscript(int number) {
		String[] subs = "¹ ² ³ ⁴ ⁵ ⁶ ⁷ ⁸ ⁹".split(" ");
		return subs[number];
	}
	
	public static void printBoard(String[] board) {
		for (int x = 0; x < 9; x++) {
			if (board[x].equals("")) {
				System.out.print(subscript(x));
			}
			else {
				System.out.print(board[x]);
			}
			if (x != 2 && x != 5 && x != 8) {
				System.out.print("|");
			}
			if (x == 2 || x == 5) {
				System.out.println();
				System.out.println("─|─|─");
			}
		}
		System.out.println();
		System.out.println();
	}
	
	public static String[] getRow(String[] board, int _row) {
		int row = _row*3;
		String[] output = {board[row], board[row+1], board[row+2]};
		return output;
	}
	
	public static String[] getCol(String[] board, int col) {
		String[] output = {board[col], board[col+3], board[col+6]};
		return output;
	}
	
	public static String[] getDia(String[] board, int dia) {
		if (dia == 1) {
			String[] output = {board[6], board[4], board[2]};
			return output;
		}
		else {
			String[] output = {board[0], board[4], board[8]};
			return output;
		}
	}
	
	public static boolean isWinner(String[] line) {
		if (line[0].equals(line[1]) && line[1].equals(line[2])) {
			if (line[0].equals("X") || line[0].equals("O")) {
				return true;
			}
		}
		return false;
	}
	
	public static String determineWinner(String[] board) {
		for (int x = 0; x < 3; x++) {
			if (isWinner(getRow(board, x))) {
				return getRow(board, x)[0];
			}
			if (isWinner(getCol(board, x))) {
				return getCol(board, x)[0];
			}
		}
		if (isWinner(getDia(board, 1))) {
			return getDia(board, 1)[0];
		}
		if (isWinner(getDia(board, 0))) {
			return getDia(board, 1)[0];
		}
		return "";
	}
	
	public static int whoWonAlgo(String[] board) {
		for (int x = 0; x < 3; x++) {
			if (isWinner(getRow(board, x))) {
				if (getRow(board, x)[0].equals("X")) {
					return 1;
				}
				else {
					return -1;
				}
			}
			if (isWinner(getCol(board, x))) {
				if (getCol(board, x)[0].equals("X")) {
					return 1;
				}
				else {
					return -1;
				}
			}
		}
		if (isWinner(getDia(board, 1))) {
			if (getDia(board, 1)[0].equals("X")) {
				return 1;
			}
			else {
				return -1;
			}
		}
		if (isWinner(getDia(board, 0))) {
			if (getDia(board, 0)[0].equals("X")) {
				return 1;
			}
			else {
				return -1;
			}
		}
		for (int x = 0; x < 9; x++) {
			if (board[x].equals("")) {
				return 2;
			}
		}
		return 0;
	}
	
	public static int getValidInput(String[] board) {
		int userInput = 0;
		String temp = "";
		boolean isNum = false;
		Scanner IO = new Scanner(System.in);

		while ((userInput < 1 || userInput > 9) || !board[userInput-1].equals("")) {
			System.out.println("Enter a valid tile number from 1 to 9");
			temp = IO.nextLine();
			try {
				Integer.parseInt(temp);
				isNum = true;
			}
			catch(NumberFormatException nfe) {  
			    isNum = false;
			}
			if (isNum) {
				userInput = Integer.valueOf(temp);
			}
		}
		//IO.close();
		return userInput-1;
	}
	
	public static int findBestMove(String[] board, boolean playingAsX) {
		int bestMove = -1;
		int evaluation = 0;
		
		if (playingAsX) {
			int bestScore = -2;
			for (int x = 0; x < 9; x++) {
				if (board[x].equals("")) {
					board[x] = "X";
					evaluation = minimax(board, 0, false);
					board[x] = "";
					if (evaluation > bestScore) {
						bestScore = evaluation;
						bestMove = x;
					}
				}
			}
			return bestMove;
		}
		else {
			int bestScore = 2;
			for (int x = 0; x < 9; x++) {
				if (board[x].equals("")) {
					board[x] = "O";
					evaluation = minimax(board, 0, true);
					board[x] = "";
					if (evaluation < bestScore) {
						bestScore = evaluation;
						bestMove = x;
					}
				}
			}
			return bestMove;
		}
	}
	
	public static int minimax(String[] board, int depth, boolean isMaxing) {
		int result = whoWonAlgo(board);
		int evaluation = 0;
		
		if (result != 2) {
			return result;
		}
		
		if (isMaxing) {
			int bestScore = -1;
			for (int x = 0; x < 9; x++) {
				if (board[x].equals("")) {
					board[x] = "X";
					evaluation = minimax(board, depth+1, false);
					board[x] = "";
					if (evaluation > bestScore) {
						bestScore = evaluation;
					}
				}
			}
			return bestScore;
		}
		else {
			int bestScore = 1;
			for (int x = 0; x < 9; x++) {
				if (board[x].equals("")) {
					board[x] = "O";
					evaluation = minimax(board, depth+1, true);
					board[x] = "";
					if (evaluation < bestScore) {
						bestScore = evaluation;
					}
				}
			}
			return bestScore;
		}
	}
	
	public static void main(String[] args) {
		boolean algoIsX = true;
		int move = 0;
		int count = 0;
		String win = "";
		boolean turn = true;
		
		while (true) {
			String[] board = {"", "", "", "", "", "", "", "", ""};
			
			turn = true;
			algoIsX = !algoIsX;
			count = 1;
			while (true) {
				printBoard(board);
				//System.out.println(turn);
				if (turn) {
					System.out.println("Player X's turn");
					if (algoIsX) {
						move = findBestMove(board, true);
					}
					else {
						//System.out.println("player input");
						move = getValidInput(board);
					}
				}
				else {
					System.out.println("Player O's turn");
					if (algoIsX) {
						move = getValidInput(board);
					}
					else {
						move = findBestMove(board, false);
					}
				}
				
				if (turn) {
					//System.out.println(move);
					board[move] = "X";
				}
				else {
					board[move] = "O";
				}
				
				win = determineWinner(board);
				if (win.equals("X") || win.equals("O")) {
					printBoard(board);
					System.out.println("Player "+win+" won!");
					break;
				}
				
				if (count == 9) {
					printBoard(board);
					System.out.println("The players drew.");
					break;
				}
				
				count += 1;
				turn = !turn;
			}
			System.out.println();
		}
	}
}

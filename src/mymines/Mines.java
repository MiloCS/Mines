package mymines;

import java.util.Random;
import java.util.Scanner;

public class Mines {
	public static int SIZE;
	String[][] mines = new String[SIZE][SIZE];
	boolean[][] visible = new boolean[SIZE][SIZE];
	boolean[][] marked = new boolean[SIZE][SIZE];
	boolean isOver = false;
	
	public Mines() {
		String[] choices = {" ", " ", " ", " ", " ", " ", " ", "*"};
		Random r = new Random();
		for (String[] array : mines) {
			for (int i = 0; i < array.length; i++) {
				int x = r.nextInt(choices.length);
				array[i] = choices[x];
			}
		}
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < mines[0].length; j++) {
				if (!isMine(i, j)) {
					mines[i][j] = numPeers(i, j) == 0 ? "0" : numPeers(i, j) + "";
				}
			}
		}
	}
	
	public int numPeers(int r, int c) {
		int x = 0;
		x += testPeer(r-1,c-1) ? 1 : 0;
		x += testPeer(r,c-1) ? 1 : 0;
		x += testPeer(r+1,c-1) ? 1 : 0;
		x += testPeer(r-1,c) ? 1 : 0;
		x += testPeer(r+1,c) ? 1 : 0;
		x += testPeer(r-1,c+1) ? 1 : 0;
		x += testPeer(r,c+1) ? 1 : 0;
		x += testPeer(r+1,c+1) ? 1 : 0;
		return x;
	}
	
	public String reveal(int r, int c) {
		if (!(r < 0 || r > SIZE - 1 || c < 0 || c > SIZE - 1)) {
			if (isMine(r, c)) {
				visible[r][c] = true;
				isOver = true;
				return "Game Over. :(";
			}
			else if (mines[r][c].equals("0")) {
				revealZero(r, c, new boolean[SIZE][SIZE]);
				return "Successful reveal!";
			}
			else {
				visible[r][c] = true;
				return "Successful reveal!";
			}
		}
		else {
			return "Failed! off board";
		}
	}
	
	private void revealZero(int r, int c, boolean[][] seen) {
		if ((r >= 0 && r < SIZE && c >= 0 && c < SIZE) && !seen[r][c]) {
			seen[r][c] = true;
			visible[r][c] = true;
			if (mines[r][c].equals("0")) {
				revealZero(r+1, c, seen);
				revealZero(r-1, c, seen);
				revealZero(r, c+1, seen);
				revealZero(r, c-1, seen);
			}
		}
	}
	
	public String mark(int r, int c) {
		marked[r][c] = true;
		return "Successful marking!";
	}
	
	private boolean isMine(int r, int c) {
		return mines[r][c].equals("*");
	}
	
	private boolean testPeer(int r, int c) {
		if (r < 0 || r > SIZE - 1 || c < 0 || c > SIZE - 1) {
			return false;
		}
		else {
			return isMine(r, c);
		}
	}
	
	public void printMines() {
		System.out.print("       Columns");
		
		for (int q = 0; q < 3; q++) {
		System.out.print("\n       ");
			for (int k = 0; k < SIZE; k++) {
				System.out.print(q == 0 ? k : q == 1 ? "|" : "v");
			}	
		}
		System.out.print("\nRows\n");
		for (int i = 0; i < SIZE; i++) {
			String space = i + " ->   ";
			System.out.print(space);
			for (int a = 0; a < SIZE; a++) {
				boolean b = visible[i][a];
				if (b) {System.out.print(mines[i][a]);} else if (marked[i][a]){System.out.print("F");} else {System.out.print("X");}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public boolean isWon() {
		for (int i = 0; i < SIZE; i++) {
			for (int a = 0; a < SIZE; a++) {
				if (marked[i][a]) {
					if (!isMine(i, a)) {return false;}
				}
				if (isMine(i, a)) {
					if (!marked[i][a]) {return false;}
				}
			}
		}
		isOver = true;
		return true;
	}
	
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		System.out.print("**MINESWEEPER**\n\nPlease select size of square board (greater than 0):\n>>> ");
		SIZE = Integer.parseInt(console.nextLine());
		Mines m = new Mines();
		m.printMines();
		if (m.isWon() || m.isOver) {
			System.out.println("Game is Over");
			return;
		}
		String command = "";
		while (!command.equals("quit")) {
			System.out.print("Options: reveal mark quit\n>>> ");
			command = console.nextLine();
			if (command.equals("reveal")) {
				System.out.print("Specify location to reveal with row and column (0 indexed):\nRow?\n>>> ");
				int row = Integer.parseInt(console.nextLine());
				System.out.print("Column?\n>>> ");
				int col = Integer.parseInt(console.nextLine());
				m.reveal(row, col);
				m.printMines();
				boolean won = m.isWon();
				if (won || m.isOver) {
					System.out.println(won ? "You won!" : "You lost.");
					command = "quit";
				}
			}
			else if (command.equals("mark")) {
				System.out.print("Specify location to mark as bomb with row and column (0 indexed):\nRow?\n>>> ");
				int row = Integer.parseInt(console.nextLine());
				System.out.print("Column?\n>>>");
				int col = Integer.parseInt(console.nextLine());
				m.mark(row, col);
				m.printMines();
				boolean won = m.isWon();
				if (won || m.isOver) {
					System.out.println(won ? "You won!" : "You lost.");
					command = "quit";
				}

			}
			else if (!command.equals("quit")) {
				System.out.print("Unrecognized command.\n");
			}
		}
		System.out.println("Game is Over");
	}
}

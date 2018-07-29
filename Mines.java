package mymines;

import java.util.Random;
import java.util.Scanner;

public class Mines {
	public final int SIZE = 5;
	String[][] mines = new String[SIZE][SIZE];
	boolean[][] visible = new boolean[SIZE][SIZE];
	boolean[][] marked = new boolean[SIZE][SIZE];
	boolean isOver = false;
	
	public Mines() {
		String[] choices = {" ", " ", " ", " ", " ", " ", " ", " ", " ", "*"};
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
					mines[i][j] = numPeers(i, j) == 0 ? "" : numPeers(i, j) + "";
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
				revealZero(r, c);
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
	
	private void revealZero(int r, int c) {
		visible[r][c] = true;
		reveal(r+1, c);
		reveal(r-1, c);
		reveal(r, c+1);
		reveal(r, c-1);
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
		for (int i = 0; i < SIZE; i++) {
			for (int a = 0; a < SIZE; a++) {
				boolean b = visible[i][a];
				if (b) {System.out.print(mines[i][a]);} else {System.out.print("X");}
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		Mines m = new Mines();
		Scanner console = new Scanner(System.in);
		String command = console.nextLine();
		while (!command.equals("quit")) {
			if (command.equals("reveal")) {
				System.out.print("row?");
				int row = Integer.parseInt(console.nextLine());
				System.out.print("col?");
				int col = Integer.parseInt(console.nextLine());
				m.reveal(row, col);
				m.printMines();
				command = console.nextLine();
			}
			else if (command.equals("mark")) {
				System.out.print("row?");
				int row = Integer.parseInt(console.nextLine());
				System.out.print("col?");
				int col = Integer.parseInt(console.nextLine());
				m.mark(row, col);
				m.printMines();
				command = console.nextLine();
			}
			else {
				command = console.nextLine();
			}
		}
	}
}

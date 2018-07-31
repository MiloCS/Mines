package mymines;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MinesGUI implements ActionListener {
	Mines ref;
	int s;
	boolean revmode;
	JButton[][] buttonArray;
	JButton switchmode;
	String switchtext;
	JFrame gameFrame;
	
	public MinesGUI () {
		ref = new Mines();
		s = ref.SIZE;
		revmode = true;
		buttonArray = new JButton[s][s];
		switchmode = new JButton();
		switchtext = "Reveal";
		switchmode.setText(switchtext);
		switchmode.addActionListener(this);
		for (int i=0; i<s; i++)
		{
			for (int j=0; j<s; j++)
			{
				buttonArray[i][j] = new JButton();
				buttonArray[i][j].addActionListener(this);
				buttonArray[i][j].setText("");
			}
		}
		gameFrame = new JFrame();
		gameFrame.getContentPane().setLayout(new BorderLayout());
		gameFrame.add(switchmode, BorderLayout.SOUTH);
		JPanel grid = new JPanel(new GridLayout(s, s));
		for(int i=0; i<s; i++)
		{
			for (int j=0; j<s; j++)
			{
				grid.add(buttonArray[i][j]);
			}
		}
		gameFrame.add(grid, BorderLayout.CENTER);
		gameFrame.setSize(600, 600);
		gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void display() {
		gameFrame.setVisible(true);
	}
	
	public void update() {
		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				buttonArray[i][j].setText(ref.visible[i][j] ? ref.mines[i][j] : (ref.marked[i][j] ? "F" : " "));
			}
		}
		if (ref.isOver || ref.isWon()) {
			if (ref.isWon()) {
				switchtext = "You Won!";
			}
			else {
				switchtext = "You Lost.";
			}
		}
		switchmode.setText(switchtext);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == switchmode) {
			System.out.println("mode switched");
			revmode = !revmode;
			switchtext = switchtext.equals("Reveal") ? "Mark" : "Reveal";
		}
		else {
			for (int i = 0; i < s; i++)
			{
				for (int j = 0; j < s; j++)
				{
					if (e.getSource() == buttonArray[i][j])
					{
						System.out.println("clicked: " + i + ", " + j);
						if (revmode)
						{
							ref.reveal(i, j);
						}
						else
						{
							ref.mark(i, j);
						}
					}
				}
			}
		}
		update();
	}
}

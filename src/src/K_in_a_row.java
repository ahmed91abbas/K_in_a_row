package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class K_in_a_row {
	private int k;
	private int players;
	private Node[][] field;
	private int turnCount;
	private int bluePoints;
	private int greenPoints;
	private int yellowPoints;
	
	public K_in_a_row(int k, int players) {
		this.k = k;
		this.players = players;
		turnCount = 0;
		bluePoints = 0;
		greenPoints = 0;
		yellowPoints = 0;
	}
	
	private JButton createButton(final int row, final int col) {
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(40, 40));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int playerID = turnCount % players;
				boolean succ = addNode(row, col, playerID);
				if (succ) {
					switch (playerID) {
						case 0: 
							button.setBackground(Color.BLUE);
							break;
						case 1:
							button.setBackground(Color.GREEN);
							break;
						case 2:
							button.setBackground(Color.YELLOW);
							break;
					}
					turnCount++;
					if (turnCount >= field.length * field[0].length) {
						if (bluePoints > greenPoints && bluePoints > yellowPoints) {
							System.out.println("\nThe winner is BLUE!");							
						} else if (greenPoints > bluePoints && greenPoints > yellowPoints) {
							System.out.println("The winner is GREEN!");
						} else if (yellowPoints > bluePoints && yellowPoints > greenPoints) {
							System.out.println("The winner is YELLOW!");
						} else {
							System.out.println("\nThe game finished with a tie!");
						}
						//System.out.println("The game has finshes after " + turnCount + " rounds.");
						for (int i = 0; i < players; i++) {
							switch (i) {
							case 0: System.out.println("Blue scored " + bluePoints + " points."); break;
							case 1: System.out.println("Green scored " + greenPoints + " points.");  break;
							case 2: System.out.println("Yellow scored " + yellowPoints + " points."); break;
							}									
						}
					}
				}
			}
        });
		return button;
	}
	
	public void createField(int rows, int columns) {
		field = new Node[rows][columns];
		JFrame frame = new JFrame("K Joint");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		GridLayout layout = new GridLayout(rows,columns);
		JPanel panel = new JPanel();
		panel.setLayout(layout);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				panel.add(createButton(i, j));
			}
		}
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void addPointTo(int playerID) {
		switch (playerID) {
		case 0: bluePoints++; break;
		case 1: greenPoints++; break;
		case 2: yellowPoints++; break;
		}
	}
	
	public boolean addNode(int x, int y, int playerID) {
		if (field[x][y] == null) {
			field[x][y] = new Node(playerID);
			checkNeighbors(field[x][y], x, y, playerID);
			return true;
		}
		return false;
	}
	
	/*checks the node neighbors and increment the degree of a direction if found another node
	 	terminate the program if k joint has been achieved*/
	private void checkNeighbors(Node node, int x, int y, int playerID) {
		//check left and right
		int col = y - 1; //left index
		for (int i = 0; i < 2; i++) {
			if (!outOfBounds(x, col)) {
				Node nieghbor = field[x][col];
				if (nieghbor != null && playerID == nieghbor.getPlayerID()) {
					node.incHorizantalBy(nieghbor.getHorizantal());
					nieghbor.incHorizantalBy(1);
					if (node.getHorizantal() >= k) {
						addPointTo(node.getPlayerID());
						System.out.println("Horizantal point for " + node.getPlayerName());
						break;
					}
				}
			}
			col = y + 1; //right index
		}
		//check up and down
		int row = x + 1; //down index
		for (int i = 0; i < 2; i++) {
			if (!outOfBounds(row, y)) {
				Node nieghbor = field[row][y];
				if (nieghbor != null && playerID == nieghbor.getPlayerID()) {
					node.incVerticalBy(nieghbor.getVertical());
					nieghbor.incVerticalBy(1);
					if (node.getVertical() >= k) {
						addPointTo(node.getPlayerID());
						System.out.println("vertical point for " + node.getPlayerName());
						break;
					}
				}
			}
			row = x - 1; //up index
		}
		//check left right diagonal
		row = x - 1; //up
		col = y + 1;
		for (int i = 0; i < 2; i++) {
			if (!outOfBounds(row, col)) {
				Node nieghbor = field[row][col];
				if (nieghbor != null && playerID == nieghbor.getPlayerID()) {
					node.incLRdiagonalBy(nieghbor.getLRdiagonal());
					nieghbor.incLRdiagonalBy(1);
					if (node.getLRdiagonal() >= k) {
						addPointTo(node.getPlayerID());
						System.out.println("diagonal point for " + node.getPlayerName());
						break;
					}
				}
			}
			row = x + 1; //down
			col = y - 1;
		}
		//check right left diagonal
		row = x - 1; //up
		col = y - 1;
		for (int i = 0; i < 2; i++) {
			if (!outOfBounds(row, col)) {
				Node nieghbor = field[row][col];
				if (nieghbor != null && playerID == nieghbor.getPlayerID()) {
					node.incRLdiagonalBy(nieghbor.getRLdiagonal());
					nieghbor.incRLdiagonalBy(1);
					if (node.getRLdiagonal() >= k) {
						addPointTo(node.getPlayerID());
						System.out.println("diagonal point for " + node.getPlayerName());
						break;
					}
				}
			}
			row = x + 1; //down
			col = y + 1;
		}
	}
	
	private boolean outOfBounds(int x, int y) {
		int rows = field.length - 1;
		int columns = field[0].length - 1;
		if (x < 0 || x > rows || y < 0 || y > columns)
			return true;
		return false;
	}

	public static void start() {
		JDialog dialog = new JDialog();
		dialog.setLocationRelativeTo(null);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setTitle("Start menu");
		dialog.setPreferredSize(new Dimension(400,330));
		JPanel[] panelList = new JPanel[4];
		Font font = new Font("Serif", Font.PLAIN, 30);
		for (int i = 0; i < panelList.length; i++) {
			panelList[i] = new JPanel();
			panelList[i].setBackground(Color.decode("#E6CFB8"));
		}
		JLabel kLabel = new JLabel("Choose K ");
		kLabel.setFont(font);
		panelList[0].add(kLabel);
		JTextField kJta = new JTextField();
		kJta.setFont(font);
		kJta.setPreferredSize(new Dimension(90,50));
		panelList[0].add(kJta);
		JLabel boardLabel = new JLabel("Board size ");
		boardLabel.setFont(font);
		JTextField xJta = new JTextField();
		xJta.setFont(font);
		xJta.setPreferredSize(new Dimension(70,50));
		JLabel xLabel = new JLabel("  x  ");
		xLabel.setFont(font);
		JTextField yJta = new JTextField();
		yJta.setFont(font);
		yJta.setPreferredSize(new Dimension(70,50));
		panelList[1].add(boardLabel);
		panelList[1].add(xJta);
		panelList[1].add(xLabel);
		panelList[1].add(yJta);
		JLabel playersLabel = new JLabel("Number of players ");
		playersLabel.setFont(font);
		Integer[] players = new Integer[] {1, 2, 3};
		JComboBox<Integer> box = new JComboBox<Integer>(players);
		box.setPreferredSize(new Dimension(70,50));
		box.setFont(font);
		panelList[2].add(playersLabel);
		panelList[2].add(box);
		JButton startButton = new JButton("Start");
		startButton.setPreferredSize(new Dimension(90, 50));
		startButton.setFont(font);
		startButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					dialog.dispose();
					K_in_a_row joint = new K_in_a_row(Integer.parseInt(kJta.getText()), (int)box.getSelectedItem());
					joint.createField(Integer.parseInt(xJta.getText()), Integer.parseInt(yJta.getText()));
				} catch (Exception e) {
					start();
				}
			}
			
		});
		panelList[3].add(startButton);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,1));
		for (int i = 0; i < panelList.length; i++)
			panel.add(panelList[i]);
		dialog.add(panel);
		dialog.pack();
		dialog.setVisible(true);
	}
	
	public static void main(String[] args) {
		start();
	}

}


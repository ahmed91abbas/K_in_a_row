package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class K_in_a_row {
	private int k;
	private Node[][] field;
	
	public K_in_a_row(int k) {
		this.k = k;
	}
	
	private JButton createButton(final int row, final int col) {
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(40, 40));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				addNode(row, col);
				button.setBackground(Color.BLUE);
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
	
	public void addNode(int x, int y) {
		if (field[x][y] == null) {
			field[x][y] = new Node();
			checkNeighbors(field[x][y], x, y);
		}
	}
	
	/*checks the node neighbors and increment the degree of a direction if found another node
	 	terminate the program if k joint has been achieved*/
	private void checkNeighbors(Node node, int x, int y) {
		//check left and right
		int col = y - 1; //left index
		for (int i = 0; i < 2; i++) {
			if (!outOfBounds(x, col)) {
				Node nieghbor = field[x][col];
				if (nieghbor != null) {
					node.incHorizantalBy(nieghbor.getHorizantal());
					nieghbor.incHorizantalBy(1);
					if (node.getHorizantal() >= k) {
						System.out.println("Horizantal win");
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
				if (nieghbor != null) {
					node.incVerticalBy(nieghbor.getVertical());
					nieghbor.incVerticalBy(1);
					if (node.getVertical() >= k) {
						System.out.println("vertical win");
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
				if (nieghbor != null) {
					node.incLRdiagonalBy(nieghbor.getLRdiagonal());
					nieghbor.incLRdiagonalBy(1);
					if (node.getLRdiagonal() >= k) {
						System.out.println("diagonal win");
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
				if (nieghbor != null) {
					node.incRLdiagonalBy(nieghbor.getRLdiagonal());
					nieghbor.incRLdiagonalBy(1);
					if (node.getRLdiagonal() >= k) {
						System.out.println("diagonal win");
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
		dialog.setPreferredSize(new Dimension(400,300));
		JPanel[] panelList = new JPanel[2];
		for (int i = 0; i < panelList.length; i++)
			panelList[i] = new JPanel();
		panelList[0].add(new JLabel("Welcome \n Choose"));
		panelList[1].add(new JLabel("Choose K"));
		JTextField jta = new JTextField();
		panelList[1].add(jta);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		for (int i = 0; i < panelList.length; i++)
			panel.add(panelList[i]);
		dialog.add(panel);
		dialog.pack();
		dialog.setVisible(true);
	}
	
	public void finish (String player, String winType) {
		
	}
	
	public static void main(String[] args) {
		int k = 3;
		int rows = 5;
		int columns = 5;
		K_in_a_row joint = new K_in_a_row(k);
		joint.createField(rows, columns);
	}

}


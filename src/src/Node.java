package src;

public class Node {
	private int horizantal;
	private int vertical;
	private int LRdiagonal;
	private int RLdiagonal;
	
	public Node() {
			horizantal = 1;
			vertical = 1;
			LRdiagonal = 1;
			RLdiagonal = 1;
		}
	
	public void incHorizantalBy(int x) {
		horizantal += x;
	}
	
	public void incVerticalBy(int x) {
		vertical += x;
	}
	
	public void incLRdiagonalBy(int x) {
		LRdiagonal += x;
	}
	
	public void incRLdiagonalBy(int x) {
		RLdiagonal += x;
	}

	public int getHorizantal() {
		return horizantal;
	}

	public int getVertical() {
		return vertical;
	}

	public int getLRdiagonal() {
		return LRdiagonal;
	}

	public int getRLdiagonal() {
		return RLdiagonal;
	}
	
}
package put.game2048;

import java.util.Arrays;

import com.google.common.base.Preconditions;

public class Board {
	public static final int SIZE = 4;
	public static int TILE_VALUES[] = { 0, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536 };

	private int[][] board = new int[SIZE][SIZE];

	public Board(int[][] board) {
		this.board = new int[SIZE][SIZE];
		for (int row = 0; row < SIZE; row++) {
			this.board[row] = board[row].clone();
		}
	}

	public int[][] get() {
		return board;
	}

	public int getValue(int row, int column) {
		Preconditions.checkArgument(0 <= row && row < SIZE);
		Preconditions.checkArgument(0 <= column && column < SIZE);
		int value = board[row][column];
		assert Arrays.binarySearch(TILE_VALUES, value) >= 0;
		return value;
	}
}

package put.game2048;

public enum Action {
	UP(0), RIGHT(1), DOWN(2), LEFT(3);

	private final int id;

	Action(int id) {
		this.id = id;
	}

	int getId() {
		return id;
	}
}

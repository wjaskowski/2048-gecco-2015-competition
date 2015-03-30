package put.game2048;

import java.util.ArrayList;
import java.util.List;

public class GameResult {
	private final int score;
	private final int maxTile;
	private final List<Long> actionDurationNanos;

	public GameResult(int scoreAchieved, int maxTileAchieved, List<Long> actionDurationNanos) {
		this.score = scoreAchieved;
		this.maxTile = maxTileAchieved;
		this.actionDurationNanos = actionDurationNanos;
	}

	public int getScore() {
		return score;
	}

	public int getMaxTile() {
		return maxTile;
	}

	public List<Long> getActionDurationNanos() {
		return actionDurationNanos;
	}


	public static class Builder {
		private List<Long> actionDurationNanos = new ArrayList<>();

		public void addActionDuration(long actionDurationNanos) {
			this.actionDurationNanos.add(actionDurationNanos);
		}

		public GameResult build(int scoreAchieved, int maxTileAchieved) {
			return new GameResult(scoreAchieved, maxTileAchieved, actionDurationNanos);
		}
	}
}

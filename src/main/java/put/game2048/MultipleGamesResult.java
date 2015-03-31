package put.game2048;

import java.util.HashMap;

import org.apache.commons.math3.stat.descriptive.StatisticalSummary;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class MultipleGamesResult {
	private SummaryStatistics score = new SummaryStatistics();
	private HashMap<Integer, Integer> tiles = new HashMap<>();
	private SummaryStatistics actionDurationNanos = new SummaryStatistics();
	private SummaryStatistics actionTimeLimitExceeds = new SummaryStatistics();

	public MultipleGamesResult() {
	}

	public StatisticalSummary getScore() {
		return score;
	}

	public HashMap<Integer, Integer> getMaxTile() {
		return tiles;
	}

	public StatisticalSummary getActionDurationNanos() {
		return actionDurationNanos;
	}

	private double confInterval95(SummaryStatistics stats) {
		return 1.96 * stats.getStandardDeviation() / Math.sqrt(stats.getN());
	}

	public void addGameResult(GameResult gameResult) {
		score.addValue(gameResult.getScore());
		for (int tile = gameResult.getMaxTile(); tile >= 16; tile /= 2) {
			tiles.put(tile, tiles.getOrDefault(tile, 0) + 1);
		}
		gameResult.getActionDurationNanos().forEach(actionDurationNanos::addValue);
	}

	public String toCvsRow() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				"MeanScore,95ConfInterval,MaxScore,AvgActionTime[ms],16,32,64,128,256,512,1024,2048,4096,8912,16384,32768,65536\n");
		builder.append(String.format("%.1f,", score.getMean()));
		builder.append(String.format("%.1f,", confInterval95(score)));
		builder.append(score.getMax()).append(",");
		builder.append(String.format("%.3f", actionDurationNanos.getMean() / 1000000.0));
		for (int i = 4; i <= 16; ++i) {
			builder.append(String.format(",%.3f", tiles.getOrDefault(1 << i, 0) / (double)score.getN()));
		}
		builder.append("\n");
		return builder.toString();
	}
}

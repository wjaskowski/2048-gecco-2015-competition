package put.game2048;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import org.apache.commons.math3.random.RandomDataGenerator;
import put.ci.cevo.games.game2048.Action2048;
import put.ci.cevo.games.game2048.Game2048;
import put.ci.cevo.games.game2048.Player2048;
import put.ci.cevo.games.game2048.State2048;
import put.ci.cevo.util.Pair;

public class Game {

	private final Duration actionTimeLimitSoft;

	public Game(Duration actionTimeLimit) {
		this.actionTimeLimitSoft = actionTimeLimit;
	}

	public GameResult playSingle(Supplier<Agent> agentSupplier, RandomDataGenerator random) {
		Preconditions.checkNotNull(agentSupplier);
		Preconditions.checkNotNull(random);

		Agent agent = agentSupplier.get();

		GameResult.Builder gameResult = new GameResult.Builder();

		// I am wrapping up our original implementation
		Game2048 game = new Game2048();
		Pair<Integer, Integer> result = game.playGame(new Player2048() {
			@Override
			public Action2048 chooseAction(State2048 state, List<Action2048> actions) {
				List<Action> possibleActions = actions.stream().map(Game::toAction).collect(Collectors.toList());
				Stopwatch stopwatch = new Stopwatch();
				stopwatch.start();
				Action action = agent.chooseAction(new Board(state.getBoard()), possibleActions, actionTimeLimitSoft);
				if (action == null || !possibleActions.contains(action))
					throw new IllegalStateException("The agent made an illegal action");
				long elapsedNanos = stopwatch.elapsed(TimeUnit.NANOSECONDS);
				gameResult.addActionDuration(elapsedNanos);

				return toAction2048(action);
			}
		}, random);

		return gameResult.build(result.first(), result.second());
	}

	public MultipleGamesResult playMultiple(Supplier<Agent> agentSupplier, int numGames,
			RandomDataGenerator random) {
		Preconditions.checkNotNull(agentSupplier);
		Preconditions.checkArgument(numGames > 0);
		Preconditions.checkNotNull(random);

		MultipleGamesResult result = new MultipleGamesResult();
		for (int i = 0; i < numGames; ++i) {
			result.addGameResult(playSingle(agentSupplier, random));
		}
		return result;
	}

	static Action2048 toAction2048(Action action) {
		switch (action) {
		case UP:
			return Action2048.UP;
		case DOWN:
			return Action2048.DOWN;
		case LEFT:
			return Action2048.LEFT;
		case RIGHT:
			return Action2048.RIGHT;
		}
		throw new IllegalStateException("Cannot happen");
	}

	static Action toAction(Action2048 action) {
		switch (action) {
		case UP:
			return Action.UP;
		case DOWN:
			return Action.DOWN;
		case LEFT:
			return Action.LEFT;
		case RIGHT:
			return Action.RIGHT;
		}
		throw new IllegalStateException("Cannot happen");
	}
}

package put.game2048;

import java.time.Duration;
import java.util.List;

public interface Agent {
	public Action chooseAction(Board board, List<Action> possibleActions, Duration maxTime);
}
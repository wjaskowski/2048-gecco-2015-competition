package put.game2048;

import java.time.Duration;
import java.util.List;

import com.google.common.base.Preconditions;

public class BlindReflexAgent implements Agent {
	public Action chooseAction(Board board, List<Action> possibleActions, Duration maxTime) {
		Preconditions.checkArgument(0 < possibleActions.size());
		for (Action a : Action.values())
			if (possibleActions.contains(a)) {
				return a;
			}
		throw new IllegalStateException("Cannot happen");
	}
}

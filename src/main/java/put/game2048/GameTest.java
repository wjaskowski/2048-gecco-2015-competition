package put.game2048;

import java.time.Duration;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.junit.Assert;
import org.junit.Test;

public class GameTest {
	final Duration ACTION_TIME_LIMIT = Duration.ofMillis(1);

	@Test
	public void regressionBlindReflexAgent() {
		RandomDataGenerator random = new RandomDataGenerator(new MersenneTwister(123));
		MultipleGamesResult result = new Game(ACTION_TIME_LIMIT).playMultiple(BlindReflexAgent::new, 10000, random);
		System.out.println(result.toCvsRow());
		Assert.assertEquals(2287.195, result.getScore().getMean(), 0.001);
	}

	@Test
	public void regressionRandomAgent() {
		RandomDataGenerator random = new RandomDataGenerator(new MersenneTwister(123));
		MultipleGamesResult result = new Game(ACTION_TIME_LIMIT).playMultiple(RandomAgent::new, 10000, random);
		System.out.println(result.toCvsRow());
		Assert.assertEquals(1238.0348, result.getScore().getMean(), 0.001);
	}
}
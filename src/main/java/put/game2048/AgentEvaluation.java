package put.game2048;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.time.Duration;
import java.util.function.Supplier;

import com.google.common.base.Preconditions;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomDataGenerator;

public class AgentEvaluation {
	public static void main(String[] args) {
		if (args.length != 5) {
			System.err.println(usage());
			System.exit(1);
		}
		try {
			final Supplier<Agent> AGENT = createAgentFactoryByReflection(new File(args[0]), args[1]);
			final int REPEATS = Integer.parseInt(args[2]);
			final Duration ACTION_TIME_LIMIT = Duration.ofNanos((int) (Double.parseDouble(args[3]) * 1000 * 1000));
			final long RANDOM_SEED = Long.parseLong(args[4]);

			RandomDataGenerator random = new RandomDataGenerator(new MersenneTwister(RANDOM_SEED));
			MultipleGamesResult result = new Game(ACTION_TIME_LIMIT).playMultiple(AGENT, REPEATS, random);
			System.out.println(result.toCvsRow());

		} catch (Exception e) {
			System.err.println(e.toString());
			System.err.println();
			System.err.println(usage());
			System.exit(1);
		}
	}

	private static String usage() {
		return "usage: java -jar jar agent_jar_file agent_class_name num_games action_time_limit_ms random_seed";
	}

	private static Supplier<Agent> createAgentFactoryByReflection(File jar, String className)
			throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException, MalformedURLException {
		URL[] cp = { jar.toURI().toURL() };
		URLClassLoader urlcl = new URLClassLoader(cp);
		Class<?> clazz = urlcl.loadClass(className);
		Constructor<?> constructor = clazz.getConstructor();
		return () -> {
			try {
				return (Agent) constructor.newInstance();
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException("Cannot instantiate agent", e);
			}
		};
	}
}

2048 Controller Competition @ GECCO 2015
========================================
Competition website: http://www.cs.put.poznan.pl/wjaskowski/game-2048-competition-gecco-2015

Prerequisites
-------------
Java 8.

Building
--------
You do need to build the code. Just download the prebuild [2048.jar](https://github.com/wjaskowski/2048-gecco-2015-competition/releases/tag/1.0)

If you want to build it yourself, you need Maven. Just execute:

```bash
> mvn package
```

Preparing your agent
--------------------
A simple examplary controller (MyAgent.java):
```java
import java.time.Duration;
import java.util.List;
import java.util.Random;
import put.game2048.*;

public class MyAgent implements Agent {
	public Random random = new Random(123);

	// A nonparametric constructor is required

    /** timeLimit will always be 1 ms */
	public Action chooseAction(Board board, List<Action> possibleActions, Duration timeLimit) {
	    // Prefer right direction
	    if (possibleActions.contains(Action.RIGHT)) {
	        return Action.RIGHT;
        } else {
            // If cannot go right, then make a random move
            return possibleActions.get(random.nextInt(possibleActions.size()));
        }
	}
}
```

First, compile the agent:
```bash
> javac -cp 2048.jar MyAgent.java
```

Then, put it into a jar:
```bash
> jar -cf MyAgent.jar MyAgent.class
```

Finally, evaluate it:
```bash
> java -jar 2048.jar MyAgent.jar MyAgent 10000 1.0 123
MeanScore,95ConfInterval,MaxScore,AvgActionTime[ms],16,32,64,128,256,512,1024,2048,4096,8912,16384,32768,65536
1249.4,11.3,5320.0,0.000,1.000,0.999,0.957,0.623,0.104,0.001,0.000,0.000,0.000,0.000,0.000,0.000,0.000
```

The values at the end of the resulting row are the percentages of games in which the controller achieved a tile of a given value.

See also:
```bash
> java -jar 2048.jar
usage: java -jar jar agent_jar_file agent_class_name num_games action_time_limit_ms random_seed
```

```bash
> java -jar 2048.jar 2048.jar put.game2048.BlindReflexAgent 100 1 123
MeanScore,95ConfInterval,MaxScore,AvgActionTime[ms],16,32,64,128,256,512,1024,2048,4096,8912,16384,32768,65536
2235.9,190.3,5096.0,0.002,1.000,1.000,1.000,0.920,0.490,0.020,0.000,0.000,0.000,0.000,0.000,0.000,0.000
```

Authors
-------
* Marcin Szubert (<http://www.cs.put.poznan.pl/mszubert>)
* Wojciech Jaśkowski (<http://www.cs.put.poznan.pl/wjaskowski>)

License
-------
Licensed under the Apache License, Version 2.0 (the "License");
you may not use code in this repository except in compliance with 
the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

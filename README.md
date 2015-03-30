N-Tuple Networks for the Game 2048 
==============================

Competition

Authors
-------
* Marcin Szubert (<http://www.cs.put.poznan.pl/mszubert>)
* Wojciech Jaśkowski (<http://www.cs.put.poznan.pl/wjaskowski>)

Building
--------
You need Java 1.8 and Maven. To build execute:

mvn package

Coding your agent
-----------------

import java.time.Duration;
import java.util.List;
import java.util.Random;
import put.game2048.*;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomDataGenerator;

class MyAgent implements Agent {
	public Random random = new Random(123);

	public Action chooseAction(Board board, List<Action> possibleActions, Duration maxTime) {
	    if (possibleActions.contains(Action.UP)) {
	        return Action.UP;
        } else {
            return possibleActions.get(random.nextInt(possibleActions.size()));
        }
	}

    public static void main(String[] args) {
        final Duration ACTION_TIME_LIMIT = Duration.ofMillis(1);
        final int NUM_GAMES = 10000;

        RandomDataGenerator random = new RandomDataGenerator(new MersenneTwister(123));
		MultipleGamesResult result = new Game(ACTION_TIME_LIMIT).playMultiple(MyAgent::new, NUM_GAMES, random);
		System.out.println(result.toCvsRow());
    }

Compiling your agent
--------------------
> javac -cp 2048.jar MyAgent.java

Running your agent
------------------
> java -cp .:2048.jar MyAgent
MeanScore,95IntervalScore,MaxScore,AvgActionTime[ms],16,32,64,128,256,512,1024,2048,4096,8912,16384,32768,65536
1305.3,13.1,5356.0,0.000,1.000,0.995,0.929,0.614,0.135,0.001,0.000,0.000,0.000,0.000,0.000,0.000,0.000


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

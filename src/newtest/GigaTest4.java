package newtest;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;

import util.Tuple;

//import ai.charles2.Charles_2;
import ai.charles2.Charles_2;
import ai.mch5boltzmann.MonteCarloH5Boltzmann;
import ai.mch7boltzmann.MonteCarloH7Boltzmann;
import ai.mchboltzmann.MonteCarloHBoltzmann;
import ai.montecarlo.MonteCarlo;
import ai.montecarloheuristic10.MonteCarloH10;
import ai.montecarloheuristic5.MonteCarloH5;
import ai.montecarloheuristic55.MonteCarloH55;
import ai.montecarloheuristic7.MonteCarloH7;
//import ai.random.LuckyAI;

import core.Board;
import core.Player;
import core.Rules;

public class GigaTest4 {

	public static void main(String[] args) throws Exception {
		//Statistical variables.
		int e1TotalWins = 0,
		totalDraws = 0,
		e1TotalLoses = 0,
		e1WinAsPlayer1 = 0,
		e1DrawAsPlayer1 = 0,
		e1LoseAsPlayer1 = 0,
		e1WinAsPlayer2 = 0,
		e1DrawAsPlayer2 = 0,
		e1LoseAsPlayer2 = 0,
		e2TotalWins = 0,
		e2TotalLoses = 0,
		e2WinAsPlayer1 = 0,
		e2DrawAsPlayer1 = 0,
		e2LoseAsPlayer1 = 0,
		e2WinAsPlayer2 = 0,
		e2DrawAsPlayer2 = 0,
		e2LoseAsPlayer2 = 0;

		/***********************************************************************
		 * Test #1: (50,000 roll-outs) MCTS_UCT v MCTS + H(5) 3-point board.
		 **********************************************************************/

		//Board that is used in games.
		Board boardTest1 = null;

		//Board that keeps copy of initial position, used to quickly reset the 
		//board before new game take place.
		Board initialPositionTest1 = null;

		//Array of all boards that are used in the test case.
		Board[] boardCollectionTest1 = null;

		//Index of player that is entitled to make a move.
		int currentIndexTest1 = 0;

		//Number of all moves that was made during the game.
		int numberOfMoveTest1 = 0;

		//Players participating in the test case.
		Player[] playersTest1 =  {
				new Player("MCTS_UCT", "MCTS_UCT", "w", 50000),
				new Player("MCTS_H(5)", "MCTS_H(5)", "b", 50000)
		};

		//Number of total moves. It is used to check whether the game is in 
		//terminate state or not (the game finishes when there is no empty 
		//fields in the board).
		int totalNumberOfMovesTest1 = 38;

		//Load board.
		try {
			FileInputStream fisTest1 = new FileInputStream("50_boards_11.sav");
			ObjectInputStream oisTest1 = new ObjectInputStream(fisTest1);
			boardCollectionTest1 = (Board[]) oisTest1.readObject();
			oisTest1.close();
		} catch(Exception e) {
			System.err.println("Error" + e.getMessage());
		}

		//The beginning and the end of the test.
		long startTime = 0, endTime = 0;

		//Report when games commenced.
		startTime = System.currentTimeMillis();

		//Define buffers
		BufferedWriter outputTest1 = new BufferedWriter(
				new FileWriter("results_50k_11b_MCTS_UCTvMCTS_H(5).txt", true));
		MonteCarlo mc_t1 = new MonteCarlo(
				boardTest1.duplicate(), 
				playersTest1[currentIndexTest1].getColor(), 
				numberOfMoveTest1, 
				totalNumberOfMovesTest1);
		MonteCarloH5 mc_h5t = new MonteCarloH5(
				boardTest1.duplicate(), 
				playersTest1[currentIndexTest1].getColor(), 
				numberOfMoveTest1, 
				totalNumberOfMovesTest1);

		testOne(currentIndexTest1, numberOfMoveTest1,
				 playersTest1,  boardTest1, boardCollectionTest1
				, initialPositionTest1, totalNumberOfMovesTest1,
				 mc_t1,  mc_h5t,  outputTest1, startTime,
				endTime);
		
		//Boards are OK. Proceed to testing.
	
		

	}
	
public static Integer maxCast (int a){
		
		Integer valInteger = (Integer) a; 
			
		return valInteger;
		}
		
public static void testOne (int currentIndexTest1, int numberOfMoveTest1,
		Player[] playersTest1, Board boardTest1, Board[] boardCollectionTest1
		, Board initialPositionTest1, int totalNumberOfMovesTest1,
		MonteCarlo mc_t1, MonteCarloH5 mc_h5t, BufferedWriter outputTest1,long startTime,
		long endTime) throws Exception {
	
	int e1TotalWins = 0,
			totalDraws = 0,
			e1TotalLoses = 0,
			e1WinAsPlayer1 = 0,
			e1DrawAsPlayer1 = 0,
			e1LoseAsPlayer1 = 0,
			e1WinAsPlayer2 = 0,
			e1DrawAsPlayer2 = 0,
			e1LoseAsPlayer2 = 0,
			e2TotalWins = 0,
			e2TotalLoses = 0,
			e2WinAsPlayer1 = 0,
			e2DrawAsPlayer1 = 0,
			e2LoseAsPlayer1 = 0,
			e2WinAsPlayer2 = 0,
			e2DrawAsPlayer2 = 0,
			e2LoseAsPlayer2 = 0;
	
	for(int testIndex = 1; testIndex <= 100; ++testIndex) {
		System.out.println("Test1: " + testIndex + " / 100");
		//Reset settings.
		currentIndexTest1 = 0;
		numberOfMoveTest1 = 0;

		//Swap players.
		Player tmp = playersTest1[0];
		playersTest1[0] = playersTest1[1];
		playersTest1[1] = tmp;

		//Reset the board to an initial state. When index is odd generate a 
		//new random board.
		
		newRandomBoard ( testIndex, boardTest1, 
				boardCollectionTest1, initialPositionTest1 );


		//Run a single game.
		while(numberOfMoveTest1 < totalNumberOfMovesTest1) {
			if(playersTest1[currentIndexTest1].getType().equals("MCTS_UCT")) {
				//MCTS + H(7) to play.
				Tuple<Integer, Integer> move;
				//Pure Monte-Carlo will select a move.
				

				move = mc_t1.uct(playersTest1[currentIndexTest1].
						getSimulationNumber());

				boardTest1.makeMove(move, playersTest1[currentIndexTest1].getColor());

				//Increment number of currently made moves.
				++numberOfMoveTest1;

				//Adjust index of current player.
				currentIndexTest1 = (currentIndexTest1 + 1) % 2;
			} else if(playersTest1[currentIndexTest1].getType().equals("MCTS_H(5)")) {
				//MCTS (UCT) to play.
				Tuple<Integer, Integer> move;
				//Pure Monte-Carlo will select move.
				
				move = mc_h5t.uct(playersTest1[currentIndexTest1].
						getSimulationNumber());


				boardTest1.makeMove(move, playersTest1[currentIndexTest1].getColor());

				//Increment number of currently made moves.
				++numberOfMoveTest1;

				//Adjust index of current player.
				currentIndexTest1 = (currentIndexTest1 + 1) % 2;
			}
		} //end of single game.

		String gameOutcome = Rules.calculateScore(boardTest1);
		
		outputTest1.append("Match #" + testIndex);
		outputTest1.newLine();
		outputTest1.append("Player 1: " + playersTest1[0].getName() + 
				" Player 2: " + playersTest1[1].getName());
		outputTest1.newLine();

		//Append the result to the text file and update counters..
		String zero = "0";
		
		if(gameOutcome.equals(zero)) {
			//The game was a draw.
			++totalDraws;
			//Append information to the file.
			outputTest1.append("Result: draw");
			outputTest1.newLine();
			outputTest1.close();

			//Update statistics.
			boolean valueUS6A = playersTest1[0].getName().equals("MCTS_H(5)");
			updateStaticsA ( valueUS6A,  e1DrawAsPlayer1,
					 e2DrawAsPlayer2,  e1DrawAsPlayer2,  e2DrawAsPlayer1);
		
		} else {
			//One side wins the game.
			if(gameOutcome.equals(playersTest1[0].getColor())) {
				//Player #1, whoever it is, wins the game.

				//Add note about the winner to the file.
				outputTest1.append("Result: " + playersTest1[0].getName() + " wins");

				//Update statistics.
				boolean valueUS6B = playersTest1[0].getName().equals("MCTS_H(5)");
				
				
				updateStatisticsB ( valueUS6B,  e1TotalWins,
						 e2TotalLoses, e1WinAsPlayer2,  e2LoseAsPlayer1,
						 e2TotalWins,  e1TotalLoses,  e2WinAsPlayer2,  e1LoseAsPlayer1);

			} else {
				//Player #2, whoever it is, wins the game.

				//Add note about the winner to the file.
				outputTest1.append("Result: " + playersTest1[1].getName() + " wins");

				//Update statistics.
				boolean valueUS6C = playersTest1[1].getName().equals("MCTS_H(5)");
				
				updateStatisticsB ( valueUS6C,  e1TotalWins,
						 e2TotalLoses, e1WinAsPlayer2,  e2LoseAsPlayer1,
						 e2TotalWins,  e1TotalLoses,  e2WinAsPlayer2,  e1LoseAsPlayer1);
			}
			outputTest1.newLine();
			outputTest1.close();
		}			
	} //End of the test case. (for)

	//Report when games ended.
	endTime = System.currentTimeMillis();

	//Append total outcome of the test case to the file.
	BufferedWriter output1Test1 = new BufferedWriter(
			new FileWriter("results_50k_11b_MCTS_UCTvMCTS_H(5).txt", true));
	output1Test1.append("========================================");
	output1Test1.newLine();
	output1Test1.append("*Summary 11-point board 50k roll-outs*");
	output1Test1.newLine();
	output1Test1.append("Draw occurred: " + totalDraws);
	output1Test1.newLine();
	output1Test1.append("MCTS_UCT total wins: " + e2TotalWins);
	output1Test1.newLine();
	output1Test1.append("MCTS_H(5) total wins: " + e1TotalWins);
	output1Test1.newLine();
	output1Test1.append("Play time: " + (endTime - startTime)/1000 + " seconds.");
	output1Test1.newLine();

	//Write statistics for MCTS.
	output1Test1.append("MCTS_UCT wins as player #1 : " + e2WinAsPlayer1);
	output1Test1.newLine();
	output1Test1.append("MCTS_UCT wins as player #2 : " + e2WinAsPlayer2);
	output1Test1.newLine();
	output1Test1.append("MCTS_UCT draws as player #1 : " + e2DrawAsPlayer1);
	output1Test1.newLine();
	output1Test1.append("MCTS_UCT draws as player #2 : " + e2DrawAsPlayer2);
	output1Test1.newLine();
	output1Test1.append("MCTS_UCT loses as player #1 : " + e2LoseAsPlayer1);
	output1Test1.newLine();
	output1Test1.append("MCTS_UCT loses as player #2 : " + e2LoseAsPlayer2);
	output1Test1.newLine();

	//Write statistics for Random AI.
	output1Test1.append("MCTS_H(5) wins as player #1 : " + e1WinAsPlayer1);
	output1Test1.newLine();
	output1Test1.append("MCTS_H(5) wins as player #2 : " + e1WinAsPlayer2);
	output1Test1.newLine();
	output1Test1.append("MCTS_H(5) draws as player #1 : " + e1DrawAsPlayer1);
	output1Test1.newLine();
	output1Test1.append("MCTS_H(5) draws as player #2 : " + e1DrawAsPlayer2);
	output1Test1.newLine();
	output1Test1.append("MCTS_H(5) loses as player #1 : " + e1LoseAsPlayer1);
	output1Test1.newLine();
	output1Test1.append("MCTS_H(5) loses as player #2 : " + e1LoseAsPlayer2);
	output1Test1.newLine();

	output1Test1.append("========================================");
	output1Test1.close();

}

public static void newRandomBoard (int testIndex, Board boardTest1, 
		Board[] boardCollectionTest1, Board initialPositionTest1 ) {
	if(testIndex % 2 == 1) {
		//Load a new board.
		boardTest1 = boardCollectionTest1[(Integer) testIndex/2];
		initialPositionTest1 = boardTest1.duplicate();
	} else {
		//Reset the board.
		boardTest1 = initialPositionTest1.duplicate();
	}

	
}

public static void updateStatisticsB (boolean valueUS6C, int e1TotalWins,
		int e2TotalLoses,int e1WinAsPlayer2, int e2LoseAsPlayer1,
		int e2TotalWins, int e1TotalLoses, int e2WinAsPlayer2, int e1LoseAsPlayer1) {
	
	if(valueUS6C) {
		e1TotalWins++;
		e2TotalLoses++;

		e1WinAsPlayer2++;
		e2LoseAsPlayer1++;
	} else {
		e2TotalWins++;
		e1TotalLoses++;

		e2WinAsPlayer2++;
		e1LoseAsPlayer1++;
	}
	
}

public static void updateStaticsA (boolean valueUS6A, int e1DrawAsPlayer1,
		int e2DrawAsPlayer2, int e1DrawAsPlayer2, int e2DrawAsPlayer1) {
	
	if(valueUS6A) {
		e1DrawAsPlayer1++;
		e2DrawAsPlayer2++;
	} else {
		e1DrawAsPlayer2++;
		e2DrawAsPlayer1++;
	}

	
}

}

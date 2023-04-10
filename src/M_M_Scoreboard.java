
/**
 * Program:			M_M_Scoreboard.java
 * Date:			Nov. 24, 2019
 * Author:			Mahan Mehdipour
 * Description		code a Java program that obtains data from two
 * 					files to generates and displays a contest scoreboard 
 * 					including a title and column labels
 */

import java.io.*;
import java.util.*;

public class M_M_Scoreboard {

	public static void main(String[] args) throws FileNotFoundException {

		// Create File Objects to import the files
		File submits = new File("submissions.txt");
		File team = new File("teams.txt");

		// using try and catch in order to get out of any type of error
		try {
			// Create Scanner objects to read Files one for submission and the other for
			// teams
			Scanner inputSubmit = new Scanner(submits);
			Scanner inputTeam = new Scanner(team);

			// printing out the description by storing that in a variable and printing out
			String description = inputSubmit.nextLine();
			System.out.println(description + "\n");

			// Getting the constant variables from the second row of the submission file
			final int TEAM_NUMBER = inputSubmit.nextInt();
			final int PROBLEM_NUMBER = inputSubmit.nextInt();
			final int TIME = inputSubmit.nextInt();
			final int ADDED_TIME = inputSubmit.nextInt();

			// initializing the number of valid submissions and invalid submissions in order
			// to be able to
			// calculate the result
			int valid = 0;
			int invalid = 0;

			// Declare an array to store the names of the teams
			String[] teamNames = new String[TEAM_NUMBER];
			// Declare a 2D array to store 1. the time
			// 2. the number of yeses for each problem(it doesn't go more than one)
			// 3. number of no (will allow us to calculate the total time)
			// I used them all in the same array to avoid creating too many arrays by
			// multiplying the length by 3
			// so if we want to manipulate yeses we would do allInfo[team number][(problem
			// number*3)+1]
			// for no we would do allInfo[team number][(problem number*3)+2]
			// and for time submitted we would allInfo[team number][(problem number*3)]
			int[][] allInfo = new int[TEAM_NUMBER][(PROBLEM_NUMBER) * 3];

			// a for loop to get all the names of the team
			for (int i = 0; i < teamNames.length; i++) {
				inputTeam.nextInt();
				//Adding to team names array 
				teamNames[i] = inputTeam.nextLine().trim();
				//Trimming the after the fifteenth word for future printing purposes
				if (teamNames[i].length() > 15) {
					teamNames[i] = teamNames[i].substring(0, 15);
				}
			}//end for loop

		

			// Create a while loop to get data from the submission file
			while (inputSubmit.hasNextInt()) {

				int teamId = inputSubmit.nextInt();
				int timePassed = inputSubmit.nextInt();
				int problemNum = inputSubmit.nextInt();
				char yesOrNo = inputSubmit.next().toUpperCase().charAt(0);

				// validating the correctness of the data in the submission file
				if (M_M_ScoreboardMethods.validating(timePassed, problemNum, TIME, teamId, PROBLEM_NUMBER,
						TEAM_NUMBER) == true) {

					// using a switch statement to find out either we are adding to the number of
					// yes or number of no
					switch (yesOrNo) {
					case 'Y':
						allInfo[teamId - 1][(problemNum - 1) * 3 + 1]++;
						
						// adding the time passed to the 2D array if they entered it right
						allInfo[teamId - 1][(problemNum - 1) * 3] = timePassed;
						
						break;

					case 'N':
						allInfo[teamId - 1][(problemNum - 1) * 3 + 2]++;

						break;

					}
					// increment valid every time that the method returns true
					valid++;

				} else {
					// increment invalid every time that the method returns false
					invalid++;
				}//end else

			}//end while
			
			// Declare variables to find out the total time and number of correct answers
			// Declare 1 copy of each to be able to find the lowest and highest without
			// changing the actual array in the future;
			int[] correctNum = new int[TEAM_NUMBER];
			int[] correctNumCopy = new int[TEAM_NUMBER];
			int[] totalTime = new int[TEAM_NUMBER];
			int[] totalTimeCopy = new int[TEAM_NUMBER];

			// create a for loop to add information to the arrays using getCorrect method
			// and getTotal Time
			//Make a copy for each for future calculations
			for (int i = 0; i < TEAM_NUMBER; i++) {
				correctNum[i] = M_M_ScoreboardMethods.getCorrect(allInfo, i, PROBLEM_NUMBER);
				correctNumCopy[i] = M_M_ScoreboardMethods.getCorrect(allInfo, i, PROBLEM_NUMBER);
				totalTime[i] = M_M_ScoreboardMethods.getTotalTime(allInfo, i, PROBLEM_NUMBER, ADDED_TIME);
				totalTimeCopy[i] = M_M_ScoreboardMethods.getTotalTime(allInfo, i, PROBLEM_NUMBER, ADDED_TIME);

			}//end for loop
			// Printing out the columns with tabs by adjusting
			System.out.print("Rank\tTeam\t\tSlv/Time\t");
			// using for loop to print out the problems with numbers
			for (int i = 0; i < PROBLEM_NUMBER; i++) {
				String pNum = ("P" + (i + 1) + "\t");
				System.out.printf("%5s", pNum);
			}//end for loop
			// Create a new line
			System.out.println("\n");
			// Create an array that will hold the index of the teams ranked
			int[] highToLowIndex = new int[TEAM_NUMBER];
			for (int i = 0; i < TEAM_NUMBER; i++) {
				highToLowIndex[i] = i;
			}//end for loop
			// Using a bubble sorting method to change the values of the copied arrays to
			// avoid any type of repetition in the for loop
			for (int p = 0; p < TEAM_NUMBER - 1; p++) {
				// One pass
				for (int i = 0; i < TEAM_NUMBER - 1; i++) {

					// This if statement will make sure that the swap will happen if the two
					// corrects are equal and will decide if the time is different
					if (correctNumCopy[i] < correctNumCopy[i + 1] || (correctNumCopy[i] == correctNumCopy[i + 1]
							&& totalTimeCopy[i] > totalTimeCopy[i + 1])) {
						// These swaps will make sure that repetition won't happen
						M_M_ScoreboardMethods.swap(highToLowIndex, i, i + 1);
						M_M_ScoreboardMethods.swap(correctNumCopy, i, i + 1);
						M_M_ScoreboardMethods.swap(totalTimeCopy, i, i + 1);
					}//end if statement

				}//end inner for loop
			}//end outer for loop

			// using for loop to print out the results
			for (int i = 0; i < TEAM_NUMBER; i++) {

				// Using the index array to print the name of the teams in order of ranking and allign names from left
				System.out.printf("%d\t%-15s\t", (i + 1), teamNames[highToLowIndex[i]]);
				//Create a string to store correct number over the total time for printing purposes
				String yOverTime = correctNum[highToLowIndex[i]] + "/" + totalTime[highToLowIndex[i]];
				System.out.printf("%8s\t", yOverTime);

				// using for loop to print out the results
				for (int j = 0; j < PROBLEM_NUMBER; j++) {
					// Declare a String variable for ease of printing purposes.
					String trueOrFalseOverSubmission;
					// using an if statement to realize whether the problem is solved or not and
					// change the true or false string variable based on
					// the y for yes and use n for no
					if ((allInfo[highToLowIndex[i]][(j * 3) + 1]) > 0) {

						trueOrFalseOverSubmission = ("Y/" + ((allInfo[highToLowIndex[i]][(j * 3) + 2])
								+ (allInfo[highToLowIndex[i]][(j * 3) + 1])));

					} //end if statement
					else {
						trueOrFalseOverSubmission = "N/" + ((allInfo[highToLowIndex[i]][(j * 3) + 2])
								+ (allInfo[highToLowIndex[i]][(j * 3) + 1]));

					}//end else
					// Print out the result every time based on the answer and align from right
					System.out.printf("%4s\t", trueOrFalseOverSubmission);
				}//end inner for loop

				System.out.println();
			}//end outer for loop

			System.out.println();
			// Printing out the number of valids and invalids
			System.out.println(valid + " valid submission(s) were processed.");
			System.out.println(invalid + " submission(s) were invalid and ignored.");

			// Close the Scanner objects
			inputSubmit.close();
			inputTeam.close();
		} // end try
		//Catch statement
		catch (FileNotFoundException ex) {
			System.out.println("Your file was not found. Quitting.");
			System.out.println("Exception message: " + ex.getMessage());

		}// end catch
	}//end main

}

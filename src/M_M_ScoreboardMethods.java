
/**
 * Program:			M_M_ScoreboardMethods.java
 * Date:			Nov. 22, 2019
 * Author:			Mahan Mehdipour
 * Description		Methods associated with the main method 
 */

;
public class M_M_ScoreboardMethods {

	/**
	 * Method Name: validating 
	 * Purpose: Validate the value imported from the file
	 * Accepts: three integers of time, problem number and team number 
	 * Returns: boolean
	 */
	public static boolean validating(int time, int problemNum, int totalTime, int teamNum, int proNumMax,
			int teamNumMax) {
		if (time <= totalTime && (problemNum <= proNumMax && problemNum > 0)
				&& (teamNum <= teamNumMax && teamNum > 0)) {
			return true;

		} else {

			return false;
		}

	}// end Validating

	/**
	 * Method Name: getCorrect 
	 * Purpose: calculate the total solved problems for each
	 * group Accepts: array, and two integers 
	 * Returns: integer value for total solved problems
	 */
	public static int getCorrect(int[][] y, int num, int problem) {

		int totalCorrect = 0;
		for (int i = 0; i < problem; i++) {
			totalCorrect += y[num][(i * 3) + 1];
		}

		return totalCorrect;
	} // end getCorrect

	/**
	 * Method Name: getTotalTime 
	 * Purpose: calculating the total time 
	 * Accepts: allInfo array, and three integers 
	 * Returns: integer that will give the total time
	 */
	public static int getTotalTime(int[][] y, int num, int problem, int addedTime) {

		int totalTime = 0;
		int wrongSubmission = 0;
		for (int i = 0; i < problem; i++) {
			if (y[num][(i * 3) + 1] > 0) {
				wrongSubmission += y[num][(i * 3) + 2];

				totalTime += y[num][(i * 3)];
				
			}
		}
		return (wrongSubmission * addedTime) + totalTime;

	} // end getTotalTime

	/**
	 * Method: swap
	 * Purpose: To exchange values in two elements of an array 
	 * Accepts: An array of type int, and two indexes as integers
	 * Returns: Nothing
	 */
	public static void swap(int[] a, int index1, int index2) {
		if (index1 >= 0 && index1 < a.length && index2 >= 0 && index2 < a.length) {
			int temp = a[index1];
			a[index1] = a[index2];
			a[index2] = temp;
		}//end if statement
	} // end swap

	/**
	 * Method: bubbleSort 
	 * Purpose: To sort an array of integers in ascending order
	 * using the bubble sort algorithm 
	 * Accepts: The array of integers to be sorted
	 * Returns: void
	 */
	public static void bubbleSort(int[] a) {
		// All p passes
		for (int p = 0; p < a.length - 1; p++) {
			// One pass
			for (int i = 0; i < a.length - 1; i++) {
				if (a[i] > a[i + 1]) {
					swap(a, i, i + 1);
				}
			}//end inner for loop
		} // end bubbleSort

	}//end outer loop
}
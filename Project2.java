/*Lia Yaghobi
 COMP 482 Project 2: EDF vs SJF vs LSF scheduling
 */

import java.util.*;
import java.io.*;
import java.util.Arrays;

public class Project2 {

   public static void main(String[] args) {
      Project2 s = new Project2();
      int[][] theData = s.getInput();
      s.sortEDF(theData);
      s.sortSJF(theData);
      s.sortLSF(theData);
   }

   int size;

   private int[][] getInput() {
      Scanner sc = null;
      try {

         sc = new Scanner(new File("input.txt"));
      } catch (FileNotFoundException e) {
         System.out.println("Did you forget the input file?");
         System.exit(1);
      }

      size = sc.nextInt();
      int[][] theData = new int[size][2];
      sc.nextLine();

      for (int i = 0; i < size; i++) {
         for (int j = 0; j < 2; j++) {
            theData[i][j] = sc.nextInt();
         }
      }

      return theData;
   }

   private void sortEDF(int[][] theData) {

      int[] deadlines = new int[size]; /* stores the deadlines */

      for (int i = 0; i < size; i++) {
         deadlines[i] = theData[i][1];
      }
      Arrays.sort(deadlines); /* sorts the deadlines in ascending order */

      int finishTime = 0;
      int late = 0;
      for (int j = 0; j < size; j++) {
         for (int k = 0; k < size; k++) { /* Go through sorted deadlines and find its jobs duration */

            if (deadlines[j] == theData[k][1]) {
               int deadline = theData[k][1];
               int time = theData[k][0];
               finishTime = finishTime + time;
               int lateness = finishTime - deadline;

               if (lateness > 0) {
                  late = late + 1;
               }
            }
         }
      }
      System.out.println("EDF " + late);
   }

   private void sortSJF(int[][] theData) {

      int[] jobs = new int[size]; /* stores the jobs duration */

      for (int i = 0; i < size; i++) {
         jobs[i] = theData[i][0];
      }
      Arrays.sort(jobs); /* sorts the jobs by ascending duration */

      int finishTime = 0;
      int late = 0;
      for (int j = 0; j < size; j++) {
         for (int k = 0; k < size; k++) { /* Go through sorted job duration and find its jobs deadline */

            if (jobs[j] == theData[k][0]) {
               int deadline = theData[k][1];
               int time = theData[k][0];
               finishTime = finishTime + time;
               int lateness = finishTime - deadline;

               if (lateness > 0) {
                  late = late + 1;
               }
            }
         }
      }
      System.out.println("SJF " + late);
   }

   private void sortLSF(int[][] theData) {

      int[] slack = new int[size]; /* slack = deadline - duration */

      for (int i = 0; i < size; i++) {

         slack[i] = theData[i][1] - theData[i][0];
      }
      Arrays.sort(slack); /* sort by ascending slack */
      int finishTime = 0;
      int late = 0;
      for (int j = 0; j < size; j++) {
         for (int k = 0; k < size; k++) { /* Go through sorted slack and find its jobs deadline and duration */

            if (slack[j] == theData[k][1] - theData[k][0]) {
               int deadline = theData[k][1];
               int time = theData[k][0];
               finishTime = finishTime + time;
               int lateness = finishTime - deadline;

               if (lateness > 0) {
                  late = late + 1;
               }
            }
         }
      }
      System.out.println("LSF " + late);
   }
}
/*
Program: Team Data Entry
Author: Michael Durkan
Date: 1st October 2023
Date last modified: 14th October 2023 
Purpose: Allow user to input team details, and store details in csv file
*/

import java.util.*;
import java.io.*;

public class DataEntry
{
    public static void main(String[] args)
    {
        int numTeams;
        printHeader();                                                      // Welcome user by printing the header 
        numTeams = inputNumTeams();                                         // Get number of teams data being entered from user
        Team [] teamArray = new Team[numTeams];                             // Initalize array of team objects the size of teams being input
        teamArray = addNewTeams(teamArray);                                 // Get details for each team from user and store in teamArray 
        listData(teamArray);                                                // List the current input data
        exportTeams(teamArray);                                             // Export team objects into formatted csv file
    }
    
    /* 
    METHOD: printHeader
    IMPORT: none
    EXPORT: none
    ASSERTION: Welcome the user by printing header
    */
    public static void printHeader()
    {
        System.out.println("Welcome to the FIFA WWC Data Entry Program\n"); 
    }

    /*
    METHOD: inputNumTeams
    IMPORT: none
    EXPORT: outNumTeams(Integer)
    ASSERTION: ask user with prompt for number of teams and return in outNumTeams integer
    */
    public static int inputNumTeams()
    {
        int outNumTeams;
        outNumTeams = getInt(1,32,"How many teams' data are you planning to enter?", "Enter an amount of teams from 1 to 32"); // Ask user for number of teams, store in outNumTeams
        System.out.println();
        return outNumTeams;
    }

    /*
    METHOD: getInt
    IMPORT: pMin (Integer), pMax (Integer), pPrompt (String), pOutRangeError (String) 
    EXPORT: outInt (Integer)
    ASSERTION: Ask user with String pPrompt for integer between pMin and pMax, OutRangeError print when input outside range
    */
    public static int getInt(int pMin, int pMax, String pPrompt, String pOutRangeError)
    {
        Scanner input = new Scanner(System.in);
        boolean validInput = false;
        int outInt = 0;
        while(!validInput)                                                  // loop to re-open prompt if exception occurs
        {
            try                                                             // Exception handling for non Integer being input
            {
                do                                                          // Loop prompt if input int outside of required range
                {
                    System.out.println(pPrompt);                            // Print input prompt
                    outInt = input.nextInt();                               // Get Integer and store in outInt
                    if((outInt < pMin) || (outInt > pMax))                  // Print outRangeError if input outside range
                    {
                        System.out.println(pOutRangeError);
                    }
                } while ((outInt < pMin) || (outInt >pMax)); 
                validInput = true;                                          // If program progresses to this point do-while has been exited, thus correct integer input
            }
            catch(InputMismatchException error)                             // Catch non Integer input and display message to re-enter, restart input
            {
                System.out.println("Enter an integer number character\n");
                input.nextLine();
            }
        }
        return outInt;
    }
    
    /*
    METHOD: addNewTeams
    IMPORT: pTeamArray (Team), pNumTeams (Integer)
    EXPORT: pTeamArray (Team)
    ASSERTION: Ask User with prompt for the details of teams of teamNum number of teams
    */
    public static Team[] addNewTeams(Team[] pTeamArray)
    {
        int numTeams = pTeamArray.length;                   
        for(int i = 0; i < numTeams; i++)                                   // Loop through every team object in pTeamArray 
        {
            System.out.println("Enter the data:\n");          
            String teamName = getString("Team Name:");
            System.out.println();      
            String teamCode = getString("Team Code:");       
            System.out.println();      
            int goalsScored = getInt(0,150,"Goals Scored by the Team:","Enter an amount of goals from 0 to 150\n");    
            System.out.println();      
            int goalsLost = getInt(0,150,"Goals Scored Against the Team:","Enter an amount of goals from 0 to 150\n"); 
            System.out.println();      
            char teamGroup = getChar("Group (A, B, C, D, E, F, G or H):");
            System.out.println();             
            pTeamArray[i] = new Team(teamName, teamCode, goalsScored, goalsLost, teamGroup);    // Store input details in the number i object within pTeamArray
        }
        return pTeamArray;
        
    }
    
    /*
    METHOD: getString
    IMPORT: pPrompt (String)
    EXPORT: outString (String)
    ASSERTION: Ask user for a string with prompt, handle errors and exceptions
    */
    public static String getString(String pPrompt)
    {
        Scanner input = new Scanner(System.in);
        boolean validInput = false;
        String outString = "";
        int outStringLength;
        while(!validInput)                                                  // Loop to re-open prompt if exception handling occurs
        {
            try
            {   
                do                                                          // Loop to re-open prompt if incorrect format input
                {
                    System.out.println(pPrompt);
                    outString = input.nextLine();
                    outStringLength = outString.length();                   // Get length of input String
                    if(outString.isEmpty() || outString == null)            // Output error if input string empty or null 
                    {
                        System.out.println("Empty-null string, please re-enter\n");
                    }
                    if(((outString.charAt(0) == ' ' ) || (outString.charAt(outStringLength-1) == ' '))) // Output error if input string contains space before or after input
                    {
                        System.out.println("Error in formatting, space before or after word.\n");
                    }
                } while ((outString.isEmpty()) || outString == null || ((outString.charAt(0) == ' ' ) || (outString.charAt(outStringLength-1) == ' ')));
                validInput = true;                                          // If program progresses to this point do while coniditons are met, thus valid string input
            }
            catch(InputMismatchException error)                
            {
                System.out.println("Something went wrong! The error: " + error);
                input.nextLine();
            }
            catch(StringIndexOutOfBoundsException error)                    // Handle empty-null Strings
            {
            }
        }
        return outString;
    }
    
    /*
    METHOD: getChar
    IMPORT: pPrompt (String)
    EXPORT: outChar (Character)
    ASSERTION: Ask for a character with prompt, handle errors and exceptions
    */
    public static char getChar(String pPrompt)
    {
        Scanner input = new Scanner(System.in);
        boolean validInput = false;
        char outChar = ' ';
        while(!validInput)                                                  // Loop prompt if error exception occurs
        {
            try
            {
                while(!(outChar == 'A' || outChar == 'B' || outChar == 'C' || outChar == 'D' || outChar == 'E' || outChar == 'F' || outChar == 'G' || outChar == 'H' )) // Loop while Char outisde range is input
                {                                                   
                    System.out.println(pPrompt);
                    outChar = input.next().charAt(0);
                    if(!(outChar == 'A' || outChar == 'B' || outChar == 'C' || outChar == 'D' || outChar == 'E' || outChar == 'F' || outChar == 'G' || outChar == 'H' )) // Error message if Char outside range is input
                    {        
                        System.out.println("Team group invalid, enter group A, B, C, D, E, F, G or H\n");
                    }
                }
                validInput = true;                                          // If program exit while loop correct char has been input
            }
            catch(InputMismatchException error)
            {
                System.out.println("Something went wrong! The error: " + error);
                input.nextLine();
            }
        }
        return outChar;
    }
    
    /*
    METHOD: listData
    IMPORT: pTeamArray (Team)
    EXPORT: none
    ASSERTION: Print a list of the current data in the Team Objects within the Array
    */
    public static void listData(Team[] pTeamArray)
    {
        int numTeams = pTeamArray.length;
        System.out.println("The current data looks like this:");
        System.out.println();
        for(int i = 0; i < numTeams; i++)                                   // Loop through teams within pTeamArray
        {
            int teamNum = i + 1;
            System.out.print(teamNum + ": " + pTeamArray[i]);             // Print team i details with number in list 
            System.out.println(". Data in file format: (" + pTeamArray[i].toFileString() + ").");               // Print team i as will appear in csv file
            System.out.println();
        }
    }
    
    /*
    METHOD: exportTeams
    IMPORT: pTeamArray (Team)
    EXPORT: none
    ASSERTION: Export the Team objects from teamArray into a csv file
    */
    public static void exportTeams(Team[] pTeamArray)               
    {
        FileOutputStream fileStrm = null;
        PrintWriter pw;
        int numTeams = pTeamArray.length;
        String newFileName = getString("Please enter export csv file name:");
        try
        {
            fileStrm = new FileOutputStream(newFileName);           
            pw = new PrintWriter(fileStrm);
            pw.println("Team Name,Team Code,Goals For,Goals Against,Group"); // Print first row headers in csv
            for(int i = 0; i < numTeams; i++)
            {
                pw.println(pTeamArray[i].toFileString());                   // Print team i into csv
            }
            pw.close();
        }
        catch(IOException e)
        {
            System.out.println("Error in writing to file: " + e.getMessage());
        }
        System.out.println("Thankyou!");
    }
}


/*
Program: Team Data Analysis
Author: Michael Durkan
Date: 1st October 2023
Date last modified: 14th October 2023
Purpose: Allow the user to import team data from a csv and analyse the data
*/

import java.util.*;
import java.io.*;

public class DataAnalysis
{
    public static void main(String[] args)
    {
        String fileName = "";
        int numTeams = 0;
        printHeader();                                                                              // Welcome User by printing header
        while(numTeams == 0)                                                                        // Loop prompt for file name if file is empty(contains no teams) or doesnt exist
        {
            fileName = inputFileName();                                                             // Input the file name from user
            numTeams = countFileTeams(fileName);                                                    // count the number of teams within the file
        }
        Team[] teamArray = new Team[numTeams];
        teamArray = readFile(fileName, teamArray);                                                  // Create team object array from input file
        groupOrOverallAnalysis(teamArray);                                                          // Give user option of Group or Overall Analysis or exit
    }
    
    /*  
    METHOD: printHeader
    IMPORT: none
    EXPORT: none
    ASSERTION: Welceme user by printing header
    */
    public static void printHeader()
    {
        System.out.println("Welcome to the FIFA WWC Data Analysis Program\n");
    }
    
    /*
    METHOD: inputFileName
    IMPORT: none
    EXPORT: outFileName (string)
    ASSERTION: Ask user with prompt for file name of data to be read
    */
    public static String inputFileName()
    {
        String outFileName = "";
        outFileName = getString("Enter the name of the file containing the data:");                 // Get String input for name of data to be read
        System.out.println();
        return outFileName;
    }
    /*
    METHOD: countFileTeams
    IMPORT: pFileName (String)
    EXPORT: outLineNum (Integer)
    ASSERTION: Count the number of lines and therefore teams stored within csv file and return
    */
    public static int countFileTeams(String pFileName)
    {
        FileInputStream fileStream = null;
        InputStreamReader isr;
        BufferedReader bufRdr;
        int outLineNum = 0;
        String line;
        try
        {
            fileStream = new FileInputStream(pFileName);
            isr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(isr);
            outLineNum = 0;
            bufRdr.readLine();                                                                      // Skip the first header row in counting
            line = bufRdr.readLine();                                                               // Read first line from the file
            while(line != null)                                                                     // Countlines (Teams) in csv file until line is empty line, i.e end of file
            {    
                outLineNum++;
                line = bufRdr.readLine();                                                           // Read the next line in file
            }
            fileStream.close();
            if(outLineNum == 0)
            {
                System.out.println("File contains no data! Please enter a file that contains teams."); // Error Checking for an empty file
            }
            if(outLineNum > 32)                                                                     // Error print if csv file contains too many teams
            {
                System.out.println("Error in processing: File entries exceeding 32, only first 32 recorded");
                outLineNum = 32;
            }
        }
        catch(IOException errorDetails)                                                             // Error handling for IOException and null filestream
        {
            if(fileStream != null)
            {
                try
                {
                    fileStream.close();
                }
                catch(IOException ex2)
                { }
            }
            System.out.println("Error in fileProcessing: " + errorDetails.getMessage());
        }
        return outLineNum;
    }
    
    /*
    METHOD: getString
    IMPORT: pPrompt (String)
    EXPORT: outString (String)
    ASSERTION: Prompt user for string and validate
    */
    public static String getString(String pPrompt)
    {
        Scanner input = new Scanner(System.in);
        boolean validInput = false;
        String outString = "";
        while(!validInput)                                                                          // Loop to re-open prompt if error exception handling occurs
        {
            try
            {   
                int outStringLength = outString.length();
                do                                                                                  // Loop to re-open prompt if incorrect format input
                {
                    System.out.println(pPrompt);
                    outString = input.nextLine();
                    outStringLength = outString.length();                                           // Get length of input string
                    if(outString.isEmpty() || outString == null)                                    // Output error if input string empty or null
                    {
                        System.out.println("Empty-null string, please re-enter");
                    }
                    if(((outString.charAt(0) == ' ' ) || (outString.charAt(outStringLength-1) == ' '))) // Output error if input string contains space before or after input
                    {
                        System.out.println("Error in formatting, space before or after word.");
                    }
                } while ((outString.isEmpty()) || outString == null || ((outString.charAt(0) == ' ' ) || (outString.charAt(outStringLength-1) == ' ')));
                validInput = true;                                                                  // If program progresses to this point string input is valid
            }
            catch(InputMismatchException error)
            {
                System.out.println("Something went wrong! The error: " + error);
                input.nextLine();
            }
            catch(StringIndexOutOfBoundsException error)                                            // Handle errors from empty string input
            {
            }
        }
        return outString;
    }
    
    /*
    METHOD: readFile
    IMPORT: pFileName (String), pTeamArray (Team)
    EXPORT: pTeamArray (Team)
    ASSERTION: Read Csv File lines to store them in team objects within array teamArray   
    */
    public static Team[] readFile(String pFileName, Team[] pTeamArray)
    {
        FileInputStream fileStream = null;
        InputStreamReader isr;
        BufferedReader bufRdr;
        int lineNum;
        String line;
        try
        {
            fileStream = new FileInputStream(pFileName);
            isr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(isr);
            lineNum = 0;
            bufRdr.readLine();                                                                      // Skip the First Line (Header) in reading
            line = bufRdr.readLine();                                                               // Read the first line of data in the file
            while((line != null) && (lineNum <= 32))
            {
                pTeamArray[lineNum] = processLine(line);                                            // Assign the line of data to the corresponding team in object array by passing to parse
                lineNum++;
                line = bufRdr.readLine();                                                           // Read the next line in file
            }
            if(lineNum == 0)                                                                        // Print error if file contains no data
            {
                System.out.println("File was empty.");
            }
            fileStream.close();
        }
        catch(IOException errorDetails)                                                             // Catch IO Exceptions, filestream null
        {
            if(fileStream != null)
            {
                try
                {
                    fileStream.close();
                }
                catch(IOException ex2)
                { }
            }
            System.out.println("Error in fileProcessing: " + errorDetails.getMessage());
        }
        catch(ArrayIndexOutOfBoundsException errorDetails)                                          // Catch OutofBounds Exception
        {
        }
        return pTeamArray;
    }
    
    /* 
    METHOD: processLine
    IMPORT: csvRow (String)
    EXPORT: outTeam (Team)
    ASSERTION: Parse row into seperate data for each team object field, create new Team object with these fields and return
    */
    public static Team processLine(String csvRow)
    {
        String[] splitLine;
        Team outTeam;
        splitLine = csvRow.split(",");                                                              // Declare the split of fields to be defined by comma ","
        String teamName;
        String teamCode;
        int goalsScored;
        int goalsLost;
        char teamGroup;
        teamName = splitLine[0];                                                                    // Declare first parsed field as team name
        teamCode = splitLine[1];                                                                    // Declare second parsed field as team code
        goalsScored = Integer.parseInt(splitLine[2]);                                               // Declare third parsed field as goals scored
        goalsLost = Integer.parseInt(splitLine[3]);                                                 // Decalre fourth parsed field as goals against
        teamGroup = splitLine[4].charAt(0);                                                         // Declare fifth parsed field as team group
        outTeam = new Team(teamName, teamCode, goalsScored, goalsLost, teamGroup);                  // Declare new team object with above fields
        return outTeam;
        
    }
    
    /*
    METHOD: groupOrOverallAnalysis
    IMPORT: pTeamArray (Team)
    EXPORT: none
    ASSERTION: Ask user with prompt whether group or overall analysis or exit required, loop if not to exit
    */
    public static void groupOrOverallAnalysis(Team[] pTeamArray)
    {
        int groupOrOverall;
        char notExit = 'N';                                                                             
        while(notExit == 'N' || notExit == 'n' )                                                    // Do not exit when N or n is input
        {   
            System.out.println("What form of analysis do you require:");
            groupOrOverall = getInt(1,2,"(1) Overall analysis or\n(2) Group analysis?","Enter integer 1 or 2"); // Prompt whether overall or group analysis
            System.out.println();
            if(groupOrOverall == 1)                                                                 // Complete taks for overall analysis
            {
                overallAnalysis(pTeamArray);                                                        // pass pTeamArray into menu for overall analysis options
                notExit = getCharExit("\nWould you like to exit: \n(Y) Yes.\n(N) No.");             // Prompt for exit or not
                System.out.println();
            }
            if(groupOrOverall == 2)                                                                 // Complete tasks for group analysis
            {
                groupAnalysis(pTeamArray);                                                          // pass pTeamArray into menu for group analysis options
                notExit = getCharExit("\nWould you like to exit: \n(Y) Yes.\n(N) No.");             // Prompt for exit or not
                System.out.println();
            }
        }
    }
    
    /*
    METHOD: overallAnalysis
    IMPORT: pTeamArray (Team)
    EXPORT: none
    ASSERTION: Provide analysis options of overall teams in menu
    */
    public static void overallAnalysis(Team[] pTeamArray)
    {
        int analysisType;
        System.out.println("What overall analysis do you require?:");
        printAnalysisMenu();                                                                        // Print menu of analysis option types
        analysisType = getInt(1,4,"","Enter and integer from 1 to 4");                              // Input selection from menu
        System.out.println();
        switch(analysisType)                                                                        // Case statements for selected analysis
        {
            case 1: 
                displaySortNetGoals(pTeamArray);                                                    // Display sorted net goals
                break;
            case 2:
                displaySortGoalsLost(pTeamArray);                                                   // Diplay sorted goals against
                break;
            case 3:
                displaySortGoals(pTeamArray);                                                       // Display sorted goals for
                break;  
            case 4:
                displayBestTeam(pTeamArray);                                                        // Display best performing team
                break;
        }
    }
    
    /*
    METHOD: printAnalysisMenu
    IMPORT: none
    EXPORT: none
    ASSERTION: Print the analysis options for both overall and group analysis
    */
    public static void printAnalysisMenu()
    {
        System.out.println("(1) Display teams sorted by net goals in descending order.");
        System.out.println("(2) Display teams sorted by total goals scored against them in descending order.");
        System.out.println("(3) Display teams sorted by total goals they scored in descending order.");
        System.out.println("(4) Display best performing team: team with highest net goals.");
    }
    
    /*
    METHOD: displaySortNetGoals
    IMPORT: pTeamArray (Team)
    EXPORT: none
    ASSERTION: Print teams sorted by net goals descending
    */
    public static void displaySortNetGoals(Team[] pTeamArray)
    {
        int numTeams;
        numTeams = pTeamArray.length;                                                               // Get number of teams by finding pTeamArray length
        Team [] sortedNetGoalsArray = new Team[numTeams];                                           // Create new team array for sorted teams the size of number of teams
        int [] netGoalsArray = new int[numTeams];                                                   // Create new array for net goals sorted teams the size of number of teams
        sortedNetGoalsArray = bubbleSortNetGoals(pTeamArray);                                       // Bubble sort pTeamArray by net goals and store in sorted array
        netGoalsArray = netGoals(sortedNetGoalsArray);                                              // Find net goals of team in sorted array
        System.out.println("This is the teams sorted by net goals descending:\n");
        if(numTeams > 0)                                                                            // Print the sorted by net goals descending list
        {
            for(int i = 0; i < numTeams; i++)
            {
                System.out.print((i+1) +". Team name: " + sortedNetGoalsArray[i].getTeamName() + "\n");
                System.out.print("Net goals: " + netGoalsArray[i] + "\n\n");
            }
        }
    }
 
    /*
    METHOD: netGoals
    IMPORT: pTeamArray (Team)
    EXPORT: outNetGoalsArray (Integer)
    ASSERTION: Display net goals of each team in team array
    */
    public static int[] netGoals(Team[] pTeamArray)
    {
        int netGoals = 0;
        int numTeams = 0;
        numTeams = pTeamArray.length;                                                               // Get number of teams by finding pTeamArray length
        int [] outNetGoalsArray = new int[numTeams];                                                // Create net goals Array the size of number of teams
        for(int i = 0; i < numTeams; i++)                                                           // Calculate and assign the net goals for each team 
        {
            netGoals = pTeamArray[i].getGoalsScored() - pTeamArray[i].getGoalsLost();
            outNetGoalsArray[i] = netGoals;
        }
        return outNetGoalsArray;
    }

    /*
    METHOD: bubbleSortNetGoals
    IMPORT: pTeamArray (Team)
    EXPORT: pTeamArray (Team)
    ASSERTION: bubbleSort the pTeamArray array of team objects by net goals of each team
    */
    public static Team[] bubbleSortNetGoals(Team[] pTeamArray)
    {
        for(int pass = 0; pass < (pTeamArray.length - 1); pass++)                                   // Iterate through array comparring elements
            {
                for(int i = 0; i < (pTeamArray.length - 1 - pass); i++)
                {
                    if((pTeamArray[i].getGoalsScored()) - (pTeamArray[i].getGoalsLost()) <= (pTeamArray[i+1].getGoalsScored()) - (pTeamArray[i+1].getGoalsLost())) // If elements are not in order of net goals descending swap them
                    {
                        Team temp = pTeamArray[i];
                        pTeamArray[i] = pTeamArray[i + 1];
                        pTeamArray[i + 1] = temp;
                    }
                }
            }
        return pTeamArray;
    }

    /*
    METHOD: displaySortGoalsLost
    IMPORT: pTeamArray (Team)
    EXPORT: none
    ASSERTION: display teams sorted by goals scored against them, descending
    */
    public static void displaySortGoalsLost(Team[] pTeamArray)
    {
        int numTeams;
        numTeams = pTeamArray.length;                                                               // Get number of team by finding pTeamArray length
        Team [] sortedGoalsLostArray = new Team[numTeams];                                          // Create new team array for sorted teams the size of number of teams
        sortedGoalsLostArray = bubbleSortGoalsLost(pTeamArray);                                     // Bubble sort pTeam Array by goals against and store in sorted Array
        System.out.println("This is the teams sorted by goals scored against them, in descending order:\n");
        if(numTeams > 0)                                                                            // Print the sorted by goals against descending list
        {
            for(int i = 0; i < numTeams; i++)
            {
                System.out.print((i+1) + ". Team name: " + sortedGoalsLostArray[i].getTeamName() + "\n");
                System.out.print("Total goals scored against them: " + sortedGoalsLostArray[i].getGoalsLost() + "\n\n");
            }
        }
    }
 
    /*
    METHOD: bubbleSortGoalsLost
    IMPORT: pTeamArray (Team)
    EXPORT: pTeamArray (Team)
    ASSERTION: bubbleSort the goals scored against the teams stored in pTeamArray in descending order
    */
    public static Team[] bubbleSortGoalsLost(Team[] pTeamArray)
    {
        for(int pass = 0; pass < (pTeamArray.length - 1); pass++)                                   // Iterate through array comparing elements
            {
                for(int i = 0; i < (pTeamArray.length - 1 - pass); i++)
                {
                    if(pTeamArray[i].getGoalsLost() <= pTeamArray[i+1].getGoalsLost())              // If elements are not in order of goals against descending swap them
                    {
                        Team temp = pTeamArray[i];
                        pTeamArray[i] = pTeamArray[i + 1];
                        pTeamArray[i + 1] = temp;
                    }
                }
            }
        return pTeamArray;
    }

    /*
    METHOD: displaySortGoals
    IMPORT: pTeamArray (Team)
    EXPORT: none
    ASSERTION: display teams sorted by goals scored in descending order
    */
    public static void displaySortGoals(Team[] pTeamArray)
    {
        int numTeams;
        numTeams = pTeamArray.length;                                                               // Get number of teams by finding pTeamArray length
        Team [] sortedGoalsArray = new Team[numTeams];                                              // Create new team array for sorted teams the size of number of teams
        sortedGoalsArray = bubbleSortGoals(pTeamArray);                                             // Bubble sort pTeam Array by goals for and store in sorted Array
        System.out.println("This is the teams sorted by goals scored by them, in descending order:\n");
        if(numTeams > 0)                                                                            // Print the sorted goals for descending list
        {
            for(int i = 0; i < numTeams; i++)
            {
                System.out.print((i+1) + ". Team name: " + sortedGoalsArray[i].getTeamName() + "\n");
                System.out.print("Toal goals scored: " + sortedGoalsArray[i].getGoalsScored() + "\n\n");
            }
        }
    }
 
    /*
    METHOD: bubbleSortGoals
    IMPORT: pTeamArray (Team)
    EXPORT: pTeamArray (Team)
    ASSERTION: bubbleSort the goals scored bythe teams stored in pTeamArray, in descending order
    */
    public static Team[] bubbleSortGoals(Team[] pTeamArray)
    {
        for(int pass = 0; pass < (pTeamArray.length - 1); pass++)                                   // Iterate through array comparing elements
            {
                for(int i = 0; i < (pTeamArray.length - 1 - pass); i++)
                {
                    if(pTeamArray[i].getGoalsScored() <= pTeamArray[i+1].getGoalsScored())          // If elements are not in order of goals for descending swap them
                    {
                        Team temp = pTeamArray[i];
                        pTeamArray[i] = pTeamArray[i + 1];
                        pTeamArray[i + 1] = temp;
                    }
                }
            }
        return pTeamArray;
    }

    /*
    METHOD: displayBestTeam
    IMPORT: pTeamArray (Team)
    EXPORT: none
    ASSERTION: Displays the team with the highest net goals to user
    */
    public static void displayBestTeam(Team[] pTeamArray)
    {
        int numTeams;
        numTeams = pTeamArray.length;                                                               // Calculate number of teams by finding pTeamArray length
        Team [] sortedNetGoalsArray = new Team[numTeams];                                           // Create new array size of number of teams for net goals sorted array
        int [] netGoalsArray = new int[numTeams];                                                   // Create new array size of number of teams for net goals array
        sortedNetGoalsArray = bubbleSortNetGoals(pTeamArray);                                       // Bubble sorted the team array by net goals descending
        netGoalsArray = netGoals(sortedNetGoalsArray);                                              // Find the net goals for each team in sorted array
        System.out.println("This is the best performing team (with highest net goals):");           
        System.out.println("Team name: " + sortedNetGoalsArray[0].getTeamName());                   // Print team and net goals of the team with highest net goals
        System.out.println("Net goals: " + netGoalsArray[0]);
    }
 
    /*
    METHOD: groupAnalysis
    IMPORT: pTeamArray (Team)
    EXPORT: none
    ASSERTION: Complete analysis options of teams in a group
    */
    public static void groupAnalysis(Team[] pTeamArray)
    {
        int analysisType;
        char chosenGroup;
        int numTeams;
        int numGroup = 0;
        numTeams = pTeamArray.length;                                                               // Calculate number of teams by finding pTeamArray length
        chosenGroup = getCharGroup("Enter the group you wish to analyse (A, B, C, D, E, F, G or H):"); // Prompt for group required
        numGroup = numTeamInGroup(pTeamArray, chosenGroup);                                         // Get number of teams in group
        System.out.println();
        if(numGroup > 0)                                                                            // If number of teams in group is 0 notify user
        {
            Team [] groupArray = new Team[numGroup];                                                // Create array for groups size of number of teams in group
            groupArray = occupyGroupArray(pTeamArray, groupArray, chosenGroup);                     // Occupy group array with teams within chosen group
            System.out.println("What group analysis do you require?:");
            printAnalysisMenu();                                                                    // Print analysis options
            analysisType = getInt(1,4,"","Enter and integer from 1 to 4");                          // Prompt for analysis option needed
            System.out.println(); 
            switch(analysisType)                                                                    // Switch statement for completing analysis options
            {
                case 1: 
                    displaySortNetGoals(groupArray);                                                // Display net goals sorted
                    break;
                case 2:
                    displaySortGoalsLost(groupArray);                                               // Display goals against sorted
                    break;
                case 3:
                    displaySortGoals(groupArray);                                                   // Display goals for sorted
                    break;  
                case 4:
                    displayBestTeam(groupArray);                                                    // Display best performing team
                    break;
            }
        }
        else
        {
            System.out.println("No teams in group");
        }
    }
    
    /*
    METHOD: occupyGroupArray
    IMPORT: pTeamArray (Team), pGroupArray (Team), pChosenGroup (Integer)
    EXPORT: outGroupArray (Team)
    ASSERTION: Occupy the outGroupArray with teams within selected group
    */    
    public static Team[] occupyGroupArray(Team[] pTeamArray, Team[] pGroupArray, char pChosenGroup)
    {
        int numTeams = pTeamArray.length;                                                           // Find number of teams in input array
        int i = 0;
        int j = 0;
        while(i < numTeams)                                                                         // Iterate through pTeamArray
        {
            if(pTeamArray[i].getTeamGroup() == pChosenGroup)                                        // IF team group is chosen group put in group array at position j, increase j by 1 and i by 1
            {
                pGroupArray[j] = pTeamArray[i];                                                     
                j += 1;
            }
            i += 1;                                                                                 // If team group not in chosen group only increase i by 1
        }
        return pGroupArray;
    }

    /*
    METHOD: numTeamInGroup
    IMPORT: pTeamArray (Team), pChosenGroup (Char)
    EXPORT: outNumGroup (Integer)
    ASSERTION: get the number of teams within selected group
    */
    public static int numTeamInGroup(Team[] pTeamArray, char pChosenGroup)
    {
        int outNumGroup = 0;
        int numTeams = pTeamArray.length;                                                           // Get length of pTeam array i.e number of teams within
        for(int i = 0; i < numTeams; i++)                                                           // iterate through teams within array
        {
            if(pTeamArray[i].getTeamGroup() == pChosenGroup)                                        // If team group of team at position i is chosen group increase outNumGroup by 1
            {
                outNumGroup += 1;
            }
        }
    return outNumGroup;
    }
    
    /*
    METHOD: getInt
    IMPORT: pMin (Integer), pMax (Integer), pPrompt (String), 
    EXPORT: outInt (Integer)
    ASSERTION: Ask user with String pPrompt for integer between pMin and pMax, OutRangeError print when input outside range
    */
    public static int getInt(int pMin, int pMax, String pPrompt, String pOutRangeError)
    {
        Scanner input = new Scanner(System.in);
        boolean validInput = false;
        int outInt = 0;
        while(!validInput)                                                                          // loop prompt even if exception occurs
        {
            try                                                                                     // Exception handling for non Integer being input
            {
                do                                                                                  // Loop prompt if input int outside of required range
                {
                    System.out.println(pPrompt);                                                    // Print input Prompt
                    outInt = input.nextInt();                                                       // Int to return is input
                    if((outInt < pMin) || (outInt > pMax))                                          // Print outRangeError if input outside range
                    {
                        System.out.println(pOutRangeError);
                    }
                } while ((outInt < pMin) || (outInt >pMax)); 
                validInput = true;                                                                  // If program progresses to this point do-while has been exited, thus correct integer input
            }
            catch(InputMismatchException error)                                                     // Catch non Integer input and display message to re-enter, restart input
            {
                System.out.println("Enter an integer number character");
                input.nextLine();
            }
        }
        return outInt; 
    }
    
    /*
    METHOD: getCharGroup
    IMPORT: pPrompt (String)
    EXPORT: outChar (Character)
    ASSERTION: Ask for a group character with prompt, handle errors and exceptions
    */
    public static char getCharGroup(String pPrompt)
    {
        Scanner input = new Scanner(System.in);
        boolean validInput = false;
        char outChar = ' ';
        while(!validInput)                                                                          // Loop while errors are caught
        {
            try                                                                                     
            {
                while(!(outChar == 'A' || outChar == 'B' || outChar == 'C' || outChar == 'D' || outChar == 'E' || outChar == 'F' || outChar == 'G' || outChar == 'H' )) // Loop while invalid character input
                {
                    System.out.println(pPrompt);
                    outChar = input.next().charAt(0);                                               // Get character from user
                    if(!(outChar == 'A' || outChar == 'B' || outChar == 'C' || outChar == 'D' || outChar == 'E' || outChar == 'F' || outChar == 'G' || outChar == 'H' ))
                    {        
                        System.out.println("Team group invalid, enter group A, B, C, D, E, F, G or H"); // Print Invalid input if invalid character input
                    }
                }
                validInput = true;
            }
            catch(InputMismatchException error)                                                     // Handle incorrect input errors and notify user
            {
                System.out.println("Something went wrong! The error: " + error);
                input.nextLine();
            }
        }
        return outChar;
    }
    
    /*
    METHOD: getCharExit
    IMPORT: pPrompt (String)
    EXPORT: outChar (Character)
    ASSERTION: Ask for an exit character with prompt, handle errors and exceptions
    */
    public static char getCharExit(String pPrompt)
    {
        Scanner input = new Scanner(System.in);
        boolean validInput = false;
        char outChar = ' ';
        while(!validInput)                                                                          // Loop if errors occur
        {
            try
            {
                while(!(outChar == 'Y' || outChar == 'y' || outChar == 'N' || outChar == 'n'))      // loop while invalid input given
                {
                    System.out.println(pPrompt);
                    outChar = input.next().charAt(0);                                               // Get Input from user
                    if(!(outChar == 'Y' || outChar == 'y' || outChar == 'N' || outChar == 'n' ))
                    {        
                        System.out.println("Enter character Y or N");                               // Print error if invalid input
                    }
                }
                validInput = true;
            }
            catch(InputMismatchException error)                                                     // Catch invalid inputs and notify user
            {
                System.out.println("Something went wrong! The error: " + error);
                input.nextLine();
            }
        }
        return outChar;
    }
}

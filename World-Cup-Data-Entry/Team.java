/*
Program: Team Class
Author: Michael Durkan
Date: 1st October 2023
Date last modified: 12th October 2023 
Purpose: Construct, get, set, compare, validate, and export strings of Team Objects
*/
import java.util.*;

public class Team
{   
    // Class Fields
    private String teamName;
    private String teamCode;
    private int goalsScored;
    private int goalsLost;
    private char teamGroup;
    
    // Default Constructor
    // IMPORT: none
    // EXPORT: none
    // ASSERTION: Create a Team object with the default values
    public Team()
    {
        teamName = "DefaultTeam";
        teamCode = "DEF";
        goalsScored = 42;
        goalsLost = 42;
        teamGroup = 'I';    
    }

    // Copy Constructor
    // IMPORT: pTeam (Team)
    // EXPORT: none
    // ASSERTION: Create a copy of the import team object
    public Team(Team pTeam)
    {
        teamName = pTeam.getTeamName();
        teamCode = pTeam.getTeamCode();
        goalsScored = pTeam.getGoalsScored();
        goalsLost = pTeam.getGoalsLost();
        teamGroup = pTeam.getTeamGroup();
    }
    
    // Constructor with Parameters
    // IMPORT: pTeamName (String), pTeamCode (String), pGoalsScored (Integer), pGoalsLost (Integer), pTeamGroup (Character)
    // EXPORT: none
    // ASSERTION: Create team object with imported values, if import values are valid
    public Team(String pTeamName, String pTeamCode, int pGoalsScored, int pGoalsLost, char pTeamGroup)
    {
        if(validTeamName(pTeamName))                                                                    // Validity check for team name, use default name if incorrect
        {
            teamName = pTeamName;
        }
        else
        {
            teamName = "DefaultTeam";
        } 
        if(validTeamCode(pTeamCode))                                                                    // Validity check for team code, use default code if incorrect
        {
            teamCode = pTeamCode;
        }
        else
        {
            teamCode = "DEF";
        }
        if(validGoalsScored(pGoalsScored))                                                              // Validity check for goals for, use default if incorrect
        {
            goalsScored = pGoalsScored;
        }
        else
        {
            goalsScored = 42;
        }
        if(validGoalsLost(pGoalsLost))                                                                  // Validity check for goals against, use default if incorrect
        {
            goalsLost = pGoalsLost;
        }
        else
        {
            goalsLost = 42;
        }
        if(validTeamGroup(pTeamGroup))                                                                  // Validity check for team group, use default if incorrect
        {
            teamGroup = pTeamGroup;
        }
        else
        {
            teamGroup = 'I';
        }
    }

    // ACCESSOR: getTeamName
    // IMPORT: none
    // EXPORT: teamName (String)
    // ASSERTION: Return the team name
    public String getTeamName()
    {
        return teamName;
    }
    
    // ACCESSOR: getTeamCode
    // IMPORT: none
    // EXPORT: teamCode (String)
    // ASSERTION: Return the team code
    public String getTeamCode()
    {
        return teamCode;
    }
    
    // ACCESSOR: getGoalsScored
    // IMPORT: none
    // EXPORT: goalsScored (Integer)
    // ASSERTION: Return the goals scored by the team
    public int getGoalsScored()
    {
        return goalsScored;
    }

    // ACCESSOR: getGoalsLost
    // IMPORT: none
    // EXPORT: goalsLost (Integer)
    // ASSERTION: Return the goals scored against the team
    public int getGoalsLost()
    {
        return goalsLost;
    }

    // ACCESSOR: getTeamGroup
    // IMPORT: none
    // EXPORT: teamGroup (Character)
    // ASSERTION: Return the group of the team
    public char getTeamGroup()
    {
        return teamGroup;
    }
    
    // ACCESSOR: toString
    // IMPORT: none
    // EXPORT: stringValue (String)
    // ASSERTION: Exports readable string of the values within team object
    public String toString()
    {
        String stringValue;
        stringValue = "Team name: " + teamName + ". Team code: " + teamCode + ". Teams goals scored: " + goalsScored + ". Goals scored against team: " + goalsLost + ". Teams group: " + teamGroup;
        return stringValue;
    }
    
    // ACCESSOR: equals
    // IMPORT: inObject (Object)
    // EXPORT: isEqual (Boolean)
    // ASSERTION: Compares the equality of two team objects
    public boolean equals(Object inObject)
    {
        boolean isEqual = false;
        Team inTeam = null;
        if(inObject instanceof Team)
        {
            inTeam = (Team)inObject;
            if(teamName.equals(inTeam.getTeamName()))
            {
                if(teamCode.equals(inTeam.getTeamCode()))
                {
                    if(goalsScored == inTeam.getGoalsScored())
                    {
                        if(goalsLost == inTeam.getGoalsLost())
                        {
                            if(teamGroup == inTeam.getTeamGroup())
                            {
                                isEqual = true;   
                            }
                        }
                    }
                }
            }
        }
        return isEqual;
    }
    
    // MUTATOR: setTeamName
    // IMPORT: pTeamName (String)
    // EXPORT: none
    // ASSERTION: State of teamName is updated to pName value, if valid
    public void setTeamName(String pTeamName)
    {
        if(validTeamName(pTeamName))                                                                    // validity check, if invalid assign default
        {
            teamName = pTeamName;
        }
        else
        {
            teamName = "DefaultTeam";
        }
    }
    
    // MUTATOR: setTeamCode
    // IMPORT: pTeamCode (String)
    // EXPORT: none
    // ASSERTION: State of teamCode is updated to pTeamCode value, if valid
    public void setTeamCode(String pTeamCode)
    {
        if(validTeamCode(pTeamCode))                                                                    // validity check, if invalid assign default
        {
            teamCode = pTeamCode;
        }
        else
        {
            teamCode = "DEF";
        }
    }
    
    // MUTATOR: setGoalsScored
    // IMPORT: pGoalsScored (Integer)
    // EXPORT: none
    // ASSERTION: State of goalsScored is updated to pGoalsScored value, if valid
    public void setGoalsScored(int pGoalsScored)
    {
        if(validGoalsScored(pGoalsScored))                                                              // validity check, if invalid assign default
        {
            goalsScored = pGoalsScored;
        }
        else
        {
            goalsScored = 42;
        }
    }
    
    // MUTATOR: setGoalsLost
    // IMPORT: pGoalsLost (Integer)
    // EXPORT: none
    // ASSERTION: State of goalsLost is updated to pGoalsLost value, if valid
    public void setGoalsLost(int pGoalsLost)
    {
        if(validGoalsLost(pGoalsLost))                                                                  // validity check, if invalid assign default
        {
            goalsLost = pGoalsLost;
        }
        else
        {
            goalsLost = 42;
        }
    }
    
    // MUTATOR: setTeamGroup
    // IMPORT: pSetTeamGroup (Character)
    // EXPORT: none
    // ASSERTION: State of teamGroup is updated to pTeamGroup value, if valid
    public void setTeamGroup(char pTeamGroup)
    {
        if(validTeamGroup(pTeamGroup))                                                                  // validity check, if invalid assign default
        {
            teamGroup = pTeamGroup;
        }
        else
        {
            teamGroup = 'I';
        }
    }
    
    // METHOD: validTeamName
    // IMPORT: pTeamName (String)
    // EXPORT: isValidTeamName (Boolean)
    // ASSERTION: Validates if pTeamName is possible value
    private boolean validTeamName(String pTeamName)
    {
        boolean isValidTeamName = false;
        if((!(pTeamName.isEmpty())) && (pTeamName != null))                                             // If team name not empty and not null return is valid
        {
            isValidTeamName = true;
        }
        return isValidTeamName;
    }

    // METHOD: validTeamCode
    // IMPORT: pTeamCode (String)
    // EXPORT: isValidTeamCode (Boolean)
    // ASSERTION: Validates if pTeamCode is possible value
    private boolean validTeamCode(String pTeamCode)
    {
        boolean isValidTeamCode = false;                                                                // If team code not empty and not null return is valid
        if((!(pTeamCode.isEmpty())) && (pTeamCode != null))
        {
            isValidTeamCode = true;
        }
        return isValidTeamCode;
    }
    
    // METHOD: validGoalsScored
    // IMPORT: pGoalsScored (Integer)
    // EXPORT: isValidGoalsScored (Boolean)
    // ASSERTION: Validates if pGoalsScored is possible value
    private boolean validGoalsScored(int pGoalsScored)
    {
        boolean isValidGoalsScored = false;
        if(pGoalsScored >= 0)                                                                           // If goals scored >= 0 return is valid
        {
            isValidGoalsScored = true;
        }
        return isValidGoalsScored;
    }

    // METHOD: validGoalsLost
    // IMPORT: pGoalsLost (Integer)
    // EXPORT: isValidGoalsLost (Boolean)
    // ASSERTION: Validates if pGoalsLost is possible value
    private boolean validGoalsLost(int pGoalsLost)
    {
        boolean isValidGoalsLost = false;
        if(pGoalsLost >= 0)                                                                             // If goals against >= 0 return is valid
        {
            isValidGoalsLost = true;
        }
        return isValidGoalsLost;
    }

    // METHOD: validTeamGroup
    // IMPORT: pTeamGroup
    // EXPORT: isValidTeamGroup
    // ASSERTION: Validates if pTeamGroup is possible value
    private boolean validTeamGroup(char pTeamGroup)
    {
        boolean isValidTeamGroup = false;
        if(pTeamGroup == 'A' || pTeamGroup == 'B' || pTeamGroup == 'C' || pTeamGroup == 'D' || pTeamGroup == 'E' || pTeamGroup == 'F' || pTeamGroup == 'G' || pTeamGroup == 'H') // If team group A-H return is valid
        {
            isValidTeamGroup = true;
        }
        return isValidTeamGroup;
    }
    
    // METHOD: toFileString
    // IMPORT: none
    // EXPORT: fileString (String)
    // ASSERTION: Export Objecct as a row of comma seperated values, in a string
    public String toFileString()
    {
        String fileString = "";
        fileString = teamName + "," + teamCode + "," + goalsScored + "," + goalsLost + "," + teamGroup;
        return fileString;
    }
}

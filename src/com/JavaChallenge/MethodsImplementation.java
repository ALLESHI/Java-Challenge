package com.JavaChallenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodsImplementation {
    int nuOfTournaments;
    String tourName;
    int totalTeams;
    String teamName;
    int nuOfGames;

    Scanner input = new Scanner(System.in);

    public void getNoOfTour(){
        do {
            try {
                do {
                    System.out.print("Input the number of tournaments: ");
                    nuOfTournaments = Integer.parseInt(input.nextLine());
                    if(nuOfTournaments <= 0 || nuOfTournaments > 999){      // (0 < N < 1,000)
                        System.out.println("The number must be between 1 - 999");
                    }
                }while (nuOfTournaments <= 0 || nuOfTournaments > 999);
            }catch (NumberFormatException e){
                System.out.println("Invalid characters, only whole numbers!");
            }
        }while (nuOfTournaments <= 0 || nuOfTournaments > 999);
    }
    public void printMethod(){
        for(int m = 0; m < nuOfTournaments; m++) {
           getTournamentDescription(m);

           getNuOfTeams();

           Team[] teams = new Team[totalTeams];
           HashMap<String, Integer> teamId = new HashMap<>();

           getTeamName(teams, teamId);

           getNuOfGames();

           int goalTeam1;
           int goalTeam2;

           for (int i = 0; i < nuOfGames; i++) {
               boolean correctFinalGame;
               do {
                   System.out.print("Input the result of game " + (i + 1) + ": ");
                   String gameResult = input.nextLine().toLowerCase();

                   String finalGameResult = "[a-zA-Z]{1,30}[#][0-9]{1,2}[@][0-9]{1,2}[#][a-zA-Z]{1,30}";
                   Pattern pattern = Pattern.compile(finalGameResult);
                   Matcher matcher = pattern.matcher(gameResult);
                   correctFinalGame = matcher.matches();

                   if (getResultOfGame(correctFinalGame, gameResult)){
                       continue;
                   }

                   String[] parts = gameResult.split("[#@]");
                   String team1Name = parts[0];
                   String team2Name = parts[3];

                   if(team1Name.equalsIgnoreCase(team2Name)){
                       System.out.println("Invalid input!, no team will play against itself");
                       return;
                   }

                   goalTeam1 = Integer.parseInt(parts[1]);
                   goalTeam2 = Integer.parseInt(parts[2]);

                   if ((goalTeam1 < 0 || goalTeam1 > 19) || (goalTeam2 < 0 || goalTeam2 > 19)) {  // All goals will be non-negative integers less than 20
                       System.out.println("All goals must be between 0 - 19!");
                       continue;
                   }
                   int idTeam1 = teamId.get(team1Name.toLowerCase());
                   int idTeam2 = teamId.get(team2Name.toLowerCase());

                   teams[idTeam1].addGame(goalTeam1, goalTeam2);
                   teams[idTeam2].addGame(goalTeam2, goalTeam1);

               }while (!correctFinalGame);
           }
           Arrays.sort(teams);

           System.out.println();
           System.out.println(tourName);
           finalOutput(teams);
           System.out.println();
        }
    }

    private void getNuOfTeams() {
        do{
            try {
                do {
                    System.out.print("Input the number of participating teams: ");
                    totalTeams = Integer.parseInt(input.nextLine());
                    if (totalTeams < 2 || totalTeams > 30) {
                        System.out.println("Number of teams must be between 2 - 30!");
                    }
                } while (totalTeams < 2 || totalTeams > 30);
            }catch (Exception e) {
                System.out.println("Invalid characters, only whole numbers between 2 - 30!");
            }
        }while (totalTeams < 2 || totalTeams > 30);
    }

    private void getNuOfGames() {
        do {
            try {
                System.out.print("Input number of games: ");
                nuOfGames = Integer.parseInt(input.nextLine());
                if (nuOfGames <= 0 || nuOfGames > 1000) System.out.println("Number of games should be between 1 - 1,000!");
            }catch (NumberFormatException e){
                System.out.println("Invalid characters, only whole numbers between 1 - 1,000!");
            }
        } while (nuOfGames <= 0 || nuOfGames > 1000);
    }

    private void getTournamentDescription(int m) {
        do {
            System.out.print("Input the description of tournament " + (m + 1) + ": ");
            tourName = input.nextLine();
            if (tourName.length() > 100) {
                System.out.println("Tournament descriptions should not extend 100 characters (with spaces)!");
            }
        } while (tourName.length() > 100);
    }

    private boolean getResultOfGame(boolean correctFinalGame, String gameResult) {
        if (!correctFinalGame){
            if (gameResult.isBlank()){
                System.out.println("Result of the game must not be blank!");
            }else {
                System.out.println("Invalid input! Please use the format \"team name 1#goals1@goals2#team name 2\" !");
            }
            return true;
        }
        return false;
    }

    private void finalOutput(Team[] teams) {
        for (int i = 0; i < totalTeams; i++) {
            String finalTeamName = teams[i].teamName.substring(0, 1).toUpperCase() + teams[i].teamName.substring(1);
            System.out.println((i + 1) + ") " + finalTeamName + " "
                    + teams[i].totalPoints + "p, "
                    + teams[i].gamePlayed + "g("
                    + teams[i].gameWins + "-"
                    + teams[i].gameDraw + "-"
                    + teams[i].gameLoses + "), "
                    + teams[i].goalDiff + "gd("
                    + teams[i].goalScored + "-"
                    + teams[i].goalAgainst + ")");
        }
    }

    private void getTeamName(Team[] teams, HashMap<String, Integer> teamId) {
        for (int i = 0; i < totalTeams; i++) {
            do {
                System.out.print("Input team " + (i + 1) + ": ");
                teamName = input.nextLine().toLowerCase();
                teamId.put(teamName, i);
                teams[i] = new Team(teamName);

                if(teamName.isBlank()){
                    System.out.println("Team names should not be empty!");
                }
                if (teamName.length() > 30){
                    System.out.println("Team names should not extend 30 characters!");
                }
                if (teamName.contains("#") || teamName.contains("@")){
                    System.out.println("Invalid characters! Team name should not contain '@' or '#'!");
                }
            } while ((teamName.length() > 30 || teamName.isBlank()) || (teamName.contains("#") || teamName.contains("@")));
        }
    }
}
package com.JavaChallenge;

public class Team implements Comparable<Team>{
    String teamName;
    int gameWins;
    int gameLoses;
    int gameDraw;
    int gamePlayed;
    int totalPoints;
    int goalScored;
    int goalAgainst;
    int goalDiff;

    Team(String name){
        this.teamName = name;
    }
    public void addGame(int goalScored, int goalAgainst){
        this.goalScored += goalScored;
        this.goalAgainst += goalAgainst;
        this.goalDiff = this.goalScored - this.goalAgainst;
        this.gamePlayed++;
        if(goalScored > goalAgainst){
            this.gameWins++;
            this.totalPoints += 3;
        }
        else if(goalScored < goalAgainst){
            this.gameLoses++;
        }
        else{
            this.gameDraw++;
            this.totalPoints++;
        }
    }

    @Override
    public int compareTo(Team t) {    // Teams are ranked according to these rules (in this order):
        if(this.totalPoints != t.totalPoints) return t.totalPoints - this.totalPoints;
        if(this.gameWins != t.gameWins) return t.gameWins - this.gameWins;
        if(this.goalDiff != t.goalDiff) return t.goalDiff - this.goalDiff;
        if(this.goalScored != t.goalScored) return t.goalScored - this.goalScored;
        if(this.gamePlayed != t.gamePlayed) return t.gamePlayed - this.gamePlayed;
        return this.teamName.compareToIgnoreCase(t.teamName);
    }
}

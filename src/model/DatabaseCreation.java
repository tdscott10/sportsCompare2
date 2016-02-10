package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// @formatter:off
public class DatabaseCreation {

   /*
    * Create the professional database
    * Create Tables Player, Team, GameLog, and Stats
    */
   public void createProfessionalDatabase() {
      Connection c = null;
      Statement stmt = null;

      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:professional.db");
         c.createStatement().execute("PRAGMA foreign_keys = ON");
         stmt = c.createStatement();
         
         String sql = "drop table if exists Stats;";
         stmt.executeUpdate(sql);
         sql = "drop table if exists GameLog;";
         stmt.executeUpdate(sql);
         sql = "drop table if exists Player;";
         stmt.executeUpdate(sql);
         sql = "drop table if exists Team;";
         stmt.executeUpdate(sql);

         sql = "CREATE TABLE Team(" +
            "TeamID         TEXT    PRIMARY KEY," +
            "TeamName       TEXT," +
            "Logo           TEXT," +
            "PrimaryColor   TEXT," +
            "SecondaryColor TEXT);";
         stmt.executeUpdate(sql);

         sql = "CREATE TABLE Player(" +
            "PlayerID       TEXT    PRIMARY KEY," +
            "PlayerName     TEXT," +
            "Team           TEXT," +
            "Position       TEXT," +
            "Picture        TEXT," +
            "Height         INT," +
            "Weight         INT," +
            "BirthDate      TEXT," +
            "College        TEXT," +
            "Number         INT," +
            "FOREIGN KEY(Team) REFERENCES Team(TeamID));";
         stmt.executeUpdate(sql);

         sql = "CREATE TABLE GameLog(" +
            "GameID         TEXT    PRIMARY KEY," +
            "Date           TEXT," +
            "Team           TEXT," +
            "Opponent       TEXT," +
            "Score          TEXT," +
            "FOREIGN KEY(Team) REFERENCES Team(TeamID)," +
            "FOREIGN KEY(Opponent) REFERENCES Team(TeamID));";
         stmt.executeUpdate(sql);

         sql = "CREATE TABLE Stats(" +
            "StatsID        INTEGER    PRIMARY KEY    AUTOINCREMENT," +
            "GameID         TEXT," +
            "PlayerID       TEXT," +
            "Yards            INT," +
            "TDs            INT," +
            "Att            INT," +
            "Average        REAL," +
            "FOREIGN KEY(GameID) REFERENCES GameLog(GameID)," +
            "FOREIGN KEY(PlayerID) REFERENCES Player(PlayerID));";
         stmt.executeUpdate(sql);
         stmt.close();
         c.close();
      } catch (Exception e) {
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
      } finally {
         try {
            if(c != null)
                c.close();
         } catch (SQLException se){
            se.printStackTrace();
         }
      }
   }
   /*
    * Create the individual database
    */
   public void createIndividualDatabase() {
       Connection c = null;
       Statement stmt = null;
       
       try {
           Class.forName("org.sqlite.JDBC");
           c = DriverManager.getConnection("jdbc:sqlite:individual.db");
           c.createStatement().execute("PRAGMA foreign_keys = ON");
           stmt = c.createStatement();
           
           String sql = "drop table if exists Stats;";
           stmt.executeUpdate(sql);
           sql = "drop table if exists GameLog;";
           stmt.executeUpdate(sql);
           sql = "drop table if exists Sport;";
           stmt.executeUpdate(sql);
           sql = "drop table if exists User;";
           stmt.executeUpdate(sql);
           
           sql = "CREATE TABLE User(" +
           "UserID         INTEGER       PRIMARY KEY    AUTOINCREMENT," +
           "Email          TEXT  UNIQUE," +
           "Password       TEXT," +
           "FirstName      TEXT," +
           "LastName       TEXT," +
           "BirthDate	   TEXT);";
           stmt.executeUpdate(sql);
           
           sql = "CREATE TABLE Sport(" +
           "UserID        INTEGER," +
           "SportName     TEXT," +
           "Position      TEXT," +
           "FavoriteTeam  TEXT," +
           "PRIMARY KEY (UserID, SportName)," +
           "FOREIGN KEY(UserID) REFERENCES User(UserID));";
           stmt.executeUpdate(sql);
           
           sql = "CREATE TABLE GameLog(" +
           "GameID         INTEGER    PRIMARY KEY AUTOINCREMENT," +
           "UserID         INT," +
           "Week           INT," +
           "Date           TEXT," +
           "Opponent       TEXT," +
           "Score          TEXT," +
           "FOREIGN KEY(UserID) REFERENCES User(UserID));";
           stmt.executeUpdate(sql);
           
           sql = "CREATE TABLE Stats(" +
           "StatsID        INTEGER    PRIMARY KEY	AUTOINCREMENT," +
           "GameID         INT," +
           "UserID         INT," +
           "Yds            INT," +
           "TDs            INT," +
           "Att            INT," +
           "Average        REAL," +
           "ProStatsID     INT," +
           "FOREIGN KEY(GameID) REFERENCES GameLog(GameID)," +
           "FOREIGN KEY(UserID) REFERENCES User(UserID));";
           stmt.executeUpdate(sql);
           stmt.close();
           c.close();
       } catch (Exception e) {
           System.err.println(e.getClass().getName() + ": " + e.getMessage());
       } finally {
           try {
               if(c != null)
                   c.close();
           } catch (SQLException se){
               se.printStackTrace();
           }
       }
   }
}

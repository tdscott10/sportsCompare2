/**
 * CompareDao Implementation
 *
 * @author Taylor Scott (tdscott2@asu.edu)
 * @version Mar 31, 2015
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Compare;

public class CompareDaoImpl implements CompareDao {

    private static final int POSITION_TEAM = 2;
    
    @Override
    public int playerComparison(float average, String position, String favTeam) {
        Connection conPro = DaoFactory.createConnectionProfessional();
       
        PreparedStatement stmt = null;
        ResultSet resultSet;
        int result = -1;
        float averageDifference = 0.005f;
        try {
            String sql = "SELECT StatsID " +
            "FROM Stats " +
            "INNER JOIN Player " +
            "ON Stats.PlayerID=Player.PlayerID " +
            "AND Player.Position = ? " +
            "INNER JOIN Team " +
            "ON (Team.TeamID = Player.Team) " +
            "AND Team.TeamName = ? " +
            "WHERE Stats.Average > ? AND Stats.Average < ?;";
            stmt = conPro.prepareStatement(sql);
            stmt.setString(1, position);
            stmt.setString(2, favTeam);
            stmt.setFloat(3, average-0.1f);
            stmt.setFloat(4, average+0.1f);
            
            resultSet = stmt.executeQuery();
            if(resultSet.next())
                result = resultSet.getInt("StatsID");
            else {
                while(result == -1) {
                    sql = "SELECT StatsID " +
                    "FROM Stats " +
                    "INNER JOIN Player " +
                    "ON Stats.PlayerID=Player.PlayerID " +
                    "AND Player.Position = ? " +
                    "WHERE Stats.Average > ? AND Stats.Average < ?;";
                    stmt = conPro.prepareStatement(sql);
                    stmt.setString(1, position);
                    stmt.setFloat(2, average-averageDifference);
                    stmt.setFloat(3, average+averageDifference);
                
                    resultSet = stmt.executeQuery();
                    if(resultSet.next())
                        result = resultSet.getInt("StatsID");
                    averageDifference += 0.005f;
                }
            }
            
        } catch(Exception e) {
            System.err.println(e.getClass().getName() + ": "
                               + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    DaoFactory.closeConnection(conPro);
                }
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": "
                                   + e.getMessage());
            }
        }
        return result;
    }
    
    @Override
    public boolean insertStat(int proStatsId, int gameId) {
        Connection conInd = DaoFactory.createConnectionIndividual();
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String insert = "UPDATE Stats SET ProStatsID = ? " +
            "WHERE GameID = ?";
            stmt = conInd.prepareStatement(insert);
            stmt.setFloat(1, proStatsId);
            stmt.setInt(2, gameId);
            stmt.execute();
        } catch(Exception e) {
            System.err.println(e.getClass().getName() + ": "
                               + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    DaoFactory.closeConnection(conInd);
                }
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": "
                                   + e.getMessage());
            }
        }
    return result;
    }
    
    @Override
    public String[] userPositionTeam(int userId) {
        Connection con = DaoFactory.createConnectionIndividual();
        PreparedStatement stmt = null;
        ResultSet resultSet;
        String[] result = new String[POSITION_TEAM];
        try {
            String sql = "SELECT Position, FavoriteTeam " +
            "FROM Sport Where UserID = ? AND SportName = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, "Football");
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                result[0] = resultSet.getString("Position");
                result[1] = resultSet.getString("FavoriteTeam");
            }
        } catch(Exception e) {
            System.err.println(e.getClass().getName() + ": "
                               + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    DaoFactory.closeConnection(con);
                }
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": "
                                   + e.getMessage());
            }
        }
        return result;
    }
    
    @Override
    public int findStatsId(int gameId) {
        Connection con = DaoFactory.createConnectionIndividual();
        PreparedStatement stmt = null;
        ResultSet resultSet;
        int result = -1;
        try {
            String sql = "SELECT ProStatsID FROM Stats WHERE GameID = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, gameId);
            resultSet = stmt.executeQuery();
            if (resultSet.next())
                result = resultSet.getInt("ProStatsID");
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    DaoFactory.closeConnection(con);
                }
            }
            catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return result;
    }
    
    @Override
    public Compare inputStats(int gameId, int statsId) {
        Connection conInd = DaoFactory.createConnectionIndividual();
        Connection conPro = DaoFactory.createConnectionProfessional();
        PreparedStatement stmt = null;
        ResultSet resultSet;
        Compare result = new Compare();
        try {
        	// TODO: refactor
            String sql = "SELECT * FROM Stats WHERE GameID = ?";
            stmt = conInd.prepareStatement(sql);
            stmt.setInt(1, gameId);
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                result.setUserAttempts(resultSet.getInt("Att"));
                result.setUserYards(resultSet.getInt("Yds"));
                result.setUserTouchdowns(resultSet.getInt("TDs"));
                System.out.println(resultSet.getInt("TDs"));
            }
            
            stmt.close();
            sql = "SELECT * FROM Stats WHERE StatsID = ?";
            stmt = conPro.prepareStatement(sql);
            stmt.setInt(1, statsId);
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                result.setProAttempts(resultSet.getInt("Att"));
                result.setProYards(resultSet.getInt("Yards"));
                result.setProTouchdowns(resultSet.getInt("TDs"));
                
                result.setProGameId(resultSet.getString("GameId"));
                
                System.out.println(resultSet.getInt("TDs"));
            }
            
            stmt.close();
            sql = "SELECT * FROM GameLog WHERE GameId = ?";
            stmt = conPro.prepareStatement(sql);
            stmt.setString(1, result.getProGameId());
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                result.setGameDate(resultSet.getString("Date"));
                result.setGameTeam1(resultSet.getString("Team"));
                result.setGameTeam2(resultSet.getString("Opponent"));
                result.setGameScore(resultSet.getString("Score"));

            }

        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    DaoFactory.closeConnection(conInd);
                    DaoFactory.closeConnection(conPro);
                }
            }
            catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return result;
    }
    
    @Override
    public Compare qcInputStats(int statsId) {
        Connection conPro = DaoFactory.createConnectionProfessional();
        PreparedStatement stmt = null;
        ResultSet resultSet;
        Compare result = new Compare();
        try {
            String sql = "SELECT * FROM Stats WHERE StatsID = ?";
            stmt = conPro.prepareStatement(sql);
            stmt.setInt(1, statsId);
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                result.setProAttempts(resultSet.getInt("Att"));
                result.setProYards(resultSet.getInt("Yards"));
                result.setProTouchdowns(resultSet.getInt("TDs"));
                
                result.setProGameId(resultSet.getString("GameId"));
                
                System.out.println(resultSet.getInt("TDs"));
            }
            
            stmt.close();
            sql = "SELECT * FROM GameLog WHERE GameId = ?";
            stmt = conPro.prepareStatement(sql);
            stmt.setString(1, result.getProGameId());
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                result.setGameDate(resultSet.getString("Date"));
                result.setGameTeam1(resultSet.getString("Team"));
                result.setGameTeam2(resultSet.getString("Opponent"));
                result.setGameScore(resultSet.getString("Score"));
                
            }
            
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    DaoFactory.closeConnection(conPro);
                }
            }
            catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return result;
    }
}

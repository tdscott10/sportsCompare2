package model;

import static model.Stats.compareAlgorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuildProfessionalDB {
	
	private static final String DB_URL = "jdbc:sqlite:professional.db";
	private static final String DRIVER = "org.sqlite.JDBC";
	
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	public static JSONObject readJsonFromUrl(String url) throws IOException,
			JSONException {
		InputStream is = null;
		URLConnection openConnection = new URL(url).openConnection();
		openConnection
				.addRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
		try {
			is = openConnection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		}
		finally {
			if (is != null)
				is.close();
		}
	}
	
	public static void teamPrint() {
		JSONObject jsonPlayer;
		Connection c = null;
		PreparedStatement stmt = null;
		try {
			
			jsonPlayer = readJsonFromUrl("http://api.sportsdatallc.org/nfl-t1/teams/hierarchy.json?api_key=8q8cz7sy9wuqv8u9ba8mfsa5");
			JSONArray confArr = jsonPlayer.getJSONArray("conferences");
			for (int i = 0; i < confArr.length(); i++) {
				JSONObject conf = confArr.getJSONObject(i);
				JSONArray divArr = conf.getJSONArray("divisions");
				for (int j = 0; j < divArr.length(); j++) {
					JSONObject div = divArr.getJSONObject(j);
					JSONArray teams = div.getJSONArray("teams");
					for (int k = 0; k < teams.length(); k++) {
						JSONObject jsonOBject = teams.getJSONObject(k);
						try {
							c = getConnection();
							c.setAutoCommit(false);
							String sql = "INSERT INTO Team (TeamID, TeamName) "
									+ "VALUES (?,?)";
							stmt = c.prepareStatement(sql);
							stmt.setString(1, jsonOBject.getString("id").toString());
							stmt.setString(2, jsonOBject.get("market").toString() + " "
									+ jsonOBject.get("name").toString());
							stmt.executeUpdate();
						}
						catch (Exception e1) {
							System.err.println(e1.getClass().getName() + ": "
									+ e1.getMessage());
						}
						finally {
							try {
								stmt.close();
								c.commit();
								c.close();
							}
							catch (Exception e2) {
								System.err.println(e2.getClass().getName() + ": "
										+ e2.getMessage());
							}
						}
					}
				}
			}
		}
		catch (JSONException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		catch (IOException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	public static void playersPrint() {
		JSONObject jsonTeam;
		JSONObject jsonPlayer;
		Connection c = null;
		PreparedStatement stmt = null;
		try {
			jsonTeam = readJsonFromUrl("http://api.sportsdatallc.org/nfl-t1/teams/hierarchy.json?api_key=8q8cz7sy9wuqv8u9ba8mfsa5");
			JSONArray confArr = jsonTeam.getJSONArray("conferences");
			for (int i = 0; i < confArr.length(); i++) {
				JSONObject conf = confArr.getJSONObject(i);
				JSONArray divArr = conf.getJSONArray("divisions");
				for (int j = 0; j < divArr.length(); j++) {
					JSONObject div = divArr.getJSONObject(j);
					JSONArray teams = div.getJSONArray("teams");
					for (int k = 0; k < teams.length(); k++) {
						JSONObject team = teams.getJSONObject(k);
						try {
							TimeUnit.SECONDS.sleep(1);
						}
						catch (InterruptedException e) {
							System.err.println(e.getClass().getName() + ": "
									+ e.getMessage());
						}
						jsonPlayer = readJsonFromUrl("http://api.sportsdatallc.org/nfl-t1/teams/"
								+ team.get("id").toString()
								+ "/roster.json?api_key=8q8cz7sy9wuqv8u9ba8mfsa5");
						JSONArray players = jsonPlayer.getJSONArray("players");
						for (int l = 0; l < players.length(); l++) {
							JSONObject player = players.getJSONObject(l);
							try {
								c = getConnection();
								c.setAutoCommit(false);
								String sql = "INSERT INTO Player (PlayerID, PlayerName, Team, Position, Height, Weight, BirthDate, College, Number) "
										+ "VALUES (?,?,?,?,?,?,?,?,?)";
								stmt = c.prepareStatement(sql);
								stmt.setString(1, player.get("id").toString());
								stmt.setString(2, player.get("name_full").toString());
								stmt.setString(3, team.get("id").toString());
								stmt.setString(4, player.get("position").toString());
								stmt.setString(5, player.get("height").toString());
								stmt.setString(6, player.get("weight").toString());
								stmt.setString(7, player.get("birthdate").toString());
								stmt.setString(8, player.get("college").toString());
								stmt.setString(9, player.get("jersey_number").toString());
								stmt.executeUpdate();
							}
							catch (Exception e1) {
								System.err.println(e1.getClass().getName() + ": "
										+ e1.getMessage());
							}
							finally {
								try {
									stmt.close();
									c.commit();
									c.close();
								}
								catch (Exception e2) {
									System.err.println(e2.getClass().getName() + ": "
											+ e2.getMessage());
								}
							}
						}
					}
				}
			}
		}
		catch (JSONException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		catch (IOException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	public static void statsPrint() {
		JSONObject jsonSeason;
		JSONObject jsonStats;
		Connection connection = null;
		PreparedStatement sqlStatement = null;
		try {
			jsonSeason = readJsonFromUrl("http://api.sportsdatallc.org/nfl-t1/2014/REG/schedule.json?api_key=8q8cz7sy9wuqv8u9ba8mfsa5");
			JSONArray weekArr = jsonSeason.getJSONArray("weeks");
			for (int i = 0; i < weekArr.length(); i++) {
				JSONObject week = weekArr.getJSONObject(i);
				JSONArray gameArr = week.getJSONArray("games");
				for (int j = 0; j < gameArr.length(); j++) {
					JSONObject game = gameArr.getJSONObject(j);
					try {
						TimeUnit.SECONDS.sleep(1);
					}
					catch (InterruptedException e) {
						System.err
								.println(e.getClass().getName() + ": " + e.getMessage());
					}
					jsonStats = readJsonFromUrl("http://api.sportsdatallc.org/nfl-t1/2014/REG/"
							+ week.get("number").toString()
							+ "/"
							+ game.get("away").toString()
							+ "/"
							+ game.get("home").toString()
							+ "/statistics.json?api_key=8q8cz7sy9wuqv8u9ba8mfsa5");
					
					JSONObject homeTeam = jsonStats.getJSONObject("home_team");
					JSONObject statsHome = homeTeam.getJSONObject("statistics");
					JSONObject awayTeam = jsonStats.getJSONObject("away_team");
					JSONObject statsAway = awayTeam.getJSONObject("statistics");
					
					try {
						connection = getConnection();
						connection.setAutoCommit(false);
						
						String sql = "INSERT INTO GameLog (GameID, Date, Team, Opponent, Score) "
								+ "VALUES (?,?,?,?,?)";
						sqlStatement = connection.prepareStatement(sql);
						sqlStatement.setString(1, game.get("id").toString());
						sqlStatement.setString(2, game.get("scheduled").toString());
						sqlStatement.setString(3, game.get("home").toString());
						sqlStatement.setString(4, game.get("away").toString());
						sqlStatement.setString(5, homeTeam.get("points").toString() + "-"
								+ awayTeam.get("points").toString());
						sqlStatement.executeUpdate();
					}
					catch (Exception e1) {
						System.err.println(e1.getClass().getName() + ": "
								+ e1.getMessage());
					}
					finally {
						try {
							sqlStatement.close();
							connection.commit();
							connection.close();
						}
						catch (Exception e2) {
							System.err.println(e2.getClass().getName() + ": "
									+ e2.getMessage());
						}
					}
					
					// Home Team Rushing Stats
					JSONObject rushHome = statsHome.getJSONObject("rushing");
					insertPlayerStatsFrom(rushHome, game);
					
					// Home Team Passing Stats
					JSONObject passHome = statsHome.getJSONObject("passing");
					insertPlayerStatsFrom(passHome, game);
					
					// Home Team Receiving Stats
					JSONObject recHome = statsHome.getJSONObject("receiving");
					insertReceiverStatsFrom(recHome, game);
					
					// Away Team Rushing Stats
					JSONObject rushAway = statsAway.getJSONObject("rushing");
					insertPlayerStatsFrom(rushAway, game);
					
					// Away Team Passing Stats
					JSONObject passAway = statsAway.getJSONObject("passing");
					insertPlayerStatsFrom(passAway, game);
					
					// Away Team Receiving Stats
					JSONObject recAway = statsAway.getJSONObject("receiving");
					insertReceiverStatsFrom(recAway, game);
				}
			}
		}
		catch (IOException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	private static void insertPlayerStatsFrom(JSONObject statsObject, JSONObject game) {
		JSONArray players = statsObject.getJSONArray("players");
		for (int k = 0; k < players.length(); k++) {
			JSONObject player = players.getJSONObject(k);
			insertPlayerStats(player, game);
		}
	}
    
    private static void insertReceiverStatsFrom(JSONObject statsObject, JSONObject game) {
        JSONArray players = statsObject.getJSONArray("players");
        for (int k = 0; k < players.length(); k++) {
            JSONObject player = players.getJSONObject(k);
            insertReceiverStats(player, game);
        }
    }
	
	private static void insertPlayerStats(JSONObject player, JSONObject game) {
		Connection connection = null;
		PreparedStatement sqlStatement = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			
			String sql = "INSERT INTO Stats(GameID, PlayerID, Yards, TDs, Att, Average) "
					+ "VALUES (?,?,?,?,?,?)";
			
			sqlStatement = connection.prepareStatement(sql);
			sqlStatement.setString(1, game.get("id").toString());
			sqlStatement.setString(2, player.get("id").toString());
			sqlStatement.setInt(3, player.getInt("yds"));
			sqlStatement.setInt(4, player.getInt("td"));
			sqlStatement.setInt(5, player.getInt("att"));
			sqlStatement.setFloat(
					6,
					compareAlgorithm(player.getInt("yds"), player.getInt("td"),
							player.getInt("att")));
			sqlStatement.executeUpdate();
			
		}
		catch (Exception e1) {
			System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
		}
		finally {
			try {
				sqlStatement.close();
				connection.commit();
				connection.close();
			}
			catch (Exception e2) {
				System.err.println(e2.getClass().getName() + ": " + e2.getMessage());
			}
		}
	}
    
    private static void insertReceiverStats(JSONObject player, JSONObject game) {
        Connection connection = null;
        PreparedStatement sqlStatement = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            
            String sql = "INSERT INTO Stats(GameID, PlayerID, Yards, TDs, Att, Average) "
            + "VALUES (?,?,?,?,?,?)";
            
            sqlStatement = connection.prepareStatement(sql);
            sqlStatement.setString(1, game.get("id").toString());
            sqlStatement.setString(2, player.get("id").toString());
            sqlStatement.setInt(3, player.getInt("yds"));
            sqlStatement.setInt(4, player.getInt("td"));
            sqlStatement.setInt(5, player.getInt("rec"));
            sqlStatement.setFloat(
                                  6,
                                  compareAlgorithm(player.getInt("yds"), player.getInt("td"),
                                                   player.getInt("rec")));
            sqlStatement.executeUpdate();
            
        }
        catch (Exception e1) {
            System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
        }
        finally {
            try {
                sqlStatement.close();
                connection.commit();
                connection.close();
            }
            catch (Exception e2) {
                System.err.println(e2.getClass().getName() + ": " + e2.getMessage());
            }
        }
    }

	
	public static Connection getConnection() throws ClassNotFoundException {
		Class.forName(DRIVER);
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DB_URL);
		}
		catch (SQLException ex) {
			System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
		}
		return connection;
	}
	
	public static void addPhotosFromCBS() {
		JSONObject jsonPlayer;
		Connection c = null;
		PreparedStatement stmt = null;
		try {
			jsonPlayer = readJsonFromUrl("http://api.cbssports.com/fantasy/players/list?version=3.0&SPORT=football&response_format=JSON");
			JSONObject body = (JSONObject) jsonPlayer.get("body");
			JSONArray players = body.getJSONArray("players");
			for (int i = 0; i < players.length(); i++) {
				JSONObject player = players.getJSONObject(i);
				try {
					c = getConnection();
					c.setAutoCommit(false);
					String sql = "UPDATE Player SET Picture=? " + "WHERE PlayerName=?";
					stmt = c.prepareStatement(sql);
					stmt.setString(1, player.get("photo").toString());
					stmt.setString(2, player.get("fullname").toString());
					stmt.executeUpdate();
				}
				catch (Exception e1) {
					System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
				}
				finally {
					try {
						stmt.close();
						c.commit();
						c.close();
					}
					catch (Exception e2) {
						System.err.println(e2.getClass().getName() + ": "
								+ e2.getMessage());
					}
				}
			}
		}
		catch (JSONException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		catch (IOException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		teamPrint();
		try {
			TimeUnit.SECONDS.sleep(1);
		}
		catch (InterruptedException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		playersPrint();
		try {
			TimeUnit.SECONDS.sleep(1);
		}
		catch (InterruptedException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		statsPrint();
		addPhotosFromCBS();
	}
}

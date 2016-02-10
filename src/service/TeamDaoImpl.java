package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Team;
import model.TeamInfo;

public class TeamDaoImpl implements TeamDao{

	@Override
	public List<Team> findAll() {
		Connection con = DaoFactory.createConnectionProfessional();
		PreparedStatement stmt = null;
		ResultSet resultSet;
		List<Team> teams = null;
		try {
			teams = new ArrayList<Team>();

			String sql = "SELECT TeamID, TeamName, Logo, PrimaryColor, SecondaryColor FROM Team";

			stmt = con.prepareStatement(sql);
			resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				teams.add( new Team(resultSet.getString(1), resultSet.getString(2), 
									resultSet.getString(3), resultSet.getString(4), 
									resultSet.getString(5)));
			}

		} catch (Exception exc) {
			System.err.println(exc.getClass().getName() + ":"
					+ exc.getMessage());
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

		return teams;
	}
    
    @Override
    public TeamInfo findTeamInfo(String teamId) {
        Connection con = DaoFactory.createConnectionProfessional();
        PreparedStatement stmt = null;
        ResultSet resultSet;
        TeamInfo info = new TeamInfo();
        try {
            String sql = "SELECT * FROM Team WHERE TeamID = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, teamId);
            resultSet = stmt.executeQuery();
            if(resultSet.next()) {
                info.setTeamId(resultSet.getString("TeamID"));
                info.setTeamName(resultSet.getString("TeamName"));
                info.setTeamPicture(resultSet.getString("Logo"));
                info.setPrimaryColor(resultSet.getString("PrimaryColor"));
                info.setSecondaryColor(resultSet.getString("SecondaryColor"));
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
        return info;
    }
}

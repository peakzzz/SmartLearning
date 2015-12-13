package edu.sjsu.assess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.User;
import edu.sjsu.assess.util.Utility;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	public DataSource dataSource;

	public User createUser(final User usr) throws DAOException {

		final StringBuilder query = new StringBuilder();
		StringBuilder valuesClause = new StringBuilder();

		query.append("INSERT INTO users (login, fname, lname, password, role ");
		valuesClause.append("VALUES (?,?,?,?,?");

		String birthDateStr = usr.getBirthDate();
		final long birthDateLong = birthDateStr != null ? Utility
				.getDateLongFromStr(birthDateStr) : 0;
		if (birthDateLong != 0) {
			query.append(", birthdate");
			valuesClause.append(",?");
		}

		final String profession = usr.getProfession();
		if (profession != null && !profession.isEmpty()) {
			query.append(", profession");
			valuesClause.append(",?");
		}

		final String education = usr.getEducation();
		if (education != null && !education.isEmpty()) {
			query.append(", education");
			valuesClause.append(",?");
		}

		final String careerLevel = usr.getCareerLevel();
		if (careerLevel != null && !careerLevel.isEmpty()) {
			query.append(", careerLevel");
			valuesClause.append(",?");
		}

		query.append(") ");
		valuesClause.append(")");
		query.append(valuesClause);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
					PreparedStatement ps = con.prepareStatement(
							query.toString(), new String[] { "id" });

					int index = 1;

					ps.setString(index++, usr.getLogin());
					ps.setString(index++, usr.getFname());
					ps.setString(index++, usr.getLname());
					ps.setString(index++, usr.getPassword());
					ps.setString(index++, usr.getRole());

					if (birthDateLong != 0) {
						ps.setLong(index++, birthDateLong);
					}

					if (profession != null) {
						ps.setString(index++, profession.toString());
					}

					if (education != null) {
						ps.setString(index++, education.toString());
					}

					if (careerLevel != null) {
						ps.setString(index++, careerLevel.toString());
					}

					return ps;
				}
			}, keyHolder);

			usr.setId(keyHolder.getKey().intValue());

			// Save job preferences of this user in DB
			this.createUserJobPreferences(usr.getId(), usr.getJobPreferences());

		} catch (Exception e) {
			DAOException daoe = new DAOException("Failed to insert user in DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return usr;
	}

	/**
	 * Method to save user's job preferences in database.
	 * 
	 * @param userID
	 * @param jobPreferenceList
	 * @throws DAOException
	 */
	@Override
	public void createUserJobPreferences(final Integer userID,
			Set<Integer> jobPreferenceList) throws DAOException {

		if (jobPreferenceList == null || jobPreferenceList.size() <= 0) {
			return;
		}

		final StringBuilder query = new StringBuilder();
		query.append("INSERT INTO userJobPreference(userID, jobCodeID) ");
		query.append("VALUES (?,?)");

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			for (final Integer jobID : jobPreferenceList) {

				jdbcTemplate.update(new PreparedStatementCreator() {

					@Override
					public PreparedStatement createPreparedStatement(
							Connection con) throws SQLException {
						PreparedStatement ps = con.prepareStatement(query
								.toString());

						int index = 1;

						ps.setInt(index++, userID);
						ps.setInt(index++, jobID);

						return ps;
					}
				});
			}
		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to insert user's job preferences in DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

	}

	@Override
	public User getUserByID(Integer id) throws DAOException {

		String query = "SELECT * FROM users WHERE id = ?";

		List<User> result = null;

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			result = jdbcTemplate.query(query.toString(), new Object[] { id },
					new RowMapper<User>() {

						@Override
						public User mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							User user = new User(Integer.parseInt(rs
									.getString("id")), rs.getString("login"),
									rs.getString("password"), rs
											.getString("fname"), rs
											.getString("lname"), rs
											.getString("role"), rs
											.getLong("birthdate"));
							String prof = rs.getString("profession");
							if (prof != null && !prof.isEmpty()) {
								user.setProfession(prof);
							}

							String educationStr = rs.getString("education");
							if(educationStr != null && !educationStr.equals("")){
								user.setEducation(educationStr);
							}
							
							String careerLevelStr = rs.getString("careerLevel");
							if(careerLevelStr != null && !careerLevelStr.equals("")){
								user.setCareerLevel(careerLevelStr);
							}

							return user;

						}
					});
		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get user by ID from DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		if (result.size() <= 0) {
			return null;
		}

		return result.get(0);
	}

	@Override
	public User getUserByLogin(String login) throws DAOException {

		String query = "SELECT * FROM users WHERE login = ?";

		List<User> result = null;

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			result = jdbcTemplate.query(query.toString(),
					new Object[] { login }, new RowMapper<User>() {

						@Override
						public User mapRow(ResultSet rs, int rowNum)
								throws SQLException {

							User user = new User(Integer.parseInt(rs
									.getString("id")), rs.getString("login"),
									rs.getString("password"), rs
											.getString("fname"), rs
											.getString("lname"), rs
											.getString("role"), rs
											.getLong("birthdate"));
							
							String prof = rs.getString("profession");
							if (prof != null && !prof.isEmpty()) {
								user.setProfession(prof);
							}

							String educationStr = rs.getString("education");
							if(educationStr != null && !educationStr.equals("")){
								user.setEducation(educationStr);
							}
							
							String careerLevelStr = rs.getString("careerLevel");
							if(careerLevelStr != null && !careerLevelStr.equals("")){
								user.setCareerLevel(careerLevelStr);
							}							

							return user;

						}
					});
		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get user by login from DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		if (result.size() <= 0) {
			return null;
		}

		return result.get(0);
	}

	@Override
	public Set<Integer> getJobPreferencesOfUser(Integer userID)
			throws DAOException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT jobcodeid FROM userjobpreference ");
		query.append("WHERE userid = ?");

		Set<Integer> result = new HashSet<Integer>();

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			List<Map<String, Object>> rows = jdbcTemplate.queryForList(
					query.toString(), new Object[] { userID });

			for (Map<String, Object> row : rows) {
				result.add(Integer.parseInt(String.valueOf(row.get("jobcodeid"))));
			}

		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get job preferences of user from DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return result;
	}

	@Override
	public void deleteJobPreferencesByID(Integer userID) throws DAOException {

		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM userjobpreference ");
		query.append("WHERE userid = ?");

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update(query.toString(), userID);

		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to delete job preferences of user from DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}
	}

	@Override
	public void deleteJobPreferencesByLogin(String login) throws DAOException {

		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM userjobpreference ");
		query.append("WHERE userid = ");
		query.append("(SELECT id FROM users WHERE login = ?)");

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update(query.toString(), login);

		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to delete job preferences of user from DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}
	}

	@Override
	public boolean deleteUserByID(Integer id) throws DAOException {
		String query = "DELETE FROM users WHERE id = ?";

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update(query, new Integer(id));

		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to delete user from DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return true;
	}

	@Override
	public boolean deleteUserByLogin(String login) throws DAOException {
		String query = "DELETE FROM users WHERE login = ?";

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update(query, login);

		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to delete user from DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return true;
	}

	@Override
	public User updateUser(final User usr) throws DAOException {
		User dbuser = null;

		String birthDateStr = usr.getBirthDate();

		final StringBuilder query = new StringBuilder();
		query.append("UPDATE users SET ");
		query.append("fname = ?, ");
		query.append("lname = ?, ");
		query.append("password = ?, ");
		query.append("role = ? ");

		final long birthDateLong = birthDateStr != null ? Utility
				.getDateLongFromStr(birthDateStr) : 0;
		if (birthDateLong != 0) {
			query.append(", birthdate = ? ");
		}

		final String profession = usr.getProfession();
		if (profession != null && !profession.isEmpty()) {
			query.append(", profession = ? ");
		}

		final String education = usr.getEducation();
		if (education != null && !education.isEmpty()) {
			query.append(", education = ? ");
		}

		final String careerLevel = usr.getCareerLevel();
		if (careerLevel != null && !careerLevel.isEmpty()) {
			query.append(", careerLevel = ? ");
		}

		query.append("WHERE id = ?");

		KeyHolder keyHolder = new GeneratedKeyHolder();

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
					PreparedStatement ps = con.prepareStatement(
							query.toString(), new String[] { "id" });

					int index = 1;

					ps.setString(index++, usr.getFname());
					ps.setString(index++, usr.getLname());
					ps.setString(index++, usr.getPassword());
					ps.setString(index++, usr.getRole());

					if (birthDateLong != 0) {
						ps.setLong(index++, birthDateLong);
					}

					if (profession != null) {
						ps.setString(index++, profession.toString());
					}

					if (education != null) {
						ps.setString(index++, education.toString());
					}

					if (careerLevel != null) {
						ps.setString(index++, careerLevel.toString());
					}
					
					ps.setInt(index++, usr.getId());
					
					return ps;
				}
			}, keyHolder);

			dbuser = this.getUserByID(usr.getId());

		} catch (Exception e) {
			DAOException daoe = new DAOException("Failed to update user in DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return dbuser;
	}

}

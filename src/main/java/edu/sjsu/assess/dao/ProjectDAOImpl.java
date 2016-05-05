package edu.sjsu.assess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.ForumReply;
import edu.sjsu.assess.model.ProjectSubmission;
import edu.sjsu.assess.model.StudentProject;

@Repository
public class ProjectDAOImpl implements ProjectDAO{
	@Autowired
	public DataSource dataSource;
	/*method saves the project in postgres*/
	@Override
	public StudentProject createProject(final StudentProject studentProject)
			throws DAOException {
		System.out.println("Hi in DAO createProject method"+studentProject.getDescription());
		final StringBuilder query = new StringBuilder();
		StringBuilder valuesStr = new StringBuilder();

		query.append("INSERT INTO studentproject(title, description, isAlive,userid");
		valuesStr.append(" VALUES(?,?,?,?");

		query.append(")");
		valuesStr.append(");");

		query.append(valuesStr);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update(
					new PreparedStatementCreator() {

						@Override
						public PreparedStatement createPreparedStatement(Connection con)
								throws SQLException {

							PreparedStatement ps = con.prepareStatement(query.toString(), new String[] {"pid"});

							int index = 1;
							ps.setString(index++, studentProject.getTitle());
							ps.setString(index++, studentProject.getDescription());
							ps.setBoolean(index++, Boolean.TRUE);
							ps.setInt(index++, studentProject.getUserID());
							return ps;
						}
					}, keyHolder);

			studentProject.setId(keyHolder.getKey().intValue());


		} catch (Exception e) {
			e.printStackTrace();
			DAOException daoe = new DAOException(
					"Failed to Insert Question in DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return studentProject;
	}
	/*method retrieves the projects in postgres*/
	@Override
	public List<StudentProject> getProjects() throws DAOException {
		System.out.println("Hi in DAO getProjects method");
		StringBuilder query = new StringBuilder();
		query.append("SELECT pid,title,description,userid,isAlive,fname FROM studentproject s ");
		query.append("inner join users u on s.userid = u.id WHERE ");
		query.append("s.isAlive = 'TRUE' ");
		System.out.println("query building done:"+query);
		List<StudentProject> studentProjects = new ArrayList<StudentProject>();
		try{

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString());

			for (Map<String, Object> row : rows){
				StudentProject studentProject = new StudentProject();
				studentProject.setId((int)row.get("pid"));
				studentProject.setTitle((String)row.get("title"));
				studentProject.setDescription((String)row.get("description"));
				studentProject.setUserID((int)row.get("userid"));
				studentProject.setfName((String)row.get("fname"));
				studentProject.setAlive((Boolean)row.get("isAlive"));
				studentProjects.add(studentProject);
			}
		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get projects.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}
		//System.out.println("before return: "+ forumPosts.get(0).getDescription());
		return studentProjects;
	}
	@Override
	public StudentProject getProjectByID(Integer id) throws DAOException {
		System.out.println("Hi in DAO getProjectByID method");
		StringBuilder query = new StringBuilder();
		query.append("SELECT pid,title,description,userid,isAlive,fname FROM studentproject s ");
		query.append("inner join users u on s.userid = u.id WHERE ");
		query.append("pid ='" +id +"'");
		System.out.println("query building done: "+query);
		StudentProject result = null;
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			result = jdbcTemplate.queryForObject(query.toString(), new ForumProjectRowMapper());
			System.out.println("result: "+result.getDescription());
		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get post.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return result;
	}
	
	@Override
	public List<ProjectSubmission> getSubmissionsById(Integer projectId) throws DAOException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT p.sid,p.gitlink,u.fname FROM projectsubmission p,users u ");
		query.append("WHERE p.studentid = u.id AND ");
		query.append("p.projectid ="+projectId);
		System.out.println("projectdaoimpl query  :"+query);
		System.out.println("projectdaoimpl prj id "+projectId);
		List<ProjectSubmission> projectsubmissions = new ArrayList<ProjectSubmission>();
		try{
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString());
			
			for (Map<String, Object> row : rows){
				ProjectSubmission pSubmission = new ProjectSubmission();
				pSubmission.setId((int)row.get("sid"));
				pSubmission.setGitLink((String)row.get("gitlink"));
				pSubmission.setFname((String)row.get("fname"));
				projectsubmissions.add(pSubmission);
				System.out.println("Fname is retireved successfully in projectdaoimpl  :"+pSubmission.getFname());
			}
			//System.out.println("inside try");
		} catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get submission.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
		//System.out.println("before return: "+ forumPosts.get(0).getDescription());
		return projectsubmissions;
	
	}

	public class ForumProjectRowMapper implements RowMapper<StudentProject>{	
		@Override
		public StudentProject mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			System.out.println("inside mapRow");
			StudentProject studentProject = new StudentProject();
			studentProject.setId(rs.getInt("pid"));

			studentProject.setTitle(rs.getString("title"));
			studentProject.setDescription(rs.getString("description"));
			studentProject.setUserID(rs.getInt("userid"));
			studentProject.setfName(rs.getString("fname"));
			studentProject.setAlive(rs.getBoolean("isAlive"));  
			System.out.println("before return mapRow:"+ rs.getString("fname"));
			return studentProject;

		}
	}


	public ProjectSubmission createProjectSubmission(final ProjectSubmission projectSubmission) throws DAOException {
		final StringBuilder query = new StringBuilder();
		StringBuilder valuesStr = new StringBuilder();
		
		Calendar calendar = Calendar.getInstance();
		java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
		
		projectSubmission.setSubmissionDate(date);
		
		System.out.println("Date is coming :"+date);
		
		query.append("INSERT INTO projectsubmission(studentid, projectid, gitlink, submissiondate");
		valuesStr.append(" VALUES(?,?,?,?");

		query.append(")");
		valuesStr.append(");");

		query.append(valuesStr);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		System.out.println("query INSIDE create project submission ="+query);
		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update(
					new PreparedStatementCreator() {

						@Override
						public PreparedStatement createPreparedStatement(Connection con)
								throws SQLException {

							PreparedStatement ps = con.prepareStatement(query.toString(), new String[] {"sid"});

							int index = 1;

							ps.setInt(index++, projectSubmission.getUserID());
							ps.setInt(index++, projectSubmission.getProjectId());							
							ps.setString(index++, projectSubmission.getGitLink());
							ps.setDate(index++, date);
							System.out.println("project id projectdaoimpl :"+projectSubmission.getProjectId());
							return ps;
						}
					}, keyHolder);

			projectSubmission.setId(keyHolder.getKey().intValue());


		} catch (Exception e) {
			e.printStackTrace();
			DAOException daoe = new DAOException(
					"Failed to Insert Question in DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return projectSubmission;
	}

}

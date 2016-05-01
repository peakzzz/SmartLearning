package edu.sjsu.assess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

import edu.sjsu.assess.dao.DiscussionForumDAOImpl.ForumPostRowMapper;
import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.ForumPost;
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
				//System.out.println("inside for");
				StudentProject studentProject = new StudentProject();
				//System.out.println("inside for2");
				studentProject.setId((int)row.get("pid"));
				studentProject.setTitle((String)row.get("title"));
				//System.out.println("inside for3:"+fpost.getTitle());
				studentProject.setDescription((String)row.get("description"));
				//System.out.println("inside for4:"+fpost.getDescription());
				studentProject.setUserID((int)row.get("userid"));
				studentProject.setfName((String)row.get("fname"));
				//System.out.println("inside for5:"+fpost.getfName());
				studentProject.setAlive((Boolean)row.get("isAlive"));
	        	//System.out.println("fpost.getDescription"+fpost.getDescription());
				studentProjects.add(studentProject);
			}
			//System.out.println("inside try");
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

}

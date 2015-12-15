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

import edu.sjsu.assess.dao.JobCodeDAOImpl.JobCodeRowMapper;
import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.ForumPost;
import edu.sjsu.assess.model.JobCode;
import edu.sjsu.assess.model.Option;
import edu.sjsu.assess.model.Question;

@Repository
public class DiscussionForumDAOImpl implements DiscussionForumDAO{

	@Autowired
	public DataSource dataSource;

	@Override
	public ForumPost createForumPost(final ForumPost forumPost) throws DAOException {
		// TODO Auto-generated method stub
		System.out.println("Hi in DAO createForumPost method"+forumPost.getDescription());
		final StringBuilder query = new StringBuilder();
		StringBuilder valuesStr = new StringBuilder();
		
		query.append("INSERT INTO forumPost(title, description, isAlive, userid");
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
							ps.setString(index++, forumPost.getTitle());
							ps.setString(index++, forumPost.getDescription());
							ps.setBoolean(index++, Boolean.TRUE);
							ps.setInt(index++, forumPost.getUserID());
							return ps;
						}
					}, keyHolder);
            
            forumPost.setId(keyHolder.getKey().intValue());
            

        } catch (Exception e) {
			e.printStackTrace();
            DAOException daoe = new DAOException(
                    "Failed to Insert Question in DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        return forumPost;
	}

	public List<ForumPost> getPosts() throws DAOException{
		System.out.println("Hi in DAO getPosts method");
		StringBuilder query = new StringBuilder();
		query.append("SELECT pid,title,description,userid,isAlive,fname FROM forumPost f ");
		query.append("inner join users u on f.userid = u.id WHERE ");
		query.append("f.isAlive = 'TRUE' ");
		System.out.println("query building done:"+query);
		List<ForumPost> forumPosts = new ArrayList<ForumPost>();
		try{
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString());
			
			for (Map<String, Object> row : rows){
				System.out.println("inside for");
				ForumPost fpost = new ForumPost();
				System.out.println("inside for2");
				fpost.setId((int)row.get("pid"));
				fpost.setTitle((String)row.get("title"));
				System.out.println("inside for3:"+fpost.getTitle());
				fpost.setDescription((String)row.get("description"));
				System.out.println("inside for4:"+fpost.getDescription());
				fpost.setUserID((int)row.get("userid"));
				fpost.setfName((String)row.get("fname"));
				System.out.println("inside for5:"+fpost.getfName());
	        	fpost.setAlive((Boolean)row.get("isAlive"));
	        	System.out.println("fpost.getDescription"+fpost.getDescription());
	        	forumPosts.add(fpost);
			}
			System.out.println("inside try");
		} catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get posts.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
		System.out.println("before return: "+ forumPosts.get(0).getDescription());
		return forumPosts;
	}
	
	public class ForumPostRowMapper implements RowMapper<ForumPost>{	
			@Override
	        public ForumPost mapRow(ResultSet rs, int rowNum)
	                throws SQLException {
				System.out.println("inside mapRow");
				ForumPost fpost = new ForumPost();
				fpost.setId(rs.getInt("pid"));
	        	
				fpost.setTitle(rs.getString("title"));
				fpost.setDescription(rs.getString("description"));
				fpost.setUserID(rs.getInt("userid"));
				fpost.setfName(rs.getString("fname"));
	        	fpost.setAlive(rs.getBoolean("isAlive"));  
	        	System.out.println("before return mapRow:"+ rs.getString("fname"));
	        	return fpost;
	        	
	        }
	}

	@Override
	public ForumPost getPostByID(Integer id) throws DAOException {
		System.out.println("Hi in DAO getPostByID method");
		StringBuilder query = new StringBuilder();
		query.append("SELECT pid,title,description,userid,isAlive,fname FROM forumPost f ");
		query.append("inner join users u on f.userid = u.id WHERE ");
		query.append("pid ='" +id +"'");
		System.out.println("query building done: "+query);
		ForumPost result = null;
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			result = jdbcTemplate.queryForObject(query.toString(), new ForumPostRowMapper());
			System.out.println("result: "+result.getDescription());
		} catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get post.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
		
		return result;
	}
	
}

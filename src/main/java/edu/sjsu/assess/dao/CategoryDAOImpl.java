package edu.sjsu.assess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.exception.JobCodeException;
import edu.sjsu.assess.model.Category;
import edu.sjsu.assess.model.JobCode;

@Component
@Repository
public class CategoryDAOImpl implements CategoryDAO{

	@Autowired
    public DataSource dataSource;
	
	@Override
	public Category createCategory(final Category c) throws DAOException {
		
		final StringBuilder query = new StringBuilder();
        
        List<Object> paramsList = new ArrayList<>();
        
        query.append("INSERT INTO category(title, type) ");
        query.append("VALUES(?,?)");
        
        paramsList.add(c.getTitle());
        paramsList.add(c.getType());
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            jdbcTemplate.update(
            		new PreparedStatementCreator() {
						
						@Override
						public PreparedStatement createPreparedStatement(Connection con)
								throws SQLException {
							PreparedStatement ps = con.prepareStatement(query.toString(), new String[] {"id"});
							ps.setString(1, c.getTitle());
							ps.setString(2, c.getType());
					        
							return ps;
						}
					}, keyHolder);
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to Insert Category in DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        c.setId(keyHolder.getKey().intValue());
        return c;
	}

	@Override
	public Category getCategoryByID(Integer cID) throws DAOException {
		
		StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM category ");
        query.append("WHERE id = ?;");

        Category result = null;
        
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            result = (Category)jdbcTemplate.queryForObject(
            		query.toString(), new Object[] { cID }, new CategoryRowMapper());
            		
        } catch (Exception e) {
            e.printStackTrace();
            DAOException daoe = new DAOException(
                    "Failed to get category from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        return result;
	}

	
	@Override
	public Category updateCategoryByID(Category c)
			throws DAOException {
		
		StringBuilder updateStatement = new StringBuilder();
		updateStatement.append("UPDATE category ");
		updateStatement.append("SET title = ?, ");
		updateStatement.append("type = ? ");
		updateStatement.append("WHERE id = ?;");
		
		List<Object> paramsList = new ArrayList<>();
		
		paramsList.add(c.getTitle());
		paramsList.add(c.getType());
		paramsList.add(c.getId());
		
        try{
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            jdbcTemplate.update(updateStatement.toString(), paramsList.toArray());
        }
        catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to update category.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
        
        return null;
	}

	@Override
	public void deleteCategoryByID(Integer cID) throws DAOException {
		String sql = "DELETE FROM category WHERE id = ?;";

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            jdbcTemplate.update(sql, cID);
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to delete category from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
	}
	
	
	@Override
	public List<Category> getCategorySuggestions(Integer jobCodeID, Integer parentCategoryID, Integer userID) throws DAOException{
		
		StringBuilder query = new StringBuilder();
		
		List<Object> paramsList = new ArrayList<>();
		
		int index = 0;
		
        query.append("SELECT C.id, C.categoryName, C.type ");
        query.append("FROM Category C, JobCode J, JobCategories JC WHERE ");
        query.append("JC.jobID = J.id AND ");
        query.append("JC.categoryID = C.id AND ");
        query.append("J.jobID = ? AND ");
        query.append("(J.type = ? OR J.userid = ?) AND " );
        
        paramsList.add(index++, jobCodeID);
        paramsList.add(index++, JobCode.EntityType.PREDEFINED.getValue());
        paramsList.add(index++, userID);
        
        if(parentCategoryID == null){
        	query.append("JC.parentCategoryID IS null");
        }
        else{
        	query.append("JC.parentCategoryID = ?;");
        	paramsList.add(index++, parentCategoryID);
        }
        
        List<Category> result = null;
        
        try {
        	
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            result = jdbcTemplate.query(query.toString(),
                    paramsList.toArray(), new CategoryRowMapper());
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get predefined job codes from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
        
		return result;
	}
	
	
	public List<Category> getJobCodeCategories(Integer jobCodeID, Integer parentCategoryID) throws DAOException{
		return this.getJobCodeCategories(jobCodeID, parentCategoryID, null);
	}
	
	public List<Category> getJobCodeCategories(Integer jobCodeID, Integer parentCategoryID, String type) throws DAOException{
		
		StringBuilder query = new StringBuilder();
		
		List<Object> paramsList = new ArrayList<>();
		
		int i = 0;
		
        query.append("SELECT C.id, C.title, C.type ");
        query.append("FROM Category C, JobCode J, JobCategories JC WHERE ");
        query.append("JC.jobID = J.id AND ");
        query.append("JC.categoryID = C.id AND ");
        query.append("J.id = ? ");

        paramsList.add(i++, jobCodeID);
        
        if(parentCategoryID == null){
        	query.append("AND JC.parentCategoryID IS null ");
        }
        else{
        	query.append("AND JC.parentCategoryID = ? ");
        	paramsList.add(i++, parentCategoryID);
        }
        
        if(type != null){
        	query.append("AND JC.type = ?");
        	paramsList.add(i++, type);
        }
        
        List<Category> result = null;
        
        try {
        	
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            result = jdbcTemplate.query(query.toString(),
                    paramsList.toArray(), new CategoryRowMapper());
            
            for(Category cat : result){
            	cat.setParentCategoryID(parentCategoryID);
            }
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get predefined job codes from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
        
		return result;
	}

    @Override
    public List<Category> getAllPredefinedCategories()
            throws DAOException
    {
        StringBuilder query = new StringBuilder();

        List<Object> paramsList = new ArrayList<>();

        query.append("SELECT C.id, C.title, C.type ");
        query.append("FROM Category C WHERE C.type='predefined'");

        List<Category> result = null;

        try {

            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            result = jdbcTemplate.query(query.toString(),
                    paramsList.toArray(), new CategoryRowMapper());

        } catch (Exception e) {
            e.printStackTrace();
            DAOException daoe = new DAOException(
                    "Failed to get predefined job codes from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        return result;
    }
	
	@Override
	public void createJobCodeCategoryMapping(Integer jobCodeID, Integer categoryID, Integer parentCategoryID) throws DAOException {
		
		StringBuilder query = new StringBuilder();
		StringBuilder valuesStr = new StringBuilder();
        
        List<Object> paramsList = new ArrayList<>();
        
        query.append("INSERT INTO jobcategories(jobid, categoryid");
        valuesStr.append(" VALUES(?,?");
        
        paramsList.add(jobCodeID);
        paramsList.add(categoryID);
        
        if(parentCategoryID != 0){
        	query.append(",parentcategoryid");
        	valuesStr.append(",?");
        	paramsList.add(parentCategoryID);
        }
        
        query.append(")");
        valuesStr.append(")");
        query.append(valuesStr);
        
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            jdbcTemplate.update(query.toString(), paramsList.toArray());
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to Insert Category in DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

	}
	
	
	@Override
	public void deleteCategoryMappingForJobCodeID(Integer jobCodeID) throws DAOException {
		
		StringBuilder query = new StringBuilder();

		query.append("DELETE FROM jobcategories ");
		query.append("WHERE jobid = ?;");
		
		try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            jdbcTemplate.update(query.toString(), jobCodeID);
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to delete category mapping for job code from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
	}
	
	
	/**
	 * Category Row Mapper
	 * @author Shefali
	 *
	 */
	public class CategoryRowMapper implements RowMapper<Category>{
		
		@Override
        public Category mapRow(ResultSet rs, int rowNum)
                throws SQLException {
			Category c = new Category();
        	c.setId(rs.getInt("id"));
        	c.setTitle(rs.getString("title"));
        	c.setType(rs.getString("type"));
        	
        	return c;
        	
        }
	}

}

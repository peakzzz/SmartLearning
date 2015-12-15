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
import edu.sjsu.assess.model.Category;
import edu.sjsu.assess.model.JobCode;

@Component
@Repository
public class DepartmentDAOImpl implements DepartmentDAO{

	@Autowired
    public DataSource dataSource;
	
	@Override
	public Category createDepartment(final Category c) throws DAOException {
		
		final StringBuilder query = new StringBuilder();
        
        List<Object> paramsList = new ArrayList<>();
        
        query.append("INSERT INTO department(title, type) ");
        query.append("VALUES(?,?)");
        
        paramsList.add(c.getTitle());
        paramsList.add(c.getType());
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        try {
        	System.out.println("Entering insert department");
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
            System.out.println("done ");
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to Insert department in DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        c.setId(keyHolder.getKey().intValue());
        return c;
	}

	@Override
	public Category getDepartmentByID(Integer cID) throws DAOException {
		
		StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM department ");
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
	public Category updateDepartmentByID(Category c)
			throws DAOException {
		
		StringBuilder updateStatement = new StringBuilder();
		updateStatement.append("UPDATE department ");
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
	public void deleteDepartmentByID(Integer cID) throws DAOException {
		String sql = "DELETE FROM department WHERE id = ?;";

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
	public List<Category> getDepartmentSuggestions(Integer jobCodeID, Integer parentCategoryID, Integer userID) throws DAOException{
		
		StringBuilder query = new StringBuilder();
		
		List<Object> paramsList = new ArrayList<>();
		
		int index = 0;
		
        query.append("SELECT C.id, C.categoryName, C.type ");
        query.append("FROM department C, Course J, CourseDepartment JC WHERE ");
        query.append("JC.courseID = J.id AND ");
        query.append("JC.departmentID = C.id AND ");
        query.append("J.courseID = ? AND ");
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
	
	
	public List<Category> getCourseDepartments(Integer jobCodeID, Integer parentCategoryID) throws DAOException{
		return this.getCourseDepartments(jobCodeID, parentCategoryID, null);
	}
	
	public List<Category> getCourseDepartments(Integer jobCodeID, Integer parentCategoryID, String type) throws DAOException{
		
		StringBuilder query = new StringBuilder();
		
		List<Object> paramsList = new ArrayList<>();
		
		int i = 0;
		System.out.println("entering getcourse deparment");
        query.append("SELECT C.id, C.title, C.type ");
        query.append("FROM Department C, Course J, Coursedepartment JC WHERE ");
        query.append("JC.courseID = J.id AND ");
        query.append("JC.departmentID = C.id AND ");
        query.append("J.id = ? ");

        paramsList.add(i++, jobCodeID);
        
        if(parentCategoryID == null){
        	query.append("AND JC.parentDepartmentID IS null ");
        }
        else{
        	query.append("AND JC.parentDepartmentID = ? ");
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
            System.out.println("got the result tooo");
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
    public List<Category> getAllPredefinedDepartments()
            throws DAOException
    {
        StringBuilder query = new StringBuilder();

        List<Object> paramsList = new ArrayList<>();

        query.append("SELECT C.id, C.title, C.type ");
        query.append("FROM Department C WHERE C.type='predefined'");

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
	public void createCourseDepartmentMapping(Integer jobCodeID, Integer categoryID, Integer parentCategoryID) throws DAOException {
		
		StringBuilder query = new StringBuilder();
		StringBuilder valuesStr = new StringBuilder();
        
        List<Object> paramsList = new ArrayList<>();
        
        query.append("INSERT INTO coursedepartment(courseid, departmentid");
        valuesStr.append(" VALUES(?,?");
        
        paramsList.add(jobCodeID);
        paramsList.add(categoryID);
        
        if(parentCategoryID != 0){
        	query.append(",parentdepartmentid");
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
	public void deleteDepartmentMappingForCourseID(Integer jobCodeID) throws DAOException {
		
		StringBuilder query = new StringBuilder();

		query.append("DELETE FROM coursedepartment ");
		query.append("WHERE courseid = ?;");
		
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

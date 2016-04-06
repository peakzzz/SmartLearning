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

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.JobCode;


@Repository
public class JobCodeDAOImpl implements JobCodeDAO{

	@Autowired
    public DataSource dataSource;
	
	
	@Override
	public JobCode createJobCode(final JobCode jc) throws DAOException {
		
		final StringBuilder query = new StringBuilder();
		StringBuilder valuesStr = new StringBuilder();
		valuesStr.append(" VALUES(?,?,?");
		
		query.append("INSERT INTO jobcode(positionName, type, description");
		
		final JobCode predefinedJobCode = jc.getPredefinedJobCode();
		if(predefinedJobCode != null){
			query.append(", predefinedJobId");
			valuesStr.append(",?");
		}
		
		final Integer userID = jc.getUserID();
		if(userID != 0){
			query.append(", userid");
			valuesStr.append(",?");
		}
		
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
							PreparedStatement ps = con.prepareStatement(query.toString(), new String[] {"id"});
							
							int index = 1;
							
							ps.setString(index++, jc.getPositionName());
							ps.setString(index++, jc.getType());
							ps.setString(index++, jc.getDescription());

							
					        if(predefinedJobCode != null){
					        	ps.setInt(index++, predefinedJobCode.getId());
					        }
					        
					        if(userID != 0){
					        	ps.setInt(index++, userID);
					        }
					        
							return ps;
						}
					}, keyHolder);
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to Insert Job Code in DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        jc.setId(keyHolder.getKey().intValue());
        return jc;
	}

	@Override
	public JobCode getJobCodeByID(Integer jcID) throws DAOException {
		StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM jobcode ");
        query.append("WHERE id = ?");

        JobCode result = null;
        
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            result = jdbcTemplate.queryForObject(query.toString(), new Object[] { jcID }, new JobCodeRowMapper());
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get job code from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        return result;
	}

	
	@Override
	public List<Integer> getJobCodeList() throws DAOException{
		
		StringBuilder query = new StringBuilder();
        query.append("SELECT id FROM jobcode ");
        query.append("WHERE type = ?");

        List<Integer> result = new ArrayList<>();
        
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString(), new Object[] { JobCode.EntityType.USERDEFINED.getValue() });
            
            for (Map<String, Object> row : rows){
            	result.add(Integer.parseInt(String.valueOf(row.get("id"))));
            }
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get job code ID list from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        return result;
	}
	
	
	@Override
	public void updateJobCodeByID(JobCode jc)
			throws DAOException {
		
		StringBuilder updateStatement = new StringBuilder();
		updateStatement.append("UPDATE jobcode ");
		updateStatement.append("SET positionName=?, ");
		updateStatement.append("description=?, ");
		updateStatement.append("predefinedJobId=? ");
		updateStatement.append("WHERE id=?");
		
		List<Object> fieldsList = new ArrayList<>();
		
		fieldsList.add(jc.getPositionName());
		fieldsList.add(jc.getDescription());

        /*
		JobCode predefinedJobId = jc.getPredefinedJobCode();
		if(predefinedJobId == null){
			fieldsList.add(null);
		}
		else{
			fieldsList.add(predefinedJobId.getId());
		}*/

        if(jc.getPredefinedJobCodeID() == null){
            fieldsList.add(null);
        }
        else{
            fieldsList.add(jc.getPredefinedJobCodeID());
        }
		
		fieldsList.add(jc.getId());
		
		Object[] fieldsArray = fieldsList.toArray();
        
        try{
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            jdbcTemplate.update(updateStatement.toString(), fieldsArray);
        }
        catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to update job code.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
        
	}

	@Override
	public void deleteJobCodeByID(Integer jcID) throws DAOException {

		String sql = "DELETE FROM jobcode WHERE id=?";

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            jdbcTemplate.update(sql, jcID);
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to delete job code from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
		
	}

	@Override
	public List<JobCode> getAllPredefinedJobCodes() throws DAOException {
		StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM jobcode ");

        List<JobCode> jobCodeList = null;
        
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            jobCodeList = jdbcTemplate.query(query.toString(), new JobCodeRowMapper());
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get predefined job codes from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        return jobCodeList;
	}
	
	@Override
	public List<JobCode> getJobCodeForUserPreference() throws DAOException{
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM jobcode ");
		query.append("WHERE ");
		query.append("type = '" +JobCode.EntityType.PREDEFINED.getValue() +"' ");
		query.append("OR predefinedjobid IS NULL");
		
		List<JobCode> jobCodeList = null;
		
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			jobCodeList = jdbcTemplate.query(query.toString(), new JobCodeRowMapper());
			
		} catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get job codes for seting user preferences.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
		
		return jobCodeList;
	}
	
	
	
	/**
	 * Job Code Row Mapper
	 * @author Shefali
	 *
	 */
	public class JobCodeRowMapper implements RowMapper<JobCode>{
		
		@Override
        public JobCode mapRow(ResultSet rs, int rowNum)
                throws SQLException {
        	JobCode jc = new JobCode();
        	jc.setId(rs.getInt("id"));
        	
        	jc.setPositionName(rs.getString("positionName"));
        	jc.setDescription(rs.getString("description"));
        	jc.setType(rs.getString("type"));
        	jc.setUserID(rs.getInt("userid"));
        	
        	jc.setPredefinedJobCodeID(rs.getInt("predefinedJobId"));
        	
        	return jc;
        	
        }
	}
}

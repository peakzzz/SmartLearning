package edu.sjsu.assess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import edu.sjsu.assess.model.TrainingModule;
import edu.sjsu.assess.model.TrainingModuleSearchParams;

@Component
@Repository
public class TrainingModuleDAOImpl implements TrainingModuleDAO {
	
    @Autowired
    public DataSource dataSource;
    
    @Override
    public TrainingModule createTrainingModule(final TrainingModule tm)
            throws DAOException {
    	System.out.println("In the trainig module dao for create !!");
    	System.out.println(tm.getJobCodeID());
    	System.out.println(tm.getContent());
    	System.out.println(tm.getFocus());
    	System.out.println(tm.getTitle());
    	System.out.println(tm.getCategoriesContent().size());
        final StringBuilder query = new StringBuilder();
        
        query.append("INSERT INTO trainingModule(title, jobcodeID, userID");
        
        int fieldsCount = 3;
        
        final String focus = tm.getFocus();
        if(focus != null && !focus.equals("")){
            query.append(", focus");
            fieldsCount++;
        }
        
        
        final String level = tm.getLevel();
        if(level != null && !level.equals("")){
            query.append(", level");
            fieldsCount++;
        }
        
        final String content = tm.getContent();
        if(content != null && !content.equals("")){
            query.append(", content");
            fieldsCount++;
        }
        
        
        query.append(") ");
        query.append("VALUES(");
        
        for(int i = 0; i < fieldsCount; i++){
            query.append("?");
            if(i < fieldsCount-1){
                query.append(",");
            }
        }
        
        query.append(")");
        
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
							
							ps.setString(index++, tm.getTitle());
							ps.setInt(index++, tm.getJobCodeID());
							ps.setInt(index++, tm.getUserID());

							
					        if(focus != null){
					        	ps.setString(index++, focus);
					        }
					        
					        if(level != null){
					        	ps.setString(index++, level);
					        }
					        
					        if(content != null && !content.equals("")){
					        	ps.setString(index++, content);
					        }
					        
							return ps;
						}
					}, keyHolder);
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to insert training module in DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        tm.setId(keyHolder.getKey().intValue());
        return tm;
    }
    
    
    @Override
    public void createTrainingModuleCategories(Integer trainingModuleID, Integer categoryID, String content) throws DAOException{
    	
    	StringBuilder query = new StringBuilder();
        
        List<Object> paramsList = new ArrayList<>();
        
        query.append("INSERT INTO trainingmodulecategories(trainingModuleID, categoryID, content) ");
        query.append("VALUES(?,?,?);");
        
        paramsList.add(0, trainingModuleID);
        paramsList.add(1, categoryID);
        paramsList.add(2, content);
        
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            jdbcTemplate.update(query.toString(), paramsList.toArray());
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to insert training module categories in DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
    }
    
    @Override
    public TrainingModule getTrainingModuleByID(Integer tmID)
            throws DAOException {
    	
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM trainingModule ");
        query.append("WHERE id = ?");

        TrainingModule trainingModule = null;
        try {
        	
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            trainingModule = jdbcTemplate.queryForObject(query.toString(),
                    new Object[] { tmID }, new TrainingModuleRowMapper());
            
            trainingModule.setCategoriesContent(this.getCategoriesContent(trainingModule.getId()));
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get training module from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        return trainingModule;
    }
    
    @Override
    public List<String> getTrainingModuleByCategoryID(Integer categoryID)
            throws DAOException {
    	List<String> result = new ArrayList<String>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM trainingModuleCategories ");
        query.append("WHERE categoryid = ?");

        TrainingModule trainingModule = null;
        try {
        	
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            System.out.println("Befor querying "+query);
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString(),new Object[] { categoryID });
            System.out.println("after querying");
            //Adding all the training module retrieved for a category id
            for(Map<String, Object>row: rows)
            {
            	String content = (String)(row.get("content"));
            	result.add(content);
            }
            //trainingModule.setCategoriesContent(this.getCategoriesContent(trainingModule.getId()));
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get training module from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        return result;
    }
    
    private Map<Integer, String> getCategoriesContent(Integer tmID) throws DAOException{
    	
    	Map<Integer, String> categoriesContent = new HashMap<Integer, String>();
    	
    	StringBuilder query = new StringBuilder();
    	query.append("SELECT categoryID, content FROM trainingModuleCategories ");
    	query.append("WHERE trainingModuleID = ?");
    	
    	try {
        	
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString(), new Object[] { tmID });
            
            for(Map<String, Object> row : rows){
            	Integer categoryID = (Integer)(row.get("categoryID"));
            	String content = (String)(row.get("content"));
            	
            	categoriesContent.put(categoryID, content);
            }
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get training module from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
    	
    	return categoriesContent;
    }
    
    
    @Override
    public List<Integer> getTrainingModuleList(TrainingModuleSearchParams searchParams) throws DAOException{
    	
		StringBuilder query = new StringBuilder();
		List<Object> paramsList = new ArrayList<>();
		
		int index = 0;
		
        if(searchParams.getId() != null){
        	query.append("SELECT id FROM trainingModule ");
            query.append("WHERE ");
        	query.append("id = ?");
        	paramsList.add(index++, searchParams.getId());
        	
        }
        else if(searchParams.getJobcodeID() == null){
        	query.append("SELECT id FROM trainingModule");
        }
    	else {
    		if(searchParams.getCategoryID() != null){
        		query.append("SELECT tm.id FROM trainingModule tm, trainingModuleCategories tc ");
        		query.append("WHERE ");
        		query.append("tm.id = tc.trainingModuleID ");
        		
        		query.append("AND tm.jobcodeID = ? ");
        		paramsList.add(index++, searchParams.getJobcodeID());
        		
        		query.append("AND tc.categoryID = ? ");
        		paramsList.add(index++, searchParams.getCategoryID());
    		}
        	else{
        		query.append("SELECT tm.id FROM trainingModule tm ");
                query.append("WHERE ");
        		query.append("tm.jobcodeID = ? ");
            	paramsList.add(index ++, searchParams.getJobcodeID());
        	}
    	
        	if(searchParams.getFocus() != null){
        		query.append("AND tm.focus = ? ");
        		paramsList.add(searchParams.getFocus());
        	}
        	
        	if(searchParams.getLevel() != null){
        		query.append("AND tm.level = ? ");
        		paramsList.add(searchParams.getLevel());
        	}
    	}
        	
        
        List<Integer> result = new ArrayList<>();
        
        try {
        	
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString(), paramsList.toArray());
            
            for (Map<String, Object> row : rows){
            	result.add(Integer.parseInt(String.valueOf(row.get("id"))));
            }
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get job code list from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        return result;
	}


    @Override
    public void deleteTrainingModuleByID(Integer tmID) throws DAOException {
        String query = "DELETE FROM trainingModule WHERE id = ?";
        
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            jdbcTemplate.update(query, tmID);
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to delete training module from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
    }

    
    @Override
    public void deleteTrainingModuleCategories(Integer tmID) throws DAOException{
    	String query = "DELETE FROM trainingModuleCategories WHERE trainingModuleID = ?";
    	
    	try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            jdbcTemplate.update(query, tmID);
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to delete training module categories from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
    }
    
    @Override
    public void updateTrainingModuleByID(TrainingModule tm) throws DAOException {
        
        String updateStatement = " UPDATE trainingModule "
                + "SET title = ?, "
                + "jobcodeid = ?, "
                + "focus = ?, "
                + "level = ?, "
                + "content = ? "
                + "WHERE id = ?";
        
        try{
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(updateStatement, 
                    tm.getTitle(),
                    tm.getJobCodeID(),
                    tm.getFocus(),
                    tm.getLevel(),
                    tm.getContent(),
                    tm.getId());
        }
        catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to update training module.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
        
    }

    /**
     * This method gets recommended training modules based on job code ID & category ID.
     * @param jobCodeID
     * @param categoryID
     * @return
     * @throws DAOException
     */
    private List<Integer> getRecommendedTrainingModules(Integer jobCodeID, Integer categoryID) throws DAOException{
    	
    	List<Integer> result = new ArrayList<Integer>();
    	
    	StringBuilder query = new StringBuilder();
		query.append("SELECT tm.id ");
		query.append("FROM trainingModule tm, trainingModuleCategories tmc ");
		query.append("WHERE tm.id = tmc.trainingModuleID AND ");
		query.append("tm.jobcodeID = ? AND ");
		query.append("tmc.categoryID = ?");
		
		try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString(), new Object[] { jobCodeID, categoryID });
            
            for (Map<String, Object> row : rows){
            	result.add(Integer.parseInt(String.valueOf(row.get("tm.id"))));
            }
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get training module recommendations.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
		
		
    	return result;
    }
    
    @Override
    public void createTrainingModuleRecommendations(final Integer userID, Integer jobCodeID, Integer categoryID) throws DAOException{
    	
    	List<Integer> recommendedTMs = this.getRecommendedTrainingModules(jobCodeID, categoryID);
    	
    	if(recommendedTMs != null && recommendedTMs.size() > 0){
    		
    		final StringBuilder query = new StringBuilder();
    		
    		query.append("INSERT INTO trainingModuleRecommendation(userID, trainingModuleID) ");
    		query.append("VALUES(?,?)");
    		
    		for(final Integer recommendedTMID : recommendedTMs){
    			
    			try{
    				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    				
    				jdbcTemplate.update(
    	            		new PreparedStatementCreator() {
    							
    							@Override
    							public PreparedStatement createPreparedStatement(Connection con)
    									throws SQLException {
    								PreparedStatement ps = con.prepareStatement(query.toString());
    								
    								int index = 1;
    								
    								ps.setInt(index++, userID);
    								ps.setInt(index++, recommendedTMID);
    						        
    								return ps;
    							}
    						});
    			} catch (Exception e) {
    	            continue;
    	        }
    		}
    	}
    }
    
    @Override
    public List<TrainingModule> getRecommendedTrainingModulesForUser(Integer userID) throws DAOException{
    	
    	List<TrainingModule> result = new ArrayList<TrainingModule>();
    	
    	List<Integer> trainingModuleIDs = this.getTrainingModuleRecommendationIDs(userID);
    	
    	if(trainingModuleIDs != null && trainingModuleIDs.size() > 0){
    		for(Integer trainingModuleID : trainingModuleIDs){
    			result.add(this.getTrainingModuleByID(trainingModuleID));
    		}
    	}
    	
    	return result;
    }
    
    private List<Integer> getTrainingModuleRecommendationIDs(Integer userID) throws DAOException{
    	List<Integer> result = new ArrayList<Integer>();
    	
    	StringBuilder query = new StringBuilder();
    	query.append("SELECT trainingModuleID FROM trainingModuleRecommendation ");
    	query.append("WHERE userID = ?");
    	
    	try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString(), new Object[] { userID });
            
            for (Map<String, Object> row : rows){
            	result.add(Integer.parseInt(String.valueOf(row.get("trainingModuleID"))));
            }
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get IDs of training module recommendations for user.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
    	
    	return result;
    }
    
    @Override
    public void deleteTrainingModuleRecommendation(Integer userID, Integer jobCodeID, Integer categoryID) throws DAOException{
    	
    	List<Integer> recommendedTMs = this.getRecommendedTrainingModules(jobCodeID, categoryID);
    	
    	if(recommendedTMs != null && recommendedTMs.size() > 0){
	    	StringBuilder query = new StringBuilder();
	    	query.append("DELETE FROM trainingModuleRecommendation ");
	    	query.append("WHERE userID = ? AND ");
	    	query.append("trainingModuleID IN (");
	    	
	    	for(Integer recommendedTM : recommendedTMs){
	    		query.append(recommendedTM);
	    		query.append(",");
	    	}
	    	
	    	query.replace(query.length() - 1, query.length(), "");
	    	query.append(")");
	    	
	    	try {
	            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	            
	            jdbcTemplate.update(query.toString(), userID);
	            
	        } catch (Exception e) {
	            DAOException daoe = new DAOException(
	                    "Failed to delete training module recommendations from DB.");
	            daoe.setStackTrace(e.getStackTrace());
	            throw daoe;
	        }
    	}
        
    }
    
    @Override
    public void updateTrainingModuleStatus(final Integer userID, final Integer trainingModuleID, final String status) throws DAOException{
    	
    	final StringBuilder query = new StringBuilder();
    	query.append("INSERT INTO trainingModuleCompletionStatus(userID, trainingModuleID, status, dateCompleted) ");
    	query.append("VALUES(?,?,?,?)");
    	
    	try{
    		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            jdbcTemplate.update(
            		new PreparedStatementCreator() {
						
						@Override
						public PreparedStatement createPreparedStatement(Connection con)
								throws SQLException {
							PreparedStatement ps = con.prepareStatement(query.toString());
							
							int index = 1;
							
							ps.setInt(index++, userID);
							ps.setInt(index++, trainingModuleID);
							ps.setString(index++, status);
							ps.setLong(index++, System.currentTimeMillis());
							
							return ps;
						}
					});
    	} catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to update status of training module completion.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
    }
    
    /**
	 * Training Module Row Mapper
	 * @author Shefali
	 *
	 */
	public class TrainingModuleRowMapper implements RowMapper<TrainingModule>{
		
		@Override
        public TrainingModule mapRow(ResultSet rs, int rowNum)
                throws SQLException {
        	TrainingModule tm = new TrainingModule();

        	tm.setId(rs.getInt("id"));
        	tm.setContent(rs.getString("content"));
        	tm.setFocus(rs.getString("focus"));
        	tm.setJobCodeID(rs.getInt("jobcodeID"));
        	tm.setLevel(rs.getString("level"));
        	tm.setTitle(rs.getString("title"));
        	tm.setUserID(rs.getInt("userID"));
        	
        	return tm;
        	
        }
	}

}

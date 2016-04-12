package edu.sjsu.assess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.Option;
import edu.sjsu.assess.model.Question;
import edu.sjsu.assess.model.TestSetAttempt;
import edu.sjsu.assess.model.TestSetAttempt.CategoryWiseRecord;
import edu.sjsu.assess.model.TestSetAttempt.QuestionWiseRecord;
import edu.sjsu.assess.model.TestSetAttemptSearchParams;
import edu.sjsu.assess.util.Utility;

@Component
@Repository
public class TestSetAttemptDAOImpl implements TestSetAttemptDAO {

	@Autowired
	public DataSource dataSource;
	
	@Autowired
	public QuestionDAOImpl questionDAO;

	@Override
	public TestSetAttempt createTestSetAttempt(final TestSetAttempt ta)
			throws DAOException {

		final StringBuilder query = new StringBuilder();

		query.append("INSERT INTO testSetAttempt(userID, testSetID, date, score) ");
		query.append("VALUES(?,?,?,?) ");

		KeyHolder keyHolder = new GeneratedKeyHolder();
		Map<String, Object> rs;
		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
					PreparedStatement ps = con.prepareStatement(
							query.toString(), Statement.RETURN_GENERATED_KEYS);

					int index = 1;

					ps.setInt(index++, ta.getUserID());
					ps.setInt(index++, ta.getTestSetID());
					if(ta.getAttemptDate() == null){
						ps.setLong(index++, System.currentTimeMillis());
					} else{
						Date attemptDate = ta.getAttemptDate();
						ps.setLong(index++, attemptDate.getTime());
					}
					ps.setDouble(index++, ta.getScore());

					return ps;
				}
			}, keyHolder);
			
			rs = keyHolder.getKeys();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			DAOException daoe = new DAOException(
					"Failed to insert test set attempt in DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		ta.setId((Integer)rs.get("id"));

		this.createCategoryWiseScore(ta.getId(), ta.getCategoryWiseRecords());
		
		//this.createQuestionWiseRecord(ta.getId(), ta.getQuestionsRecords());

		return ta;
	}

	private void createCategoryWiseScore(final Integer testSetAttemptID,
			final List<CategoryWiseRecord> categoryWiseRecords) throws DAOException {

		final StringBuilder query = new StringBuilder();
		query.append("INSERT INTO categoryWiseScore(testSetAttemptID, categoryID, score, isCutoffReached) ");
		query.append("VALUES(?,?,?,?)");

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			for (final CategoryWiseRecord categoryWiseRecord : categoryWiseRecords) {

				jdbcTemplate.update(new PreparedStatementCreator() {

					@Override
					public PreparedStatement createPreparedStatement(
							Connection con) throws SQLException {
						PreparedStatement ps = con.prepareStatement(query
								.toString());

						int index = 1;

						ps.setInt(index++, testSetAttemptID);
						ps.setInt(index++, categoryWiseRecord.getCategoryID());
						ps.setDouble(index++, categoryWiseRecord.getScore());
						ps.setBoolean(index++, categoryWiseRecord.isCutoffReached());

						return ps;
					}
				});
				
				// Create entries for questions present in this category
				List<QuestionWiseRecord> questionsRecord = categoryWiseRecord.getQuestionsRecord();
				if(questionsRecord != null && questionsRecord.size() > 0){
					for(QuestionWiseRecord questionRecord : questionsRecord){
						questionRecord.setCategoryID(categoryWiseRecord.getCategoryID());
					}
					
					this.createQuestionWiseRecord(testSetAttemptID, questionsRecord);
				}
			}
		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to insert category-wise score in DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}
	}
	
	
//	private void createQuestionWiseRecord(final Integer testSetAttemptID,
//			final Map<Integer, List<QuestionWiseRecord>> questionWiseRecords) throws DAOException {
//		
//		if(questionWiseRecords != null){
//			for(Integer categoryID : questionWiseRecords.keySet()){
//				
//				List<QuestionWiseRecord> questionRecordsForCat = questionWiseRecords.get(categoryID);
//				this.createQuestionWiseRecord(testSetAttemptID, questionRecordsForCat);
//			}
//		}
//	}

	private void createQuestionWiseRecord(final Integer testSetAttemptID,
			final List<QuestionWiseRecord> questionWiseRecords) throws DAOException {
		
		if(questionWiseRecords != null){
			for(QuestionWiseRecord questionWiseRecord : questionWiseRecords){
				this.createQuestionWiseRecord(testSetAttemptID, questionWiseRecord);
			}
		}
	}
	
	
	private void createQuestionWiseRecord(final Integer testSetAttemptID,
			final QuestionWiseRecord questionWiseRecord) throws DAOException {

		final StringBuilder query = new StringBuilder();
		
		query.append("INSERT INTO questionWiseRecord(testSetAttemptID, categoryID, questionID, isCorrectAnswer, userAnswers) ");
		query.append("VALUES(?,?,?,?,?)");

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(
						Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(query
							.toString());

					int index = 1;

					ps.setInt(index++, testSetAttemptID);
					ps.setInt(index++, questionWiseRecord.getCategoryID());
					ps.setInt(index++, questionWiseRecord.getQuestionID());
					ps.setBoolean(index++, questionWiseRecord.isCorrectAnswer());
					
					String userAnswers = Utility.convertToCommaSeparatedString(questionWiseRecord.getUserAnswers());
					ps.setString(index++, userAnswers);

					return ps;
				}
			});
			
		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to insert question-wise records in DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}
	}

	@Override
	public List<Integer> getTestSetAttemptList(
			TestSetAttemptSearchParams searchParams) throws DAOException {
		
		StringBuilder query = new StringBuilder();
		List<Object> paramsList = new ArrayList<>();
		
		int index = 0;
		
		query.append("SELECT id FROM testSetAttempt ");
        query.append("WHERE ");
    	query.append("userID = ? ");
    	
    	paramsList.add(index++, searchParams.getUserID());
    	
		if(searchParams.getId() != null){
        	query.append("AND id = ? ");
        	paramsList.add(index++, searchParams.getId());
        	
        }
		else{
			if(searchParams.getTestSetID() != null){
	        	query.append("AND testSetID = ? ");
	        	paramsList.add(index++, searchParams.getTestSetID());
			}
		}
		query.append(" ORDER BY testSetID, date");
		
		List<Integer> result = new ArrayList<>();
        
        try {
        	
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString(), paramsList.toArray());
            
            for (Map<String, Object> row : rows){
            	result.add(Integer.parseInt(String.valueOf(row.get("id"))));
            }
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get test set attempt list from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
        
		return result;
	}

	
	@Override
	public TestSetAttempt getTestSetAttemptByID(Integer taID)
			throws DAOException {
		
		StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM testSetAttempt ");
        query.append("WHERE id = ?");

        TestSetAttempt testSetAttempt = null;
        try {
        	
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            testSetAttempt = jdbcTemplate.queryForObject(query.toString(),
                    new Object[] { taID }, new TestSetAttemptRowMapper());
            
            List<CategoryWiseRecord> categoryWiseRecords = this.getCategoryWiseRecords(testSetAttempt.getId());
            testSetAttempt.setCategoryWiseRecords(categoryWiseRecords);
            
//            if(categoryWiseRecords != null){
//            	
//            	for(CategoryWiseRecord categoryWiseRecord : categoryWiseRecords){
//            		
//            		Integer categoryID = categoryWiseRecord.getCategoryID();
//            		List<QuestionWiseRecord> categoryQuestionsRecord = this.getQuestionWiseRecords(taID, categoryID);
//            		testSetAttempt.addQuestionsRecord(categoryID, categoryQuestionsRecord);
//            	}
//            }
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get test set attempt record from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
        
		return testSetAttempt;
	}
	
	
	private List<QuestionWiseRecord> getQuestionWiseRecords(Integer taID, Integer categoryID) throws DAOException{
		
		StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM questionWiseRecord ");
        query.append("WHERE testSetAttemptID = ? ");
        query.append("AND categoryID = ? ");
        
        List<QuestionWiseRecord> questionWiseRecords = null;
        
        try {
        	
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            questionWiseRecords = jdbcTemplate.query(query.toString(),
                    new Object[] { taID, categoryID }, new QuestionWiseRecordRowMapper());
            
            if(questionWiseRecords != null && questionWiseRecords.size() > 0){
            	for(QuestionWiseRecord questionWiseRecord : questionWiseRecords){
            		
            		List<Integer> userAnswers = questionWiseRecord.getUserAnswers();
            		
            		Integer qid = questionWiseRecord.getQuestionID();
            		Question questionObj = questionDAO.getQuestionByID(qid);
            		
            		if(userAnswers != null && userAnswers.size() > 0){
	            		if(questionObj.isMultipleChoice()){
	            			
	            			List<Option> options = questionObj.getOptions();
	            			for(Option option : options){
	            				if(userAnswers.contains(option.getId())){
	            					option.setSelectedByUser(true);
	            				}
	            			}
	            		}
            		}
            		
            		questionWiseRecord.setQuestionObj(questionObj);
            	}
            }
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get question-wise scores from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
        
		return questionWiseRecords;
	}
	
	
	private List<CategoryWiseRecord> getCategoryWiseRecords(Integer taID) throws DAOException{
		
		List<CategoryWiseRecord> categoryWiseRecords = null;
    	
    	StringBuilder query = new StringBuilder();
    	query.append("SELECT * FROM categoryWiseScore ");
    	query.append("WHERE testSetAttemptID = ?");
    	
    	try {
        	
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            categoryWiseRecords = jdbcTemplate.query(query.toString(),
                    new Object[] { taID }, new CategoryWiseRecordRowMapper());
            
            if(categoryWiseRecords != null && categoryWiseRecords.size() > 0){
            	for(CategoryWiseRecord categoryWiseRecord : categoryWiseRecords){
            		List<QuestionWiseRecord> questionsRecord = this.getQuestionWiseRecords(taID, categoryWiseRecord.getCategoryID());
            		categoryWiseRecord.setQuestionsRecord(questionsRecord);
            	}
            }
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get category-wise scores from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
    	
		return categoryWiseRecords;
	}
	
	
	public void deleteTestSetAttempt(Integer taID) throws DAOException{
		//TODO: Not yet implemented
	}
	
	/**
	 * Test Set Attempt Row Mapper
	 * @author Shefali
	 *
	 */
	public class TestSetAttemptRowMapper implements RowMapper<TestSetAttempt>{
		
		@Override
        public TestSetAttempt mapRow(ResultSet rs, int rowNum)
                throws SQLException {
			
			TestSetAttempt ta = new TestSetAttempt();

			ta.setId(rs.getInt("id"));
			ta.setAttemptDate(new Date(rs.getLong("date")));
			ta.setScore(rs.getDouble("score"));
			ta.setTestSetID(rs.getInt("testSetID"));
			ta.setUserID(rs.getInt("userID"));
			
        	return ta;
        	
        }
	}
	
	/**
	 * Question Wise Record Row Mapper
	 * @author Shefali
	 *
	 */
	public class QuestionWiseRecordRowMapper implements RowMapper<QuestionWiseRecord>{
		
		@Override
        public QuestionWiseRecord mapRow(ResultSet rs, int rowNum)
                throws SQLException {
			
			QuestionWiseRecord qwr = new TestSetAttempt().new QuestionWiseRecord();
			
			qwr.setCategoryID(rs.getInt("categoryID"));
			qwr.setIsCorrectAnswer(rs.getBoolean("isCorrectAnswer"));
			qwr.setQuestionID(rs.getInt("questionID"));
			
			String userAnswers = rs.getString("userAnswers");
			
			if(userAnswers != null && userAnswers != ""){
				qwr.setUserAnswers(Utility.convertToListOfIntegers(userAnswers));
			}
			
        	return qwr;
        }
	}
	
	
	/**
	 * Category Wise Record Row Mapper
	 * @author Shefali
	 *
	 */
	public class CategoryWiseRecordRowMapper implements RowMapper<CategoryWiseRecord>{
		
		@Override
        public CategoryWiseRecord mapRow(ResultSet rs, int rowNum)
                throws SQLException {
			
			CategoryWiseRecord cwr = new TestSetAttempt().new CategoryWiseRecord();
			
			cwr.setId(rs.getInt("id"));
			cwr.setTestSetAttemptID(rs.getInt("testSetAttemptID"));
			cwr.setCategoryID(rs.getInt("categoryID"));
			cwr.setScore(rs.getDouble("score"));
			cwr.setIsCutoffReached(rs.getBoolean("isCutoffReached"));
			
        	return cwr;
        	
        }
	}

}

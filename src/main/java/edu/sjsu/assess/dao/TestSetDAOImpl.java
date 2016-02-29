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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.Question;
import edu.sjsu.assess.model.TestSet;
import edu.sjsu.assess.model.TestSetCategory;
import edu.sjsu.assess.model.TestSetSearchParams;

@Component
@Repository
public class TestSetDAOImpl implements TestSetDAO {

	@Autowired
	public DataSource dataSource;
	
	@Autowired
	public QuestionDAOImpl questionDAOImpl;

	@Override
	public TestSet createTestSet(final TestSet ts, Boolean jobcode) throws DAOException {
		final StringBuilder query = new StringBuilder();

		query.append("INSERT INTO testSet(jobCodeID, userID, cutoff, level, title,jobcode)");
		query.append("VALUES(?,?,?,?,?,?)");

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
					ps.setInt(index++, ts.getJobCodeID());
					ps.setInt(index++, ts.getUserID());
					ps.setFloat(index++, ts.getCutoff());
					ps.setString(index++, ts.getLevel());
					ps.setString(index++, ts.getTitle());
					ps.setBoolean(index++, jobcode);
					return ps;
				}
			}, keyHolder);

		} catch (Exception e) {
			e.printStackTrace();
			DAOException daoe = new DAOException(
					"Failed to insert test set in DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		ts.setId(keyHolder.getKey().intValue());

		// this.createTestSetCategories(ts.getTestSetCategories(), ts.getId());

		return ts;
	}

	@Override
	public List<TestSetCategory> createTestSetCategories(
			List<TestSetCategory> tsCategories, Integer testSetID, Boolean jobCode)
			throws DAOException {

		List<TestSetCategory> savedTSC = new ArrayList<>();

		if (tsCategories != null) {
			for (TestSetCategory tsc : tsCategories) {
				savedTSC.add(this.createTestSetCategory(tsc, testSetID));
			}
		}

		return savedTSC;
	}

	private TestSetCategory createTestSetCategory(final TestSetCategory tsc,
			final Integer testSetID) throws DAOException {

		final StringBuilder query = new StringBuilder();

		int fieldCount = 3;

		query.append("INSERT INTO testSetCategories(testSetID, categoryID, title"); // weightage,
																				// cutoff)

		final Float weightage = tsc.getWeightage();
		if (weightage != null) {
			query.append(", weightage");
			fieldCount++;
		}

		final Float cutoff = tsc.getCutoff();
		if (cutoff != null) {
			query.append(", cutoff");
			fieldCount++;
		}

		query.append(") ");
		query.append("VALUES(");

		for (int i = 0; i < fieldCount; i++) {
			query.append("?");
			if (i < fieldCount - 1) {
				query.append(",");
			}
		}

		query.append(")");

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

					ps.setInt(index++, testSetID);
					ps.setInt(index++, tsc.getCategoryID());
					ps.setString(index++, tsc.getTitle());

					if (weightage != null) {
						ps.setFloat(index++, tsc.getWeightage());
					}

					if (cutoff != null) {
						ps.setFloat(index++, tsc.getCutoff());
					}

					return ps;
				}
			}, keyHolder);

		} catch (Exception e) {
			e.printStackTrace();
			DAOException daoe = new DAOException(
					"Failed to insert test set category in DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		tsc.setId(keyHolder.getKey().intValue());

		// this.createTestSetQuestions(tsc.getQuestionList(),
		// tsc.getCategoryID());

		return tsc;
	}

	@Override
	public void createTestSetQuestions(List<Question> questions,
			Integer testSetCategoryID) throws DAOException {

		if (questions != null) {
			for (Question question : questions) {
				this.createTestSetQuestion(question.getId(), testSetCategoryID);
			}
		}
	}

	private void createTestSetQuestion(final Integer questionID,
			final Integer testSetCategoryID) throws DAOException {
		final StringBuilder query = new StringBuilder();

		query.append("INSERT INTO testSetQuestions(testSetCategoryID, questionID) ");
		query.append("VALUES(?,?)");

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

					ps.setInt(index++, testSetCategoryID);
					ps.setInt(index++, questionID);

					return ps;
				}
			}, keyHolder);

		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to insert test set questions in DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}
	}

	@Override
	public List<Integer> getTestSetList(TestSetSearchParams searchParams)
			throws DAOException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT ts.id FROM testSet ts");

		Map<String, String> joins = new HashMap<>();
		List<String> conditionList = new ArrayList<>();
		List<Object> params = new ArrayList<>();

		if(searchParams.getId()!=null) {
			conditionList.add("ts.id = ?");
			params.add(searchParams.getId());
		}

		if(searchParams.getUserID()!=null) {
			conditionList.add("ts.userid = ?");
			params.add(searchParams.getUserID());
		}

		if(searchParams.getCategoryID()!=null) {
			joins.put("testsetcategories tsc", "tsc.testsetid=ts.id");
			conditionList.add("tsc.categoryid = ? and tsc.status = ?");
			params.add(searchParams.getCategoryID());
			params.add("active");
		}

		if(searchParams.getJobcodeID()!=null) {
			conditionList.add("ts.jobcodeid = ?");
			params.add(searchParams.getJobcodeID());
		}

		if(searchParams.getTitle()!=null && !"".equals(searchParams.getTitle().trim())) {
			conditionList.add("ts.title like ?");
			params.add("%"+searchParams.getTitle()+"%");
		}

		if(searchParams.getLevel()!=null && !"".equals(searchParams.getLevel().trim())) {
			conditionList.add("ts.level = ?");
			params.add(searchParams.getLevel());
		}

		if(joins!=null && joins.size()>0) {
			for(String table: joins.keySet()) {
				query.append(" inner join "+ table);
				query.append(" on "+ joins.get(table));
			}
		}
		query.append(" where ts.status='active'");
		if(conditionList!=null && conditionList.size()>0) {
			for(String condition: conditionList) {
				query.append(" and "+condition);
			}
		}

		System.out.println(query.toString());
/*
		List<Object> paramsList = new ArrayList<>();

		int index = 0;

       if (searchParams.getId() != null && searchParams.getUserID() != null) {
			query.append("SELECT id FROM testSet ");
			query.append("WHERE ");
			query.append("id = ? ");
			query.append("AND userid = ?");
			paramsList.add(index++, searchParams.getId());
			paramsList.add(index++, searchParams.getUserID());

		}else if (searchParams.getId() != null && searchParams.getUserID() == null) {
            query.append("SELECT id FROM testSet ");
            query.append("WHERE ");
            query.append("id = ? ");
            paramsList.add(index++, searchParams.getId());


        } else if (searchParams.getJobcodeID() == null && searchParams.getUserID() != null) {
			query.append("SELECT id FROM testSet ");
			query.append("WHERE ");
			query.append("userid = ?");
			paramsList.add(index++, searchParams.getUserID());
			
		} else if (searchParams.getJobcodeID() == null && searchParams.getUserID() == null) {
           query.append("SELECT id FROM testSet ");
       } else {
			
			if (searchParams.getCategoryID() != null) {
				query.append("SELECT ts.id FROM testSet ts, testSetCategories tsc ");
				query.append("WHERE ");
				query.append("ts.id = tsc.testSetID ");

				query.append("AND ts.jobCodeID = ? ");
				paramsList.add(index++, searchParams.getJobcodeID());

				query.append("AND tsc.categoryID = ? ");
				paramsList.add(index++, searchParams.getCategoryID());
			} else {
				query.append("SELECT ts.id FROM testSet ts ");
				query.append("WHERE ");
				query.append("ts.jobCodeID = ? ");
				paramsList.add(index++, searchParams.getJobcodeID());
			}

			query.append("AND ts.userid = ?");
			paramsList.add(index++, searchParams.getUserID());
		}
*/

		List<Integer> result = new ArrayList<>();

		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			List<Map<String, Object>> rows = jdbcTemplate.queryForList(
					query.toString(), params.toArray());

			for (Map<String, Object> row : rows) {
				result.add(Integer.parseInt(String.valueOf(row.get("id"))));
			}

		} catch (Exception e) {
			e.printStackTrace();
			DAOException daoe = new DAOException(
					"Failed to get test set list from DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return result;
	}

	@Override
	public TestSet getTestSetByID(Integer tsID) throws DAOException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM testSet ");
		query.append("WHERE id = ?");

		TestSet testSet = null;
		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			testSet = jdbcTemplate.queryForObject(query.toString(),
					new Object[] { tsID }, new TestSetRowMapper());

			this.getTestSetCategories(testSet);

		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get training module from DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return testSet;
	}

	private void getTestSetCategories(TestSet ts) throws DAOException{

		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM testSetCategories ");
		query.append("WHERE testSetID = ? and status='active'");

		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			List<TestSetCategory> tscList = jdbcTemplate.query(query.toString(), new Object[] { ts.getId() }, new TestSetCategoriesMapper());
			
			if(tscList != null){
				for(TestSetCategory tsc : tscList){
					List<Question> qsList = this.getTestSetQuestions(tsc.getId());
					System.out.println("Delete this testsetdaoimpls ln no 409 :"+tsc.getJobCode());
					tsc.setQuestionList(qsList);
				}
			}
			
			ts.setTestSetCategories(tscList);

		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get test set categories from DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

	}
	
	private List<Question> getTestSetQuestions(Integer tscID) throws DAOException{
		
		List<Question> qsList = new ArrayList<>();
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT questionID FROM testSetQuestions ");
		query.append("WHERE testSetCategoryID = ?");
		
		try {
        	
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString(), new Object[] { tscID });
            
            for(Map<String, Object> row : rows){
            	Integer qsID = (Integer)(row.get("questionID"));
            	
            	Question qs = questionDAOImpl.getQuestionByID(qsID);
            	
            	qsList.add(qs);
            }
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to get test set questions from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
		
		return qsList;
	}
	
	@Override
	public void updateTestSetByID(TestSet ts) throws DAOException{
		
		String updateStatement = " UPDATE testSet "
                + "SET jobCodeID = ?, "
                + "cutoff = ?, "
                + "level = ?, "
				+ "title = ? "
                + "WHERE id = ?";
        
        try{
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(updateStatement, 
                    ts.getJobCodeID(),
                    ts.getCutoff(),
                    ts.getLevel(),
					ts.getTitle(),
                    ts.getId());
        }
        catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to update test set.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
	}

public void updateSetCategories(List<TestSetCategory> setCategories)
		throws DAOException
{
	for (TestSetCategory setCategory : setCategories) {
		String updateStatement = " UPDATE testsetcategories "
				+ "SET "
				+ "cutoff = ?, "
				+ "weightage = ?, "
				+ "title = ? "
				+ "WHERE id = ?";

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			jdbcTemplate.update(updateStatement,
					setCategory.getCutoff(),
					setCategory.getWeightage(),
					setCategory.getTitle(),
					setCategory.getId());
		}
		catch (Exception e) {
			e.printStackTrace();
			DAOException daoe = new DAOException(
					"Failed to update test set.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}
	}
}

	public void deleteTestSetCategories(Integer tsID) throws DAOException{
		
		String query = "UPDATE testSetCategories set status = 'deleted' WHERE id = ?";
    	
    	try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            jdbcTemplate.update(query, tsID);
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to delete training module categories from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
	}

	@Override
	public void deleteTestSetByID(Integer tsID) throws DAOException{
		
		String query = "Update testSet set status='deleted' WHERE id = ?";
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            jdbcTemplate.update(query, tsID);
            
        } catch (Exception e) {
            DAOException daoe = new DAOException(
                    "Failed to delete test set from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
	}

	public int deleteQuestionFromSetCategory(Integer testsetCategoryId, List<Integer> questionIds)
			throws DAOException
	{

		StringBuilder query = new StringBuilder();
		query.append("update testsetquestions set status =:deleted where testsetcategoryid=:setcategoryId");
		query.append(" and questionid in (:ids)");

		try {
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			Map<String, Object> parametersMap = new HashMap<>();
			parametersMap.put("deleted", "deleted");
			parametersMap.put("setcategoryId", testsetCategoryId);
			parametersMap.put("ids", questionIds);

			int deletedQuestions = namedParameterJdbcTemplate.update(query.toString(),
					parametersMap);
			return deletedQuestions;
		} catch(Exception e) {
			e.printStackTrace();
			DAOException daoException = new DAOException("Failed to delete question from setcategory");
			daoException.setStackTrace(e.getStackTrace());
			throw daoException;
        }
	}
	
	@Override
	public TestSetCategory getTestSetCategory(Integer testSetID, Integer categoryID) throws DAOException{
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM testSetCategories ");
		query.append("WHERE testSetID = ? AND ");
		query.append("categoryid = ?" +
				"AND status ='active'");

		TestSetCategory testSetCategory = null;
		
		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			testSetCategory = jdbcTemplate.queryForObject(query.toString(), new Object[] { testSetID, categoryID }, new TestSetCategoriesMapper());
			
		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get test set categories from DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}
		
		return testSetCategory;
	}
	
	/**
	 * Test Set Row Mapper
	 * 
	 * @author Shefali
	 *
	 */
	public class TestSetRowMapper implements RowMapper<TestSet> {

		@Override
		public TestSet mapRow(ResultSet rs, int rowNum) throws SQLException {

			TestSet ts = new TestSet();

			ts.setId(rs.getInt("id"));
			ts.setJobCodeID(rs.getInt("jobCodeID"));
			ts.setUserID(rs.getInt("userID"));
			ts.setCutoff(rs.getFloat("cutoff"));
			ts.setLevel(rs.getString("level"));
			ts.setTitle(rs.getString("title"));
			ts.setJobcode(rs.getBoolean("jobcode"));
			return ts;

		}
	}
	
	/**
	 * Test Set Row Mapper
	 * 
	 * @author Shefali
	 *
	 */
	public class TestSetCategoriesMapper implements RowMapper<TestSetCategory> {

		@Override
		public TestSetCategory mapRow(ResultSet rs, int rowNum) throws SQLException {

			TestSetCategory tsc = new TestSetCategory();

			tsc.setId(rs.getInt("id"));
			tsc.setCategoryID(rs.getInt("categoryID"));
			tsc.setCutoff(rs.getFloat("cutoff"));
			tsc.setWeightage(rs.getFloat("weightage"));
			tsc.setTitle(rs.getString("title"));
			tsc.setJobCode(rs.getBoolean("jobcode"));

			return tsc;
		}
	}

	public TestSetCategory addSetCategory(final int testsetId, final TestSetCategory setCategory)
		throws DAOException
	{
		final StringBuilder query = new StringBuilder();
		query.append("INSERT INTO testsetcategories(testsetid, categoryid, cutoff, weightage, title) values(");
		query.append("?, ?, ?, ?, ?)");

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

					ps.setInt(index++, testsetId);
					ps.setInt(index++, setCategory.getCategoryID());
					ps.setFloat(index++, setCategory.getCutoff());
					ps.setFloat(index++, setCategory.getWeightage());
					ps.setString(index++, setCategory.getTitle());

					return ps;
				}
			}, keyHolder);

		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to insert test set category in DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		setCategory.setId(keyHolder.getKey().intValue());

		return setCategory;
	}


}

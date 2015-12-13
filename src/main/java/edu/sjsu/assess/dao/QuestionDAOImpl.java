package edu.sjsu.assess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import edu.sjsu.assess.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.Option;
import edu.sjsu.assess.model.Question;
import edu.sjsu.assess.model.QuestionSearchParams;

@Repository
public class QuestionDAOImpl implements QuestionDAO {

	@Autowired
	public DataSource dataSource;
	

	@Override
	public Question createQuestion(final Question qs) throws DAOException {
		
		final StringBuilder query = new StringBuilder();
		StringBuilder valuesStr = new StringBuilder();
		
		query.append("INSERT INTO question(questionText, type, isTrueOrFalse, isMultipleChoice");
		valuesStr.append(" VALUES(?,?,?,?");
		
		
		// Optional field category
		final Integer categoryID = qs.getCategoryID();
		if(categoryID != null){
			query.append(", categoryID");
			valuesStr.append(",?");
		}
		
		// Optional field userID
		final Integer userID = qs.getUserID();
		if(userID != null){
			query.append(", userID");
			valuesStr.append(",?");
		}
		
		// Optional field focus
		final String focus = qs.getFocus();
		if(focus != null){
			query.append(", focus");
			valuesStr.append(",?");
		}
		
		// Optional field level
		final String level = qs.getLevel();
		if(level != null){
			query.append(", level");
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
							ps.setString(index++, qs.getQuestionText());
							ps.setString(index++, qs.getType());
							ps.setBoolean(index++, qs.isTrueOrFalse());
							ps.setBoolean(index++, qs.isMultipleChoice());
							
							if(categoryID != null){
					        	ps.setInt(index++, categoryID);
					        }
							
					        if(userID != null){
					        	ps.setInt(index++, userID);
					        }
					        
					        if(focus != null){
					        	ps.setString(index++, focus);
					        }
					        
					        if(level != null){
					        	ps.setString(index++, level);
					        }
					        
							return ps;
						}
					}, keyHolder);
            
            qs.setId(keyHolder.getKey().intValue());
            
            	// Create entries in Option table
            	List<Option> newOptions = this.createOptions(qs.getId(), qs.getOptions());
            	qs.setOptions(newOptions);

        } catch (Exception e) {
			e.printStackTrace();
            DAOException daoe = new DAOException(
                    "Failed to Insert Question in DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        return qs;
	}
	
	@Override
	public List<Option> createOptions(Integer qsID, List<Option> options) throws DAOException{
		
		List<Option> newOptions = new ArrayList<>();
		
		for(Option option : options){
    		option.setQuestionID(qsID);
    		Option newOption = this.createOption(option);
    		newOptions.add(newOption);
    	}

		return newOptions;
	}
	
	
	@Override
	public Option createOption(final Option op) throws DAOException{
		
		final StringBuilder query = new StringBuilder();
		query.append("INSERT INTO option(questionID, text, isCorrectOption) ");
		query.append("VALUES(?,?,?)");
		
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
							
							ps.setInt(index++, op.getQuestionID());
							ps.setString(index++, op.getText());
							ps.setBoolean(index++, op.isCorrectOption());
							
							return ps;
						}
					}, keyHolder);
            
		} 
		catch (Exception e) {
			e.printStackTrace();
            DAOException daoe = new DAOException(
                    "Failed to Insert Option in DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
		
		op.setId(keyHolder.getKey().intValue());
		return op;
	}

	public List<Question> searchQuestions(QuestionSearchParams searchParams)
			throws DAOException
	{
		List<Question> questions = new ArrayList<>();
		Map<Integer, Question> questionMap = new HashMap<>();
		List<String> conditionList = new ArrayList<>();
		List<Object> params = new ArrayList<>();

		StringBuilder query = new StringBuilder();
		query.append("select o.questionid, q.level, q.focus, q.questiontext, q.userid, " +
				"q.istrueorfalse, q.ismultiplechoice, q.type, " +
				"o.id, o.text, o.iscorrectoption, " +
				"q.categoryid, c.title " +
				" from question q inner join option o on o.questionid=q.id " +
				"inner join category c on q.categoryid=c.id ");

		if(searchParams.getCategoryID()!=null) {
			conditionList.add("q.categoryid = ?");
			params.add(searchParams.getCategoryID());
		}

		if(searchParams.getText()!=null && !"".equals(searchParams.getText().trim())) {
			conditionList.add("q.questiontext like ?");
			params.add("%"+searchParams.getText().trim().replaceAll(" ", "%")+"%");
		}

		if(searchParams.getLevel()!=null && !"".equals(searchParams.getLevel().trim())) {
			conditionList.add("q.level = ?");
			params.add(searchParams.getLevel());
		}

		if(searchParams.getFocus()!=null && !"".equals(searchParams.getFocus().trim())) {
			conditionList.add("q.focus = ?");
			params.add(searchParams.getFocus());
		}

		if(conditionList!=null && conditionList.size()>0) {
			query.append(" where " + conditionList.get(0));
			for(int i=1; i<conditionList.size(); i++) {
				query.append(" and "+conditionList.get(i));
			}
		}

		System.out.println(query);

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString(), params.toArray());

			for (Map<String, Object> row : rows){
				int questionId = (Integer) row.get("questionid");

				if(!questionMap.containsKey(questionId)) {
					Question question = new Question();
					question.setId((Integer) row.get("questionid"));
					question.setCategoryID((Integer) row.get("categoryid"));
					question.setLevel((String) row.get("level"));
					question.setFocus((String) row.get("focus"));
					question.setQuestionText((String) row.get("questiontext"));
					question.setTrueOrFalse((Boolean) row.get("istrueorfalse"));
					question.setMultipleChoice((Boolean) row.get("ismultiplechoice"));
					question.setUserID((Integer) row.get("userid"));

					Category category = new Category();
					category.setId((Integer) row.get("categoryid"));
					category.setTitle((String) row.get("title"));

					question.setCategoryObj(category);
					question.setOptions(new ArrayList<Option>());
					questions.add(question);
					questionMap.put(questionId, question);
				}

				Question question = questionMap.get(questionId);
				Option option = new Option();
				option.setId((Integer) row.get("id"));
				option.setCorrectOption((Boolean) row.get("iscorrectoption"));
				option.setText((String) row.get("text"));
				option.setQuestionID((Integer) row.get("questionid"));
				question.getOptions().add(option);
			}

		} catch (Exception e) {
			e.printStackTrace();
			DAOException daoe = new DAOException(
					"Failed to get questions list from DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}


		return questions;
	}
	
	@Override
	public List<Integer> getQuestionList(QuestionSearchParams searchParams)
			throws DAOException {

		StringBuilder query = new StringBuilder();

		Map<String, String> joins = new HashMap<>();
		List<String> conditionList = new ArrayList<>();
		List<Object> params = new ArrayList<>();

		query.append("select * from question q ");

		if(searchParams.getId()!=null) {
			conditionList.add("q.id = ?");
			params.add(searchParams.getId());
		}

		if(searchParams.getCategoryID()!=null) {
			conditionList.add("q.categoryid = ?");
			params.add(searchParams.getCategoryID());
		}

		if(searchParams.getJobcodeID()!=null) {
			joins.put("testsetquestions tsq", "tsq.questionid = q.id");
			joins.put("testsetcategories tsc", "tsc.id = tsq.testsetcategoryid");
			joins.put("testset ts", "ts.id = tsc.testsetid");

			conditionList.add("ts.jobcodeid = ?");
			params.add(searchParams.getJobcodeID());
		}

		if(searchParams.getText()!=null && !"".equals(searchParams.getText().trim())) {
			conditionList.add("q.questiontext like ?");
			params.add("%"+searchParams.getText().trim().replaceAll(" ", "%")+"%");
		}

		if(searchParams.getLevel()!=null && !"".equals(searchParams.getLevel().trim())) {
			conditionList.add("q.level = ?");
			params.add(searchParams.getLevel());
		}

		if(searchParams.getFocus()!=null && !"".equals(searchParams.getFocus().trim())) {
			conditionList.add("q.focus = ?");
			params.add(searchParams.getFocus());
		}

		if(joins!=null && joins.size()>0) {
			for(String table: joins.keySet()) {
				query.append(" inner join "+ table);
				query.append(" on "+ joins.get(table));
			}
		}

		if(conditionList!=null && conditionList.size()>0) {
			query.append(" where " + conditionList.get(0));
			for(int i=1; i<conditionList.size(); i++) {
				query.append(" and "+conditionList.get(i));
			}
		}

		System.out.println(query.toString());

		/*
		List<Object> paramsList = new ArrayList<>();

		int index = 0;
		
        if(searchParams.getId() != null){
        	query.append("SELECT id FROM question ");
            query.append("WHERE ");
        	query.append("id = ?");
        	paramsList.add(index++, searchParams.getId());
        	
        }
        else if(searchParams.getJobcodeID() == null){
        	query.append("SELECT id FROM question");
        }
        else {
    		if(searchParams.getCategoryID() != null){
        		query.append("SELECT id FROM question ");
        		query.append("WHERE ");
        		
        		query.append("jobID = ? ");
        		paramsList.add(index++, searchParams.getJobcodeID());
        		
        		query.append("AND categoryID = ? ");
        		paramsList.add(index++, searchParams.getCategoryID());
    		}
        	else{
        		query.append("SELECT id FROM question ");
                query.append("WHERE ");
        		query.append("jobID = ? ");
            	paramsList.add(index ++, searchParams.getJobcodeID());
        	}
    	
        	if(searchParams.getFocus() != null){
        		query.append("AND focus = ? ");
        		paramsList.add(searchParams.getFocus());
        	}
        	
        	if(searchParams.getLevel() != null){
        		query.append("AND level = ? ");
        	}
    	}*/
        
        List<Integer> result = new ArrayList<>();
        
        try {
        	
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString(), params.toArray());
            
            for (Map<String, Object> row : rows){
            	result.add(Integer.parseInt(String.valueOf(row.get("id"))));
            }
            
        } catch (Exception e) {
			e.printStackTrace();
            DAOException daoe = new DAOException(
                    "Failed to get questions list from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        return result;
	}
	
	@Override
	public Question getQuestionByID(Integer qsID) throws DAOException {
		
		StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM question ");
        query.append("WHERE id = ?");

        Question question = null;
        
        try {
        	
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            question = jdbcTemplate.queryForObject(query.toString(),
                    new Object[] { qsID }, new QuestionRowMapper());
            
			List<Option> options = this.getOptions(qsID);
			question.setOptions(options);
            
        } catch (Exception e) { e.printStackTrace();
            DAOException daoe = new DAOException(
                    "Failed to get question from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        return question;
	}

	public List<Question> getQuestionByID(List<Integer> quesIds) throws DAOException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM question ");
		query.append("WHERE id in (:ids)");

		List<Question> questionList = null;

		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			questionList = namedParameterJdbcTemplate.query(query.toString(),
					Collections.singletonMap("ids", quesIds),
					new QuestionRowMapper());

			for(Question question: questionList) {
				List<Option> options = this.getOptions(question.getId());
				question.setOptions(options);
			}

		} catch (Exception e) { e.printStackTrace();
			DAOException daoe = new DAOException(
					"Failed to get question from DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return questionList;
	}


	private List<Option> getOptions(Integer qsID) throws DAOException {
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM option ");
		query.append("WHERE questionID = ?");
		
		List<Option> optionsList = null;
		
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			optionsList = jdbcTemplate.query(query.toString(),  new Object[] { qsID }, new OptionRowMapper());
		}
		catch (Exception e) {
			e.printStackTrace();
            DAOException daoe = new DAOException(
                    "Failed to get question from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
		
		return optionsList;
	}
	
	@Override
	public void updateQuestionByID(Question qs) throws DAOException{
		
		String updateStatement = " UPDATE question "
                + "SET "
                + "categoryID = ?, " 
                + "questionText = ?, "
                + "isTrueOrFalse = ?, "
                + "isMultipleChoice = ?, "
                + "focus = ?, "
                + "level = ? "
                + "WHERE id = ?";
        
        try{
        	
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            jdbcTemplate.update(updateStatement, 
//                    qs.getJobCodeID(),
                    qs.getCategoryID(),
                    qs.getQuestionText(),
                    qs.isTrueOrFalse(),
                    qs.isMultipleChoice(),
                    qs.getFocus(),
                    qs.getLevel(),
            		qs.getId());
			updateOptions(qs.getOptions());
        }
        catch (Exception e) {
			e.printStackTrace();
            DAOException daoe = new DAOException(
                    "Failed to update question.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
	}
	
	public void updateOptions(List<Option> optionList) throws DAOException
	{
		String updateStatement = " UPDATE option "
				+ "SET text = ?, "
				+ "iscorrectoption = ? "
				+ "WHERE id = ?";
		for(Option option: optionList) {

			try{
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

				jdbcTemplate.update(updateStatement,
						option.getText(), option.isCorrectOption(), option.getId());

			} catch (Exception e) { e.printStackTrace();
				e.printStackTrace();
				DAOException daoe = new DAOException(
						"Failed to update question.");
				daoe.setStackTrace(e.getStackTrace());
				throw daoe;
			}
		}


		}

	@Override
	public void deleteAllOptions(Integer qsID) throws DAOException{
		
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM option ");
		query.append("WHERE questionID = ?");
		
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			jdbcTemplate.update(query.toString(), qsID);
		}
		catch (Exception e) {
			e.printStackTrace();
            DAOException daoe = new DAOException(
                    "Failed to delete options from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
	}
	
	@Override
	public void deleteQuestionByID(Integer qsID) throws DAOException{
		
		String query = "DELETE FROM question WHERE id = ?";
        
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            jdbcTemplate.update(query, qsID);
            
        } catch (Exception e) {
			e.printStackTrace();
            DAOException daoe = new DAOException(
                    "Failed to delete question from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
	}
	
	/**
	 * Question Row Mapper
	 * @author Shefali
	 *
	 */
	public class QuestionRowMapper implements RowMapper<Question>{
		
		@Override
        public Question mapRow(ResultSet rs, int rowNum)
                throws SQLException {
			
        	Question qs = new Question();
        	qs.setId(rs.getInt("id"));
        	
//        	qs.setJobCodeID(rs.getInt("jobID"));
        	qs.setCategoryID(rs.getInt("categoryID"));
        	qs.setQuestionText(rs.getString("questionText"));
        	qs.setType(rs.getString("type"));
        	qs.setTrueOrFalse(rs.getBoolean("isTrueOrFalse"));
        	qs.setMultipleChoice(rs.getBoolean("isMultipleChoice"));
        	qs.setFocus(rs.getString("focus"));
        	qs.setLevel(rs.getString("level"));
        	
        	qs.setUserID(rs.getInt("userID"));
        	
        	return qs;
        }
	}
	
	
	/**
	 * Option Row Mapper
	 * @author Shefali
	 *
	 */
	public class OptionRowMapper implements RowMapper<Option>{
		
		@Override
        public Option mapRow(ResultSet rs, int rowNum)
                throws SQLException {
			
			Option op = new Option();
			
        	op.setId(rs.getInt("id"));
        	op.setQuestionID(rs.getInt("questionID"));
        	op.setText(rs.getString("text"));
        	op.setCorrectOption(rs.getBoolean("isCorrectOption"));
        	
        	return op;
        }
	}

}

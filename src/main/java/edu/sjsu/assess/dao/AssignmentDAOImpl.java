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

import edu.sjsu.assess.model.Assignment;
import edu.sjsu.assess.model.AssignmentSearchParams;
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

@Repository
public class AssignmentDAOImpl implements AssignmentDAO {

	@Autowired
	public DataSource dataSource;
	

	@Override
	public Assignment createAssignment(Assignment qs) throws DAOException {
			
		final StringBuilder query = new StringBuilder();
		StringBuilder valuesStr = new StringBuilder();
		
		query.append("INSERT INTO assignment(questionText, answerText, type, isTrueOrFalse, isMultipleChoice");
		valuesStr.append(" VALUES(?,?,?,?,?");
		
		
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
							ps.setString(index++, qs.getAnswerText());
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
                    "Failed to Insert Assigment in DB.");
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
		query.append("INSERT INTO assignment_option(assignmentid, text, isCorrectOption) ");
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
                    "Failed to Insert assignment_option in DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
		
		op.setId(keyHolder.getKey().intValue());
		return op;
	}

	public List<Assignment> searchAssignments(AssignmentSearchParams searchParams)
			throws DAOException
	{
		List<Assignment> assignments = new ArrayList<>();
		Map<Integer, Assignment> assignmentMap = new HashMap<>();
		List<String> conditionList = new ArrayList<>();
		List<Object> params = new ArrayList<>();

		StringBuilder query = new StringBuilder();
		query.append("select o.assignmentid, q.level, q.focus, q.questiontext, q.answertext, q.userid, " +
				"q.istrueorfalse, q.ismultiplechoice, q.type, " +
				"o.id, o.text, o.iscorrectoption, " +
				"q.categoryid, c.title " +
				" from assignment q inner join assignment_option o on o.assignmentid=q.id " +
				"inner join category c on q.categoryid=c.id ");

		if(searchParams.getCategoryID()!=null) {
			conditionList.add("q.categoryid = ?");
			params.add(searchParams.getCategoryID());
		}

		if(searchParams.getText()!=null && !"".equals(searchParams.getText().trim())) {
			conditionList.add("q.questiontext like ?");
			params.add("%"+searchParams.getText().trim().replaceAll(" ", "%")+"%");
		}
		
		if(searchParams.getText()!=null && !"".equals(searchParams.getText().trim())) {
			conditionList.add("q.answertext like ?");
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
		System.out.println("Preeeeeeeeti");
		System.out.println(query);

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString(), params.toArray());

			for (Map<String, Object> row : rows){
				int assignmentid = (Integer) row.get("assignmentid");

				if(!assignmentMap.containsKey(assignmentid)) {
					Assignment assignment = new Assignment();
					assignment.setId((Integer) row.get("assignmentid"));
					assignment.setCategoryID((Integer) row.get("categoryid"));
					assignment.setLevel((String) row.get("level"));
					assignment.setFocus((String) row.get("focus"));
					assignment.setQuestionText((String) row.get("questiontext"));
					assignment.setAnswerText((String) row.get("answertext"));
					assignment.setTrueOrFalse((Boolean) row.get("istrueorfalse"));
					assignment.setMultipleChoice((Boolean) row.get("ismultiplechoice"));
					assignment.setUserID((Integer) row.get("userid"));

					Category category = new Category();
					category.setId((Integer) row.get("categoryid"));
					category.setTitle((String) row.get("title"));

					assignment.setCategoryObj(category);
					assignment.setOptions(new ArrayList<Option>());
					assignments.add(assignment);
					assignmentMap.put(assignmentid, assignment);
				}

				Assignment assignment = assignmentMap.get(assignmentid);
				Option option = new Option();
				option.setId((Integer) row.get("id"));
				option.setCorrectOption((Boolean) row.get("iscorrectoption"));
				option.setText((String) row.get("text"));
				option.setQuestionID((Integer) row.get("assignmentid"));
				assignment.getOptions().add(option);
			}

		} catch (Exception e) {
			e.printStackTrace();
			DAOException daoe = new DAOException(
					"Failed to get assignments list from DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		System.out.println("Assignment Lists:" +assignments);
		return assignments;
	}
	
	@Override
	public List<Integer> getAssignmentList(AssignmentSearchParams searchParams)
			throws DAOException {

		StringBuilder query = new StringBuilder();

		Map<String, String> joins = new HashMap<>();
		List<String> conditionList = new ArrayList<>();
		List<Object> params = new ArrayList<>();

		query.append("select * from assignment q ");

		if(searchParams.getId()!=null) {
			conditionList.add("q.id = ?");
			params.add(searchParams.getId());
		}

		if(searchParams.getCategoryID()!=null) {
			conditionList.add("q.categoryid = ?");
			params.add(searchParams.getCategoryID());
		}

		if(searchParams.getJobcodeID()!=null) {
			joins.put("testsetquestions tsq", "tsq.assignmentid = q.id");
			joins.put("testsetcategories tsc", "tsc.id = tsq.testsetcategoryid");
			joins.put("testset ts", "ts.id = tsc.testsetid");

			conditionList.add("ts.jobcodeid = ?");
			params.add(searchParams.getJobcodeID());
		}

		if(searchParams.getText()!=null && !"".equals(searchParams.getText().trim())) {
			conditionList.add("q.questiontext like ?");
			params.add("%"+searchParams.getText().trim().replaceAll(" ", "%")+"%");
		}
		
		if(searchParams.getText()!=null && !"".equals(searchParams.getText().trim())) {
			conditionList.add("q.answertext like ?");
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
                    "Failed to get assignments list from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        return result;
	}
	
	@Override
	public List<Assignment> getAssignmentList2(AssignmentSearchParams searchParams)
			throws DAOException {

		StringBuilder query = new StringBuilder();

		Map<String, String> joins = new HashMap<>();
		List<String> conditionList = new ArrayList<>();
		List<Object> params = new ArrayList<>();

		query.append("select * from assignment q ");

		if(searchParams.getId()!=null) {
			conditionList.add("q.id = ?");
			params.add(searchParams.getId());
		}

		if(searchParams.getCategoryID()!=null) {
			conditionList.add("q.categoryid = ?");
			params.add(searchParams.getCategoryID());
		}

		if(searchParams.getJobcodeID()!=null) {
			joins.put("testsetquestions tsq", "tsq.assignmentid = q.id");
			joins.put("testsetcategories tsc", "tsc.id = tsq.testsetcategoryid");
			joins.put("testset ts", "ts.id = tsc.testsetid");

			conditionList.add("ts.jobcodeid = ?");
			params.add(searchParams.getJobcodeID());
		}

		if(searchParams.getText()!=null && !"".equals(searchParams.getText().trim())) {
			conditionList.add("q.questiontext like ?");
			params.add("%"+searchParams.getText().trim().replaceAll(" ", "%")+"%");
		}
		
		if(searchParams.getText()!=null && !"".equals(searchParams.getText().trim())) {
			conditionList.add("q.answertext like ?");
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
        
        List<Assignment> result = new ArrayList<>();
        
        try {
        	
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString(), params.toArray());
            System.out.println("Rows!!!!!" +rows);
            for (Map<String, Object> row : rows){
            	result.add(Integer.parseInt(String.valueOf(row.get("id"))), null);
            }
            
        } catch (Exception e) {
			e.printStackTrace();
            DAOException daoe = new DAOException(
                    "Failed to get assignments list from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        return result;
	}
	
	@Override
	public Assignment getAssignmentByID(Integer qsID) throws DAOException {
		
		StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM assignment ");
        query.append("WHERE id = ?");

        Assignment assignment = null;
        
        try {
        	
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            assignment = jdbcTemplate.queryForObject(query.toString(),
                    new Object[] { qsID }, new AssignmentRowMapper());
            
			List<Option> options = this.getOptions(qsID);
			assignment.setOptions(options);
            
        } catch (Exception e) { e.printStackTrace();
            DAOException daoe = new DAOException(
                    "Failed to get assignment from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }

        return assignment;
	}

	public List<Assignment> getAssignmentByID(List<Integer> quesIds) throws DAOException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM assignment ");
		query.append("WHERE id in (:ids)");

		List<Assignment> assignmentList = null;

		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			assignmentList = namedParameterJdbcTemplate.query(query.toString(),
					Collections.singletonMap("ids", quesIds),
					new AssignmentRowMapper());

			for(Assignment assignment: assignmentList) {
				List<Option> options = this.getOptions(assignment.getId());
				assignment.setOptions(options);
			}

		} catch (Exception e) { e.printStackTrace();
			DAOException daoe = new DAOException(
					"Failed to get assignment from DB.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return assignmentList;
	}


	private List<Option> getOptions(Integer qsID) throws DAOException {
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM assignment_option ");
		query.append("WHERE assignmentid = ?");
		
		List<Option> optionsList = null;
		
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			optionsList = jdbcTemplate.query(query.toString(),  new Object[] { qsID }, new OptionRowMapper());
		}
		catch (Exception e) {
			e.printStackTrace();
            DAOException daoe = new DAOException(
                    "Failed to get assignment from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
		
		return optionsList;
	}
	
	@Override
	public void updateAssignmentByID(Assignment qs) throws DAOException{
		
		String updateStatement = " UPDATE assignment "
                + "SET "
                + "categoryID = ?, " 
                + "questionText = ?, "
                + "answerText = ?, "
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
                    "Failed to update assignment.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
	}
	
	public void updateOptions(List<Option> optionList) throws DAOException
	{
		String updateStatement = " UPDATE assignment_option "
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
						"Failed to update assignment.");
				daoe.setStackTrace(e.getStackTrace());
				throw daoe;
			}
		}


		}

	@Override
	public void deleteAllOptions(Integer qsID) throws DAOException{
		
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM assignment_option ");
		query.append("WHERE assignmentid = ?");
		
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			jdbcTemplate.update(query.toString(), qsID);
		}
		catch (Exception e) {
			e.printStackTrace();
            DAOException daoe = new DAOException(
                    "Failed to delete assignment_options from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
	}
	
	@Override
	public void deleteAssignmentByID(Integer qsID) throws DAOException{
		
		String query = "DELETE FROM assignment WHERE id = ?";
        
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            jdbcTemplate.update(query, qsID);
            
        } catch (Exception e) {
			e.printStackTrace();
            DAOException daoe = new DAOException(
                    "Failed to delete assignment from DB.");
            daoe.setStackTrace(e.getStackTrace());
            throw daoe;
        }
	}

	public class AssignmentRowMapper implements RowMapper<Assignment>{
		
		@Override
        public Assignment mapRow(ResultSet rs, int rowNum)
                throws SQLException {
			
			Assignment qs = new Assignment();
        	qs.setId(rs.getInt("id"));
        	
//        	qs.setJobCodeID(rs.getInt("jobID"));
        	qs.setCategoryID(rs.getInt("categoryID"));
        	qs.setQuestionText(rs.getString("questionText"));
        	qs.setAnswerText(rs.getString("answerText"));
        	qs.setType(rs.getString("type"));
        	qs.setTrueOrFalse(rs.getBoolean("isTrueOrFalse"));
        	qs.setMultipleChoice(rs.getBoolean("isMultipleChoice"));
        	qs.setFocus(rs.getString("focus"));
        	qs.setLevel(rs.getString("level"));
        	
        	qs.setUserID(rs.getInt("userID"));
        	
        	return qs;
        }
	}
	

	public class OptionRowMapper implements RowMapper<Option>{
		
		@Override
        public Option mapRow(ResultSet rs, int rowNum)
                throws SQLException {
			
			Option op = new Option();
			
        	op.setId(rs.getInt("id"));
        	op.setQuestionID(rs.getInt("assignmentid"));
        	op.setText(rs.getString("text"));
        	op.setCorrectOption(rs.getBoolean("isCorrectOption"));
        	
        	return op;
        }
	}



}

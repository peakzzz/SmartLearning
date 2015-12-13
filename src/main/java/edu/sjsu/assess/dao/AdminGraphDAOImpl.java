package edu.sjsu.assess.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.AdminGraphData.ContentHolder;
import edu.sjsu.assess.model.AdminGraphData.JobCodeAttempt;
import edu.sjsu.assess.model.AdminGraphData.JobCodeDetails;
import edu.sjsu.assess.model.AdminGraphData.JobCodeScore;
import edu.sjsu.assess.model.AdminGraphData.RegistrationCount;

@Component
@Repository
public class AdminGraphDAOImpl implements AdminGraphDAO {

	@Autowired
	public DataSource dataSource;

	public JobCodeAttempt getJobcodeAttempts() throws DAOException {

		JobCodeAttempt result = new JobCodeAttempt();

		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) AS attempts, jc.positionName as jobCode ");
		query.append("FROM testSetAttempt ta, testSet ts, jobcode jc ");
		query.append("WHERE ta.testSetID = ts.id AND ");
		query.append("ts.jobCodeID = jc.id AND ");
		query.append("jc.type = 'userdefined' ");
		query.append("GROUP BY jc.positionName");

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			final List<Map<String, Object>> rows = jdbcTemplate
					.queryForList(query.toString());

			List<String> jobCodes = new ArrayList<String>();
			List<Integer> attempts = new ArrayList<Integer>();

			for (Map<String, Object> row : rows) {
				Integer attemptCount = Integer.parseInt(String.valueOf(row
						.get("attempts")));
				attempts.add(attemptCount);

				String jobCode = String.valueOf(row.get("jobCode"));
				jobCodes.add(jobCode);
			}

			result.getAttempt().addAll(attempts);
			result.getJobcode().addAll(jobCodes);

		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get job code attempts for admin dashboard.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return result;
	}

	@Override
	public JobCodeScore getJobCodeScores() throws DAOException {
		JobCodeScore result = new JobCodeScore();

		StringBuilder query = new StringBuilder();
		query.append("SELECT AVG(ta.score) AS score, jc.positionName as jobCode ");
		query.append("FROM testSetAttempt ta, testSet ts, jobcode jc ");
		query.append("WHERE ta.testSetID = ts.id AND ");
		query.append("ts.jobCodeID = jc.id AND ");
		query.append("jc.type = 'userdefined' ");
		query.append("GROUP BY jc.positionName");

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			final List<Map<String, Object>> rows = jdbcTemplate
					.queryForList(query.toString());

			List<String> jobCodes = new ArrayList<String>();
			List<Double> averageScores = new ArrayList<Double>();

			for (Map<String, Object> row : rows) {
				String jobCode = String.valueOf(row.get("jobCode"));
				jobCodes.add(jobCode);

				Double score = Double.parseDouble(String.valueOf(row
						.get("score")));
				averageScores.add(score);
			}

			result.getJobcode().addAll(jobCodes);
			result.getMean().addAll(averageScores);

		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get average scores in job codes for admin dashboard.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return result;
	}

	@Override
	public JobCodeDetails getCategoryWiseScoresForQA() throws DAOException {
		JobCodeDetails result = null;

		StringBuilder query = new StringBuilder();
		query.append("SELECT AVG(cws.score) AS avg_score, MAX(cws.score) as max_score, MIN(cws.score) as min_score, c.title as category ");
		query.append("FROM testSetAttempt ta, categoryWiseScore cws, testSet ts, jobcode jc, category c ");
		query.append("WHERE ta.id = cws.testSetAttemptID AND ");
		query.append("ta.testSetID = ts.id AND ");
		query.append("ts.jobCodeID = jc.id AND ");
		query.append("cws.categoryID = c.id AND ");
		query.append("jc.predefinedJobID = (select pjc.id from jobcode pjc where pjc.positionName LIKE 'QA Engineer') AND ");
		query.append("jc.type = 'userdefined' ");
		query.append("GROUP BY c.title");

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			final List<Map<String, Object>> rows = jdbcTemplate
					.queryForList(query.toString());

			List<String> categories = new ArrayList<String>();
			List<Double> averageScores = new ArrayList<Double>();
			List<List<Double>> dataList = new ArrayList<List<Double>>();

			for (Map<String, Object> row : rows) {
				String category = String.valueOf(row.get("category"));
				categories.add(category);

				final Double avgScore = Double.parseDouble(String.valueOf(row
						.get("avg_score")));
				final Double maxScore = Double.parseDouble(String.valueOf(row
						.get("max_score")));
				final Double minScore = Double.parseDouble(String.valueOf(row
						.get("min_score")));

				List<Double> data = new ArrayList<Double>() {

					private static final long serialVersionUID = 1L;

					{
						add(minScore);
						add(minScore);
						add(avgScore);
						add(maxScore);
						add(maxScore);
					}
				};

				dataList.add(data);
			}

			Double scoresSum = (double) 0;
			for (Double score : averageScores) {
				scoresSum += score;
			}

			Double mean = scoresSum / averageScores.size();

			result = new JobCodeDetails(
					"Category-wise scores (QA Engineer)", mean);
			
			result.getData().addAll(dataList);
			result.getCategory().addAll(categories);

		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get category-wise scores in QA for admin dashboard.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return result;
	}
	
	@Override
	public JobCodeDetails getCategoryWiseScoresForSoftwareDevelpment() throws DAOException {
		JobCodeDetails result = null;

		StringBuilder query = new StringBuilder();
		query.append("SELECT AVG(cws.score) AS avg_score, MAX(cws.score) as max_score, MIN(cws.score) as min_score, c.title as category ");
		query.append("FROM testSetAttempt ta, categoryWiseScore cws, testSet ts, jobcode jc, category c ");
		query.append("WHERE ta.id = cws.testSetAttemptID AND ");
		query.append("ta.testSetID = ts.id AND ");
		query.append("ts.jobCodeID = jc.id AND ");
		query.append("cws.categoryID = c.id AND ");
		query.append("jc.predefinedJobID = (select pjc.id from jobcode pjc where pjc.positionName = 'Software Development Engineer') AND ");
		//query.append("jc.positionName LIKE 'Software' AND ");
		query.append("jc.type = 'userdefined' ");
		query.append("GROUP BY c.title");
		
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			final List<Map<String, Object>> rows = jdbcTemplate
					.queryForList(query.toString());

			List<String> categories = new ArrayList<String>();
			List<Double> averageScores = new ArrayList<Double>();
			List<List<Double>> dataList = new ArrayList<List<Double>>();

			for (Map<String, Object> row : rows) {
				String category = String.valueOf(row.get("category"));
				categories.add(category);

				final Double avgScore = Double.parseDouble(String.valueOf(row
						.get("avg_score")));
				final Double maxScore = Double.parseDouble(String.valueOf(row
						.get("max_score")));
				final Double minScore = Double.parseDouble(String.valueOf(row
						.get("min_score")));

				List<Double> data = new ArrayList<Double>() {

					private static final long serialVersionUID = 1L;

					{
						add(minScore);
						add(minScore);
						add(avgScore);
						add(maxScore);
						add(maxScore);
					}
				};

				dataList.add(data);
			}

			Double scoresSum = (double) 0;
			for (Double score : averageScores) {
				scoresSum += score;
			}

			Double mean = scoresSum / averageScores.size();

			result = new JobCodeDetails(
					"Category-wise scores (Software Development)", mean);
			
			result.getData().addAll(dataList);
			result.getCategory().addAll(categories);

		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get category-wise scores in software development for admin dashboard.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return result;
	}

	@Override
	public RegistrationCount getProfessionBasedRegistration()
			throws DAOException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) AS count, profession ");
		query.append("FROM users ");
		query.append("WHERE role = 'candidate' ");
		query.append("GROUP BY profession");
		
		RegistrationCount result = new RegistrationCount(
				"Profession-Based Registration");
		
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			final List<Map<String, Object>> rows = jdbcTemplate
					.queryForList(query.toString());

			List<ContentHolder> content = new ArrayList<>();

			for (Map<String, Object> row : rows) {
				Integer count = ((Long)row.get("count")).intValue();
				String profession = String.valueOf(row.get("profession"));
				content.add(new ContentHolder(profession, count));
			}
			
			result.getEntries().addAll(content);
			
		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get profession-based registration for admin dashboard.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}
		
		return result;
	}
	
	@Override
	public RegistrationCount getAgeBasedRegistration() throws DAOException{
		RegistrationCount result = new RegistrationCount(
				"Age-Based Registration");
		
		StringBuilder query_20to25 = new StringBuilder();
		query_20to25.append("SELECT COUNT(*) FROM users ");
		query_20to25.append("WHERE 2015 - TO_NUMBER(TO_CHAR(TO_TIMESTAMP(birthdate / 1000), 'YYYY'), '9999') BETWEEN 20 AND 25");
		
		StringBuilder query_25to30 = new StringBuilder();
		query_25to30.append("SELECT COUNT(*) FROM users ");
		query_25to30.append("WHERE 2015 - TO_NUMBER(TO_CHAR(TO_TIMESTAMP(birthdate / 1000), 'YYYY'), '9999') BETWEEN 25 AND 30");
		
		StringBuilder query_30to35 = new StringBuilder();
		query_30to35.append("SELECT COUNT(*) FROM users ");
		query_30to35.append("WHERE 2015 - TO_NUMBER(TO_CHAR(TO_TIMESTAMP(birthdate / 1000), 'YYYY'), '9999') BETWEEN 30 AND 35");
		
		StringBuilder query_35to40 = new StringBuilder();
		query_35to40.append("SELECT COUNT(*) FROM users ");
		query_35to40.append("WHERE 2015 - TO_NUMBER(TO_CHAR(TO_TIMESTAMP(birthdate / 1000), 'YYYY'), '9999') BETWEEN 35 AND 40");
		
		StringBuilder query_40to45 = new StringBuilder();
		query_40to45.append("SELECT COUNT(*) FROM users ");
		query_40to45.append("WHERE 2015 - TO_NUMBER(TO_CHAR(TO_TIMESTAMP(birthdate / 1000), 'YYYY'), '9999') BETWEEN 40 AND 45");
		
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			// Age Group 20 to 25
			List<ContentHolder> content1 = new ArrayList<>();
			final List<Map<String, Object>> rows1 = jdbcTemplate
					.queryForList(query_20to25.toString());
			for (Map<String, Object> row : rows1) {
				Integer count = ((Long)row.get("count")).intValue();
				content1.add(new ContentHolder("20-25", count));
			}
			result.getEntries().addAll(content1);
			
			
			// Age Group 25 to 30
			List<ContentHolder> content2 = new ArrayList<>();
			final List<Map<String, Object>> rows2 = jdbcTemplate
					.queryForList(query_25to30.toString());
			for (Map<String, Object> row : rows2) {
				Integer count = ((Long)row.get("count")).intValue();
				content2.add(new ContentHolder("25-30", count));
			}
			result.getEntries().addAll(content2);
			
			
			// Age Group 30 to 35
			List<ContentHolder> content3 = new ArrayList<>();
			final List<Map<String, Object>> rows3 = jdbcTemplate
					.queryForList(query_30to35.toString());
			for (Map<String, Object> row : rows3) {
				Integer count = ((Long)row.get("count")).intValue();
				content3.add(new ContentHolder("30-35", count));
			}
			result.getEntries().addAll(content3);
						
						
			// Age Group 35 to 40
			List<ContentHolder> content4 = new ArrayList<>();
			final List<Map<String, Object>> rows4 = jdbcTemplate
					.queryForList(query_35to40.toString());
			for (Map<String, Object> row : rows4) {
				Integer count = ((Long)row.get("count")).intValue();
				content4.add(new ContentHolder("35-40", count));
			}
			result.getEntries().addAll(content4);
			
			
			// Age Group 40 to 45
			List<ContentHolder> content5 = new ArrayList<>();
			final List<Map<String, Object>> rows5 = jdbcTemplate
					.queryForList(query_40to45.toString());
			for (Map<String, Object> row : rows5) {
				Integer count = ((Long)row.get("count")).intValue();
				content5.add(new ContentHolder("40-45", count));
			}
			result.getEntries().addAll(content5);
			
		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get age-based registration for admin dashboard.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}
		
		return result;
	}
	
}

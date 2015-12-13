package edu.sjsu.assess.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import edu.sjsu.assess.exception.DAOException;
import edu.sjsu.assess.model.AdminGraphData.ContentHolder;
import edu.sjsu.assess.model.CandidateGraphData;
import edu.sjsu.assess.model.CandidateGraphData.AverageHolder;
import edu.sjsu.assess.model.CandidateGraphData.DomainWisePerformance;
import edu.sjsu.assess.model.CandidateGraphData.EffortsDevoted;
import edu.sjsu.assess.model.CandidateGraphData.PerformanceData;
import edu.sjsu.assess.model.TrainingModule;
import edu.sjsu.assess.model.TrainingModuleGraphData.JobCodeModule;

@Component
@Repository
public class CandidateGraphDAOImpl implements CandidateGraphDAO {

	@Autowired
	public DataSource dataSource;

	@Override
	public AverageHolder getFrequencyAttemptData(Integer userID)
			throws DAOException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT TO_CHAR(TO_TIMESTAMP(date / 1000), 'mon') as month, COUNT(*) from testSetAttempt ");
		query.append("WHERE userID = ? ");
		// query.append(" AND  TO_CHAR(TO_TIMESTAMP(date / 1000), 'YY') = '14' ");
		query.append("GROUP BY TO_CHAR(TO_TIMESTAMP(date / 1000), 'mon')");

		AverageHolder result = new AverageHolder();

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			final List<Map<String, Object>> rows = jdbcTemplate.queryForList(
					query.toString(), new Object[] { userID });

			List<Double> avg = result.getAvg();

			for (Map<String, Object> row : rows) {
				String month = String.valueOf(row.get("month"));
				Double count = Double.parseDouble(String.valueOf(row
						.get("count")));

				if (month.equalsIgnoreCase("jan")) {
					avg.set(0, count);
				} else if (month.equalsIgnoreCase("feb")) {
					avg.set(1, count);
				} else if (month.equalsIgnoreCase("mar")) {
					avg.set(2, count);
				} else if (month.equalsIgnoreCase("apr")) {
					avg.set(3, count);
				} else if (month.equalsIgnoreCase("may")) {
					avg.set(4, count);
				} else if (month.equalsIgnoreCase("jun")) {
					avg.set(5, count);
				} else if (month.equalsIgnoreCase("jul")) {
					avg.set(6, count);
				} else if (month.equalsIgnoreCase("aug")) {
					avg.set(7, count);
				} else if (month.equalsIgnoreCase("sep")) {
					avg.set(8, count);
				} else if (month.equalsIgnoreCase("oct")) {
					avg.set(9, count);
				} else if (month.equalsIgnoreCase("nov")) {
					avg.set(10, count);
				} else if (month.equalsIgnoreCase("dec")) {
					avg.set(11, count);
				}
			}

		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get attempt frequency for candidate.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return result;
	}

	@Override
	public AverageHolder getPerformanceProgression(Integer userID)
			throws DAOException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT avg(score) as avg_score, TO_CHAR(TO_TIMESTAMP(date / 1000), 'mon') as month ");
		query.append("FROM testSetAttempt ");
		query.append("WHERE userID = ? ");
		// query.append("AND TO_CHAR(TO_TIMESTAMP(date / 1000), 'YY') = '14' ");
		query.append("GROUP BY TO_CHAR(TO_TIMESTAMP(date / 1000), 'mon')");

		AverageHolder result = new AverageHolder();
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			final List<Map<String, Object>> rows = jdbcTemplate.queryForList(
					query.toString(), new Object[] { userID });
			List<Double> avg = result.getAvg();

			for (Map<String, Object> row : rows) {
				String month = String.valueOf(row.get("month"));
				Double avgScore = Double.parseDouble(String.valueOf(row
						.get("avg_score")));

				if (month.equalsIgnoreCase("jan")) {
					avg.set(0, avgScore);
				} else if (month.equalsIgnoreCase("feb")) {
					avg.set(1, avgScore);
				} else if (month.equalsIgnoreCase("mar")) {
					avg.set(2, avgScore);
				} else if (month.equalsIgnoreCase("apr")) {
					avg.set(3, avgScore);
				} else if (month.equalsIgnoreCase("may")) {
					avg.set(4, avgScore);
				} else if (month.equalsIgnoreCase("jun")) {
					avg.set(5, avgScore);
				} else if (month.equalsIgnoreCase("jul")) {
					avg.set(6, avgScore);
				} else if (month.equalsIgnoreCase("aug")) {
					avg.set(7, avgScore);
				} else if (month.equalsIgnoreCase("sep")) {
					avg.set(8, avgScore);
				} else if (month.equalsIgnoreCase("oct")) {
					avg.set(9, avgScore);
				} else if (month.equalsIgnoreCase("nov")) {
					avg.set(10, avgScore);
				} else if (month.equalsIgnoreCase("dec")) {
					avg.set(11, avgScore);
				}
			}
		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get performance progression for candidate.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return result;
	}

	@Override
	public EffortsDevoted getEffortsDevoted(Integer userID) throws DAOException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) as count, c.title ");
		query.append("FROM trainingModuleCompletionStatus tms, trainingModuleCategories tmc, category c ");
		query.append("WHERE userID = ? AND ");
		query.append("tms.trainingModuleID = tmc.trainingModuleID AND ");
		query.append("tmc.categoryID = c.id AND ");
		query.append("tms.status = ? ");
		query.append("GROUP BY c.title");

		EffortsDevoted result = new EffortsDevoted();

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			final List<Map<String, Object>> rows = jdbcTemplate
					.queryForList(
							query.toString(),
							new Object[] {
									userID,
									TrainingModule.CompletionStatus.COMPLETE
											.getValue() });

			List<ContentHolder> contentHolderList = new ArrayList<>();

			for (Map<String, Object> row : rows) {
				String category = String.valueOf(row.get("title"));
				Integer count = ((Long) row.get("count")).intValue();
				contentHolderList.add(new ContentHolder(category, count));
			}

			result.getContent().addAll(contentHolderList);

		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get category-wise efforts for candidate.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return result;
	}

	@Override
	public DomainWisePerformance getDomainWisePerformance(Integer userID)
			throws DAOException {
		StringBuilder query2014 = new StringBuilder();
		query2014.append("SELECT avg(cws.score) as ave_score, c.title ");
		query2014
				.append("FROM categoryWiseScore cws, category c, testSetAttempt ta ");
		query2014.append("WHERE ta.userID = ? AND ");
		query2014.append("cws.categoryID = c.id AND ");
		query2014.append("cws.testSetAttemptID = ta.id AND ");
		query2014.append("TO_CHAR(TO_TIMESTAMP(ta.date / 1000), 'YY') = '14' ");
		query2014.append("GROUP BY c.title");

		StringBuilder query2015 = new StringBuilder();
		query2015.append("SELECT avg(cws.score) as ave_score, c.title ");
		query2015
				.append("FROM categoryWiseScore cws, category c, testSetAttempt ta ");
		query2015.append("WHERE ta.userID = ? AND ");
		query2015.append("cws.categoryID = c.id AND ");
		query2015.append("cws.testSetAttemptID = ta.id AND ");
		query2015.append("TO_CHAR(TO_TIMESTAMP(ta.date / 1000), 'YY') = '15' ");
		query2015.append("GROUP BY c.title");

		DomainWisePerformance result = new DomainWisePerformance();

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			final List<Map<String, Object>> rows = jdbcTemplate.queryForList(
					query2014.toString(), new Object[] { userID });

			List<PerformanceData> performanceDataList = new ArrayList<CandidateGraphData.PerformanceData>();

			Map<String, Double> map1 = new HashMap<String, Double>();
			for (Map<String, Object> row : rows) {
				String category = String.valueOf(row.get("title"));
				Double avgScore = (Double) row.get("ave_score");
				map1.put(category, avgScore);
			}

			// Execute query for 2015
			final List<Map<String, Object>> rows2 = jdbcTemplate.queryForList(
					query2015.toString(), new Object[] { userID });

			Map<String, Double> map2 = new HashMap<String, Double>();

			for (Map<String, Object> row : rows2) {
				String category = String.valueOf(row.get("title"));
				Double aveScore = (Double) row.get("ave_score");
				map2.put(category, aveScore);
			}

			Set<String> ssSet = new HashSet<String>();
			ssSet.addAll(map1.keySet());
			ssSet.addAll(map2.keySet());

			List<Double> data_1 = new ArrayList<Double>(ssSet.size());
			List<Double> data_2 = new ArrayList<Double>(ssSet.size());
			List<String> categories = new ArrayList<String>(ssSet.size());
			for (String c : ssSet) {
				categories.add(c);
				Double d = map1.get(c);
				if (d != null) {
					data_1.add(d);
				} else {
					data_1.add(null);
				}
				Double d2 = map2.get(c);
				if (d2 != null) {
					data_2.add(d2);
				} else {
					data_2.add(null);
				}
			}

			PerformanceData performanceData1 = new PerformanceData("2014");
			performanceData1.getData().addAll(data_1);
			PerformanceData performanceData2 = new PerformanceData("2015");
			performanceData2.getData().addAll(data_2);

			performanceDataList.add(performanceData1);
			performanceDataList.add(performanceData2);

			result.getCategories().addAll(categories);
			result.getPerformanceDataList().addAll(performanceDataList);

		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get category-wise efforts for candidate.");
			daoe.setStackTrace(e.getStackTrace());
			throw daoe;
		}

		return result;
	}

	@Override
	public JobCodeModule getJobcodeTrainingData(Integer userID) {
		StringBuilder query = new StringBuilder();

		// select j.positionname , count(j.userid) from jobcode j join
		// trainingmodule tm
		// on j.id=tm.jobcodeid
		// join trainingmodulecompletionstatus tms on tm.id =
		// tms.trainingmoduleid
		// group by j.positionname;
		query.append("select j.positionname as positionname , count(j.userid) as count from jobcode j join trainingmodule tm on j.id=tm.jobcodeid join trainingmodulecompletionstatus tms on tm.id = tms.trainingmoduleid group by j.positionname ");
		System.out.println(query);

		JobCodeModule result = new JobCodeModule();

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			// int total = jdbcTemplate.queryForInt(query.toString());
			// System.out.println("Total rows -->>"+total );

			final List<Map<String, Object>> rows = jdbcTemplate
					.queryForList(query.toString());

			List<String> jobcode = new ArrayList<>();
			List<Integer> count = new ArrayList<>();

			System.out.println("rows------>>" + rows.size());

			for (Map<String, Object> row : rows) {
				String jobcd = String.valueOf(row.get("positionname"));
				System.out.println(jobcd);
				jobcode.add(jobcd);
				System.out.println(row.get("count"));
				Long number = (Long) row.get("count");
				int x = number.intValue();
				Integer i = (int) x;
				System.out.println(i);
				count.add(i);
			}

			result.getJobcode().addAll(jobcode);
			result.getCount().addAll(count);

		} catch (Exception e) {
			DAOException daoe = new DAOException(
					"Failed to get training module details for candidate.");
			daoe.setStackTrace(e.getStackTrace());
			// throw daoe;
		}

		return result;

	}

}

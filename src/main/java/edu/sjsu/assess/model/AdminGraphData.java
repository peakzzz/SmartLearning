package edu.sjsu.assess.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdminGraphData implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class RegistrationCount implements Serializable {
		private static final long serialVersionUID = -4528779825008706153L;
		private final List<ContentHolder> entries = new ArrayList<>();
		private final String title;

		public String getTitle() {
			return title;
		}

		public RegistrationCount(String title) {
			this.title = title;
		}

		public List<ContentHolder> getEntries() {
			return entries;
		}
	}

	public static class ContentHolder implements Serializable {
		private static final long serialVersionUID = -8617661228694752405L;
		private final String category;
		private final Integer count;

		public String getCategory() {
			return category;
		}

		public Integer getCount() {
			return count;
		}

		public ContentHolder(String category, Integer count) {
			this.category = category;
			this.count = count;
		}
	}

	// JobCodeScore
	public static class JobCodeScore implements Serializable {
		private static final long serialVersionUID = 7424618724963464567L;
		private final List<String> jobcode = new ArrayList<>();
		private final List<Double> mean = new ArrayList<>();
		private final List<Double> median = new ArrayList<>();

		public List<String> getJobcode() {
			return jobcode;
		}

		public List<Double> getMean() {
			return mean;
		}

		public List<Double> getMedian() {
			return median;
		}

	}

	// JobCodeSAttempt
	public static class JobCodeAttempt implements Serializable {
		private static final long serialVersionUID = -3227892260538463350L;
		private final List<String> jobcode = new ArrayList<>();
		private final List<Integer> attempt = new ArrayList<>();

		public List<Integer> getAttempt() {
			return attempt;
		}

		public List<String> getJobcode() {
			return jobcode;
		}

	}

	public static class JobCodeDetails implements Serializable {
		private static final long serialVersionUID = 3771064393901743359L;
		private final List<String> category = new ArrayList<>();
		private final String title;
		private final Double mean;
		// Size of each inner list should be five, index 0 = min, index 1 =
		// lower quartile, index 2 = mean , index 3 = upper quartile , index 4 =
		// max
		private final List<List<Double>> data = new ArrayList<>();

		public JobCodeDetails(String title, Double mean) {
			this.title = title;
			this.mean = mean;
		}

		public List<String> getCategory() {
			return category;
		}

		public String getTitle() {
			return title;
		}

		public Double getMean() {
			return mean;
		}

		public List<List<Double>> getData() {
			return data;
		}

		final public static JobCodeDetails emptyInstance = new JobCodeDetails(
				"No Job Code", 0.0);

	}

	private JobCodeScore jobCodeScore;
	private JobCodeAttempt jobCodeAttempt;
	private JobCodeDetails topJobCode;
	private JobCodeDetails nextToTopJobCode;
	private RegistrationCount professionBasedRegistration;
	private RegistrationCount ageBasedRegisration;

	public JobCodeScore getJobCodeScore() {
		return jobCodeScore;
	}

	public void setJobCodeScore(JobCodeScore jobCodeScore) {
		this.jobCodeScore = jobCodeScore;
	}

	public RegistrationCount getProfessionBasedRegistration() {
		return professionBasedRegistration;
	}

	public void setProfessionBasedRegistration(
			RegistrationCount professionBasedRegistration) {
		this.professionBasedRegistration = professionBasedRegistration;
	}

	public RegistrationCount getAgeBasedRegisration() {
		return ageBasedRegisration;
	}

	public void setAgeBasedRegisration(RegistrationCount ageBasedRegisration) {
		this.ageBasedRegisration = ageBasedRegisration;
	}

	public JobCodeAttempt getJobCodeAttempt() {
		return jobCodeAttempt;
	}

	public void setJobCodeAttempt(JobCodeAttempt jobCodeAttempt) {
		this.jobCodeAttempt = jobCodeAttempt;
	}

	public JobCodeDetails getTopJobCode() {
		return topJobCode;
	}

	public void setTopJobCode(JobCodeDetails topJobCode) {
		this.topJobCode = topJobCode;
	}

	public JobCodeDetails getNextToTopJobCode() {
		return nextToTopJobCode;
	}

	public void setNextToTopJobCode(JobCodeDetails nextToTopJobCode) {
		this.nextToTopJobCode = nextToTopJobCode;
	}

}

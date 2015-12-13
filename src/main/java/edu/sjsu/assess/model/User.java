package edu.sjsu.assess.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.sjsu.assess.util.Utility;

public class User {

	private int id;

	private String login;

	private String password;

	private String fname;

	private String lname;

	private String role;

	private String birthDate;

	private long birthDateLong;

	private Set<Integer> jobPreferences = new HashSet<Integer>();

	private String profession;

	private String education;

	private String careerLevel;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	public java.lang.String getPassword() {
		return password;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public void setRole(java.lang.String role) {
		this.role = role;
	}

	public java.lang.String getRole() {
		return role;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public long getBirthDateLong() {
		return birthDateLong;
	}

	public void setBirthDateLong(long birthDateLong) {
		this.birthDateLong = birthDateLong;
	}

	public Set<Integer> getJobPreferences() {
		return jobPreferences;
	}

	public void setJobPreferences(Set<Integer> jobPreferences) {
		this.jobPreferences = jobPreferences;
	}

	public void addJobPreference(Integer jobCodeID) {
		this.jobPreferences.add(jobCodeID);
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getCareerLevel() {
		return careerLevel;
	}

	public void setCareerLevel(String careerLevel) {
		this.careerLevel = careerLevel;
	}

	public boolean isAdmin() {
		if (role.equalsIgnoreCase("admin")) {
			return true;
		}

		return false;
	}

	/**
	 * Default Constructor
	 */
	public User() {

	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param login
	 * @param password
	 * @param fname
	 * @param lname
	 * @param role
	 */
	public User(int id, String login, String password, String fname,
			String lname, String role, long birthDateLong) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.role = role;
		this.birthDateLong = birthDateLong;
		this.birthDate = Utility.getDateStrFromLong(birthDateLong);
	}

	/**
	 * Enum for Profession Drop Down
	 * 
	 * @author shefalid
	 *
	 */
	public static enum Profession {

		STUDENT("Student"), PROFESSOR("Professor"), SOFTWARE_ENGINEER(
				"Software Engineer"), QA_ENGINEER("Software Quality Engineer"), PRODUCT_MANAGER(
				"Product Manager"), SELF_EMPLOYED("Self Employed"), UNEMPLOYED(
				"Unemployed");

		private Profession(String description) {
			this.description = description;
		}

		private final String description;

		public String getDescription() {
			return description;
		}

		@Override
		public String toString() {
			return description;
		}

		public static Profession getProfession(String desc) {
			for (Profession a : Profession.values()) {
				if (a.toString().equals(desc)) {
					return a;
				}
			}
			return null;
		}

	}

	/**
	 * Enum for Profession Drop Down
	 * 
	 * @author shefalid
	 *
	 */
	public static enum Education {

		HIGH_SCHOOL("High School"), VOCATIONAL("Vocational Training"), ASSOCIATE_DEGREE(
				"Associate Degree"), BACHELORS_DEGREE("Bachelor's Degree"), MASTERS_DEGREE(
				"Master's Degree"), PHD("PhD");

		private Education(String description) {
			this.description = description;
		}

		private final String description;

		public String getDescription() {
			return description;
		}

		@Override
		public String toString() {
			return description;
		}

		public static Education getEducation(String desc) {
			for (Education edu : Education.values()) {
				if (edu.toString().equals(desc)) {
					return edu;
				}
			}
			return null;
		}

	}

	/**
	 * Enum for Career Level Drop Down
	 * 
	 * @author shefalid
	 *
	 */
	public static enum CareerLevel {

		STUDENT_HIGHSCHOOL("Student(High School)"), STUDENT_COLLEGE(
				"Student(Undergraduate/Graduate)"), ENTRY_LEVEL(
				"Entry Level Professional"), EXPERIENCED(
				"Experienced Professional"), MANAGER("Manager, Supervisor"), EXECUTIVE(
				"Executive(SVP, VP, Department Head)"), SENIOR_EXECUTIVE(
				"Senior Executive(President, CEO etc.)");

		private CareerLevel(String description) {
			this.description = description;
		}

		private final String description;

		public String getDescription() {
			return description;
		}

		@Override
		public String toString() {
			return description;
		}

		public static CareerLevel getCareerLevel(String desc) {
			for (CareerLevel cl : CareerLevel.values()) {
				if (cl.toString().equals(desc)) {
					return cl;
				}
			}
			return null;
		}

	}

	public static class JobCodeSelected {
		private JobCode jobCode;
		private boolean selected;

		public JobCodeSelected(JobCode jobCode, boolean selected) {
			this.jobCode = jobCode;
			this.selected = selected;
		}

		public JobCodeSelected() {
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		public JobCode getJobCode() {
			return jobCode;
		}

		public void setJobCode(JobCode jobCode) {
			this.jobCode = jobCode;
		}
	}

	public static class UserPreferenceHolder {
		private User user;
		private List<JobCodeSelected> jcList = new ArrayList<JobCodeSelected>();

		public User getUser() {
			return user;
		}

		public Profession[] getProfessions() {
			return Profession.values();
		}

		public Education[] getEducations() {
			return Education.values();
		}

		public CareerLevel[] getCareerLevels() {
			return CareerLevel.values();
		}

		public void setUser(User user) {
			this.user = user;
		}

		public List<JobCodeSelected> getJcList() {
			return jcList;
		}

		public UserPreferenceHolder(User user, List<JobCodeSelected> jcList) {
			assert (user != null);
			this.user = user;
			if (jcList != null) {
				this.jcList.addAll(jcList);
			}
		}

		public UserPreferenceHolder() {
		}
	}

}
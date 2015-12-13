package edu.sjsu.assess.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Model Class for Test Set Attempt
 * @author Shefali
 *
 */
public class TestSetAttempt {

	private Integer id;
	
	private Integer userID;
	
	private Integer testSetID;
	
	private TestSet testSetObj;
	
	private Date attemptDate;
	
	private Double score;

	private Double progress;
	
	private List<CategoryWiseRecord> categoryWiseRecords;
	
	//private Map<Integer, List<QuestionWiseRecord>> questionsRecords;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getTestSetID() {
		return testSetID;
	}

	public void setTestSetID(Integer testSetID) {
		this.testSetID = testSetID;
	}

	public TestSet getTestSetObj() {
		return testSetObj;
	}

	public void setTestSetObj(TestSet testSetObj) {
		this.testSetObj = testSetObj;
	}

	public Date getAttemptDate() {
		return attemptDate;
	}

	public void setAttemptDate(Date attemptDate) {
		this.attemptDate = attemptDate;
	}

	
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getProgress()
	{
		return progress;
	}

	public void setProgress(Double progress)
	{
		this.progress = progress;
	}

	public List<CategoryWiseRecord> getCategoryWiseRecords() {
		return categoryWiseRecords;
	}

	public void setCategoryWiseRecords(List<CategoryWiseRecord> categoryWiseRecords) {
		this.categoryWiseRecords = categoryWiseRecords;
	}

	public void addCategoryWiseRecord(CategoryWiseRecord categoryWiseRecord){
		if(this.categoryWiseRecords == null){
			this.categoryWiseRecords = new ArrayList<TestSetAttempt.CategoryWiseRecord>();
		}
		
		this.categoryWiseRecords.add(categoryWiseRecord);
	}
	
//	public Map<Integer, List<QuestionWiseRecord>> getQuestionsRecords() {
//		return questionsRecords;
//	}
//
//	public void setQuestionsRecords(
//			Map<Integer, List<QuestionWiseRecord>> questionsRecords) {
//		this.questionsRecords = questionsRecords;
//	}
//
//	public List<QuestionWiseRecord> getQuestionsRecordForCategory(Integer categoryID){
//		if(this.questionsRecords != null){
//			return this.questionsRecords.get(categoryID);
//		}
//		
//		return null;
//	}
//	
//	public void addQuestionsRecord(Integer categoryID, List<QuestionWiseRecord> questionsRecord){
//		if(this.questionsRecords == null){
//			this.questionsRecords = new HashMap<Integer, List<QuestionWiseRecord>>();
//		}
//		
//		this.questionsRecords.put(categoryID, questionsRecord);
//	}
	
	
	/**
	 * Inner class containing category-wise attempt record
	 * @author Shefali
	 *
	 */
	public class CategoryWiseRecord{
		
		private Integer id;
		
		private Integer testSetAttemptID;
		
		private Integer categoryID;
		
		private Category category;
		
		private Double score;
		
		private boolean isCutoffReached;
		
		private List<QuestionWiseRecord> questionsRecord = new ArrayList<TestSetAttempt.QuestionWiseRecord>();

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getTestSetAttemptID() {
			return testSetAttemptID;
		}

		public void setTestSetAttemptID(Integer testSetAttemptID) {
			this.testSetAttemptID = testSetAttemptID;
		}

		public Integer getCategoryID() {
			return categoryID;
		}

		public void setCategoryID(Integer categoryID) {
			this.categoryID = categoryID;
		}

		public Category getCategory() {
			return category;
		}

		public void setCategory(Category category) {
			this.category = category;
		}

		public Double getScore() {
			return score;
		}

		public void setScore(Double score) {
			this.score = score;
		}

		public boolean isCutoffReached() {
			return isCutoffReached;
		}

		public void setIsCutoffReached(boolean isCutoffReached) {
			this.isCutoffReached = isCutoffReached;
		}

		public List<QuestionWiseRecord> getQuestionsRecord() {
			return questionsRecord;
		}

		public void setQuestionsRecord(List<QuestionWiseRecord> questionsRecord) {
			this.questionsRecord = questionsRecord;
		}
		
		public void addQuestionWiseRecord(QuestionWiseRecord questionWiseRecord){
			this.questionsRecord.add(questionWiseRecord);
		}
	}
	
	
	
	/**
	 * Inner class containing question-wise attempt record
	 * @author Shefali
	 *
	 */
	public class QuestionWiseRecord{
		
		private Integer questionID;
		
		private Question questionObj;
		
		private Integer categoryID;
		
		private List<Integer> userAnswers;
		
		private boolean isCorrectAnswer;

		public Integer getQuestionID() {
			return questionID;
		}

		public void setQuestionID(Integer questionID) {
			this.questionID = questionID;
		}

		public Question getQuestionObj() {
			return questionObj;
		}

		public void setQuestionObj(Question questionObj) {
			this.questionObj = questionObj;
		}

		public Integer getCategoryID() {
			return categoryID;
		}

		public void setCategoryID(Integer categoryID) {
			this.categoryID = categoryID;
		}

		public List<Integer> getUserAnswers() {
			return userAnswers;
		}

		public void setUserAnswers(List<Integer> userAnswers) {
			this.userAnswers = userAnswers;
		}

		public boolean isCorrectAnswer() {
			return isCorrectAnswer;
		}

		public void setIsCorrectAnswer(boolean isCorrectAnswer) {
			this.isCorrectAnswer = isCorrectAnswer;
		}
		
	}

	public TestSetAttempt getDummyTestSetAttempt(TestSet testset) {
		TestSetAttempt testSetAttempt = new TestSetAttempt();
		testSetAttempt.setId(1);
		testSetAttempt.setScore(100.0);
		testSetAttempt.setAttemptDate(new Date());
		testSetAttempt.setUserID(4);
		testSetAttempt.setTestSetID(testset.getId());

		List<CategoryWiseRecord> categoryRecordList = new ArrayList<>();
		for(TestSetCategory setCategory: testset.getTestSetCategories()) {
			CategoryWiseRecord categoryRecord = new CategoryWiseRecord();
			categoryRecord.setTestSetAttemptID(1);
			categoryRecord.setCategory(setCategory.getCategory());
			categoryRecord.setCategoryID(setCategory.getCategoryID());
			categoryRecord.setScore(50.0);
			categoryRecord.setIsCutoffReached(true);
			categoryRecordList.add(categoryRecord);

			List<QuestionWiseRecord> questionList = new ArrayList<>();
			for(Question question: setCategory.getQuestionList()) {
				QuestionWiseRecord questionWiseRecord = new QuestionWiseRecord();
				questionWiseRecord.setQuestionID(question.getId());
				questionWiseRecord.setCategoryID(question.getCategoryID());
				questionWiseRecord.setQuestionObj(question);
				questionList.add(questionWiseRecord);
			}
			categoryRecord.setQuestionsRecord(questionList);
		}
		testSetAttempt.setCategoryWiseRecords(categoryRecordList);
		return testSetAttempt;
	}

	public TestSetAttempt getDummyTestSetAttempt(List<Question> questionList) {
		TestSetAttempt testset = new TestSetAttempt();
		testset.setId(1);
		testset.setScore(100.0);
		testset.setAttemptDate(new Date());
		testset.setTestSetID(1);
		testset.setUserID(4);

		Category category = new Category();
		category.setId(1);
		category.setTitle("BB testing");

		List<CategoryWiseRecord> categoryRecordList = new ArrayList<>();
		CategoryWiseRecord categoryRecord = new CategoryWiseRecord();
		categoryRecord.setTestSetAttemptID(1);
		categoryRecord.setCategory(category);
		categoryRecord.setCategoryID(1);
		categoryRecord.setScore(50.0);
		categoryRecord.setIsCutoffReached(true);
		categoryRecordList.add(categoryRecord);

		for(Question question : questionList) {
			QuestionWiseRecord questionWiseRecord = new QuestionWiseRecord();
			questionWiseRecord.setQuestionID(question.getId());
			questionWiseRecord.setCategoryID(question.getCategoryID());
			questionWiseRecord.setQuestionObj(question);
			if(question.getOptions()!=null) {
				for (Option option : question.getOptions()) {
					option.setSelectedByUser(option.isCorrectOption());
				}
			}
		}
		return testset;
	}
}

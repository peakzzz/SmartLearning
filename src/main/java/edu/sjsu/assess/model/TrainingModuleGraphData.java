package edu.sjsu.assess.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrainingModuleGraphData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private JobCodeModule jobCodeModule;
	public JobCodeModule getJobCodeModule() {
		return jobCodeModule;
	}
	public void setJobCodeModule(JobCodeModule jobCodeModule) {
		this.jobCodeModule = jobCodeModule;
	}
	
	
	
	public static class JobCodeModule implements Serializable {
		private static final long serialVersionUID = 7424618724963464567L;
		private List<String> jobcode = new ArrayList<>();
		private List<Integer> count = new ArrayList<>();
		public List<String> getJobcode() {
			return jobcode;
		}
		public void setJobcode(List<String> jobcode) {
			this.jobcode = jobcode;
		}
		public List<Integer> getCount() {
			return count;
		}
		public void setCount(List<Integer> count) {
			this.count = count;
		}

	}
}

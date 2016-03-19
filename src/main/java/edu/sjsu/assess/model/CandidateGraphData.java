package edu.sjsu.assess.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.sjsu.assess.model.AdminGraphData.ContentHolder;

public class CandidateGraphData implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class AverageHolder implements Serializable {
		private static final long serialVersionUID = -222008179838265337L;
		private final List<Double> avg = new ArrayList<Double>(12){
			{
				add(0.0);
				add(0.0);
				add(0.0);
				add(0.0);
				add(0.0);
				add(0.0);
				add(0.0);
				add(0.0);
				add(0.0);
				add(0.0);
				add(0.0);
				add(0.0);
			}
			
		};

		public List<Double> getAvg() {
			return avg;
		}
		
	}

	public static class EffortsDevoted implements Serializable {
		private static final long serialVersionUID = -1444099177708433939L;
		private final List<ContentHolder> content = new ArrayList<>();

		public List<ContentHolder> getContent() {
			return content;
		}
	}

	public static class DomainWisePerformance implements Serializable {

		private static final long serialVersionUID = 9020276703024770422L;
		private final List<String> categories = new ArrayList<String>();
		private final List<PerformanceData> performanceDataList = new ArrayList<PerformanceData>();

		public List<String> getCategories() {
			return categories;
		}

		public List<PerformanceData> getPerformanceDataList() {
			return performanceDataList;
		}
	}
	
	public static class FocusWisePerformance implements Serializable {

		private static final long serialVersionUID = 2049971659305525651L;
		private final List<String> focuses = new ArrayList<String>();
		private final List<Double> score = new ArrayList<Double>();
		
		public List<String> getFocuses() {
			return focuses;
		}
		public List<Double> getScore() {
			return score;
		}		
	}
	
	public static class PerformanceData implements Serializable {

		private static final long serialVersionUID = -8809170917323089953L;
		private final String name;
		private final List<Double> data = new ArrayList<Double>();
		private final String pointPlacement = "on";

		public String getName() {
			return name;
		}

		public List<Double> getData() {
			return data;
		}

		public String getPointPlacement() {
			return pointPlacement;
		}

		public PerformanceData(String name) {
			this.name = name;
		}
	}

	private AverageHolder performanceProgression;
	private AverageHolder frequencyAttempt;
	private EffortsDevoted effortsDevoted;
	private DomainWisePerformance domainWisePerformance;
	private FocusWisePerformance focusWisePerformance;
	
	public FocusWisePerformance getFocusWisePerformance() {
		return focusWisePerformance;
	}

	public void setFocusWisePerformance(FocusWisePerformance focusWisePerformance) {
		this.focusWisePerformance = focusWisePerformance;
	}

	public EffortsDevoted getEffortsDevoted() {
		return effortsDevoted;
	}

	public void setEffortsDevoted(EffortsDevoted effortsDevoted) {
		this.effortsDevoted = effortsDevoted;
	}

	public AverageHolder getPerformanceProgression() {
		return performanceProgression;
	}

	public void setPerformanceProgression(AverageHolder performanceProgression) {
		this.performanceProgression = performanceProgression;
	}

	public AverageHolder getFrequencyAttempt() {
		return frequencyAttempt;
	}

	public void setFrequencyAttempt(AverageHolder frequencyAttempt) {
		this.frequencyAttempt = frequencyAttempt;
	}

	public DomainWisePerformance getDomainWisePerformance() {
		return domainWisePerformance;
	}

	public void setDomainWisePerformance(
			DomainWisePerformance domainWisePerformance) {
		this.domainWisePerformance = domainWisePerformance;
	}

}

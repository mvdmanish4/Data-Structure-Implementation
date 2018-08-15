package cmu.proj.pojo;


public class Result {
	
	private Input val;
	private int totalTime;
	private int overlapTime;
	private int remainingTime;
	
	public Result(Input val, int totalTime, int overlapTime, int remainingTime) {
		super();
		this.val = val;
		this.totalTime = totalTime;
		this.overlapTime = overlapTime;
		this.remainingTime = remainingTime;
	}

	public Input getVal() {
		return val;
	}

	public void setVal(Input val) {
		this.val = val;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public int getOverlapTime() {
		return overlapTime;
	}

	public void setOverlapTime(int overlapTime) {
		this.overlapTime = overlapTime;
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}

	@Override
	public String toString() {
		return "Result [val=" + val + ", totalTime=" + totalTime + ", overlapTime=" + overlapTime + ", remainingTime="
				+ remainingTime + "]";
	}	

}

package javareading;

public class ProceedTime {
	
	private int id;
	private int value;
	private TimeUnit unit;
	
	
	public ProceedTime(int id, int value, TimeUnit unit) {
		this.id = id;
		this.value = value;
		this.unit = unit;
	}


	public int getId() {
		return id;
	}


	public int getValue() {
		return value;
	}

	public TimeUnit getUnit() {
		return unit;
	}
	
	

}

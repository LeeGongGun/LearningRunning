package bean;

public class Curriculum {
	int cur_id;
	String cur_name;
	
	public Curriculum() {
		super();
	}
	public Curriculum(int cur_id, String cur_name) {
		super();
		this.cur_id = cur_id;
		this.cur_name = cur_name;
	}
	public int getCur_id() {
		return cur_id;
	}
	public void setCur_id(int cur_id) {
		this.cur_id = cur_id;
	}
	public String getCur_name() {
		return cur_name;
	}
	public void setCur_name(String cur_name) {
		this.cur_name = cur_name;
	}
	
}

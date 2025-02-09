package lt.ca.javau11.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NytResponse<T> {
    private String status;
    private String copyright;
    private int num_results;
    private List<T> results;
    
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public int getNum_results() {
		return num_results;
	}
	public void setNum_results(int num_results) {
		this.num_results = num_results;
	}
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
    
}
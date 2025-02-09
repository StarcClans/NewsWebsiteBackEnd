package lt.ca.javau11.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Section {
    private String section;
    private String display_name;
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
    
}

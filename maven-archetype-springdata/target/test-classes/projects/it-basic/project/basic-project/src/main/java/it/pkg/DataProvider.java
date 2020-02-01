package it.pkg;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class DataProvider {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int license_id;
	String dataProviderName ;
	public int getLicense_id() {
		return license_id;
	}
	public void setLicense_id(int license_id) {
		this.license_id = license_id;
	}
	public String getDataProviderName() {
		return dataProviderName;
	}
	public void setDataProviderName(String dataProviderName) {
		this.dataProviderName = dataProviderName;
	}

}

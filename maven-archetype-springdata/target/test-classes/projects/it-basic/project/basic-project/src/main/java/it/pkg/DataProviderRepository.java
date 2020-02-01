package it.pkg;

import org.springframework.data.repository.CrudRepository;



public interface DataProviderRepository<T> extends CrudRepository<T, String> {
	
	public DataProvider findByDataProviderName ( String name );

}

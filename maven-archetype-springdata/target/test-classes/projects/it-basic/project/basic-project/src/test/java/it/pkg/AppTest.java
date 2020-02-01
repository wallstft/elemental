package it.pkg;

import org.junit.jupiter.api.DisplayName;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
	/*
	@Autowired
	DataProviderRepository dp_repository;
	*/


	
	@DisplayName("Test DataProvider")
	@Test
	public void test_DataProvider()
	{
		/*
		 * Need to plug in the imports for the DataProvider and DataRepository
		 * 
		DataProvider dp = dp_repository.findByDataProviderName("Bloomberg"); 
				
		if ( dp == null ) {		
			dp = new DataProvider();
			dp.setDataProviderName("Bloomberg");
			dp_repository.save(dp);
		}
		
		System.out.println (String.format ( "Data Provider %s %d", dp.getDataProviderName(), dp.getLicense_id()));		
		*/
	}
}

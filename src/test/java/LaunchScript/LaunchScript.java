package LaunchScript;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.annotations.Test;

import com.sun.utils.PropertiesFile;
import com.sun.utils.SunAutoTest;


public class LaunchScript extends SunAutoTest {
		
	@Test
	public void Launch(){
		try {		
			PropertiesFile properties = new PropertiesFile();
			properties.properties();
			//startup();			
			TestNG testng = new TestNG();
			List<String> suites = new ArrayList<String>();
			suites.add("./testng.xml");			
			testng.setTestSuites(suites);			
			//GenericKeywords.extent = new ExtentReports(GenericKeywords.outputDirectory+"/Results.html", true);
			testng.run();		

		} catch (Exception e) {
			//writeToLogFile("error", e.toString());
		} 

		}

}

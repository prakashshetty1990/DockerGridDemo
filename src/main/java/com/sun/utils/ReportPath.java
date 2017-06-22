package com.sun.utils;

import java.io.File;

public class ReportPath {
	
	public static String fullReportPath = null;
	private static ReportPath instance = null;
	private String reportPath;	

	

	/**
	 * Get the current report folder
	 * @return
	 */
	public String getReportPath(String browserName){
		reportPath = TestUtils.getRelativePath() + File.separator
				+ FrameworkConstants.RESULT_FOLDER + File.separator + "Result_"
				+ TestUtils.getTimeStamp();
		reportPath = reportPath + File.separator + browserName;
		boolean success = (new File(reportPath)).mkdirs();
		this.fullReportPath = reportPath;
		return reportPath;
	}
	
	
}

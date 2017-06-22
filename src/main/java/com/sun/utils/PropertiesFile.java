package com.sun.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import jxl.Sheet;
import jxl.Workbook;

import org.w3c.dom.Document;
import org.w3c.dom.Element;





public class PropertiesFile {
	public File nf;
	public Workbook w;
	public PropertiesFile() {}

	public final ArrayList<String> testCases = new ArrayList();

	public void properties() {
		Properties prop = new Properties();
		int noBrowser = 0;
		List machineName = new ArrayList(noBrowser);
		List host = new ArrayList(noBrowser);
		List port = new ArrayList(noBrowser);
		List browser = new ArrayList(noBrowser);
		List os = new ArrayList(noBrowser);
		List browserVersion = new ArrayList(noBrowser);
		List osVersion = new ArrayList(noBrowser);



		try
		{
			nf = new File("./src/test/resources/TestConfiguration.xls");
			if (nf.exists()) {
				try {
					w = Workbook.getWorkbook(nf);
				}          
				catch (IOException e) {
					e.printStackTrace();
				}

			}         
			Sheet sheet1 = w.getSheet(1);

			for (int index = 1; index < sheet1.getRows(); index++)
			{
				if (sheet1.getCell(8, index).getContents().equalsIgnoreCase("yes"))
				{
					machineName.add(sheet1.getCell(1, index).getContents());
					host.add(sheet1.getCell(2, index).getContents());
					port.add(sheet1.getCell(3, index).getContents());
					os.add(sheet1.getCell(4, index).getContents());
					browser.add(sheet1.getCell(6, index).getContents());
					browserVersion.add(sheet1.getCell(7, index).getContents());
					osVersion.add(sheet1.getCell(5, index).getContents());
				}
			}

			noBrowser = host.size();
			w.close();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element rootElement = doc.createElement("suite");
			doc.appendChild(rootElement);
			rootElement.setAttribute("name", "Suite");
			rootElement.setAttribute("parallel", "tests");



			/*
      Element listeners = doc.createElement("listeners");
      rootElement.appendChild(listeners);
      Element listener = doc.createElement("listener");
      listener.setAttribute("class-name", "Utilities.Reporters");
      listeners.appendChild(listener);
			 */

			int id = 1;
			for (int brow = 1; brow <= noBrowser; brow++)
			{
				String idS = String.valueOf(id++);
				Element test = doc.createElement("test");
				test.setAttribute("name", machineName.get(brow - 1).toString());
				test.setAttribute("id", idS);

				Element classes = doc.createElement("classes");
				classes.setAttribute("name", idS);

				Element classs = doc.createElement("class");
				classs.setAttribute("name", "TestCases.TestCases");
				classs.setAttribute("id", idS);

				Element methods = doc.createElement("methods");
				methods.setAttribute("id", idS);

				Element parameter0 = doc.createElement("parameter");
				parameter0.setAttribute("name", "selenium.machinename");
				parameter0.setAttribute("value", machineName.get(brow - 1).toString());

				Element parameter1 = doc.createElement("parameter");
				parameter1.setAttribute("name", "selenium.host");
				parameter1.setAttribute("value", host.get(brow - 1).toString());


				Element parameter2 = doc.createElement("parameter");
				parameter2.setAttribute("name", "selenium.port");
				parameter2.setAttribute("value", port.get(brow - 1).toString());
				Element parameter3 = doc.createElement("parameter");
				parameter3.setAttribute("name", "selenium.browser");
				parameter3.setAttribute("value", browser.get(brow - 1).toString());
				Element parameter4 = doc.createElement("parameter");
				parameter4.setAttribute("name", "selenium.url");
				parameter4.setAttribute("value", "Change Url later");
				parameter4.setAttribute("id", idS);

				Element parameter5 = doc.createElement("parameter");
				parameter5.setAttribute("name", "selenium.os");
				parameter5.setAttribute("value", os.get(brow - 1).toString());
				parameter5.setAttribute("id", idS);

				Element parameter6 = doc.createElement("parameter");
				parameter6.setAttribute("name", "selenium.browserVersion");
				parameter6.setAttribute("value", browserVersion.get(brow - 1).toString());
				parameter6.setAttribute("id", idS);

				Element parameter7 = doc.createElement("parameter");
				parameter7.setAttribute("name", "selenium.osVersion");
				parameter7.setAttribute("value", osVersion.get(brow - 1).toString());
				parameter7.setAttribute("id", idS);

				rootElement.appendChild(test);
				test.appendChild(parameter0);
				test.appendChild(parameter1);
				test.appendChild(parameter2);
				test.appendChild(parameter3);
				test.appendChild(parameter4);
				test.appendChild(parameter5);
				test.appendChild(parameter6);
				test.appendChild(parameter7);
				test.appendChild(classes);
				classes.appendChild(classs);
				classes.appendChild(methods);
				nf = new File("./src/test/resources/TestConfiguration.xls");
				if (nf.exists()) {
					try {
						w = Workbook.getWorkbook(nf);
					}          
					catch (IOException e) {
						e.printStackTrace();
					}

				}           
				Sheet readsheet = w.getSheet(0);

				for (int i = 1; i < readsheet.getRows(); i++) {
					String Keyword = readsheet.getCell(1, i).getContents();
					String value = readsheet.getCell(2, i).getContents();
					prop.setProperty(Keyword, value);
				}


				prop.store(new FileOutputStream(
						"./src/test/resources/framework.properties"), null);
				w.close();

				File  nf = new File("./src/test/resources/TestCaseSettings.xls");
				if (nf.exists()) {
					try {
						w = Workbook.getWorkbook(nf);
					}          
					catch (IOException e) {
						e.printStackTrace();
					}

				}   

				Sheet readsheet1 = w.getSheet(0);

				for (int i = 1; i < readsheet1.getRows(); i++)
				{

					String value = readsheet1.getCell(4, i).getContents();
					Keyword = readsheet1.getCell(2, i).getContents();
					if (value.trim().equalsIgnoreCase("Yes"))
					{

						Element include = doc.createElement("include");
						methods.appendChild(include);

						testCases.add(Keyword);
						include.setAttribute("name", Keyword);


					}
					else if (value.trim().equalsIgnoreCase("No"))
					{
						Element exclude = doc.createElement("exclude");
						methods.appendChild(exclude);

						exclude.setAttribute("name", Keyword);



					}
					else if (!value.trim().equalsIgnoreCase(""))
					{
						System.out.println("Warnin!!Invalid/Empty Execution flag");
					}
				}
			}


			TransformerFactory tff = TransformerFactory.newInstance();
			Transformer transformer = tff.newTransformer();
			transformer.setOutputProperty("indent", "yes");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource xmlSource = new DOMSource(doc);
			StreamResult outputTarget = new StreamResult(
					".\\testng.xml");
			transformer.transform(xmlSource, outputTarget);
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String Keyword;
}

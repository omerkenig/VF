package vfro.regression;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class UpdateGrafana {

	static HashMap<String, String> envParams = new HashMap<>(); 
	
	public static void main(String []args) throws FilloException{
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rsst=null;
		String testCaseNames=args[3];
		String release=args[0];
		String application=null;
		String package_name=null;
		String phase=args[1];
		String execution_time=null;
		String new_status=args[2];
		String browsers[]=null;
		List<String> testCase=new ArrayList<String>();
		String tempPhaseName=null;
		String htmlCode=null;
		File file=null;
		FileWriter fw=null;
		int count=0;
		boolean testCaseFound=false;
				
		Fillo environmentSheet=new Fillo();
		com.codoid.products.fillo.Connection environmentConnection=environmentSheet.getConnection(System.getProperty("user.dir")+"/environments/Environments.xlsx");

		Recordset rs = environmentConnection.executeQuery("select * from COMMON_PARAMETERS");

		while(rs.next())
			envParams.put(rs.getField("PARAMETER"), rs.getField("VALUE"));
				
		environmentConnection.close();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		} 
    	
		try {
			
			htmlCode="<table border='1' cellpadding='10' cellspacing='4' width='100%'>"
					+ "<tbody>"
					+"<tr >"
                    +"      <td style='width: 100px;' align=CENTER><strong>Application Name</strong></td>"
                    +"      <td style='width: 100px;' align=CENTER><strong>Package Name</strong></td>"
                    +"      <td style='width: 100px;' align=CENTER><strong>Phase Name</strong></td>"
                    +"      <td style='width: 100px;' align=CENTER><strong>Test Case Name</strong></td>"
                    +"    </tr>";
            						
			conn = DriverManager.getConnection(envParams.get("GRAFANA_DB_HOST"),envParams.get("GRAFANA_DB_USER"),envParams.get("GRAFANA_DB_PWD"));
			System.out.println("Connected to grafana");
			
			try {
				stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			for(int i=0;i<testCaseNames.split(";").length;i++)
				testCase.add(testCaseNames.split(";")[i].trim());
			
			for(int j=0;j<testCase.size();j++){
				release=null;
				application=null;
				package_name=null;
				execution_time=null;
				count=0;
				testCaseFound=false;
				
				try {
					try{
						if(phase.length()==0)
							throw new NullPointerException();
						
						if(j!=0){
							try{
								if(tempPhaseName.length()!=0)
									phase=tempPhaseName;
							}catch(NullPointerException ne){
								
							}
						}						
						
						if(phase.trim().toUpperCase().equals("CIT")==false && phase.trim().toUpperCase().equals("UAT")==false){
							System.out.println("\nQuery to fetch test case details: select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and package_name like ('%_"+phase+"') and execution_status<>'Passed' order by execution_date desc limit 1");
							rsst=stmt.executeQuery("select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and package_name like ('%_"+phase+"') and execution_status<>'Passed' order by execution_date desc limit 1");
							rsst.next();
							tempPhaseName=phase;
							try{
							phase=rsst.getString("phase_name");
							}catch(SQLException sqe){
								if(sqe.getLocalizedMessage().contains("Illegal operation on empty result set")){
									System.out.println("Test case: '"+testCase.get(j)+"' not found. Querying for actual test case name");
									System.out.println("Querying for all test cases present in grafana and looking for a match: select distinct(test_case_name) as test from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where package_name not like ('%_Sanity%')");
									rsst=stmt.executeQuery("select distinct(test_case_name) as test from grafanadb."+envParams.get("GRAFANA_TABLE_NAME"));
									fetchCorrectTestName:
									while(rsst.next()){
										if(rsst.getString("test").replaceAll(" ", "").replaceAll("\\r|\\n", "").equals(testCase.get(j).replaceAll(" ", "").replaceAll("'", "").replaceAll("\"", "").replaceAll("\\r|\\n", ""))){
											System.out.println("Actual test case name: "+rsst.getString("test"));
											testCase.set(j, rsst.getString("test"));
											try{
							    				System.out.println("Query to fetch test case details using correct test case name: select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and execution_status<>'Passed' order by execution_date desc limit 1");
							    				rsst=stmt.executeQuery("select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and package_name like ('%_"+phase+"') and execution_status<>'Passed' order by execution_date desc limit 1");
												rsst.next();
												phase=rsst.getString("phase_name");
							    				testCaseFound=true;
							    				break fetchCorrectTestName;
							    				}catch(SQLException sq){
							    					if(sqe.getLocalizedMessage().contains("Illegal operation on empty result set")){
							    						System.out.println("Correct test case name not found for test: "+testCase.get(j)+". Continuing search");
//							    						continue fetchCorrectTestName;
							    					}
							    				}
				    						rsst=stmt.executeQuery("select distinct(test_case_name) as test from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where package_name not like ('%_Sanity')");
				    						for(int i=0;i<count;i++)
				    							rsst.next();
										}
									}
									
									if(testCaseFound==false){
					    				System.out.println("Unable to find test case: "+testCase.get(j)+". Hence ignoring this test.");
					    				continue;
					    			}
								 }
								System.out.println("Actual phase for test case: "+testCase.get(j)+" is "+phase);
								}
						}else{
				    		System.out.println("\nQuery to fetch test case details: select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and execution_status<>'Passed' and package_name not like ('%_Sanity') order by execution_date desc limit 1");
				    		rsst=stmt.executeQuery("select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and execution_status<>'Passed' and package_name not like ('%_Sanity') order by execution_date desc limit 1");
				    		rsst.next();
				    		try{
				    			phase=rsst.getString("phase_name");
				    		}catch(SQLException sqe){
				    			if(sqe.getLocalizedMessage().contains("Illegal operation on empty result set")){
				    				System.out.println("Test case: '"+testCase.get(j)+"' not found. Querying for actual test case name");
				    				System.out.println("Querying for all test cases present in grafana and looking for a match: select distinct(test_case_name) as test from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where package_name not like ('%_Sanity')");
				    				rsst=stmt.executeQuery("select distinct(test_case_name) as test from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where package_name not like ('%_Sanity')");
				    				fetchCorrectTestName:
				    				while(rsst.next()){
				    					count++;
				    					if(rsst.getString("test").replaceAll(" ", "").replaceAll("\\r|\\n", "").equals(testCase.get(j).replaceAll(" ", "").replaceAll("'", "").replaceAll("\"", "").replaceAll("\\r|\\n", ""))){
				    						System.out.println("Actual test case name: "+rsst.getString("test"));
				    						testCase.set(j, rsst.getString("test"));
				    						try{
							    				System.out.println("Query to fetch test case details using correct test case name: select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and execution_status<>'Passed' order by execution_date desc limit 1");
							    				rsst=stmt.executeQuery("select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and execution_status<>'Passed' order by execution_date desc limit 1");
							    				rsst.next();
							    				phase=rsst.getString("phase_name");
							    				testCaseFound=true;
							    				break fetchCorrectTestName;
							    				}catch(SQLException sq){
							    					if(sqe.getLocalizedMessage().contains("Illegal operation on empty result set")){
							    						System.out.println("Correct test case name not found for test: "+testCase.get(j)+". Continuing search");
//							    						continue fetchCorrectTestName;
							    					}
							    				}
				    						rsst=stmt.executeQuery("select distinct(test_case_name) as test from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where package_name not like ('%_Sanity')");
				    						for(int i=0;i<count;i++)
				    							rsst.next();
				    					}
				    				}
				    			}
				    			
				    			if(testCaseFound==false){
				    				System.out.println("Unable to find test case: "+testCase.get(j)+". Hence ignoring this test.");
				    				continue;
				    			}
				    		}
				    	}
					}catch(NullPointerException ne){
						System.out.println("\nQuery to fetch test case details: select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and execution_status<>'Passed' and package_name not like ('%_Sanity') order by execution_date desc limit 1");
						rsst=stmt.executeQuery("select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and execution_status<>'Passed' and package_name not like ('%_Sanity') order by execution_date desc limit 1");
						rsst.next();
						try{
							phase=rsst.getString("phase_name");
						}catch(SQLException sqe){
							if(sqe.getLocalizedMessage().contains("Illegal operation on empty result set")){
								System.out.println("Test case: '"+testCase.get(j)+"' not found. Querying for actual test case name");
								System.out.println("Querying for all test cases present in grafana and looking for a match: select distinct(test_case_name) as test from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+"where package_name not like ('%_Sanity')");
								rsst=stmt.executeQuery("select distinct(test_case_name) as test from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+"where package_name not like ('%_Sanity')");
								fetchCorrectTestCaseName:
								while(rsst.next()){
									count++;
									if(rsst.getString("test").replaceAll(" ", "").replaceAll("\\r|\\n", "").equals(testCase.get(j).replaceAll(" ", "").replaceAll("'", "").replaceAll("\"", "").replaceAll("\\r|\\n", ""))){
										System.out.println("Actual test case name: "+rsst.getString("test"));
										testCase.set(j, rsst.getString("test"));
										try{
											System.out.println("Fetching test case details using correct test case name: select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and execution_status<>'Passed' and package_name not like ('%_Sanity') order by execution_date desc limit 1");
											rsst=stmt.executeQuery("select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and execution_status<>'Passed' and package_name not like ('%_Sanity') order by execution_date desc limit 1");
											rsst.next();
											phase=rsst.getString("phase_name");
											testCaseFound=true;
											break fetchCorrectTestCaseName;
											}catch(SQLException sq){
						    					if(sqe.getLocalizedMessage().contains("Illegal operation on empty result set")){
						    						System.out.println("Correct test case name not found for test: "+testCase.get(j)+". Continuing search");
						    						continue;
						    					}
						    				}
										System.out.println("Querying for all test cases present in grafana and looking for a match: select distinct(test_case_name) as test from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+"where package_name not like ('%_Sanity')");
										rsst=stmt.executeQuery("select distinct(test_case_name) as test from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+"where package_name not like ('%_Sanity')");
										for(int i=0;i<count;i++)
											rsst.next();
									}
								}
								
							}
							if(testCaseFound==false){
			    				System.out.println("Unable to find test case: "+testCase.get(j)+". Hence ignoring this test.");
			    				continue;
			    			}
						}
						System.out.println("Phase for test case: "+testCase.get(j)+" is "+phase);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				try{
					if(release.length()==0)
						throw new NullPointerException();
				}catch(NullPointerException e){
					try {
						release=rsst.getString("release_name");
						System.out.println("Release for test case: "+testCase.get(j)+" is "+release);
					} catch (SQLException e1) {
//						e1.printStackTrace();
						System.out.println("Exception while fetching release name for the test case: "+testCase.get(j)+": "+e1.getLocalizedMessage());
					}
				}
				
				try{
					if(application.length()==0)
						throw new NullPointerException();
				}catch(NullPointerException e){
					try {
						application=rsst.getString("application_name");
						System.out.println("Application for test case: "+testCase.get(j)+" is "+application);
					} catch (SQLException e1) {
//						e1.printStackTrace();
						System.out.println("Exception while fetching application name for the test case: "+testCase.get(j)+": "+e1.getLocalizedMessage());
					}
				}
				
				try{
					if(package_name.length()==0)
						throw new NullPointerException();
				}catch(NullPointerException e){
					try {
						package_name=rsst.getString("package_name");
						System.out.println("Package name for test case: "+testCase.get(j)+" is "+package_name);
					} catch (SQLException e1) {
//						e1.printStackTrace();
						System.out.println("Exception while fetching package name for the test case: "+testCase.get(j)+": "+e1.getLocalizedMessage());
					}
				}
				
				if(package_name.startsWith("CrossBrowser") || application.startsWith("EdgeLite")){
				try{
					if(tempPhaseName.length()==0)
						throw new NullPointerException();
					
					browsers=new String[]{"FIREFOX","EDGE","IE"};
					
					for(int i=0;i<browsers.length;i++){
						execution_time=null;
						try{
							System.out.println("Query to fetch test case details: select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and package_name like ('%_"+tempPhaseName+"') and execution_status<>'Passed' and browser_name='"+browsers[i]+"'order by execution_date desc limit 1");
							rsst=stmt.executeQuery("select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and package_name like ('%_"+tempPhaseName+"') and execution_status<>'Passed' and browser_name='"+browsers[i]+"' order by execution_date desc limit 1");
							rsst.next();
							execution_time=rsst.getString("time");
							
							if(execution_time.length()==0)
								throw new NullPointerException();

							System.out.println("Update query: update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where time='"+execution_time+"' and test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and package_name like ('%_"+tempPhaseName+"') and browser_name='"+browsers[i]+"'");
							stmt.executeUpdate("update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where time='"+execution_time+"' and test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and package_name like ('%_"+tempPhaseName+"') and browser_name='"+browsers[i]+"'");
							System.out.println("Grafana updated for "+browsers[i]+" browser");
						}catch(NullPointerException npe){
							System.out.println("Update query: update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and package_name like ('%_"+tempPhaseName+"') and browser_name='"+browsers[i]+"'");
							stmt.executeUpdate("update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and package_name like ('%_"+tempPhaseName+"') and browser_name='"+browsers[i]+"'");
							System.out.println("Grafana updated for "+browsers[i]+" browser");
						}
					}
					
					htmlCode=htmlCode+"<tr >"
							+"      <td style='width: 100px;' align=CENTER>"+application+"</td>"
							+"      <td style='width: 100px;' align=CENTER>"+package_name+"</td>"
							+"      <td style='width: 100px;' align=CENTER>"+phase+"</td>"
							+"      <td style='width: 100px;' align=CENTER>"+testCase.get(j)+"</td>"
							+"    </tr>";
					
					if(application.startsWith("EdgeLite")){
						browsers=new String[]{"CHROME","Nexus 5","iPhone X"};
						
						for(int i=0;i<browsers.length;i++){
							execution_time=null;
							try{
								System.out.println("Query to fetch test case details: select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and package_name like ('%_"+tempPhaseName+"') and execution_status<>'Passed' and browser_name='"+browsers[i]+"'order by execution_date desc limit 1");
								rsst=stmt.executeQuery("select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and package_name like ('%_"+tempPhaseName+"') and execution_status<>'Passed' and browser_name='"+browsers[i]+"' order by execution_date desc limit 1");
								rsst.next();
								execution_time=rsst.getString("time");
								
								if(execution_time.length()==0)
									throw new NullPointerException();

								System.out.println("Update query: update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where time='"+execution_time+"' and test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and package_name like ('%_"+tempPhaseName+"') and browser_name='"+browsers[i]+"'");
								stmt.executeUpdate("update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where time='"+execution_time+"' and test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and package_name like ('%_"+tempPhaseName+"') and browser_name='"+browsers[i]+"'");
								System.out.println("Grafana updated for "+browsers[i]+" browser");
							}catch(NullPointerException npe){
								System.out.println("Update query: update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and package_name like ('%_"+tempPhaseName+"') and browser_name='"+browsers[i]+"'");
								stmt.executeUpdate("update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and package_name like ('%_"+tempPhaseName+"') and browser_name='"+browsers[i]+"'");
								System.out.println("Grafana updated for "+browsers[i]+" browser");
							}
						}
					}
				}catch(NullPointerException ne){
					browsers=new String[]{"FIREFOX","EDGE","IE"};
					
					for(int i=0;i<browsers.length;i++){
						execution_time=null;
						try{
							System.out.println("Query to fetch test case details: select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and execution_status<>'Passed' and browser_name='"+browsers[i]+"'order by execution_date desc limit 1");
							rsst=stmt.executeQuery("select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and execution_status<>'Passed' and browser_name='"+browsers[i]+"' order by execution_date desc limit 1");
							rsst.next();
							execution_time=rsst.getString("time");
							
							if(execution_time.length()==0)
								throw new NullPointerException();

							System.out.println("Update query: update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where time='"+execution_time+"' and test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and browser_name='"+browsers[i]+"'");
							stmt.executeUpdate("update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where time='"+execution_time+"' and test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and browser_name='"+browsers[i]+"'");
							System.out.println("Grafana updated for "+browsers[i]+" browser");
						}catch(NullPointerException npe){
							System.out.println("Update query: update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and browser_name='"+browsers[i]+"'");
							stmt.executeUpdate("update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and browser_name='"+browsers[i]+"'");
							System.out.println("Grafana updated for "+browsers[i]+" browser");
						}
					}
					
					htmlCode=htmlCode+"<tr >"
							+"      <td style='width: 100px;' align=CENTER>"+application+"</td>"
							+"      <td style='width: 100px;' align=CENTER>"+package_name+"</td>"
							+"      <td style='width: 100px;' align=CENTER>"+phase+"</td>"
							+"      <td style='width: 100px;' align=CENTER>"+testCase.get(j)+"</td>"
							+"    </tr>";
					
					if(application.startsWith("EdgeLite")){
						browsers=new String[]{"CHROME","Nexus 5","iPhone X"};
						
						for(int i=0;i<browsers.length;i++){
							execution_time=null;
							try{
								System.out.println("Query to fetch test case details: select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and execution_status<>'Passed' and browser_name='"+browsers[i]+"'order by execution_date desc limit 1");
								rsst=stmt.executeQuery("select * from grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and execution_status<>'Passed' and browser_name='"+browsers[i]+"' order by execution_date desc limit 1");
								rsst.next();
								execution_time=rsst.getString("time");
								
								if(execution_time.length()==0)
									throw new NullPointerException();

								System.out.println("Update query: update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where time='"+execution_time+"' and test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and browser_name='"+browsers[i]+"'");
								stmt.executeUpdate("update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where time='"+execution_time+"' and test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and browser_name='"+browsers[i]+"'");
								System.out.println("Grafana updated for "+browsers[i]+" browser");
							}catch(NullPointerException npe){
								System.out.println("Update query: update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and browser_name='"+browsers[i]+"'");
								stmt.executeUpdate("update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and browser_name='"+browsers[i]+"'");
								System.out.println("Grafana updated for "+browsers[i]+" browser");
							}
						}
					}
				}
				}else{
					
					try {
						execution_time=rsst.getString("time");
						System.out.println("Execution time for test case: "+testCase.get(j)+" is "+execution_time);
					} catch (SQLException e) {
//						e.printStackTrace();
						System.out.println("Exception while fetching execution time for the test case: "+testCase.get(j)+": "+e.getLocalizedMessage());
//						continue;
					}
					
					try{ if(execution_time.length()==0)
							throw new NullPointerException();
						
					try {
						System.out.println("Update query: update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where time='"+execution_time+"' and test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and package_name='"+package_name+"'");
						stmt.executeUpdate("update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where time='"+execution_time+"' and test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and package_name='"+package_name+"'");
						System.out.println("Grafana updated");
						
						htmlCode=htmlCode+"<tr >"
	                    +"      <td style='width: 100px;' align=CENTER>"+application+"</td>"
	                    +"      <td style='width: 100px;' align=CENTER>"+package_name+"</td>"
	                    +"      <td style='width: 100px;' align=CENTER>"+phase+"</td>"
	                    +"      <td style='width: 100px;' align=CENTER>"+testCase.get(j)+"</td>"
	                    +"    </tr>";
					} catch (SQLException e) {
//						e.printStackTrace();
						System.out.println("Error while updating status for test case: "+testCase.get(j)+" : "+e.getLocalizedMessage());
					}
					}catch(NullPointerException ne){
						System.out.println("Update query: update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and package_name='"+package_name+"'");
						stmt.executeUpdate("update grafanadb."+envParams.get("GRAFANA_TABLE_NAME")+" set execution_status='"+new_status+"' where test_case_name='"+testCase.get(j)+"' and phase_name='"+phase+"' and release_name='"+release+"' and application_name='"+application+"' and package_name='"+package_name+"'");
						System.out.println("Grafana updated");
						
						htmlCode=htmlCode+"<tr >"
								+"      <td style='width: 100px;' align=CENTER>"+application+"</td>"
								+"      <td style='width: 100px;' align=CENTER>"+package_name+"</td>"
								+"      <td style='width: 100px;' align=CENTER>"+phase+"</td>"
								+"      <td style='width: 100px;' align=CENTER>"+testCase.get(j)+"</td>"
								+"    </tr>";
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				conn.close();
				System.out.println("\nConnection closed");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		htmlCode=htmlCode+"</tbody>"
                +"</table>";
		
		file=new File(args[4].trim()+"/ExecutionSummary.html");
		
		if(file.exists())
			file.delete();
		
		try {
			fw=new java.io.FileWriter(file);
			fw.write(htmlCode);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

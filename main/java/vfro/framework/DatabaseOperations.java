package vfro.framework;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.WebDriver;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class DatabaseOperations {
    //Instance Variables
    private static Reporting Reporter;
    private HashMap<String, String> Dictionary;
    private HashMap<String, String> Environment;
    private static Connection Conn=null;

    //Constructor
    public DatabaseOperations(HashMap<String, String> GDictionary, HashMap<String,String> GEnvironment,Reporting GReporter)
    {
        Reporter = GReporter;
        Dictionary = GDictionary;
        Environment = GEnvironment;
        try {
			if(Conn==null || Conn.isClosed()==true)
				Conn=ConnecttoDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}

    }
    
   
    //created for calling update grafana method at the end of test since existing constructor requires reporting and other objects to be passed
    public DatabaseOperations(HashMap<String, String> GDictionary, HashMap<String,String> GEnvironment) {
    	 Dictionary = GDictionary;
    	 Environment = GEnvironment;
	}

   

	public DatabaseOperations() {
	
	}


	//*****************************************************************************************
    //* Name            : getDataFromDB
    //* Description     : Connect to db and execute query and return the results
    //* Author          : Zachi Gahari
    //* Updated Date    : November 16, 2017
    //* Input Params    : 
    //* Return Values   : ResultSet
    //*****************************************************************************************
    public ResultSet getDataFromDB(String pk){
    	HashMap<String, String> params = new HashMap<>();
    	
    	try{
    		params.get("<BRAND_NAME>").trim();
    	}catch(NullPointerException e){
    		if(Environment.get("LOGIN_BRAND").equals("") || Environment.get("LOGIN_BRAND").equals("METRO"))
        		params.put("<BRAND_NAME>", "'METRO' or brand_name is null");
        	else if(Environment.get("LOGIN_BRAND").equals("ALL")==false)
        		params.put("<BRAND_NAME>", "'"+Environment.get("LOGIN_BRAND")+"'");
        	else
        		params.put("<BRAND_NAME>", "'"+Environment.get("INPUT_DATA_BRAND")+"'");
    	}
    	
    	String query = null;
    	try{
    		query = getQuery(pk);
    		for (Entry<String, String> param : params.entrySet()) {
    			String oldValue = param.getKey();
    			String newValue = param.getValue();
				query = query.replace(oldValue , newValue);
			}
	    	System.out.println("About to execute query: " + query);
	    	ResultSet rsData = ExecuteQuery(query, Conn);
	    	if (!rsData.isBeforeFirst()) {    
	    	    System.out.println("No data"); 
	    	    Reporter.fnWriteToHtmlOutput("getDataFromDB", "Get data from database","No data return for query " + query, "VALIDATION FAIL");
	            return null;
	    	} 
			rsData.next();
	    	return rsData;
        }
        catch(Exception e){
            e.printStackTrace();
            Reporter.fnWriteToHtmlOutput("getDataFromDB", "Get data from database","Failed to execute query: " + query, "VALIDATION FAIL");
            return null;
        }
    }
    
	//*****************************************************************************************
    //* Name            : getDataFromDB
    //* Description     : Connect to db and execute query and return the results
    //* Author          : Zachi Gahari
    //* Updated Date    : November 16, 2017
    //* Input Params    : 
    //* Return Values   : ResultSet
    //*****************************************************************************************
    public ResultSet getDataFromDB(String pk, String dbType){
    	String query = null;
    	HashMap<String, String> params = new HashMap<>();
    	dbType = dbType.toUpperCase() + "_";
       	Connection Conn = ConnecttoDB(Environment.get(dbType+"DB_HOST"), Environment.get(dbType+"DB_USER"), Environment.get(dbType+"DB_PWD"));
    	
    	try{
    		query = getQuery(pk);
    		for (Entry<String, String> param : params.entrySet()) {
    			String oldValue = param.getKey();
    			String newValue = param.getValue();
				query = query.replace(oldValue , newValue);
			}
	    	System.out.println("About to execute query: " + query);
	    	ResultSet rsData = ExecuteQuery(query, Conn);
	    	if (!rsData.isBeforeFirst()) {    
	    	    System.out.println("No data"); 
	    	    Reporter.fnWriteToHtmlOutput("getDataFromDB", "Get data from database","No data return for query " + query, "VALIDATION FAIL");
	            return null;
	    	} 
			rsData.next();
	    	return rsData;
        }
        catch(Exception e){
            e.printStackTrace();
            Reporter.fnWriteToHtmlOutput("getDataFromDB", "Get data from database","Failed to execute query: " + query, "VALIDATION FAIL");
            return null;
        }
    }
    
	//*****************************************************************************************
    //* Name            : getDataFromDB
    //* Description     : Connect to db and execute query and return the results
    //* Author          : Zachi Gahari
    //* Updated Date    : November 16, 2017
    //* Input Params    : 
    //* Return Values   : ResultSet
    //*****************************************************************************************
    public ResultSet getDataFromDB(String host, String user, String password, String query){
    	String tempQuery = "";
		try {
			tempQuery = getQuery(query);	// it can be used with PK or specific query
		} catch (FilloException e1) {
		}
    	if(!tempQuery.isEmpty())
    		query = tempQuery;
    	
    	Connection Conn = ConnecttoDB(host, user, password);
    	try{
	    	System.out.println("About to execute query: " + query);
	    	ResultSet rsData = ExecuteQuery(query, Conn);
	    	if (!rsData.isBeforeFirst()) {    
	    	    System.out.println("No data"); 
	    	    Reporter.fnWriteToHtmlOutput("getDataFromDB", "Get data from database","No data return for query " + query, "VALIDATION FAIL");
	            return null;
	    	} 
			rsData.next();
			return rsData;
        }
        catch(Exception e){
            e.printStackTrace();
            Reporter.fnWriteToHtmlOutput("getDataFromDB", "Get data from database","Failed to execute query: " + query, "VALIDATION FAIL");
            return null;
        }
    }
    
    //*****************************************************************************************
    //* Name            : getDataFromDBWithParams
    //* Description     : Connect to db and execute query with parameters and return the results
    //* Author          : Udi Valer
    //* Updated Date    : December 7, 2017
    //* Input Params    : PK - name of the query in excel , HashMap - parameters to inject
    //* Return Values   : ResultSet
    //*****************************************************************************************
    public ResultSet getDataFromDBWithParameters(String pk , HashMap<String, String> params){
    	
    	try{
    		params.get("<BRAND_NAME>").trim();
    		if(params.get("<BRAND_NAME>").replaceAll("'", "").trim().equalsIgnoreCase("Metro"))
    			params.put("<BRAND_NAME>", "'METRO' or brand_name is null");
    	}catch(NullPointerException e){
    		if(Environment.get("LOGIN_BRAND").equals("") || Environment.get("LOGIN_BRAND").equals("METRO"))
        		params.put("<BRAND_NAME>", "'METRO' or brand_name is null");
        	else if(Environment.get("LOGIN_BRAND").equals("ALL")==false)
        		params.put("<BRAND_NAME>", "'"+Environment.get("LOGIN_BRAND")+"'");
        	else
        		params.put("<BRAND_NAME>", "'"+Environment.get("INPUT_DATA_BRAND")+"'");
    	}
    	  	
    	
    	String query = null;
    	try{
    		query = getQuery(pk);
    		for (Entry<String, String> param : params.entrySet()) {
    			String oldValue = param.getKey();
    			String newValue = param.getValue();
				query = query.replace(oldValue , newValue);
			}
	    	System.out.println("About to execute query " + query);
	    	ResultSet rsData = ExecuteQuery(query, Conn);
	    	if (!rsData.isBeforeFirst()) {    
	    	    System.out.println("No data"); 
	    	    Reporter.fnWriteToHtmlOutput("getDataFromDBWithParameters", "Get data from database","No data return for query " + query, "VALIDATION FAIL");
	            return null;
	    	} 
			rsData.next();
	    	return rsData;
        }
        catch(Exception e){
            e.printStackTrace();
            Reporter.fnWriteToHtmlOutput("getDataFromDBWithParameters", "Get data from database","Failed to execute query " + query, "VALIDATION FAIL");
            return null;
        }
    }
    
    //*****************************************************************************************
    //* Name            : getDataFromDBWithParams
    //* Description     : Connect to db and execute query with parameters and return the results
    //* Author          : Udi Valer
    //* Updated Date    : December 7, 2017
    //* Input Params    : PK - name of the query in excel , HashMap - parameters to inject
    //* Return Values   : ResultSet
    //*****************************************************************************************
    public ResultSet getDataFromDBWithParameters(String pk, String dbType, HashMap<String, String> params){
    	
    	dbType = dbType.toUpperCase() + "_";
       	Connection Conn = ConnecttoDB(Environment.get(dbType+"DB_HOST"), Environment.get(dbType+"DB_USER"), Environment.get(dbType+"DB_PWD"));
    	
    	String query = null;
    	try{
    		query = getQuery(pk);
    		for (Entry<String, String> param : params.entrySet()) {
    			String oldValue = param.getKey();
    			String newValue = param.getValue();
				query = query.replace(oldValue , newValue);
			}
	    	System.out.println("About to execute query " + query);
	    	ResultSet rsData = ExecuteQuery(query, Conn);
	    	if (!rsData.isBeforeFirst()) {    
	    	    System.out.println("No data"); 
	    	    Reporter.fnWriteToHtmlOutput("getDataFromDBWithParameters", "Get data from database","No data return for query " + query, "VALIDATION FAIL");
	            return null;
	    	} 
			rsData.next();
	    	return rsData;
        }
        catch(Exception e){
            e.printStackTrace();
            Reporter.fnWriteToHtmlOutput("getDataFromDBWithParameters", "Get data from database","Failed to execute query " + query, "VALIDATION FAIL");
            return null;
        }
    }
		
    //*****************************************************************************************
    //* Name            : getDataFromDBWithParams
    //* Description     : Connect to db and execute query with parameters and return the results
    //* Author          : Udi Valer
    //* Updated Date    : December 7, 2017
    //* Input Params    : PK - name of the query in excel , HashMap - parameters to inject
    //* Return Values   : ResultSet
    //*****************************************************************************************
    public ResultSet getDataFromDBWithParameters(String host, String user, String password, String pk , HashMap<String, String> params){
    	
    	try{
    		params.get("<BRAND_NAME>").trim();
    		if(params.get("<BRAND_NAME>").replaceAll("'", "").trim().equalsIgnoreCase("Metro"))
    			params.put("<BRAND_NAME>", "'METRO' or brand_name is null");
    	}catch(NullPointerException e){
    		if(Environment.get("LOGIN_BRAND").equals("") || Environment.get("LOGIN_BRAND").equals("METRO"))
        		params.put("<BRAND_NAME>", "'METRO' or brand_name is null");
        	else if(Environment.get("LOGIN_BRAND").equals("ALL")==false)
        		params.put("<BRAND_NAME>", "'"+Environment.get("LOGIN_BRAND")+"'");
        	else
        		params.put("<BRAND_NAME>", "'"+Environment.get("INPUT_DATA_BRAND")+"'");
    	}
    	  	
    	Connection Conn = ConnecttoDB(host, user, password);
    	String query = null;
    	try{
    		query = getQuery(pk);
    		for (Entry<String, String> param : params.entrySet()) {
    			String oldValue = param.getKey();
    			String newValue = param.getValue();
				query = query.replace(oldValue , newValue);
			}
	    	System.out.println("About to execute query " + query);
	    	ResultSet rsData = ExecuteQuery(query, Conn);
	    	if (!rsData.isBeforeFirst()) {    
	    	    System.out.println("No data"); 
	    	    Reporter.fnWriteToHtmlOutput("getDataFromDB", "Get data from database","No data return for query " + query, "VALIDATION FAIL");
	            return null;
	    	} 
			rsData.next();
	    	return rsData;
        }
        catch(Exception e){
            e.printStackTrace();
            Reporter.fnWriteToHtmlOutput("getDataFromDB", "Get data from database","Failed to execute query " + query, "VALIDATION FAIL");
            return null;
        }
    }
		
    //*****************************************************************************************
    //* Name            : ConnecttoDB
    //* Description     : Connect to db and return connection object
    //* Author          : Naveena Basetty
    //* Updated Date    : March 16, 2016
    //* Input Params    : 
    //* Return Values   : Connection
    //*****************************************************************************************

    public Connection ConnecttoDB()
    {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            Connection conn = DriverManager.getConnection(Environment.get("ABP_DB_HOST"), Environment.get("ABP_DB_USER"), Environment.get("ABP_DB_PWD"));
            Reporter.fnWriteToHtmlOutput("ConnecttoDB", "Connect to database","Connection successful", "Done");
            return conn;
        }
        catch(Exception e){
        	if(e.getLocalizedMessage().contains("String index out of range")==false){
        		e.printStackTrace();
                Reporter.fnWriteToHtmlOutput("ConnecttoDB", "Connect to database","Connection NOT successful " + e.getLocalizedMessage(), "Fail");
        	}else{
        		Reporter.fnWriteToHtmlOutput("ConnecttoDB", "Connect to database","BSO authentication not done", "Done");
        	}
            
            return null;
        }

    }
    //*****************************************************************************************
    //* Name            : ExecuteQuery
    //* Description     : Executes statement based on query string
    //* Author          : Naveena Basetty
    //* Updated Date    : March 16, 2016
    //* Input Params    : 
    //* Return Values   : Resultset
    //*****************************************************************************************

    public ResultSet ExecuteQuery(String query, Connection Conn)
    {
        try{
            Statement stmt=Conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
            ResultSet rs=stmt.executeQuery(query);
            Reporter.fnWriteToHtmlOutput("Execute query: "+query, "Query is executed successfully", "Result is returned to test", "Done");
            return rs;
        }
        catch(Exception e){
            e.printStackTrace();
            Reporter.fnWriteToHtmlOutput("Execute query: "+query, "Query is executed successfully","Error while executing query" + e.toString(), "Fail");
            return null;
        }

    }

    //*****************************************************************************************
    //* Name            : OCE_ValidateParentAndChildProcesses
    //* Description     : Executes statement based on query string
    //* Author          : Naveena Basetty
    //* Updated Date    : March 16, 2016
    //* Input Params    : 
    //* Return Values   : Resultset
    //*****************************************************************************************
    public boolean  OCE_ValidateChildProcesses(String OrderNum,Connection conn,String serviceName)
    {
        boolean Process = false;
        try{

            int i = 0;

            while(true){
                i = i + 1;
                String sql = "SELECT * FROM "+ Environment.get("AUDITLOG_DB_SCHEMA") + ".OCE_AUDIT_LOG WHERE CUSTOMER_ORDER_NUMBER= '" + OrderNum + "' and service_name = '"+ serviceName + "'";
                ResultSet rs=ExecuteQuery(sql,conn);
                Thread.sleep(5000);
               while(rs.next()){
                       System.out.println("parent process  "+ new Date() + "    " + rs.getString("auditlog_id").toString());
                       Process = true;
                }
 
                if( i > 10 || Process)
                    break;
                else
                	Thread.sleep(60000);
            }
         
        	} catch(Exception e)
        {
            e.printStackTrace();
            Reporter.fnWriteToHtmlOutput("OCE_ValidateChildProcesses", "Check for child process","Exception while runing the query:  "+e.toString(), "Fail");
            return false;
        }

        if(Process){
            Reporter.fnWriteToHtmlOutput("OCE_ValidateChildProcesses", "check for child process","Order decomposed successfully", "Done");
            return true;
        }
        else{
            Reporter.fnWriteToHtmlOutput("OCE_ValidateChildProcesses", "check for child process","Order couldn't be decomposed", "Fail");
            return false;
        }
    }


    //*****************************************************************************************
    //* Name            : closeConnection
    //* Description     : close DB connection
    //* Author          : Gavril Grigorean
    //* Updated Date    : March 24, 2016
    //* Input Params    : 
    //* Return Values   : void
    //*****************************************************************************************
    public static  boolean closeConnection(Connection connection){
        try {
            connection.close();
            if (connection.isClosed()==true){
            	Reporter.fnWriteToHtmlOutput("Close DB Connection", "DB Connection closed", "DB Connection closed", "Done");
                return true;
            }else {
            	Reporter.fnWriteToHtmlOutput("Close DB Connection", "DB Connection closed", "DB Connection not closed", "Done");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //*****************************************************************************************
    //* Name            : closeConnection
    //* Description     : close DB connection
    //* Author          : Zachi Gahri
    //* Updated Date    : July 1, 2020
    //* Input Params    : 
    //* Return Values   : void
    //*****************************************************************************************
    public boolean closeConnection(){
        try {
            Conn.close();
            if (Conn.isClosed()==true){
            	Reporter.fnWriteToHtmlOutput("Close DB Connection", "DB Connection closed", "DB Connection closed", "Done");
                return true;
            }else {
            	Reporter.fnWriteToHtmlOutput("Close DB Connection", "DB Connection closed", "DB Connection not closed", "Done");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //*****************************************************************************************
    //* Name            : getAPIResponsesForOrder
    //* Description     : Executes statement based on query string
    //* Author          : Meena Panchanatheswaran
    //* Updated Date    : April 21, 2016
    //* Input Params    : 
    //* Return Values   : Resultset
    //*****************************************************************************************
    public ResultSet  getAPIResponsesForOrder(String OrderNum,Connection conn)
    {
    	 ResultSet rs =null;
        try{



        
                String sql = "SELECT  CUSTOMER_ORDER_NUMBER,SERVICE_NAME,OPERATION_NAME ,RESPONSE,REQUEST_TIME,RESPONSE_TIME,CONVERSATION_ID from "+
                		Environment.get("AUDITLOG_DB_SCHEMA")+".oce_audit_log where "
                		+ "CUSTOMER_ORDER_NUMBER='"+OrderNum +"'  and INTERFACE_NAME='CSI' ORDER BY SERVICE_NAME";
                 rs=ExecuteQuery(sql,conn);
                Thread.sleep(5000);
            /*   while(rs.next()){
                       System.out.println( rs.getString("CUSTOMER_ORDER_NUMBER").toString());
                     
                }*/
 
             
            
         
        	} catch(Exception e)
        {
            //e.printStackTrace();
            
            return null;
        }
		return rs;

    }
    
    //*****************************************************************************************
    //* Name            : getAPIResponsesForOrder
    //* Description     : Executes statement based on query string
    //* Author          : Meena Panchanatheswaran
    //* Updated Date    : May 02, 2016
    //* Input Params    : 
    //* Return Values   : Resultset
    //*****************************************************************************************
    public String  getProcessOrder(String OrderNum,Connection conn)
    {
    	 ResultSet rs =null;
    	 String orderxml="";
        try{



        
                String sql = "SELECT  REQUEST from "+
                		Environment.get("AUDITLOG_DB_SCHEMA")+".oce_audit_log where "
                		+ "CUSTOMER_ORDER_NUMBER='"+OrderNum +"' and Layer='FUSE' and Operation_Name='INSERT'";
                 rs=ExecuteQuery(sql,conn);
                 
                 while(rs.next()){
                     orderxml=  rs.getString("REQUEST");  
                 }
                // orderxml= StringEscapeUtils.unescapeXml(orderxml);
                 orderxml=orderxml.replaceAll("\\n", "");
 				Pattern pattern = Pattern.compile("(<Order )(.*)(</Order>)");
 				Matcher matcher = pattern.matcher(orderxml);
 				while (matcher.find()) {
 					orderxml=matcher.group(0);
 		        }
 				   
                 
                 Thread.sleep(5000);
            /*   while(rs.next()){
                       System.out.println( rs.getString("CUSTOMER_ORDER_NUMBER").toString());
                     
                }*/
 
             
            
         
        	} catch(Exception e)
        {
            //e.printStackTrace();
            
            return null;
        }
		return orderxml;

    }

    //*****************************************************************************************
    //* Name            : getQuery
    //* Description     : Get SQL query from DB_SQL file
    //* Author          : Zachi Gahari
    //* Updated Date    : November 22, 2017
    //* Input Params    : 
    //* Return Values   : SQL query
    //*****************************************************************************************
    public String getQuery(String pk) throws FilloException{

		String dataSheetsPath = System.getProperty("user.dir") + "/data";
		String query = null;
		
		Fillo fillo=new Fillo();
    	com.codoid.products.fillo.Connection connection;
		connection = fillo.getConnection(dataSheetsPath + "/DB_SQL.xlsx");
    	String strQuery="Select * from Data where PK='" + pk + "'";
    	Recordset recordset=connection.executeQuery(strQuery);

    	while(recordset.next()){
    		query = recordset.getField("QUERY");
    		System.out.println(query);
    	}

    	connection.close();
    	return query;
    }
    
    //*****************************************************************************************
    //* Name            : getProductNumber
    //* Description     : Get Device or SIM number
    //* Author          : Zachi Gahari
    //* Updated Date    : Feb 2, 2018
    //* Input Params    : 
    //* Return Values   : SQL query
    //*****************************************************************************************
    public String getProductNumber(String product, String type, String storeID, String user) throws FilloException{

		String dataSheetsPath = System.getProperty("user.dir") + "\\data";
		String strQuery = null;
		String number = null;
		
		if(type.isEmpty())
			type="GSM";
		
		Fillo fillo=new Fillo();
    	com.codoid.products.fillo.Connection connection;
		connection = fillo.getConnection(dataSheetsPath + "\\RISE_DevicesAndSim.xlsx");
		if(product.equalsIgnoreCase("DEVICE")){ 
			strQuery="Select NUMBER from Devices where TYPE='" + type + "' and STORE_ID='" + storeID + "'and UName='"+ user +"'and STATUS='A' and Environment='"+Environment.get("ENV_CODE")+"'";
		}else{
			Reporter.fnWriteToHtmlOutput("getProductNumber","Product should be provided (Device or SIM)", "Not provided","Fail");
			return null;
		}
    	
		try {
			Recordset recordset=connection.executeQuery(strQuery);
			recordset.next();
			number = recordset.getField("NUMBER");
		} catch (Exception e) {
			Environment.put("FAILURE_ERROR","No data return for query");
			Reporter.fnWriteToHtmlOutput("getProductNumber","Executed query: " + strQuery, "Failed","Fail");
			return null;
		}
		System.out.println(number);
    	connection.close();
    	return number;
    }
    public String getSimNumber(String product, String type, String storeID, String user) throws FilloException{
    	String dataSheetsPath = System.getProperty("user.dir") + "\\data";
		String strQuery = null;
		String strQuery1 = null;
		String sim = null;
		
		Fillo fillo=new Fillo();
    	com.codoid.products.fillo.Connection connection;
		connection = fillo.getConnection(dataSheetsPath + "\\RISE_DevicesAndSim.xlsx");
		if(product.equalsIgnoreCase("DEVICE")){ 
			strQuery1="Select SIM from Devices where TYPE='" + type + "' and STORE_ID='" + storeID + "'and UName='"+ user +"'and STATUS='A' and Environment='"+Environment.get("ENV_CODE")+"'";
		}else if(product.equalsIgnoreCase("SIM")){
			strQuery="Select NUMBER from SIM where STORE_ID='" + storeID + "' and STATUS='A'";
		}else{
			Reporter.fnWriteToHtmlOutput("getProductNumber","Product should be provided (Device or SIM)", "Not provided","Fail");
			return null;
		}
    	
		try {
			Recordset recordset1=connection.executeQuery(strQuery1);
			recordset1.next();
			sim = recordset1.getField("SIM");
		} catch (Exception e) {
			Environment.put("FAILURE_ERROR","No data return for query");
			Reporter.fnWriteToHtmlOutput("getProductNumber","Executed query: " + strQuery, "Failed","Fail");
			return null;
		}
		System.out.println(sim);
    	connection.close();
    	return sim;
    }
    public String getOnlySimNumber(String product, String storeID) throws FilloException{
    	String dataSheetsPath = System.getProperty("user.dir") + "\\data";
		String strQuery = null;
		String sim = null;
		
		Fillo fillo=new Fillo();
    	com.codoid.products.fillo.Connection connection;
		connection = fillo.getConnection(dataSheetsPath + "\\RISE_DevicesAndSim.xlsx");
		if(product.equalsIgnoreCase("SIM")){
			strQuery="Select NUMBER from SIM where STORE_ID='" + storeID + "' and STATUS='A' and Environment='"+Environment.get("ENV_CODE")+"'";
		}else{
			Reporter.fnWriteToHtmlOutput("getProductNumber","Product should be provided (Device or SIM)", "Not provided","Fail");
			return null;
		}
    	
		try {
			Recordset recordset=connection.executeQuery(strQuery);
			recordset.next();
			sim = recordset.getField("NUMBER");
		} catch (Exception e) {
			Environment.put("FAILURE_ERROR","No data return for query");
			Reporter.fnWriteToHtmlOutput("getProductNumber","Executed query: " + strQuery, "Failed","Fail");
			return null;
		}
		System.out.println(sim);
    	connection.close();
    	return sim;
    }
    //*****************************************************************************************
    //* Name            : setProductNumberAsUsed
    //* Description     : Set Device or SIM number as used
    //* Author          : Zachi Gahari
    //* Updated Date    : Feb 3, 2018
    //* Input Params    : 
    //* Return Values   : SQL query
    //*****************************************************************************************
    public boolean setProductNumberAsUsed(String number, String product, String type, String storeID, String user) throws FilloException{

		String dataSheetsPath = System.getProperty("user.dir") + "/data";
		String strQuery = null;
		
		if(type.isEmpty())
			type="GSM";
		
		Fillo fillo=new Fillo();
    	com.codoid.products.fillo.Connection connection;
		connection = fillo.getConnection(dataSheetsPath + "/RISE_DevicesAndSim.xlsx");

		if(product.equalsIgnoreCase("DEVICE")){ 
			strQuery="Update Devices Set STATUS='U' where NUMBER='" + number + "' and TYPE='" + type + "' and STORE_ID='" + storeID + "'and UName='"+ user +"' and Environment='"+Environment.get("ENV_CODE")+"'";
		}else{
			Reporter.fnWriteToHtmlOutput("getProductNumber","Product should be updated as used", "Not updated","Fail");
			return false;
		}
		try {
			connection.executeUpdate(strQuery);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput("setProductNumberAsUsed","Executed query: " + strQuery, "Failed","Fail");
			connection.close();
			return false;
		}
		connection.close();
    	return true;
    }  
    public boolean setOnlySimNumberAsUsed(String sim, String product, String storeID) throws FilloException{

  		String dataSheetsPath = System.getProperty("user.dir") + "/data";
  		String strQuery = null;
  		
  		Fillo fillo=new Fillo();
      	com.codoid.products.fillo.Connection connection;
  		connection = fillo.getConnection(dataSheetsPath + "/RISE_DevicesAndSim.xlsx");

  		 if(product.equalsIgnoreCase("SIM")){
  			strQuery="Update SIM Set STATUS='U' where NUMBER='" + sim + "' and STORE_ID='" + storeID + "' and Environment='"+Environment.get("ENV_CODE")+"'";
  		}else{
  			Reporter.fnWriteToHtmlOutput("getSimNumber","Product should be updated as used", "Not updated","Fail");
  			return false;
  		}
      	
  		try {
  			connection.executeUpdate(strQuery);
  		} catch (Exception e) {
  			e.printStackTrace();
  			Reporter.fnWriteToHtmlOutput("setSimNumberAsUsed","Executed query: " + strQuery, "Failed","Fail");
  			return false;
  		}

  		connection.close();
      	return true;
      }    

    //*****************************************************************************************
    //* Name            : getDataFromExcel
    //* Description     : Get Data From Excel
    //* Author          : Zachi Gahari
    //* Updated Date    : Apr 22, 2018
    //* Input Params    : 
    //* Return Values   : Excel record
    //*****************************************************************************************
    public Recordset getDataFromExcel(String fileName, String sheetName, String condition) throws FilloException{

		String strQuery = null;
  		Recordset recordset;
		
		Fillo fillo=new Fillo();
    	com.codoid.products.fillo.Connection connection;
		connection = fillo.getConnection(fileName);
		strQuery = "select * from " + sheetName + " where " + condition;
		System.out.println("Query: "+strQuery);

		try {
			recordset = connection.executeQuery(strQuery);
			recordset.next();
		} catch (Exception e) {
			Reporter.fnWriteToHtmlOutput("getDataFromExcel","Executed query: " + strQuery, "Error: "+e.getLocalizedMessage(),"Fail");
			e.printStackTrace();
			return null;
		}

    	connection.close();
		return recordset;

    }
    
    //*****************************************************************************************
    //* Name            : getDataFromExcel
    //* Description     : Get Data From Excel
    //* Author          : Zachi Gahari
    //* Updated Date    : Apr 22, 2018
    //* Input Params    : 
    //* Return Values   : Excel record
    //*****************************************************************************************
    public boolean updateDataInExcel(String fileName, String sheetName, String update, String condition) throws FilloException{

		String strQuery = null;
  		
		Fillo fillo=new Fillo();
    	com.codoid.products.fillo.Connection connection;
		connection = fillo.getConnection(fileName);
		System.out.println("Filename: "+fileName);
		strQuery = "Update " + sheetName + " Set " + update + " where " + condition;

		try {
			connection.executeUpdate(strQuery);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput("updateDataInExcel","Executed query: " + strQuery, "Failed","Fail");
			connection.close();
			return false;
		}

    	connection.close();
		return true;

    }
    
    public Connection ConnecttoDB(String host, String user, String password)
    {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            Connection conn = DriverManager.getConnection(host, user, password);
            Reporter.fnWriteToHtmlOutput("ConnecttoDB", "Connect to database "+host+" using user "+user,"Connection successful", "Done");
            return conn;
        }
        catch(Exception e){
            e.printStackTrace();
            Reporter.fnWriteToHtmlOutput("ConnecttoDB", "Connect to database "+host+" using user "+user,"Connection NOT successful " + e, "Fail");
            return null;
        }

    }
    
    public static Connection getConnection(){
    	return Conn;
    }
    
    public ResultSet getDataFromDBWithParameters(String pk , HashMap<String, String> params, Connection conn){
    	String query = null;
    	try{
    		query = getQuery(pk);
    		for (Entry<String, String> param : params.entrySet()) {
    			String oldValue = param.getKey();
    			String newValue = param.getValue();
				query = query.replace(oldValue , newValue);
			}
	    	System.out.println("About to execute query " + query);
	    	ResultSet rsData = ExecuteQuery(query, conn);
	    	if (!rsData.isBeforeFirst()) {    
	    	    System.out.println("No data"); 
	    	    Reporter.fnWriteToHtmlOutput("getDataFromDB", "Get data from database","No data return for query " + query, "VALIDATION FAIL");
	            return null;
	    	} 
			rsData.next();
	    	return rsData;
        }
        catch(Exception e){
            e.printStackTrace();
            Reporter.fnWriteToHtmlOutput("getDataFromDB", "Get data from database","Failed to execute query " + query, "VALIDATION FAIL");
            return null;
        }
    }
    
	//*****************************************************************************************
	//* Name            : fCommonClearFlashMessages
	//* Description     : Clear Flash Messages if any   
	//* CREATED RELEASE : 
	//* LAST MODIFIED IN :
	//* Author          : Zachi Gahari
	//* Input Params    : Object description
	//* Modified date   : 09-Dec-2019
	//* Return Values   : 
	//*****************************************************************************************
	public void fCommonClearFlashMessages(){
		ResultSet rs=getDataFromDB("COUNT_FLASH_MSG");
		try {
			String count = rs.getString("COUNT").trim();
			if (!count.matches("0")){
				rs = ExecuteQuery("delete from flash_messages", Conn);
				rs= ExecuteQuery("commit", Conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}     
	
	public boolean executeUpdateQueryAndCommit(String pk){
		String query = null;
		
    	try{
    		query = getQuery(pk);
	    	System.out.println("About to execute query" + query);
	    	Statement stmt=Conn.createStatement();
	    	ResultSet rs=stmt.executeQuery(query);
	    	rs=stmt.executeQuery("commit");
	    	Reporter.fnWriteToHtmlOutput("Execute update query", "Execute query: "+query,"Query executed", "VALIDATION PASS");
	    }
        catch(Exception e){
            e.printStackTrace();
            Reporter.fnWriteToHtmlOutput("Execute update query", "Execute query: "+query,"Error in executing query: " + e.getLocalizedMessage(), "VALIDATION FAIL");
            return false;
        }
    	
    	return true;
	}
	
	public boolean executeUpdateQueryAndCommitWithParameters(String pk, HashMap<String, String> params){
		String query = null;
		Connection Conn=null;
				
    	try{
    		query = getQuery(pk);
    		for (Entry<String, String> param : params.entrySet()) {
    			String oldValue = param.getKey();
    			String newValue = param.getValue();
				query = query.replace(oldValue , newValue);
			}
    		Conn=ConnecttoDB(Environment.get("DB_HOST"), Environment.get("REF_DB_USER"), Environment.get("REF_DB_PWD"));
	    	System.out.println("About to execute query" + query);
	    	Statement stmt=Conn.createStatement();
	    	ResultSet rs=stmt.executeQuery(query);
	    	rs=stmt.executeQuery("commit");
	    	Reporter.fnWriteToHtmlOutput("Execute update query", "Execute query: "+query,"Query executed", "VALIDATION PASS");
	    }
        catch(Exception e){
            e.printStackTrace();
            Reporter.fnWriteToHtmlOutput("Execute update query", "Execute query: "+query,"Error in executing query: " + e.getLocalizedMessage(), "VALIDATION FAIL");
            return false;
        }finally{
        	closeConnection(Conn);
        }
    	
    	return true;
	}

	public boolean updateQueryAndCommitWithParameters(String pk, HashMap<String, String> params){
		String query = null;

		try{
			query = getQuery(pk);
			for (Entry<String, String> param : params.entrySet()) {
				String oldValue = param.getKey();
				String newValue = param.getValue();
				query = query.replace(oldValue , newValue);
			}
			System.out.println("About to execute query: " + query);
			Statement stmt=Conn.createStatement();
			stmt.executeQuery(query);
			stmt.executeQuery("commit");
			Reporter.fnWriteToHtmlOutput("Execute update query", "Execute query: "+query,"Query executed", "VALIDATION PASS");
		}
		catch(Exception e){
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Execute update query", "Execute query: "+query,"Error in executing query: " + e.getLocalizedMessage(), "VALIDATION FAIL");
			return false;
		}

		return true;
	}
    
	public boolean executeUpdateQueryAndCommit(String pk, Connection Conn){
		String query = null;
		
    	try{
    		query = getQuery(pk);
	    	System.out.println("About to execute query" + query);
	    	Statement stmt=Conn.createStatement();
	    	ResultSet rs=stmt.executeQuery(query);
	    	rs=stmt.executeQuery("commit");
	    	Reporter.fnWriteToHtmlOutput("Execute update query", "Execute query: "+query,"Query executed", "VALIDATION PASS");
	    }
        catch(Exception e){
            e.printStackTrace();
            Reporter.fnWriteToHtmlOutput("Execute update query", "Execute query: "+query,"Error in executing query: " + e.getLocalizedMessage(), "VALIDATION FAIL");
            return false;
        }
    	
    	return true;
	}

	//*****************************************************************************************
    //* Name            : countExcelSheetRows
    //* Description     : Get Data From Excel
    //* Author          : Zachi Gahari
    //* Updated Date    : Apr 22, 2018
    //* Input Params    : 
    //* Return Values   : Excel record
    //*****************************************************************************************
    public int countExcelSheetRows(String fileName, String sheetName, String condition) throws FilloException{
    	// This method hasn't tested!
		String strQuery = null;
		int count = -1;
  		Recordset recordset;
		
		Fillo fillo=new Fillo();
    	com.codoid.products.fillo.Connection connection;
		connection = fillo.getConnection(fileName);
		strQuery = "select * from " + sheetName + " where " + condition;
		System.out.println("Query: "+ strQuery);

		try {
			recordset = connection.executeQuery(strQuery);
			count = recordset.getCount();
		} catch (Exception e) {
			Reporter.fnWriteToHtmlOutput("getDataFromExcel","Executed query: " + strQuery, "Failed","Fail");
			e.printStackTrace();
		}

    	connection.close();
		return count;

    }
    
    public boolean updateGrafana(String release, String testPhase, String applicationName, String packageName, String progressionCRName, String browser, String profile, String env, String user){
    	
    	Connection conn=null;
    	Date startDate=null;
    	Date endDate=null;
    	Date today=null;
    	SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
    	    	
    	packageName=packageName.replaceAll("[0-9]", "");
    	
    	if((testPhase.toUpperCase().equals("CIT")==false && testPhase.toUpperCase().equals("UAT")==false) || testPhase.isEmpty()){
    		if(testPhase.isEmpty()==false && packageName.equals(testPhase)==false)
    			packageName=packageName+"_"+testPhase;
    		
    		try {
				startDate=format.parse(Environment.get("CIT_START_DATE"));
				endDate=format.parse(Environment.get("CIT_END_DATE"));
				today=format.parse(Environment.get("EXECUTION_DATE"));
								
				if((today.equals(startDate) || today.after(startDate)) && (today.equals(endDate) || today.before(endDate)))
					testPhase="CIT";
				else
					testPhase="UAT";
				
				if(testPhase.equals("CIT") && packageName.startsWith("Progression"))
					testPhase="DEV";
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
    	System.out.println("Test phase: "+testPhase);
    	
    	if(Dictionary.get("QC_STATUS").equals("Failed"))
    		Dictionary.put("QC_STATUS", "In Progress");
    	    		
    	try{
    		if(Environment.get("ROOT_CAUSE").equals("Environment Error") && Environment.get("FAILURE_ERROR").isEmpty()==false)
    			Environment.put("ROOT_CAUSE", Environment.get("FAILURE_ERROR").replaceAll("'", ""));
            System.out.println("Grafana Query: insert into grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" Values ("+release+",'"+testPhase+"','"+applicationName+"','"+packageName+"','"+progressionCRName+"','"+browser+"','"+profile+"','"+Dictionary.get("TEST_NAME").replaceAll("'", "").replaceAll("\"", "")+"','"+env+"','"+Dictionary.get("QC_STATUS")+"',str_to_date('"+Environment.get("EXECUTION_DATE")+"','%m/%d/%Y %T')"+",'"+Reporter.getExecutionDuration()+"','"+user+"','"+InetAddress.getLocalHost().getHostName()+"','"+Environment.get("ROOT_CAUSE")+"','NA')");
    		conn=connectToGrafana(); 		
            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );  
            stmt.executeUpdate("insert into grafanadb."+Environment.get("GRAFANA_TABLE_NAME")+" Values ("+release+",'"+testPhase+"','"+applicationName+"','"+packageName+"','"+progressionCRName+"','"+browser+"','"+profile+"','"+Dictionary.get("TEST_NAME").replaceAll("'", "").replaceAll("\"", "")+"','"+env+"','"+Dictionary.get("QC_STATUS")+"',str_to_date('"+Environment.get("EXECUTION_DATE")+"','%m/%d/%Y %T')"+",'"+Reporter.getExecutionDuration()+"','"+user+"','"+InetAddress.getLocalHost().getHostName()+"','"+Environment.get("ROOT_CAUSE")+"','NA')");
            stmt.executeUpdate("commit");
            System.out.println("Grafana updated!");
            
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Exception in updating Grafana for the TC "+Dictionary.get("TEST_NAME")+". Exception: "+e.getLocalizedMessage());
        }finally{
        	try {
				conn.close();
				 System.out.println("Connection closed");
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
    	
    	return true;
    }
    
    public Connection connectToGrafana(){
    	try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		} 
    	Connection conn=null;;
		try {
			conn = DriverManager.getConnection(Environment.get("GRAFANA_DB_HOST"),Environment.get("GRAFANA_DB_USER"),Environment.get("GRAFANA_DB_PWD"));
			System.out.println("Connected to grafana");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return conn;
    }
    
    public Recordset getAddressFromExcel(String marketID) throws FilloException{
		String strQuery = null;
		Recordset recordset;

		Fillo fillo=new Fillo();
		com.codoid.products.fillo.Connection connection;
		connection = fillo.getConnection(System.getProperty("user.dir") + "\\data" + "\\" + Environment.get("CLASS_NAME") + ".xlsx");
		strQuery = "select * from Market where Market="+ marketID;

		try {
			recordset = connection.executeQuery(strQuery);
			recordset.next();
		} catch (Exception e) {
			Reporter.fnWriteToHtmlOutput("getProductNumber","Executed query: " + strQuery, "Failed","Fail");
			e.printStackTrace();
			return null;
		}

		connection.close();
		return recordset;
	}
    
    public String getAccessory(int number) throws FilloException{
		return Environment.get("RISE_ACCESSORY_"+String.valueOf(number));
	}
    
    public boolean insertAccountNumber(String accountNumber, String noOfLines, String accountStatus, String user, String store, String env, String accSubType) throws FilloException{

		Fillo fillo = new Fillo();
		com.codoid.products.fillo.Connection connection = fillo.getConnection(System.getProperty("user.dir")+"\\data\\RISE_DevicesAndSim.xlsx");
		ResultSet rsData=getDataFromDB("LOGICAL_DATE");
		String date=null;
		try {
			date=rsData.getString("LOGICAL_DATE").replaceAll("-", "").split(" ")[0];
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		env=Environment.get("ENV_CODE");
		
		try{ 
			Recordset recordSet = connection.executeQuery("select Priority from AccountNumbers");
			int priority=0;
			while(recordSet.next()){
				if(Integer.valueOf(recordSet.getField("Priority"))>priority)
					priority=Integer.valueOf(recordSet.getField("Priority"));
			}
			connection.executeUpdate("INSERT INTO AccountNumbers(BAN, NoOfLines, BANStatus, Priority, UserName, StoreID, Environment, AccountSubType, CreatedDate) VALUES("+accountNumber+", "+noOfLines+", '"+accountStatus+"', "+(priority+1)+", '"+user+"', "+store+", '"+env+"', '"+accSubType+"','"+date+"')");
		}catch(Exception e){
			if(e.getLocalizedMessage().equals("No records found"))
				connection.executeUpdate("INSERT INTO AccountNumbers(BAN, NoOfLines, BANStatus, Priority, UserName, StoreID, Environment, AccountSubType, CreatedDate) VALUES("+accountNumber+", "+noOfLines+", '"+accountStatus+"', 1"+", '"+user+"', "+store+", '"+env+"', '"+accSubType+"','"+date+"')");
			else if(e.getLocalizedMessage().equals("accountnumbers table is not found")){
				try{ connection.createTable("AccountNumbers", new String[]{"BAN","NoOfLines","BANStatus","Priority","UserName","StoreID","Environment","Usage", "AccountSubType", "CreatedDate"});
				connection.close();
				com.codoid.products.fillo.Connection connection1 = fillo.getConnection(System.getProperty("user.dir")+"\\data\\RISE_DevicesAndSim.xlsx");
				connection1.executeUpdate("INSERT INTO AccountNumbers(BAN, NoOfLines, BANStatus, Priority, UserName, StoreID, Environment, AccountSubType, CreatedDate) VALUES("+accountNumber+", "+noOfLines+", '"+accountStatus+"', 1"+", '"+user+"', "+store+", '"+env+"', '"+accSubType+"','"+date+"')");
				connection1.close();
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}else{
				e.printStackTrace();
				System.out.println("Failed to insert BAN "+accountNumber+" into DevicesAndSim sheet. Error: "+e.getLocalizedMessage());
			}
		}

		connection.close();

		return true;
	}
    
    public String getBANForOrderSearch(int lines, String banStatus, String env, String accSubType, String store) throws FilloException{
		String ban=null;
		int randNum;
		int line;
		boolean banFound=false;
		List<String> inEligiblePriority = new ArrayList<String>();
		String status=null;
		boolean validRandomNumber;

		Fillo fillo = new Fillo();
		com.codoid.products.fillo.Connection connection = fillo.getConnection(System.getProperty("user.dir")+"\\data\\RISE_DevicesAndSim.xlsx");
		HashMap<String, String> params = new HashMap<>();
		ResultSet rs=null;
		Recordset recordset;
		
		env=Environment.get("ENV_CODE");

		do{
			try {
				if(inEligiblePriority.isEmpty())
					recordset = connection.executeQuery("select Priority from AccountNumbers where NoOfLines = "+lines+" and BANStatus = '"+banStatus+"' and Environment = '"+env+"' and AccountSubType = '"+accSubType+"' and StoreID="+store);
				else{
					if(inEligiblePriority.size()==1)
						recordset = connection.executeQuery("select Priority from AccountNumbers where NoOfLines = "+lines+" and BANStatus = '"+banStatus+"' and Environment = '"+env+"' and Priority not in ("+inEligiblePriority.get(0)+") and AccountSubType = '"+accSubType+"' and StoreID="+store);
					else{
						String[] inValidPriorities = new String[inEligiblePriority.size()];
						String query="select Priority from AccountNumbers where NoOfLines = "+lines+" and BANStatus = '"+banStatus+"' and Environment = '"+env+"' and Priority not in (";
						for(int j=0;j<inEligiblePriority.size();j++){
							if(j==0){
								inValidPriorities[j]=inEligiblePriority.get(j);
								query=query+inValidPriorities[j];
							}
							else{
								inValidPriorities[j]=","+inEligiblePriority.get(j);
								query=query+inValidPriorities[j];
							}
						}
						recordset = connection.executeQuery(query+") and AccountSubType = '"+accSubType+"' and StoreID="+store);
					}
				}      
				List<Integer> priorities = new ArrayList<Integer>();
				while(recordset.next())
					priorities.add(Integer.valueOf(recordset.getField("Priority")));
				if(priorities.size()>1){
					do{ validRandomNumber=false;
					randNum=priorities.get(0)+(int)(Math.random() * (priorities.get(priorities.size()-1) - priorities.get(0)) + 1);
					for(int k=0;k<priorities.size();k++){
						if(randNum==priorities.get(k))
							validRandomNumber=true;
					}
					}while(validRandomNumber==false);
					System.out.println("Random number: "+randNum);
					recordset = connection.executeQuery("select BAN, Priority, BANStatus from AccountNumbers where Priority="+randNum+" and NoOfLines = "+lines+" and BANStatus='"+banStatus+"'");
				}else
					recordset = connection.executeQuery("select BAN, Priority, BANStatus from AccountNumbers where Priority="+priorities.get(0)+" and NoOfLines = "+lines+" and BANStatus='"+banStatus+"'");
				recordset.next();
				params.put("{BAN_ID}", recordset.getField("BAN"));
				if(recordset.getField("BANStatus").equals("A") || recordset.getField("BANStatus").equals("R"))
					status="O";
				if(recordset.getField("BANStatus").equals("C") || recordset.getField("BANStatus").equals("NR") || recordset.getField("BANStatus").equals("N"))
					status="N";
				rs=getDataFromDBWithParameters("BAN_VALIDATION",params);
				if(rs.getString("BAN_STATUS").trim().equals(status)){
					rs=getDataFromDBWithParameters("SUBSCRIBER_VALIDATION",params);
					line=0;
					do{
						if(rs.getString("SERIAL_TYPE").trim().matches("S")){
							if(status.matches("O")){
								if(rs.getString("PTN_STATUS").trim().matches("A"))
									line++;
							}else if(status.matches("N")){
								if(rs.getString("PTN_STATUS").trim().matches("C"))
									line++;
							}
						}
					}while(rs.next());
					
					if(status.matches("O") && line==0){
						rs=getDataFromDBWithParameters("SUBSCRIBER_VALIDATION",params);
						do{
							if(rs.getString("SERIAL_TYPE").trim().matches("I") && rs.getString("PTN_STATUS").trim().matches("A"))
								line++;
						}while(rs.next());
					}
					
					if(status.matches("N") && line==0){
						rs=getDataFromDBWithParameters("SUBSCRIBER_VALIDATION",params);
						do{
							if(rs.getString("SERIAL_TYPE").trim().matches("I") && rs.getString("PTN_STATUS").trim().matches("C"))
								line++;
						}while(rs.next());
					}
					
					if(line>=lines){
						banFound=true;
						ban = recordset.getField("BAN");
					}else{
						inEligiblePriority.add(recordset.getField("Priority"));
						connection.executeUpdate("Update AccountNumbers Set NoOfLines = "+line+" where BAN = "+recordset.getField("BAN"));
					}
				}else
					connection.executeUpdate("Update AccountNumbers Set BANStatus = '"+rs.getString("BAN_STATUS").trim()+"' where BAN = "+recordset.getField("BAN"));
			}catch(Exception e){
				if(e.getLocalizedMessage().equals("No records found"))
					return null;
				if(e.getLocalizedMessage().equals("accountnumbers table is not found"))
					break;
				else
					e.printStackTrace();
			}
		}while(banFound==false);
		connection.close();
		System.out.println("BAN: "+ban);
		return ban;
	}
    
    public boolean updateBANUsage(String ban) throws FilloException{
		Fillo fillo = new Fillo();
		com.codoid.products.fillo.Connection connection = fillo.getConnection(System.getProperty("user.dir")+"\\data\\RISE_DevicesAndSim.xlsx");
				
		int usage;
		
		try{ Recordset recordset = connection.executeQuery("select Usage from AccountNumbers where BAN = "+ban);
		recordset.next();
		usage = Integer.valueOf(recordset.getField("Usage"));
		
		connection.executeUpdate("Update AccountNumbers Set Usage = "+(usage+1)+" where BAN = "+ban);
		}catch(Exception e){
			if(e.getLocalizedMessage().contains("For input string: "))
				connection.executeUpdate("Update AccountNumbers Set Usage = 1 where BAN = "+ban);
			else
			e.printStackTrace();
		}
		
		connection.close();
		return true;
		
	}
	
	public boolean updateBAN(String ban, String status, int lines) throws FilloException{
		Fillo fillo = new Fillo();
		com.codoid.products.fillo.Connection connection = fillo.getConnection(System.getProperty("user.dir")+"\\data\\RISE_DevicesAndSim.xlsx");
				
		try{ 
		connection.executeUpdate("Update AccountNumbers Set BANStatus = '"+status+"' where BAN = "+ban);
		connection.executeUpdate("Update AccountNumbers Set NoOfLines = "+lines+" where BAN = "+ban);
		}catch(Exception e){
			e.printStackTrace();
		}
		connection.close();
		
		return true;
		
	}
	
	public String getBANForCustomerSearch(int lines, String banStatus, String env, String accSubType) throws FilloException{
		String ban=null;
		int randNum;
		int line;
		boolean banFound=false;
		List<String> inEligiblePriority = new ArrayList<String>();
		String status=null;
		boolean validRandomNumber;

		Fillo fillo = new Fillo();
		com.codoid.products.fillo.Connection connection = fillo.getConnection(System.getProperty("user.dir")+"\\data\\RISE_DevicesAndSim.xlsx");
		HashMap<String, String> params = new HashMap<>();
		ResultSet rs=null;
		Recordset recordset;
		
		env=Environment.get("ENV_CODE");
				
		do{
			try {
				if(inEligiblePriority.isEmpty())
					recordset = connection.executeQuery("select Priority,CreatedDate from AccountNumbers where NoOfLines >= "+lines+" and BANStatus = '"+banStatus+"' and Environment = '"+env+"' and AccountSubType = '"+accSubType+"'"); //and CreatedDate <'+"'"+date
				else{
					if(inEligiblePriority.size()==1)
						recordset = connection.executeQuery("select Priority from AccountNumbers where NoOfLines >= "+lines+" and BANStatus = '"+banStatus+"' and Environment = '"+env+"' and Priority not in ("+inEligiblePriority.get(0)+") and AccountSubType = '"+accSubType+"'");
					else{
						String[] inValidPriorities = new String[inEligiblePriority.size()];
						String query="select Priority from AccountNumbers where NoOfLines >= "+lines+" and BANStatus = '"+banStatus+"' and Environment = '"+env+"' and Priority not in (";
						for(int j=0;j<inEligiblePriority.size();j++){
							if(j==0){
								inValidPriorities[j]=inEligiblePriority.get(j);
								query=query+inValidPriorities[j];
							}
							else{
								inValidPriorities[j]=","+inEligiblePriority.get(j);
								query=query+inValidPriorities[j];
							}
						}
						recordset = connection.executeQuery(query+") and AccountSubType = '"+accSubType+"'");
					}
				}      
				List<Integer> priorities = new ArrayList<Integer>();
				while(recordset.next()){
					priorities.add(Integer.valueOf(recordset.getField("Priority")));
					System.out.println("Priority added: "+recordset.getField("Priority"));
					
				}
				if(priorities.size()>1){
					do{ validRandomNumber=false;
					randNum=priorities.get(0)+(int)(Math.random() * (priorities.get(priorities.size()-1) - priorities.get(0)) + 1);
					for(int k=0;k<priorities.size();k++){
						if(randNum==priorities.get(k))
							validRandomNumber=true;
					}
					}while(validRandomNumber==false);
					System.out.println("Random number: "+randNum);
					recordset = connection.executeQuery("select BAN, Priority, BANStatus from AccountNumbers where Priority="+randNum+" and NoOfLines >= "+lines+" and BANStatus='"+banStatus+"'");
				}else
					recordset = connection.executeQuery("select BAN, Priority, BANStatus from AccountNumbers where Priority="+priorities.get(0)+" and NoOfLines >= "+lines+" and BANStatus='"+banStatus+"'");
				recordset.next();
				params.put("{BAN_ID}", recordset.getField("BAN"));
				if(recordset.getField("BANStatus").equals("A") || recordset.getField("BANStatus").equals("R"))
					status="O";
				if(recordset.getField("BANStatus").equals("C") || recordset.getField("BANStatus").equals("NR") || recordset.getField("BANStatus").equals("N"))
					status="N";
				rs=getDataFromDBWithParameters("BAN_VALIDATION",params);
				if(rs.getString("BAN_STATUS").trim().equals(status)){
					rs=getDataFromDBWithParameters("SUBSCRIBER_VALIDATION",params);
					line=0;
					do{
						if(rs.getString("SERIAL_TYPE").trim().matches("S")){
							if(status.matches("O")){
								if(rs.getString("PTN_STATUS").trim().matches("A"))
									line++;
							}else if(status.matches("N")){
								if(rs.getString("PTN_STATUS").trim().matches("C"))
									line++;
							}
						}
					}while(rs.next());
					
					if(status.matches("O") && line==0){
						rs=getDataFromDBWithParameters("SUBSCRIBER_VALIDATION",params);
						do{
							if(rs.getString("SERIAL_TYPE").trim().matches("I") && rs.getString("PTN_STATUS").trim().matches("A"))
								line++;
						}while(rs.next());
					}
					
					if(status.matches("N") && line==0){
						rs=getDataFromDBWithParameters("SUBSCRIBER_VALIDATION",params);
						do{
							if(rs.getString("SERIAL_TYPE").trim().matches("I") && rs.getString("PTN_STATUS").trim().matches("C"))
								line++;
						}while(rs.next());
					}
					
					if(line>=lines){
						banFound=true;
						ban = recordset.getField("BAN");
					}else{
						inEligiblePriority.add(recordset.getField("Priority"));
						connection.executeUpdate("Update AccountNumbers Set NoOfLines = "+line+" where BAN = "+recordset.getField("BAN"));
					}
				}else
					connection.executeUpdate("Update AccountNumbers Set BANStatus = '"+rs.getString("BAN_STATUS").trim()+"' where BAN = "+recordset.getField("BAN"));
			}catch(Exception e){
				if(e.getLocalizedMessage().equals("No records found"))
					return null;
				if(e.getLocalizedMessage().equals("accountnumbers table is not found"))
					break;
				else
					e.printStackTrace();
			}
		}while(banFound==false);
		connection.close();
		System.out.println("BAN: "+ban);
		return ban;
	}
	
	public boolean setOnlyIMEINumberAsUsed(String number) throws FilloException{

		String dataSheetsPath = System.getProperty("user.dir") + "/data";
		String strQuery = null;
		
		Fillo fillo=new Fillo();
		com.codoid.products.fillo.Connection connection;
		connection = fillo.getConnection(dataSheetsPath + "/RISE_DevicesAndSim.xlsx");

		strQuery="Update IMEI Set STATUS='U' where NUMBER='" + number + "' and Environment='"+Environment.get("ENV_CODE")+"'";
		
		try {
			connection.executeUpdate(strQuery);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput("setProductNumberAsUsed","Executed query: " + strQuery, "Failed","Fail");
			return false;
		}
		connection.close();
		return true;
	}
	
	public String getOnlyDeviceNumber(String product, String type, String storeID, String user) throws FilloException{

		String dataSheetsPath = System.getProperty("user.dir") + "\\data";
		String strQuery = null;
		String number = null;
		
		if(type.isEmpty())
			type="GSM";

		Fillo fillo=new Fillo();
		com.codoid.products.fillo.Connection connection;
		connection = fillo.getConnection(dataSheetsPath + "\\RISE_DevicesAndSim.xlsx");
		if(product.equalsIgnoreCase("DEVICE")){ 
			strQuery="Select NUMBER from IMEI where TYPE='" + type + "' and STORE_ID='" + storeID + "'and UName='"+ user +"'and STATUS='A' and Environment='"+Environment.get("ENV_CODE")+"'";
		}else{
			Reporter.fnWriteToHtmlOutput("getProductNumber","Product should be provided (Device or SIM)", "Not provided","Fail");
			return null;
		}

		try {
			Recordset recordset=connection.executeQuery(strQuery);
			recordset.next();
			number = recordset.getField("NUMBER");
		} catch (Exception e) {
			Environment.put("FAILURE_ERROR","No data return for query");
			Reporter.fnWriteToHtmlOutput("getProductNumber","Executed query: " + strQuery, "Failed","Fail");
			return null;
		}
		System.out.println(number);
		connection.close();
		return number;
	}
	
	public String getItemPromotion(String type, int priority) throws FilloException{
		return Environment.get("RISE_ITEM_PROMOTION_"+type+"_"+String.valueOf(priority));

	}

	public String getOrderPromotion(String type, int priority) throws FilloException{
		return Environment.get("RISE_ORDER_PROMOTION_"+type+"_"+String.valueOf(priority));
	}
	
	public String getOnlySimNumber(String product, String storeID, String user) throws FilloException{
		String dataSheetsPath = System.getProperty("user.dir") + "\\data";
		String strQuery = null;
		String sim = null;

		Fillo fillo=new Fillo();
		com.codoid.products.fillo.Connection connection;
		connection = fillo.getConnection(dataSheetsPath + "\\RISE_DevicesAndSim.xlsx");
		if(product.equalsIgnoreCase("SIM")){
			strQuery="Select NUMBER from SIM where STORE_ID='" + storeID + "' and USER_NAME='"+user+"' and STATUS='A' and Environment='"+Environment.get("ENV_CODE")+"'";
		}else{
			Reporter.fnWriteToHtmlOutput("getProductNumber","Product should be provided (Device or SIM)", "Not provided","Fail");
			return null;
		}

		try {
			Recordset recordset=connection.executeQuery(strQuery);
			recordset.next();
			sim = recordset.getField("NUMBER");
		} catch (Exception e) {
			e.printStackTrace();
			Environment.put("FAILURE_ERROR","No data return for query");
			Reporter.fnWriteToHtmlOutput("getProductNumber","Executed query: " + strQuery, "Failed","Fail");
			return null;
		}
		System.out.println(sim);
		connection.close();
		return sim;
	}

	//*****************************************************************************************
    //* Name            : updateBatchLogicalDateToBCDMinusX
    //* Description     : Update Batch Logical Date To BCD Minus X days
    //* Author          : Zachi Gahari
    //* Updated Date    : Jul 26, 2020
    //* Input Params    : 
    //* Return Values   : true/false
    //*****************************************************************************************
	public boolean updateBatchLogicalDateToBCDMinusX(String minus){
		String query = null;
		Connection Conn=null;
				
    	try{
    		query = getQuery("MOVE_BATCH_LD_MONTH_AHEAD-X").replace("<MINUS>", minus);
     		Conn=ConnecttoDB(Environment.get("OPR_DB_HOST"), Environment.get("OPR_DB_USER"), Environment.get("OPR_DB_PWD"));
	    	System.out.println("About to execute query" + query);
	    	Statement stmt=Conn.createStatement();
	    	stmt.executeQuery(query);
	    	stmt.executeQuery("commit");
	    	Reporter.fnWriteToHtmlOutput("Execute update query", "Execute query: "+query,"Query executed", "VALIDATION PASS");
	    }
        catch(Exception e){
            e.printStackTrace();
            Reporter.fnWriteToHtmlOutput("Execute update query", "Execute query: "+query,"Error in executing query: " + e.getLocalizedMessage(), "VALIDATION FAIL");
            return false;
        }finally{
        	closeConnection(Conn);
        }
    	
    	return true;
	}

	//*****************************************************************************************
    //* Name            : setF1ForCSSNDMSGBYFCT
    //* Description     : Set F1 As factor id in Operation details
    //* Author          : Zachi Gahari
    //* Updated Date    : Jul 28, 2020
    //* Input Params    : 
    //* Return Values   : true/false
    //*****************************************************************************************
	public boolean setF1ForCSSNDMSGBYFCT(){
		String query = null;
		Connection Conn=null;
				
    	try{
    		query = "update op_app_data set DATA='F1' where table_name ='CSSNDMSGBYFCT' and job_rec='BYREQ' and field_seq_num=4";
     		Conn=ConnecttoDB(Environment.get("OPR_DB_HOST"), Environment.get("OPR_DB_USER"), Environment.get("OPR_DB_PWD"));
	    	System.out.println("About to execute query" + query);
	    	Statement stmt=Conn.createStatement();
	    	stmt.executeQuery(query);
	    	stmt.executeQuery("commit");
	    	Reporter.fnWriteToHtmlOutput("Execute update query", "Execute query: "+query,"Query executed", "VALIDATION PASS");
	    }
        catch(Exception e){
            e.printStackTrace();
            Reporter.fnWriteToHtmlOutput("Execute update query", "Execute query: "+query,"Error in executing query: " + e.getLocalizedMessage(), "VALIDATION FAIL");
            return false;
        }finally{
        	closeConnection(Conn);
        }
    	
    	return true;
	}
	
	public Recordset getCardInfo(String cardID) throws FilloException{
		String strQuery = null;
  		Recordset recordset;
		
		Fillo fillo=new Fillo();
    	com.codoid.products.fillo.Connection connection;
		connection = fillo.getConnection(System.getProperty("user.dir")+"\\environments\\Environments.xlsx");
		strQuery = "select * from CARD_INFO where ID='"+cardID+"'";
		System.out.println("Query: "+strQuery);

		try {
			recordset = connection.executeQuery(strQuery);
			recordset.next();
		} catch (Exception e) {
			Reporter.fnWriteToHtmlOutput("getDataFromExcel","Executed query: " + strQuery, "Error: "+e.getLocalizedMessage(),"Fail");
			e.printStackTrace();
			return null;
		}

    	connection.close();
		return recordset;
	}

	//*****************************************************************************************
    //* Name            : executeUpdateQueryAndCommit
    //* Description     : Update table and commit
    //* Author          : Zachi Gahari
    //* Updated Date    : Aug 2, 2020
    //* Input Params    : Query, Host, User, Password
    //* Return Values   : true/false
    //*****************************************************************************************
	public boolean executeUpdateQueryAndCommit(String query, String host, String user, String password){
		Connection Conn=null;
				
    	try{
    		Conn=ConnecttoDB(host, user, password);
	    	System.out.println("About to execute query" + query);
	    	Statement stmt=Conn.createStatement();
	    	stmt.executeQuery(query);
	    	stmt.executeQuery("commit");
	    	Reporter.fnWriteToHtmlOutput("Execute update query", "Execute query: "+query,"Query executed", "VALIDATION PASS");
	    }
        catch(Exception e){
            e.printStackTrace();
            Reporter.fnWriteToHtmlOutput("Execute update query", "Execute query: "+query,"Error in executing query: " + e.getLocalizedMessage(), "VALIDATION FAIL");
            return false;
        }finally{
        	closeConnection(Conn);
        }
    	
    	return true;
	}
    
	//*****************************************************************************************
    //* Name            : deleteOrderInProgress
    //* Description     : Check if there are pending order for account and delete them
    //* Author          : Zachi Gahari
    //* Updated Date    : Feb 16, 2021
    //* Input Params    : Account number
    //* Return Values   : true/false
    //*****************************************************************************************
	public boolean deleteOrderInProgress(String ban){
		String query = "select * from pos_order where ban=" + ban +" and ORDER_STATUS in ('R', 'N')"; 
		String orderId = null;

		try{
			Statement stmt=Conn.createStatement();
	    	System.out.println("Executing query: " + query);
	    	ResultSet rsData = ExecuteQuery(query, Conn);
	    	if (!rsData.isBeforeFirst()) {    
		    	System.out.println("No order in progress for ban: " + ban);
	            return true;
	    	} 
	    	
	    	while(rsData.next()){
	    		orderId = rsData.getString("ORDER_OID");
	    		String query1 = "delete from pos_order where order_oid=" + orderId;
	    		String query2 = "delete from pos_order where order_oid=" + orderId;
	    		String query3 = "delete from POS_ORDER_SUBLINE where order_oid = " + orderId;
	    		String query4 = "delete from POS_ORDER_LINE where order_oid=" + orderId;
	    		String query5 = "delete from POS_ORDER_HISTORY where order_oid=" + orderId;
	    		String query6 = "delete from pos_feature where order_oid =" + orderId;
	    		String query7 = "delete from pos_soc where order_oid =" + orderId;
	    		String query8 = "update subscriber set ORDER_COMMITED ='N' where customer_id=" + ban + " and ORDER_COMMITED ='C'"; 
	    		
	    		Conn.setAutoCommit(false);
	    		stmt.addBatch(query1);
	    		stmt.addBatch(query2);
	    		stmt.addBatch(query3);
	    		stmt.addBatch(query4);
	    		stmt.addBatch(query5);
	    		stmt.addBatch(query6);
	    		stmt.addBatch(query7);
	    		stmt.addBatch(query8);
	    		
	    		stmt.executeBatch();
	    	    Conn.commit();
	    	}
	    	System.out.println("Deleted order in progress for ban: " + ban);
	    }
        catch(Exception e){
            e.printStackTrace();
            Reporter.fnWriteToHtmlOutput("Delete Order In Progress", "Success", "Fail", "Fail");
            return false;
        }
    	
    	return true;
	}
	
}

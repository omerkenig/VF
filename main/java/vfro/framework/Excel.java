package vfro.framework;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.opencsv.CSVWriter;

public class Excel {
	//Instance Variables
//	private
	Fillo fillo;
//	com.codoid.products.fillo.
	Connection connection;
	Recordset recordset;
	private HashMap<String, String> Environment;

	//Constructor
	public Excel(){
		fillo = new Fillo();
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
		
		connection = fillo.getConnection(fileName);
		strQuery = "select * from " + sheetName + " where " + condition;

		try {
			recordset = connection.executeQuery(strQuery);
			recordset.next();
		} catch (Exception e) {
			if(!e.getMessage().equals("No records found") && !e.getMessage().contains("table is not found"))
				e.printStackTrace();
//			else
//				System.out.println("No records found for query: " + strQuery);
			recordset = null;
		} finally {
			connection.close();
		}
		
		return recordset;
    }
    
    //*****************************************************************************************
    //* Name            : updateDataInExcel
    //* Description     : Update Data From Excel
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
		strQuery = "Update " + sheetName + " Set " + update + " where " + condition;

		try {
			connection.executeUpdate(strQuery);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Fail to update data in Excel");
			connection.close();
			return false;
		}

    	connection.close();
		return true;

    }
  //WritingToCSV File - Created by Madhuri
	public void WritingToCSV(List list, String fileName){
	  
	  // "/dvmuat/fs_env/work/"+ Environment.get("ENS_USER")+"/var/mtr/projs/csm/work"
	   try {  
		//String fileName=format.format(new Date());
		File csvFile = new File(System.getProperty("user.dir")+"/JobFiles"+"/"+fileName);
		CSVWriter writer =  new CSVWriter(new FileWriter(csvFile), '|',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
		//writer.writeNext(list);
		writer.writeAll(list);			
		writer.flush();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	     
	}

}

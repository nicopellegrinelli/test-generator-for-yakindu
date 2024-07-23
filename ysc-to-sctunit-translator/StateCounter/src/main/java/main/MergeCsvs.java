package main;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVReader;

public class MergeCsvs {

	public static void main(String[] args) throws IOException {
		String coverageCsv = args[0];
		String numStateCsv = args[1];
		String mergedCsv = args[2];
		
		FileWriter writer = new FileWriter(mergedCsv, false);
		writer.append("Directory,Statechart,NumStates,AvgDepth,MaxDepth,StandardEvosuiteCoverage,"
				+ "StandardSCTUnitCoverate,StandardSCTUnitStatus,SimplifiedEvosuiteCoverage,"
				+ "SimplifiedSCTUnitCoverage,SimplifiedSCTUnitStatus"
				+ "\n");
		
		FileReader coverageFileReader = new FileReader(coverageCsv); 
        CSVReader coverageCsvReader = new CSVReader(coverageFileReader); 
        
        coverageCsvReader.readNext();
        String[] nextRecord = coverageCsvReader.readNext();
        while (nextRecord != null) { 
        	String mergedRow = "";
        	int column = 0;
        	String dir = "";
        	String statechart = "";
            for (String cell : nextRecord) {
            	if (column == 0) dir = cell;
            	if (column == 1) statechart = cell;
            	if (column == 2) {
            		FileReader numStateFileReader = new FileReader(numStateCsv); 
                    CSVReader numStateCsvReader = new CSVReader(numStateFileReader); 
                    numStateCsvReader.readNext();
                    String[] nextInnerRecord = numStateCsvReader.readNext();
                    boolean found = false;
                    while (nextInnerRecord != null && !found) {
                    	int innerColumn = 0;
                    	String innerDir = "";
                    	String innerStatechart = "";
                    	for (String innerCell : nextInnerRecord) {
                    		if (innerColumn == 0) innerDir = innerCell;
                        	if (innerColumn == 1) innerStatechart = innerCell;
                    		if (innerColumn >= 2 && innerDir.equals(dir) && innerStatechart.equals(statechart)) {
                    			mergedRow += innerCell + ",";
                    			found = true;
                    		}
                    		innerColumn++;
                    	}
                    	if (found)
                    		break;
                    	nextInnerRecord = numStateCsvReader.readNext();
                    }
                    if (!found)
                    	mergedRow += "ERROR,ERROR,ERROR,";
                    numStateCsvReader.close();
            	}
            	mergedRow += cell + ",";
            	column++;
            }
            mergedRow = mergedRow.substring(0, mergedRow.length()-1) + "\n";
            writer.append(mergedRow);
            nextRecord = coverageCsvReader.readNext();
        }
        System.out.println("Finished");
        coverageCsvReader.close();
		writer.close();
	}

}

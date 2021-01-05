package org.neostorm.etl.util;

/*
   Copyright 2018 Wall Street Fin Tech

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. 
   
    
    */

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.neostorm.data_structures.Pair;
import org.neostorm.etl.ETLHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StateUtils {


    protected String trim ( String data )
    {
        if ( data != null ) {
            data = data.trim();
        }
        return data;
    }

    protected void clear ( String[] values ){
        if ( values != null ) {
            for ( int i=0;i<values.length;i ++ ) {
                values[i] = null;
            }
        }
    }

    protected String getParty ( String candidate )
    {
        String party = null;
        if (candidate != null ) {
            //.*\(([A-Za-z]*)\)$

            Pattern pattern = Pattern.compile(".*\\(([A-Za-z]*)\\)$");
            Matcher matcher = pattern.matcher(candidate);
            if (matcher.find())
            {
                party = matcher.group(1);
            }
        }
        return party;
    }

    protected void load_race ( String date, String state, Sheet sheet, RowFieldHandler handler )
    {
        if ( handler != null ) {
            //    public void row ( String election_date, String state, String county, String office, String party, String candidate, String votes ) ;
            String office = sheet.getSheetName();
            Row countyRow = sheet.getRow(0);

            for ( int r=1; r<=sheet.getLastRowNum(); r++ ) {
                Row dataRow = sheet.getRow(r);
                for (int c = 1; c < countyRow.getLastCellNum(); c++) {
                    Cell countyCell = dataRow.getCell(0);
                    Cell cellCandidate = countyRow.getCell(c);
                    Cell votesCell = dataRow.getCell(c);
                    if (votesCell != null && countyCell != null && cellCandidate != null ) {
                        String candidate = cellCandidate.getStringCellValue();
                        String county = countyCell.getStringCellValue();
                        Double vote   = votesCell.getNumericCellValue();
                        String party = getParty(candidate);

                        if (handler != null && vote != null ) {
                            handler.row( date, state, county, office, party, candidate, vote.toString(), null );
                        }
                    }
                }
            }


        }
    }


    protected void load_sheet_rows ( int header_row, int start_row, Sheet sheet, RowHandler handler )
    {
        if ( handler != null ) {
            //    public void row ( String election_date, String state, String county, String office, String party, String candidate, String votes ) ;
            String office = sheet.getSheetName();
            Row headerRow = sheet.getRow(header_row);

            String [] header_values = new String[headerRow.getLastCellNum()];

            for (int c = 0; c < headerRow.getLastCellNum(); c++) {
                Cell header_cell = headerRow.getCell(c);
                if ( header_cell != null ) {
                    if (header_cell.getCellType() == CellType.NUMERIC) {
                        Double v = header_cell.getNumericCellValue();
                        if (v != null) {
                            header_values[c] = v.toString();
                        }
                    } else if (header_cell.getCellType() == CellType.STRING) {
                        String v = header_cell.getStringCellValue();
                        if (v != null) {
                            header_values[c] = v.toString();
                        }
                    }
                }
            }

            String [] data_values = new String[headerRow.getLastCellNum()];
            for ( int r=start_row; r<=sheet.getLastRowNum(); r++ ) {
                Row dataRow = sheet.getRow(r);
                for (int c = 0; c < headerRow.getLastCellNum(); c++) {
                    Cell dataCell = dataRow.getCell(c);
                    if ( dataCell != null ) {
                        if (dataCell.getCellType() == CellType.NUMERIC) {
                            Double d = dataCell.getNumericCellValue();
                            if (d != null) {
                                data_values[c] = d.toString();
                            }
                        } else if (dataCell.getCellType() == CellType.STRING) {
                            data_values[c] = dataCell.getStringCellValue();
                        }
                    }
                }

                if ( handler != null ) {
                    handler.header( header_values, data_values );
                }
            }
        }
    }



    protected String remove_comma (String val )
    {
        if ( val != null )
            val = val.replace(",", "");
        return val;
    }

    public void parse_2020_xml ( RowFieldHandler row_handler )
    {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

//Build Document
            Document document = builder.parse(new File("/Users/kevintboyle/elections/StatesData/Georgia/2020/detail.xml"));

//Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

//Here comes the root node
            Element root = document.getDocumentElement();
            System.out.println(root.getNodeName());
/*
      <Choice key="1" text="Donald J. Trump (I) (Rep)" party="NP" totalVotes="2461837">
            <VoteType name="Election Day Votes" votes="587697">
                <County name="Appling" votes="1753" />
                <County name="Atkinson" votes="716" />
                <County name="Bacon" votes="431" />
                <County name="Baker" votes="291" />
                <County name="Baldwin" votes="1873" />

 */


//Get all employees
            NodeList contestList = document.getElementsByTagName("Contest");
            for (int temp = 0; temp < contestList.getLength(); temp++) {
                Node contest = contestList.item(temp);
                if (contest.getNodeType() == Node.ELEMENT_NODE) {
                    //Print each employee's detail
                    Element eElement = (Element) contest;
                    NodeList choiceList = eElement.getElementsByTagName("Choice");
                    for (int c = 0; c < choiceList.getLength(); c++) {
                        Node choiceNode = choiceList.item(c);

                        if (choiceNode.getNodeType() == Node.ELEMENT_NODE) {
                            NodeList voteTypeList = ((Element)choiceNode).getElementsByTagName("VoteType");
                            for ( int county_idx=0; county_idx<voteTypeList.getLength(); county_idx++ ) {
                                Node voteTypeNode = voteTypeList.item(county_idx);

                                NodeList CountyList = ((Element)voteTypeNode).getElementsByTagName("County");
                                for (int n = 0; n < CountyList.getLength(); n++) {
                                    Node countyNode = CountyList.item(n);
                                    if (countyNode.getNodeType() == Node.ELEMENT_NODE) {

                                        String race = ((Element) contest).getAttribute("text");

                                        String candidate = ((Element) choiceNode).getAttribute("text");
                                        String party  = ((Element) choiceNode).getAttribute("party");
//                                        String total_votes =  ((Element) choiceNode).getAttribute("totalVotes");

                                        String vote_type_name =  ((Element) voteTypeNode).getAttribute("name");
//                                        String vote_type_total_vote =  ((Element) voteTypeNode).getAttribute("votes");

                                        String county = ((Element) countyNode).getAttribute("name");
                                        String votes =  ((Element) countyNode).getAttribute("votes");

                                        if ( row_handler != null ) {
                                            row_handler.row("20201103", "Georgia", county, race, party, candidate, votes, vote_type_name );
                                        }
                                    }
                                    //name="Brantley" votes="1903"
                                }
                            }
                        }
                    }
                }
            }
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

    protected String standardized_party ( String party )
    {
        if ( party != null ) {
            if ( party.toUpperCase().equals("REPUBLICAN") || party.toUpperCase().equals("REP")) {
                return "REPUBLICAN";
            }
            else if ( party.toUpperCase().equals("DEMOCRATIC") || party.toUpperCase().equals("DEM")) {
                return "DEMOCRATIC";
            }
        }
        return party;
    }

    protected String standardized_ofice ( String office )
    {
        String standard = null;
        if ( office != null ) {
            if ( office.toUpperCase().contains("PRESIDENT")) {
                standard= "PRESIDENT";
            }
            else if ( office.toUpperCase().contains("US") && office.toUpperCase().contains("DISTRICT") && !office.toUpperCase().contains("STATE") && !office.toUpperCase().contains("ATTORNEY")) {
                standard = "HOUSE";
            }
            else if ( office.toUpperCase().contains("REPRESENTATIVE") && office.toUpperCase().contains("CONGRESS") && !office.toUpperCase().contains("STATE")) {
                standard = "HOUSE";
            }
            else if ( office.toUpperCase().contains("US") && office.toUpperCase().contains("REP") && office.toUpperCase().contains("DISTRICT") && !office.toUpperCase().contains("STATE")  && !office.toUpperCase().contains("ATTORNEY")) {
                standard = "HOUSE";
            }


        }

        return standard;
    }

    protected void load_election_data (String path, String sheet_name, int start_row, ArrayList<Pair<Integer, String>> columns, ETLHandler handler )
    {
        try {
            FileInputStream excelFile = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheet(sheet_name);

            if ( sheet != null ) {
                String[] values = new String[columns.size()];
                for (int r = start_row; r < sheet.getPhysicalNumberOfRows(); r++) {
                    clear(values);
                    for (int i = 0; i < columns.size(); i++) {
                        Pair<Integer, String> col_node = columns.get(i);
                        Row row = sheet.getRow(r);
                        if (row != null) {
                            Cell cell = row.getCell(col_node.getKey());
                            if (cell != null) {
                                if (cell.getCellTypeEnum() == CellType.STRING) {
                                    values[i] = cell.getStringCellValue();
                                } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                                    Double d = cell.getNumericCellValue();
                                    if ( d != null ) {
                                        values[i] = d.toString();
                                    }
                                }

                            }
                        }
                    }
                    if ( handler != null ) {
                        handler.handler(columns, values);
                    }
                }
            }
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }
}

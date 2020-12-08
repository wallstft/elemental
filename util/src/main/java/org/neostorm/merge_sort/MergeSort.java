package org.neostorm.merge_sort;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.neostorm.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MergeSort {

    static private Logger logger = LogManager.getLogger(MergeSort.class.getName());

    private String readNonEmptyLine ( BufferedReader br ) throws Exception {
        String l = null;
        while ( (l=br.readLine()) != null ) {
            if ( l.replace(" ", "" ).length() == 0){
                continue;
            }
            break;
        }
        return l;
    }


    public void merge_sort (File filename_1, File filename_2, MergeSortController handler ){
        if ( filename_1 != null && filename_1.exists() && filename_2 != null && filename_2.exists() ) {
            BufferedReader br1 = null;
            BufferedReader br2 = null;
            FileReader fr1 = null;
            FileReader fr2 = null;
            try {

                fr1 = new FileReader ( filename_1 );
                fr2 = new FileReader ( filename_2 );
                br1 = new BufferedReader( fr1 );
                br2 = new BufferedReader( fr2 );

                String l1 = readNonEmptyLine( br1 );
                String l2 = readNonEmptyLine( br2 );

                if (l1 != null && l2 != null) {
                    boolean has_data = true;
                    while (has_data) {
                        if (l1 != null && l2 != null) {
                            int compare_stat = handler.compare( l1, l2 );
                            if ( compare_stat == 0 ) {
                                handler.merge(l1, l2);
                                l1 = readNonEmptyLine( br1 );
                                l2 = readNonEmptyLine( br2 );
                            }
                            else if ( compare_stat < 0 ) {
                                handler.missing_in_1(l1);
                                l1 = readNonEmptyLine(br1);

                            }
                            else if ( compare_stat > 0 ) {
                                handler.missing_in_2(l2);
                                l2 = readNonEmptyLine(br2);
                            }
                        }
                    }
                    if ( l1 != null ) {
                        handler.missing_in_1(l1);
                        while ( (l1=readNonEmptyLine(br1)) != null ) {
                            handler.missing_in_1(l1);
                        }
                    }
                    if ( l2 != null ) {
                        handler.missing_in_2(l2);
                        while ( (l2=readNonEmptyLine(br2))!= null ) {
                            handler.missing_in_2(l2);
                        }
                    }
                }
            } catch (Exception x) {
                logger.error(x);
            }
            finally {
                try {
                    if (br1 != null) {
                        br1.close();
                        br1 = null;
                    }
                    if (br2 != null) {
                        br2.close();
                        br2 = null;
                    }
                }
                catch ( Exception x ) {
                    logger.error(x);
                }
            }
        }
    }

}

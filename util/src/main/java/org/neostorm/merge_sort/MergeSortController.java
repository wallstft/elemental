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

import java.util.Comparator;

public abstract class MergeSortController {
    Comparator<String> sort_comparator_1 = null;
    Comparator<String> sort_comparator_2 = null;

    public MergeSortController ( Comparator<String> sc1, Comparator<String> sc2) {
        sort_comparator_1 = sc1;
        sort_comparator_2 = sc2;
    }

    abstract public int compare ( String line1, String line2 );
    abstract public void merge  ( String line1, String line2 );

    abstract public void missing_in_1 ( String line_1 );
    abstract public void missing_in_2 ( String line_2 );

}

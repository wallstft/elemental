package org.neostorm.pivot_filters;

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

import org.neostorm.data.PivotFilter;

import java.util.ArrayList;

public class RepublicanAndDemocraticElectionFilter implements PivotFilter {

    String office = null;
    String year = null;
    String state = null;

    Integer party_idx = -1;
    Integer office_idx = -1;
    Integer state_idx = -1;
    Integer election_date_idx = -1;
    Integer year_idx = -1;
    @Override
    public boolean include(ArrayList<String> dimension_names, ArrayList<String> dimension_values) {
        if ( election_date_idx < 0 ) {
            election_date_idx = dimension_names.indexOf("election_date");
        }
        if ( party_idx < 0 ) {
            party_idx = dimension_names.indexOf("party");
        }
        if ( office_idx < 0 ) {
            office_idx = dimension_names.indexOf("office_type");
        }
        if ( state_idx < 0 ) {
            state_idx = dimension_names.indexOf("state");
        }
        if ( ( dimension_values.get(election_date_idx) != null &&  dimension_values.get(election_date_idx).toUpperCase().contains(year.toUpperCase()))) {
            if (dimension_values.get(state_idx) != null && dimension_values.get(state_idx).toUpperCase().contains(state.toUpperCase())) {
                if (dimension_values.get(office_idx) != null && dimension_values.get(office_idx).toUpperCase().contains(office.toUpperCase())) {
                    String party = dimension_values.get(party_idx);
                    if (party != null && (party.equalsIgnoreCase("REPUBLICAN") || party.equalsIgnoreCase("DEMOCRATIC"))) {
                        return true;
                    }
                    return false;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public RepublicanAndDemocraticElectionFilter ( String office, String year, String state )
    {
        this.office = office;
        this.year = year;
        this.state = state;
    }
}
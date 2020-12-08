package com.wallstft.workflow.builder;

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

public class Transition {
    public String event = null;
    public String current_state = null;
    public String target_state = null;

    @Override
    public boolean equals(Object obj) {
        if ( obj instanceof  Transition ) {
            Transition t = (Transition)obj;
            if ( t != null && t.current_state != null && t.event != null && t.target_state != null && this.event != null && this.current_state != null && this.target_state != null ) {
                return t.event.equals(this.event) && t.current_state.equals(this.current_state) && t.target_state.equals((this.target_state));
            }
        }
        return false;
    }
    public Transition ( String event, String current_state, String target_state ) {
        this.event = event;
        this.current_state = current_state;
        this.target_state = target_state;
    }
    public Transition ( String event, String target_state ) {
        this.event = event;
        this.target_state = target_state;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getCurrent_state() {
        return current_state;
    }

    public void setCurrent_state(String current_state) {
        this.current_state = current_state;
    }

    public String getTarget_state() {
        return target_state;
    }

    public void setTarget_state(String target_state) {
        this.target_state = target_state;
    }
}

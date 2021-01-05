package org.neostorm.excel.charts.data_structure;

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

public class ChartAnchorData {

    public int dx1  = 0;
    public int dy1  = 0;
    public int dx2  = 0;
    public int dy2  = 0;
    public int col1 = 0;
    public int row1 = 4;
    public int col2 = 10;
    public int row2 = 20;

    public int getWidth ()
    {
        return getRow2() - getRow1();
    }

    public int getHeight ()
    {
        return getCol2() - getCol1();
    }

    public ChartAnchorData()
    {
    }

    public ChartAnchorData ( int row, int col, int width, int height )
    {
        setPosition( row, col, width, height );
    }

    public ChartAnchorData setPosition (int row, int col, int width, int height)
    {
        setRow1(row);
        setCol1(col);
        setRow2(getRow1()+height );
        setCol2(getCol1()+width );
        return this;
    }

    public int getDx1() {
        return dx1;
    }

    public void setDx1(int dx1) {
        this.dx1 = dx1;
    }

    public int getDy1() {
        return dy1;
    }

    public void setDy1(int dy1) {
        this.dy1 = dy1;
    }

    public int getDx2() {
        return dx2;
    }

    public void setDx2(int dx2) {
        this.dx2 = dx2;
    }

    public int getDy2() {
        return dy2;
    }

    public void setDy2(int dy2) {
        this.dy2 = dy2;
    }

    public int getCol1() {
        return col1;
    }

    public void setCol1(int col1) {
        this.col1 = col1;
    }

    public int getRow1() {
        return row1;
    }

    public void setRow1(int row1) {
        this.row1 = row1;
    }

    public int getCol2() {
        return col2;
    }

    public void setCol2(int col2) {
        this.col2 = col2;
    }

    public int getRow2() {
        return row2;
    }

    public void setRow2(int row2) {
        this.row2 = row2;
    }
}

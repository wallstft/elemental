package org.neostorm;

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

import jdk.jfr.StackTrace;
import org.junit.Assert;
import org.junit.Test;
import org.neostorm.disk_collections.DiskArray;
import org.neostorm.disk_collections.DiskArrayNodeTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class DiskArrayTest {

    public String big_string ( int i )
    {
        StringBuilder sb = new StringBuilder();
        for ( int x=0; x<i*1; x++ ) {
            sb.append("X");
        }
        return sb.toString();
    }

    @Test
    public void disk_array_gzip_test() {
        String orig_str = "This is a test of the gzip compress and gunzip";
        byte[] raw_data = orig_str.getBytes();
        byte[] gz_data =Util.gzip(raw_data);
        byte[] gunzip_data = Util.gunzip(gz_data);
        String gunzip_str = new String(gunzip_data);

        Assert.assertTrue(orig_str.equals(gunzip_str));
    }

    @Test
    public void disk_array_test()
    {
        String dir = "/Users/kevintboyle/test";
        Util.delete_all_files(dir);

        Date cal_dt = Calendar.getInstance().getTime();
        DiskArray<TestNode> l = new DiskArray<>(dir, "disk_array_test_name");

        l.open();

        TestNode n = new TestNode();
        for ( int i=0; i<300; i++ ) {
            n.i= i;
            n.value = (10*i)*3.14;
            n.dt = Calendar.getInstance().getTime();
            l.add(n);
        }

        l.completed_writing();

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm.ss.SSS");
        for ( int i=0; i<l.size(); i++) {
            n = l.get(i);
            System.out.println ( String.format( "%d, %10.5f, %s %s", n.i, n.value, df.format(n.dt), n.msg ));
        }

        l.create_index("reverse", new Comparator<TestNode>() {
            @Override
            public int compare(TestNode o1, TestNode o2) {
                if ( o1 != null && o2 != null ) {
                    return o2.i.compareTo(o1.i);
                }
                return 0;
            }
        });

        l.create_filter("reverse", "even", new DiskArrayNodeTest<TestNode>() {
            @Override
            public boolean test(TestNode testNode) {
                boolean ret = false;

                if ( testNode != null ) {
                    return ( testNode.i %2 ) == 0;
                }
                return ret;
            }
        });

        l.create_filter("reverse", "odd", new DiskArrayNodeTest<TestNode>() {
            @Override
            public boolean test(TestNode testNode) {
                boolean ret = false;

                if ( testNode != null ) {
                    return ( testNode.i %2 ) == 1;
                }
                return ret;
            }
        });


        System.out.println("Print all in Reverse Order");
        for ( int i=0; i<l.size("reverse"); i++ ) {
            n = l.get("reverse", i);
            System.out.println ( String.format( "%d, %10.5f, %s %s", n.i, n.value, df.format(n.dt), n.msg ));
        }

        System.out.println("Print Even in Reverse Order");
        for ( int i=0; i<l.size("even"); i++ ) {
            n = l.get( "even", i);
            System.out.println ( String.format( "%d, %10.5f, %s %s", n.i, n.value, df.format(n.dt), n.msg ));
        }

        System.out.println("Print Odd in Reverse Order");
        for ( int i=0; i<l.size("odd"); i++ ) {
            n = l.get("odd", i);
            System.out.println ( String.format( "%d, %10.5f, %s %s", n.i, n.value, df.format(n.dt), n.msg ));
        }

        Assert.assertTrue("test even ", l.contains("even", new DiskArrayNodeTest<TestNode>() {
            @Override
            public boolean test(TestNode testNode) {
                if (testNode.i.equals(2)) {
                    return true;
                }
                return false;
            }
        }));


        Assert.assertTrue("test odd ", l.contains("odd", new DiskArrayNodeTest<TestNode>() {
            @Override
            public boolean test(TestNode testNode) {
                if (testNode.i.equals(3)) {
                    return true;
                }
                return false;
            }
        }));


        Assert.assertFalse("test odd not in list", l.contains("even", new DiskArrayNodeTest<TestNode>() {
            @Override
            public boolean test(TestNode testNode) {
                if (testNode.i.equals(3)) {
                    return true;
                }
                return false;
            }
        }));

    }

}

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

import com.google.code.externalsorting.ExternalSort;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Util {

    static private Logger logger = LogManager.getLogger(Util.class.getName());

    static public void  sleep ( int ms ) {
        try {
            Thread.sleep(ms);
        }
        catch ( Exception x ) {
            logger.error(x);
        }
    }

    static public void copyFile (String source_filename, String dest_filename )
    {
        try {
            FileUtils.copyFile(new File(source_filename), new File(dest_filename));
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }
    }

    static public void moveFile ( String source_file, String dest_file )
    {
        try {
            FileUtils.moveFile(new File(source_file), new File(dest_file));
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }
    }

    static public void delete ( String filename ) {
        if ( filename != null ) {
            delete ( new File ( filename ));
        }
    }

    static public void delete ( File file ) {
        if ( file != null ) {
            if ( file.exists() ) {
                file.delete();
            }
        }
    }

    static public void delete_all_files ( String path ) {
        delete_all_files( path, false );
    }

    static public void delete_all_files ( String path, boolean delete_this_directory_too )
    {
        try {
            if ( path != null ){
                File f = new File ( path ) ;
                if ( f!= null ) {
                    File[] list = f.listFiles();
                    if ( list != null ) {
                        for ( File file : list ) {
                            if ( file != null ) {
                                if ( file.exists() ) {
                                    Util.delete(file);
                                }
                                if ( file.isDirectory() ) {
                                    delete_all_files( file.getCanonicalPath(), true );
                                    file.delete();
                                }
                            }
                        }
                    }
                    if ( delete_this_directory_too && f.exists() && f.isDirectory() ) {
                        f.delete();
                    }
                }
            }
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }
    }

    static public String readAllLines ( File filename ) {
        String str = null;
        try {
            byte [] data = Files.readAllBytes(filename.toPath());
            if ( data != null ) {
                str = new String(data);
            }
        }
        catch ( Exception x ) {
            logger.error(x);
        }
        return str;
    }

    static public void sortFile (String file, Comparator<String> comparator ) {
        if ( file != null ) {
            sortFile ( new File (file) , comparator );
        }
    }
    static public void sortFile (File file, Comparator<String> comparator )
    {
        try {
            List<File> file_list = ExternalSort.sortInBatch ( file, comparator );
            if ( file_list != null ) {
                ExternalSort.mergeSortedFiles(file_list, file, comparator);
                file_list.clear();
                file_list = null;
            }
        }
        catch ( Exception x )
        {
            logger.error(x);
        }
    }

    static public String gzip ( String data )
    {
        String ret = null;
        try {
            if ( data != null ) {
                byte[] gz = gzip(data.getBytes("UTF-8"));
                if ( gz != null ) {
                    ret = new String (gz);
                }
            }
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }
        return ret;
    }

    static public byte[] gzip ( byte[]  data )
    {
        Deflater algo = new Deflater();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            algo.setInput( data );
            algo.finish();
            byte [] buf = new byte[1024];
            int count =0;
            while ( !algo.finished() ) {
                count = algo.deflate(buf);
                stream.write ( buf, 0, count );
            }
            return Base64.getEncoder().encode(stream.toByteArray());
        }
        catch ( Exception x ) {
            logger.error(x);
        }
        return null;
    }


    static public String gunzip ( String data ) {
        data = data.replace("\n", "");
        byte[] bdata = gunzip( data.getBytes() );
        String str = new String ( bdata );

        return str;
    }

    static public byte[] gunzip ( byte [] data )
    {
        byte [] ret = null;
        if ( data != null ) {
            ret = gunzip_to_byte_array( data );
        }
        return ret;
    }

    static private byte[] gunzip_to_byte_array ( byte[] data) {
        Inflater algo = new Inflater();
        byte[] decoded_data = Base64.getDecoder().decode(data);
        algo.setInput( decoded_data );
        try {
            ByteArrayOutputStream o = new ByteArrayOutputStream();
            byte [] buf = new byte[1024];
            int count ;
            while ((!algo.finished()) && ((count=algo.inflate(buf))>0))
            {
                o.write(buf, 0, count );
            }
            algo.end();;
            o.close();

            byte[] ret = o.toByteArray();
            return  ret ;
        }
        catch ( Exception x )
        {
            logger.error(x);
        }
        return null;
    }
}

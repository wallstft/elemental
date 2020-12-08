package org.neostorm.disk_collections;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.neostorm.Util;
import org.neostorm.merge_sort.MergeSort;

import java.io.*;
import java.util.*;

public class DiskArray <V extends Serializable> {

    static private Logger logger = LogManager.getLogger(MergeSort.class.getName());

    String base_directory = null;
    HashMap<String, RandomAccessFile> index_map = new HashMap<>();
    final long[] seek_vector = new long[2];
    final int index_byle_len ;

    String array_name = null;
    String array_instance = UUID.randomUUID().toString();

    String data_filename = null;
    String index_filename = null;

    RandomAccessFile data_reader = null;
    RandomAccessFile data_writer = null;

    RandomAccessFile index_reader = null;
    RandomAccessFile index_writer = null;

    class Index implements Serializable {
        long seek = 0L;
        int length = 0;
    }

    public DiskArray ( String directory, String name ) {
        array_name = name;
        this.base_directory = String.format("%s%s%s%s%s%s%s", directory, File.separator, "disk_array", File.separator, array_name, File.separator, array_instance );
        File dir = new File ( this.base_directory );
        dir.mkdirs();
        index_byle_len = getIndexByteLength();
    }

    private String getIndexFilename ( String index_name ) {
        String new_index_filename = String.format("%s%s%s.index", base_directory, File.separator, index_name );
        return new_index_filename;
    }

    public void open()
    {
        try {
            data_filename = String.format("%s%sdata", base_directory, File.separator );
            index_filename = String.format("%s%sindex", base_directory, File.separator );
            File df = new File (data_filename ) ;
            if ( df.exists() ) {
                df.delete();
            }
            df.createNewFile();

            File idf = new File (index_filename );
            if (idf.exists() ) {
                idf.delete();
            }
            idf.createNewFile();

            data_reader = new RandomAccessFile( data_filename, "r");
            data_writer = new RandomAccessFile( data_filename, "rw");

            index_reader = new RandomAccessFile( index_filename, "r");
            index_writer = new RandomAccessFile( index_filename, "rw");

        }
        catch ( Exception ex ) {
            logger.error(ex);
        }
    }

    public void completed_writing ()
    {
        try {
            if ( data_writer != null  ){
                data_writer.close();
            }
            if ( index_writer != null ) {
                index_writer.close();
            }
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }
    }

    public void completed_reading()
    {
        try {
            if ( data_reader != null ) {
                data_reader.close();
            }
            if (index_reader != null ) {
                index_reader.close();
            }
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }
    }

    static public void sortFile ( String filename, Comparator<String> comparator ){
        try {
            File file = new File ( filename );
            ExternalSort eSort = new ExternalSort();
            List<File> l = eSort.sortInBatch( file, comparator );
            eSort.mergeSortedFiles(l, file, comparator);
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }
    }

    public void create_index ( String index_name, Comparator<V> comparator ) {
        String new_index_name = getIndexFilename ( index_name );
        Util.copyFile( index_filename, new_index_name );
        Util.sortFile( new_index_name, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                long[] o1_idx = decodeIndexData ( o1.getBytes( ));
                long[] o2_idx = decodeIndexData ( o2.getBytes( ));
                if ( o1_idx != null && o2_idx != null ) {
                    V o1_data = loadData ( index_reader, o1_idx );
                    V o2_data = loadData ( index_reader, o2_idx );
                    if ( comparator != null ) {
                        return comparator.compare(o1_data, o2_data);
                    }
                }
                return 0;
            }
        });
    }

    public void create_filter ( String target_index_name , DiskArrayNodeTest<V> filter ) {
        create_the_filter ( this.index_reader, this.index_filename, target_index_name, filter );
    }

    public void create_filter ( String source_index_name, String target_index_name, DiskArrayNodeTest<V> filter )
    {
        RandomAccessFile IndexReader = null;
        try {
            String source_index_filename = this.getIndexFilename ( source_index_name );
            IndexReader = new RandomAccessFile( source_index_filename, "r");
            create_the_filter ( IndexReader, source_index_filename, target_index_name, filter );
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }
        finally {
            try {
                if ( IndexReader != null ) {
                    IndexReader.close();
                }
                IndexReader = null;
            }
            catch ( Exception ex ) {
                logger.error(ex);
            }
        }
    }

    public boolean contains ( String index_name, DiskArrayNodeTest<V> filter ) {
        return contains( this.getIndexReader( index_name), this.getIndexFilename(index_name), filter);
    }

    public boolean contains ( DiskArrayNodeTest<V> filter ) {
        return contains( index_reader, index_filename, filter );
    }

    private boolean contains ( RandomAccessFile IndexReader, String IndexFilename, DiskArrayNodeTest<V> filter )
    {
        for ( int i=0; i<getIndexSize(IndexFilename); i++ ) {
            IndexAndData meta = getIndexAndData(IndexReader, i);
            if ( filter != null ) {
                if ( filter.test(meta.data)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void create_the_filter ( RandomAccessFile IndexReader, String IndexFilename, String target_index_name, DiskArrayNodeTest<V> filter ){
        RandomAccessFile TargetWriter = null;

        try {
            String target_index_filename = getIndexFilename( target_index_name );
            TargetWriter = new RandomAccessFile( target_index_filename, "rw" );
            for ( int i=0; i<getIndexSize( IndexFilename ); i++ ) {
                IndexAndData meta = getIndexAndData(IndexReader, i );
                if ( filter != null ) {
                    if ( filter.stop(meta.data )) {
                        return ;
                    }
                    if ( filter.test(meta.data)) {
                        byte[] bdata = new byte[meta.index_data.length+1];
                        int idx =0;
                        for (; idx<meta.index_data.length; idx++ ) {
                            bdata[idx] = meta.index_data[idx];
                        }
                        bdata[idx] = '\n';
                        TargetWriter.write(bdata);
                    }
                }
            }
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }
        finally {
            try {
                if ( TargetWriter != null ) {
                    TargetWriter.close();
                }
            }
            catch ( Exception ex ) {
                logger.error(ex);
            }
        }
    }

    public void close()
    {
        completed_reading();
        completed_writing();
    }

    public void delete() { Util.delete_all_files( this.base_directory );}

    private int getIndexByteLength ()
    {
        int len = 0;
        try {
            ByteArrayOutputStream idx_baos = new ByteArrayOutputStream();
            ObjectOutputStream idx_oos = new ObjectOutputStream(idx_baos );
            idx_oos.writeObject( seek_vector );

            byte[] idx_raw_data = idx_baos.toByteArray();
            String encoded_data = String.format("%s\n", Base64.getEncoder().encodeToString(idx_raw_data));

            len = encoded_data.length();
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }
        return len;
    }

    public boolean add ( V v ) {
         boolean result = false;

         try {
             {
                 ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(baos);
                 oos.writeObject(v);

                 byte[] raw_data = baos.toByteArray();
                 byte[] gz_raw_data = Util.gzip(raw_data);

                 seek_vector[0] = data_writer.getFilePointer();
                 seek_vector[1] = gz_raw_data.length;

                 data_writer.write(gz_raw_data);
             }
             {
                 ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(baos);
                 oos.writeObject( seek_vector );

                 byte[] raw_data = baos.toByteArray();
                 String encoded_data = String.format( "%s\n", Base64.getEncoder().encodeToString(raw_data));

                 if ( encoded_data != null && encoded_data.length() != this.index_byle_len ) {
                     throw new Exception ("The lenght is not the same.");
                 }

                 index_writer.write( encoded_data.getBytes());
             }
             result =true;
         }
         catch ( Exception ex ) {
             logger.error(ex);
         }
         return result;
    }

    public long size() { return getIndexSize ( this.index_filename );}

    public long size( String name ) {
        String index_filename = getIndexFilename( name );
        return getIndexSize ( index_filename );
    }

    private long getIndexSize( String full_index_filename )
    {
        File f = new File ( full_index_filename );
        long sz = f.length()/this.index_byle_len;
        return sz;
    }

    class IndexAndData {
        V data = null;
        byte [] index_data = null;
        long [] index_seek_len = null;
    }

    public V get ( int i )
    {
        IndexAndData id = getIndexAndData ( index_reader, i );
        if ( id != null ) {
            return id.data;
        }
        return null;
    }

    public V get ( String index_name, int i ) {
        IndexAndData id = getIndexAndData(  getIndexReader ( index_name), i );
        if ( id != null ) {
            return id.data;
        }
        return null;
    }

    private RandomAccessFile getIndexReader ( String index_name )
    {
        RandomAccessFile r = index_map.get(index_name);
        if ( r == null ) {
            try {
                r= new RandomAccessFile( getIndexFilename( index_name ), "rw");
                index_map.put ( index_name, r );
            }
            catch ( Exception ex ) {
                logger.error(ex);
            }
        }
        return r;
    }

    private long[] decodeIndexData ( byte[] raw_data_no_carriage_return )
    {
        long [] idx = null;
        try {
            byte [] decoded_data = Base64.getDecoder().decode(raw_data_no_carriage_return);
            ByteArrayInputStream bis = new ByteArrayInputStream(decoded_data);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object o = ois.readObject();
            idx = (long[])o;
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }
        return idx;
    }

    private V loadData ( RandomAccessFile IndexReader, long[] idx )
    {
        V v = null;
        try {
            long seek = (long) idx[0];
            int len = ( int )idx[1];
            data_reader.seek(seek);
            byte[] data= new byte[len];
            data_reader.read(data, 0, len );

            byte[] gunzip_data = Util.gunzip(data);
            ByteArrayInputStream bis = new ByteArrayInputStream( gunzip_data );
            ObjectInputStream ois = new ObjectInputStream(bis);
            v = (V) ois.readObject();
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }
        return v;
    }

    private IndexAndData getIndexAndData ( RandomAccessFile IndexReader, int i )
    {
        IndexAndData id = new IndexAndData();
        try {
            long[] idx = null;
            {
                id.index_data = new byte[this.index_byle_len - 1];
                long seek = i * index_byle_len;
                IndexReader.seek(seek);
                IndexReader.read(id.index_data, 0, index_byle_len - 1);
                idx = decodeIndexData(id.index_data);
                id.index_seek_len = idx;
            }
            {
                id.data = loadData( IndexReader, idx );
            }
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }

        return id;
    }


}

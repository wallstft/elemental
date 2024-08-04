package iceberg;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.iceberg.PartitionSpec;
import org.apache.iceberg.Schema;
import org.apache.iceberg.Table;
import org.apache.iceberg.catalog.TableIdentifier;
import org.apache.iceberg.data.GenericRecord;
import org.apache.iceberg.data.parquet.GenericParquetWriter;
import org.apache.iceberg.hadoop.HadoopCatalog;
import org.apache.iceberg.io.*;
import org.apache.iceberg.parquet.Parquet;
import org.apache.iceberg.types.Types;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class JsonToIceberg {

    private static final Schema SCHEMA = new Schema(
//            Types.NestedField.required(1, "id", Types.IntegerType.get()),
//            Types.NestedField.optional(2, "name", Types.StringType.get()),
//            Types.NestedField.optional(3, "value", Types.DoubleType.get())

            Types.NestedField.optional(1, "event_id", Types.StringType.get()),
            Types.NestedField.optional(2, "username", Types.StringType.get()),
            Types.NestedField.required(3, "userid", Types.IntegerType.get()),
            Types.NestedField.required(4, "api_version", Types.StringType.get()),
            Types.NestedField.required(5, "command", Types.StringType.get())
    );

    public static void main(String[] args) throws Exception {
        // Setup Hadoop configuration
        Configuration conf = new Configuration();
        String warehousePath = "/Users/kevinboyle/data/iceberg";
        HadoopCatalog catalog = new HadoopCatalog(conf, warehousePath);

        FileUtils.deleteDirectory(new File (String.format("%s/default", warehousePath )));

        // Create Iceberg table
        TableIdentifier tableIdentifier = TableIdentifier.of("default", "json_table");
        Table table = catalog.createTable(tableIdentifier, SCHEMA);

        // Read JSON file
        File jsonFile = new File("/Users/kevinboyle/data/data.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonFile);

        // Write data to Iceberg table
        OutputFileFactory outputFileFactory = OutputFileFactory.builderFor(table, 1, 1).build();
        FileAppender<ArrayNode> appender = Parquet.write(outputFileFactory.newOutputFile().encryptingOutputFile())
                .schema(SCHEMA)
                .createWriterFunc(GenericParquetWriter::buildWriter)
                .build();

//        for (JsonNode node : rootNode) {
//            if  ( node.isArray() ) {
//                appender.add((ArrayNode)node);
//            }
//        }

        GenericRecord record = GenericRecord.create(SCHEMA);
        ImmutableList.Builder<GenericRecord> builder = ImmutableList.builder();
        builder.add(record.copy(ImmutableMap.of("event_id", UUID.randomUUID().toString(), "username", "Bruce", "userid", 1, "api_version", "1.0", "command", "grapple")));
        builder.add(record.copy(ImmutableMap.of("event_id", UUID.randomUUID().toString(), "username", "Wayne", "userid", 1, "api_version", "1.0", "command", "glide")));
        builder.add(record.copy(ImmutableMap.of("event_id", UUID.randomUUID().toString(), "username", "Clark", "userid", 1, "api_version", "2.0", "command", "fly")));
        builder.add(record.copy(ImmutableMap.of("event_id", UUID.randomUUID().toString(), "username", "Kent", "userid", 1, "api_version", "1.0", "command", "land")));
        ImmutableList<GenericRecord> records = builder.build();


        String filepath = table.location() + "/" + UUID.randomUUID().toString();
        OutputFile file = table.io().newOutputFile(filepath);
        DataWriter<GenericRecord> dataWriter =
                Parquet.writeData(file)
                        .schema(SCHEMA)
                        .createWriterFunc(GenericParquetWriter::buildWriter)
                        .overwrite()
                        .withSpec(PartitionSpec.unpartitioned())
                        .build();

        try {
            for (GenericRecord r : builder.build()) {
                dataWriter.write(r);
            }
        } finally {
            dataWriter.close();
        }


//        WriteResult result = WriteResult.builder().build();
//        table.newAppend().appendFile(result.dataFiles()[0]).commit();

        // Close the catalog
        catalog.close();
    }
}

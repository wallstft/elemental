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

import com.wallstft.workflow.WorkflowEngine;
import com.wallstft.workflow.WorkflowEngineFactory;
import com.wallstft.workflow.builder.CommonWorkflowBuilder;
import com.wallstft.workflow.builder.SequentialWorkflowBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.statemachine.support.LifecycleObjectSupport;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class pdf_reader_test {

    @DisplayName("PDF Test")
    @Test
    public  void pdf_test() throws Exception
    {
        File myFile = new File("/Users/kevintboyle/dev/elemental/test_cases/src/main/resources/ElectionResults.pdf");

        Assert.assertTrue( myFile.exists() );

        try (PDDocument doc = PDDocument.load(myFile)) {

            PDDocumentCatalog catalog = doc.getDocumentCatalog();
            PDMetadata metadata = catalog.getMetadata();

            if (metadata == null) {

                System.err.println("No metadata in document");
                System.exit(1);
            }

            try (InputStream is = metadata.createInputStream();
                 InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader br = new BufferedReader(isr)) {

                br.lines().forEach(System.out::println);
            }
        }
    }
}

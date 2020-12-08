package com.wallstft.sftp;

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

import com.wallstft.sftp.client.SftpFileSystemContainer;
import com.wallstft.sftp.client.ClientSftpFileSystemFactory;
import com.wallstft.sftp.server.SftpKeyAuthenicatorImpl;
import com.wallstft.sftp.server.SftpServerImpl;
import com.wallstft.sftp.utils.PublicKeyUtil;
import com.wallstft.workflow.LoggerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.*;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * This is a test class that creates a SFTP Server and and SFTP Client.   The client will connect to the server, authenticate and exchange a file.
 */
public class SftpServerTest {

    private Logger logger = LogManager.getLogger(SftpServerTest.class.getName());

    @Test
    public void SftpTest ()
    {
        LoggerUtil.addAppender(System.out, "test_example");
        logger.debug(String.format("This is a test"));

        String username = "kevin";
        int port = SftpServerImpl.getPort();
        String hostname = "localhost";

        Path localRoot = null;
        Path local0 = null;

        try{

            /**
             * This generates a KeyPair using the RSA algorithm.   The KeyPair is used for authentication and the PublicKey is provided to the SFTP Server while the SFTP Client uses both the PrivateKey and PublicKey to authenticate.
             */
            KeyPair pair = PublicKeyUtil.generate_RSA();

            SftpServerImpl server = new SftpServerImpl();
            SftpKeyAuthenicatorImpl auth = new SftpKeyAuthenicatorImpl();
            server.start_server(auth);

            server.addUser( username, "/tmp/sftp_home/"+username, pair.getPublic() );

            Assert.assertTrue(PublicKeyUtil.decode_RSA(PublicKeyUtil.encode(pair.getPublic())).equals(pair.getPublic()));


            ClientSftpFileSystemFactory client = new ClientSftpFileSystemFactory();
            SftpFileSystemContainer fs = client.open( username, hostname, port, pair );
            try {
                Path remoteRoot = fs.getFileSystem().getDefaultDir().resolve("/");
                Path remote0 = remoteRoot.resolve("files-1.txt");

                String filename = "files-0.txt";
                localRoot = Paths.get("/tmp");
                local0 = localRoot.resolve(filename);
                File f = new File("/tmp/" + filename);
                if (f.exists())
                    f.delete();

                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write(String.format("Hello From Test Application\n"));
                bw.close();

                if (Files.exists(remote0)) {
                    Files.delete(remote0);
                }

                Files.copy(local0, remote0);

                server.removeUser(username);
            }
            finally {
                client.close();
                fs.close();
            }
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }

    }


//    private void setupSftpClient ()
//    {
//        Path localRoot = null;
//        Path local0 = null;
//        String username = "kevin";
//        int port = SftpServerImpl.port;
//        String hostname = "localhost";
//        try {
//
//            localRoot = Paths.get("sftp");
////            Path localRoot = detectTargetFolder().resolve("sftp");
//            Files.createDirectories(localRoot);
//
//            local0 = localRoot.resolve("files-0.txt");
//            Files.deleteIfExists(local0);
//
//            String data = getClass().getName() + "#" + username + "(" + new Date() + ")" + System.lineSeparator();
//            try (BufferedWriter bos = Files.newBufferedWriter(local0)) {
//                long count = 0L;
//                while (count < 1024L * 1024L * 10L) { // 10 MB
//                    String s = String.format("%8x %s", count, data);
//                    bos.append(s);
//                    count += s.length();
//                }
//            }
//        }
//        catch ( Exception ex ) {
//            ex.printStackTrace();
//        }
//
//        try (ClientSession session = createClientSession(username, hostname, port);
//             SftpFileSystem fs = SftpClientFactory.instance().createSftpFileSystem(session)) {
//
//            Path remoteRoot = fs.getDefaultDir().resolve("target/sftp");
//            Path remote0 = remoteRoot.resolve("files-1.txt");
//            Files.deleteIfExists(remote0);
//
//            Path local1 = localRoot.resolve("files-2.txt");
//            Files.deleteIfExists(local1);
//
//            Path remote1 = remoteRoot.resolve("files-3.txt");
//            Files.deleteIfExists(remote1);
//
//            Path local2 = localRoot.resolve("files-4.txt");
//            Files.deleteIfExists(local2);
//
//            Files.copy(local0, remote0);
//            Files.copy(remote0, local1);
//            Files.copy(local1, remote1);
//            Files.copy(remote1, local2);
//
////            assertSameContent(local0, local2);
//        }
//        catch ( Exception ex ) {
//            ex.printStackTrace();
//        }
//    }



}

package com.wallstft.sftp.client;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.config.hosts.HostConfigEntryResolver;
import org.apache.sshd.client.future.AuthFuture;
import org.apache.sshd.client.keyverifier.AcceptAllServerKeyVerifier;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.client.simple.SimpleClient;
import org.apache.sshd.client.subsystem.sftp.SftpClientFactory;
import org.apache.sshd.client.subsystem.sftp.SimpleSftpClient;
import org.apache.sshd.client.subsystem.sftp.fs.SftpFileSystem;
import org.apache.sshd.common.keyprovider.KeyIdentityProvider;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Establishes a factory for SftpFileSystem session to the specified servers.
 */
public class ClientSftpFileSystemFactory {
    SshClient    client = null;
    SimpleClient simple;
    private SimpleSftpClient sftpClient;

    public static final long CONNECT_TIMEOUT = 60*1000L;
    public static final long AUTH_TIMEOUT = 60*1000L;

    private Logger logger = LogManager.getLogger(ClientSftpFileSystemFactory.class.getName());


    /**
     * Setup the SSH client which is the foundation for the data exchange and securing the connectivity.
     * @return SshClient which is the client handle to the SSH server.
     */
    private SshClient createSshClient ()
    {
        SshClient client = SshClient.setUpDefaultClient();
        client.setServerKeyVerifier(AcceptAllServerKeyVerifier.INSTANCE);
        client.setHostConfigEntryResolver(HostConfigEntryResolver.EMPTY);
        client.setKeyIdentityProvider(KeyIdentityProvider.EMPTY_KEYS_PROVIDER);

        client.start();

        return client;
    }

    /**
     * Creates a connection to the remote server and uses the KeyPair to 1) validate that the remote server has a matching public-key and 2) that a message encrypted using the public-key can be decrypted using the private-key provided in the KeyPair.
     * NOTE, the SSH server configuration will determine which authentication method, or combination, is used.
     * @param username holds the SFTP username
     * @param host the hostname of the target server
     * @param port the port on the hostname that SSH is listening
     * @param keyPair the Public/Private Key pair used to authenticate the user with the remote server.
     * @return returns the ClientSession.
     * @throws IOException
     */
    public ClientSession createClientSession(String username, String host, int port, KeyPair keyPair ) throws IOException {
        client = createSshClient();
        ClientSession session = client.connect(username, host, port).verify(CONNECT_TIMEOUT).getSession();
        try {
            session.addPublicKeyIdentity(keyPair);
            AuthFuture af = session.auth();
            af.verify(AUTH_TIMEOUT);
            return session;
        } catch (IOException e) {
            session.close();
            throw e;
        }
    }

    /**
     * creates a connection and attempts to authenticate using username and password combination.  NOTE, the SSH server configuration will determine which authentication method, or combination, is used.
     *
     * @param username the username being authenticated on the server
     * @param host the hostname on which the SSH server is running.
     * @param port the port on which the SSH server is listening on the hostname specified above.
     * @param password the password using to authenticate the user on the remote server.
     * @return returns the ClientSession.
     * @throws IOException
     */
    public ClientSession createClientSession(String username, String host, int port, String password ) throws IOException {
        client = createSshClient();
        ClientSession session = client.connect(username, host, port).verify(CONNECT_TIMEOUT).getSession();
        try {
            session.addPasswordIdentity(password);
            session.auth().verify(AUTH_TIMEOUT);
            return session;
        } catch (IOException e) {
            session.close();
            throw e;
        }
    }

    /**
     * opens a session and places the session into an SftpFileSystemContainer so that the finalize() method and close() function will ensure the connection is closed once the container goes out of scope.  This function will use the KeyPair for authenticating.
     *
     * @param username the username being authenticated on the server
     * @param hostname the hostname on which the SSH server is running.
     * @param port the port on which the SSH server is listening on the hostname specified above.
     * @param keyPair the KeyPair, containing private-key and public-key, which is used to authenticate with the remote SSH server.
     * @return returns SftpFileSystemContainer
     */
    public SftpFileSystemContainer open (String username, String hostname, int port, KeyPair keyPair )
    {
        SftpFileSystem fs = null;
        try {
            ClientSession session = createClientSession(username, hostname, port, keyPair );
            fs = SftpClientFactory.instance().createSftpFileSystem(session);
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }
        return new SftpFileSystemContainer(fs);
    }

    /**
     * opens a session and places the session into an SftpFileSystemContainer so that the finalize() method and close() function will ensure the connection is closed once the container goes out of scope.  This function will use the password for authenticating.
     * @param username the username being authenticated on the server
     * @param hostname the hostname on which the SSH server is running.
     * @param port the port on which the SSH server is listening on the hostname specified above.
     * @param password the password used to authenticate the user with the SSH server.
     * @return returns SftpFileSystemContainer
     */
    public SftpFileSystemContainer open (String username, String hostname, int port, String password )
    {
        SftpFileSystem fs = null;
        try {
            ClientSession session = createClientSession(username, hostname, port, password );
            fs = SftpClientFactory.instance().createSftpFileSystem(session);
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }
        return new SftpFileSystemContainer(fs);
    }

    /**
     * This will close the SSH client connection.
     */
    public void close ()
    {
        if ( client != null ) {
            client.stop();
            client = null;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        close();
    }


//    private void setupSshClient(Class<?> anchor) {
//        client = SshClient.setUpDefaultClient();
//        client.setServerKeyVerifier(AcceptAllServerKeyVerifier.INSTANCE);
//        client.setHostConfigEntryResolver(HostConfigEntryResolver.EMPTY);
//        client.setKeyIdentityProvider(KeyIdentityProvider.EMPTY_KEYS_PROVIDER);
//    }




}

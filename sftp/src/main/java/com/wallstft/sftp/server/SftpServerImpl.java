package com.wallstft.sftp.server;

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


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.common.keyprovider.KeyPairProvider;
import org.apache.sshd.common.session.SessionContext;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.AsyncAuthException;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.auth.pubkey.PublickeyAuthenticator;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.subsystem.SubsystemFactory;
import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * The SftpServerImpl class is used to setup a Sftp Server.
 */
public class SftpServerImpl {
    private SftpVirtualFileSystemFactory virtualFileSystem = null;
    SftpKeyAuthenicatorImpl auth = null;
    private SshServer sshd = null;

    /**
     * The port value has a default of 21555 but can be overwritten using the setPort() function.
     */
    protected static int port = 21555;

    static public int getPort() {
        return port;
    }

    /**
     * Use setPort() to override the default port value of 21555
     * @param p the new port number used to override the default.
     */
    static public void setPort(int p ) {
        port = p;
    }

    /**
     *
     * @param auth contains the actions that configures the server.  auth.getPasswordAuthenticator - This is used to determine the configuration for users logging in using passwords.   auth.getPublicKeyAuth - This is used to determine the configuration for users using publickKey authentication.
     *             auth.getHostKeyProvider - This is used to specify the storage of the host-key.
     */
    public void start_server (SftpKeyAuthenicatorImpl auth) {
        try {
            if (sshd == null && auth != null && auth.getPasswordAuthenticator() != null && auth.getHostKeyProvider() != null ) {
                this.auth = auth;
                sshd = SshServer.setUpDefaultServer();
                sshd.setPort(port);
                String hn = sshd.getHost();

                SftpSubsystemFactory factory = new SftpSubsystemFactory();

                List<SubsystemFactory> factoryList = Arrays.<SubsystemFactory>asList(new SubsystemFactory[]{factory});

                sshd.setSubsystemFactories(factoryList);
                sshd.setPasswordAuthenticator(auth.getPasswordAuthenticator());

                if ( auth.getPublicKeyAuth() != null )
                    sshd.setPublickeyAuthenticator(auth.getPublicKeyAuth());

                sshd.setKeyPairProvider(auth.getHostKeyProvider());
                virtualFileSystem = new SftpVirtualFileSystemFactory();
                sshd.setFileSystemFactory(virtualFileSystem);
                sshd.start();
            }
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

    /**
     * addUser() adds a users to the SSH server and specifies the home-directory and public-key.
     *
     * @param username add this user to the user.
     * @param home_directory this user will have this home-directory when connected to the SSH server.
     * @param public_key this is the public-key that the server will used to authenticate the users.
     */
    public void addUser ( String username, String home_directory, PublicKey public_key )
    {
        if ( virtualFileSystem != null ) {
            virtualFileSystem.setUserHomeDir( username, Paths.get(home_directory));
        }
        if ( auth != null ) {
            auth.addUser( username, public_key );
        }
    }

    /**
     * removeUser() will remote the specified user from the server.
     * @param username the user to be removed from the SSH server.
     */
    public void removeUser ( String username ) {
        if ( virtualFileSystem != null ) {
            virtualFileSystem.removeUser( username );
        }
    }

}

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

import org.apache.sshd.common.file.FileSystemFactory;
import org.apache.sshd.common.file.root.RootedFileSystemProvider;
import org.apache.sshd.common.session.SessionContext;
import org.apache.sshd.common.util.ValidateUtils;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Is used to manage the user home-directory.   It allows dynamic addition and removal of users and associated home-directories.
 */
public class SftpVirtualFileSystemFactory implements FileSystemFactory {
    private Path defaultHomeDir;
    private final Map<String, Path> homeDirs = new ConcurrentHashMap();

    public SftpVirtualFileSystemFactory() {
    }

    public SftpVirtualFileSystemFactory(Path defaultHomeDir) {
        this.defaultHomeDir = defaultHomeDir;
    }

    public void setDefaultHomeDir(Path defaultHomeDir) {
        this.defaultHomeDir = defaultHomeDir;
    }

    public Path getDefaultHomeDir() {
        return this.defaultHomeDir;
    }

    /**
     * Add a user and the associated home-directory.
     * @param userName the username
     * @param userHomeDir the Path to the user-home directory.
     */
    public void setUserHomeDir(String userName, Path userHomeDir) {
        this.homeDirs.put(ValidateUtils.checkNotNullAndNotEmpty(userName, "No username"), Objects.requireNonNull(userHomeDir, "No home dir"));
    }

    /**
     * removes the user and associated home-directory.
     * @param userName the username
     */
    public void removeUser ( String userName ) {
        if ( homeDirs != null )
            homeDirs.remove(userName);
    }

    /**
     * Gets the user home-directory
     * @param userName the username
     * @return the Path to the home-directory.
     */
    public Path getUserHomeDir(String userName) {
        return (Path)this.homeDirs.get(ValidateUtils.checkNotNullAndNotEmpty(userName, "No username"));
    }

    /**
     * Gets the user home-directory.
     * @param session this provides the username from the session using the getUsername() function.
     * @return returns the Path to the home-directory.
     * @throws IOException
     */
    public Path getUserHomeDir(SessionContext session) throws IOException {
        String username = session.getUsername();
        Path homeDir = this.getUserHomeDir(username);
        if (homeDir == null) {
            homeDir = this.getDefaultHomeDir();
        }

        return homeDir;
    }

    /**
     * will create the home-directory if once does not already exist.
     * @param session the SSH SessionContext.
     * @return returns the FileSystem to the user home-directory.
     * @throws IOException
     */
    public FileSystem createFileSystem(SessionContext session) throws IOException {
        Path dir = this.getUserHomeDir(session);
        if (dir == null) {
            throw new InvalidPathException(session.getUsername(), "Cannot resolve home directory");
        } else {
            return (new RootedFileSystemProvider()).newFileSystem(dir, Collections.emptyMap());
        }
    }
}

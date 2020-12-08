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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.sshd.common.config.keys.AuthorizedKeyEntry;
import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.common.config.keys.PublicKeyEntryResolver;
import org.apache.sshd.common.keyprovider.KeyPairProvider;
import org.apache.sshd.common.session.SessionContext;
import org.apache.sshd.server.auth.AsyncAuthException;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.auth.pubkey.PublickeyAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SftpKeyAuthenicatorImpl {

    private Logger logger = LogManager.getLogger(SftpKeyAuthenicatorImpl.class.getName());

    private ConcurrentHashMap<String, List<PublicKey>> keyStore = new ConcurrentHashMap<String, List<PublicKey>>();

    PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator() {
        @Override
        public boolean authenticate(String s, String s1, ServerSession serverSession) throws PasswordChangeRequiredException, AsyncAuthException {
            return true;
        }
    };

    PublickeyAuthenticator publicKeyAuth = new PublickeyAuthenticator() {
        @Override
        public boolean authenticate(String username, PublicKey publicKey, ServerSession serverSession) throws AsyncAuthException {
            try {
                List<PublicKey> available_keys = getKeysByUsername ( serverSession, username );
                PublicKey key = KeyUtils.findMatchingKey( publicKey, available_keys);
                return ( key != null ? true : false );
            }
            catch ( Exception x )
            {
                logger.error(x);
            }
            return false;
        }
    };

    KeyPairProvider hostKeyProvider = new SimpleGeneratorHostKeyProvider(Paths.get("hostkey.ser"));

    public KeyPairProvider getHostKeyProvider() {
        return hostKeyProvider;
    }

    public void setHostKeyProvider(KeyPairProvider hostKeyProvider) {
        this.hostKeyProvider = hostKeyProvider;
    }

    public PasswordAuthenticator getPasswordAuthenticator() {
        return passwordAuthenticator;
    }

    public void setPasswordAuthenticator(PasswordAuthenticator passwordAuthenticator) {
        this.passwordAuthenticator = passwordAuthenticator;
    }

    public PublickeyAuthenticator getPublicKeyAuth() {
        return publicKeyAuth;
    }

    public void setPublicKeyAuth(PublickeyAuthenticator publicKeyAuth) {
        this.publicKeyAuth = publicKeyAuth;
    }

    private List<PublicKey> getKeysByUsername ( SessionContext session, String username ) {
        List<PublicKey> public_keys = new ArrayList<>();
        if ( username == null ) {
            return public_keys;
        }
        if ( keyStore != null ) {
            public_keys = keyStore.get(username);
        }
        return public_keys;
    }


//    private List<PublicKey> getKeysByUsername ( SessionContext session, String username ) {
//        List<PublicKey> public_keys = new ArrayList<>();
//        if ( username == null ) {
//            return public_keys;
//        }
//        if ( keyStore != null ) {
//            List<String> key_strings = keyStore.get(username);
//            if ( key_strings != null ) {
//                for (String public_key : key_strings) {
//                    try {
//                        AuthorizedKeyEntry ke = AuthorizedKeyEntry.parseAuthorizedKeyEntry(public_key);
//                        PublicKey pk = ke.resolvePublicKey(session, PublicKeyEntryResolver.IGNORING);
//                        if (pk != null) {
//                            public_keys.add(pk);
//                        }
//                    } catch (Exception x) {
//                        logger.error(x);
//                    }
//                }
//            }
//        }
//        return public_keys;
//    }

    public void addUser ( String username, PublicKey public_key )
    {
        try {
            if (username != null && public_key != null) {
                List<PublicKey> key_strings = null;
                if (!keyStore.containsKey(username)) {
                    keyStore.put(username, key_strings = new ArrayList<PublicKey>());
                }
                if ( !key_strings.contains(public_key)) {
                    key_strings.add(public_key);
                }
                keyStore.put ( username, key_strings);
            }
        }
        catch ( Exception x ) {
            logger.error(x);
        }
    }
    public void removeUser ( String username ) {
        if (keyStore != null ) {
            List<PublicKey> key_strings = keyStore.get(username);
            if ( key_strings != null ) {
                key_strings.clear();
                key_strings = null;
            }
            keyStore.remove(username);
        }
    }
}

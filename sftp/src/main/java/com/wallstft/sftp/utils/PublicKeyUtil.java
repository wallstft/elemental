package com.wallstft.sftp.utils;

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

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Helpful utilities for Public/Private Key creation and manipulation.
 */
public class PublicKeyUtil {

    /**
     * Generates an RSA KeyPair with keysize = 1024
     * @return returns the newly generated KeyPair.
     * @throws Exception
     */
    public static KeyPair generate_RSA () throws Exception
    {
        return generate("RSA", 1024) ;
    }

    /**
     * A generic KeyPair function that allows the user to specify the algorithm and keysize.   This is used by the generate_RSA() function above.
     * @param algorithm
     * @param keysize
     * @return
     * @throws Exception
     */
    public static KeyPair generate (String algorithm, int keysize ) throws Exception
    {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
        keyGen.initialize(keysize);
        KeyPair pair = keyGen.genKeyPair();

        return pair;
    }

    /**
     * This converts the PublicKey into a string for the purposes of serializing to a destination system.
     * @param pk this is the PublicKey that is to be serialized.
     * @return this returns the Base64 encoded string that represents the PublicKey.
     */
    public static String encode ( PublicKey pk ) {
        String pk_str = Base64.getEncoder().encodeToString(pk.getEncoded());
        return pk_str;
    }

    /**
     * This will decode a Base64 string using the RSA algorithm.   This is the inverse of the encode_RSA() above.
     * @param RSA_base64_encoded
     * @return returns the PublicKey
     * @throws Exception
     */
    public static PublicKey decode_RSA ( String RSA_base64_encoded ) throws Exception
    {
        return decode ("RSA", RSA_base64_encoded);
    }

    /**
     * This is the generica decode function that is the inverse of the generic encode method.   The decode and encode functions must use the same algorithm to be successful.
     * @param algorithm the algorithm which was used to encode the create the public-key
     * @param base64_encoded the encoded string
     * @return returns the PublicKey
     * @throws Exception
     */
    public static PublicKey decode ( String algorithm, String base64_encoded ) throws Exception
    {
        byte [] publicBytes = Base64.getDecoder().decode(base64_encoded);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        return pubKey;
    }
}

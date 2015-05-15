/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.xml.sax.InputSource;

import java.io.InputStream;


/**
 * TODO
 * @User: mayc
 * @version $Revision$ $Date$
  */
public class EntityResolver implements org.xml.sax.EntityResolver {
    public InputSource resolveEntity(String publicId, String systemId) {
        ClassLoader classLoader = getClass().getClassLoader();

        if (_log.isDebugEnabled()) {
            _log.debug("Resolving entity " + publicId + " " + systemId);
        }

        if (publicId != null) {
            for (int i = 0; i < _PUBLIC_IDS.length; i++) {
                KeyValuePair kvp = _PUBLIC_IDS[i];

                if (publicId.equals(kvp.getKey())) {
                    InputStream is = classLoader.getResourceAsStream(_DEFINITIONS_PATH +
                            kvp.getValue());

                    if (_log.isDebugEnabled()) {
                        _log.debug("Entity found for public id " + systemId);
                    }

                    return new InputSource(is);
                }
            }
        } else if (systemId != null) {
            for (int i = 0; i < _SYSTEM_IDS.length; i++) {
                KeyValuePair kvp = _SYSTEM_IDS[i];

                if (systemId.equals(kvp.getKey())) {
                    InputStream is = classLoader.getResourceAsStream(_DEFINITIONS_PATH +
                            kvp.getValue());

                    if (_log.isDebugEnabled()) {
                        _log.debug("Entity found for system id " + systemId);
                    }

                    return new InputSource(is);
                }
            }
        }

        if (_log.isDebugEnabled()) {
            _log.debug("No entity found for " + publicId + " " + systemId);
        }

        return null;
    }

    private static String _DEFINITIONS_PATH = "";
    private static KeyValuePair[] _PUBLIC_IDS = {
            new KeyValuePair("pojo-builder.dtd", "pojo-builder.dtd")
        };
    private static KeyValuePair[] _SYSTEM_IDS = {
            new KeyValuePair("pojo-builder.dtd", "pojo-builder.dtd"),
            new KeyValuePair("http://www.w3.org/2001/xml.xsd", "xml.xsd")
        };
    private static Log _log = LogFactory.getLog(EntityResolver.class);
}

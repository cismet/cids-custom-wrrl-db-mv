package de.cismet.cids.custom.util;

import java.io.InputStream;

/**
 *
 * @author stefan
 */
public interface WebDAVClient {

    void delete(String path);

    InputStream getInputStream(String path);

    void put(String path, InputStream input);
}

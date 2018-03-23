/**
 * Copyright Stonewain Systems Inc.
 */
package org.helper.gerrit.core;

import org.helper.gerrit.model.GerritProperties;

import com.google.gerrit.extensions.api.GerritApi;
import com.urswolfer.gerrit.client.rest.GerritAuthData;
import com.urswolfer.gerrit.client.rest.GerritRestApiFactory;

/**
 * @author Sukhpal Singh
 *
 */
public class GerritApiUtils {

    private GerritApiUtils() {
        throw new UtilityClassInstantiationException();
    }

    public static GerritApi login(GerritProperties gerritProperties) {
        GerritRestApiFactory gerritRestApiFactory = new GerritRestApiFactory();
        GerritAuthData authData = new GerritAuthData.Basic(gerritProperties.serverUrl,
                gerritProperties.userName, gerritProperties.password);
        return gerritRestApiFactory.create(authData);
    }
}

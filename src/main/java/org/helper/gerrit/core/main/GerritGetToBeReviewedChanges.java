/**
 * Copyright Stonewain Systems Inc.
 */
package org.helper.gerrit.core.main;

import java.io.IOException;
import java.util.List;

import org.helper.gerrit.core.GerritApiUtils;
import org.helper.gerrit.core.PropertyUtils;
import org.helper.gerrit.model.GerritProperties;

import com.google.gerrit.extensions.api.GerritApi;
import com.google.gerrit.extensions.client.ListChangesOption;
import com.google.gerrit.extensions.common.ChangeInfo;
import com.google.gerrit.extensions.restapi.RestApiException;

/**
 * @author Sukhpal Singh
 *
 */
public class GerritGetToBeReviewedChanges {

    public static void main(String[] args) throws IOException, RestApiException {
        GerritProperties gerritProperties = PropertyUtils.loadProperties();
        GerritApi gerritApi = GerritApiUtils.login(gerritProperties);
        List<ChangeInfo> changes = gerritApi.changes().query("is:open+owner:self+projects:s")
                .withOptions(ListChangesOption.REVIEWER_UPDATES).get();
        System.out.println("Please review : ");
        for (int index = 0; index < changes.size(); index++) {
            ChangeInfo change = changes.get(index);
            System.out.println(change.subject);
            System.out.println("Project : " + change.project);
            System.out
                    .println("Url : " + gerritProperties.namedServerUrl + "/#/c/" + change._number);
            System.out.println("Size : +" + change.insertions + ", -" + change.deletions);
            System.out.println();
        }

    }

}

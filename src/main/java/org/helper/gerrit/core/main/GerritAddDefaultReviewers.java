package org.helper.gerrit.core.main;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.helper.gerrit.core.GerritApiUtils;
import org.helper.gerrit.core.InstanceHolder;
import org.helper.gerrit.core.PropertyUtils;
import org.helper.gerrit.model.GerritProperties;

import com.google.gerrit.extensions.api.GerritApi;
import com.google.gerrit.extensions.api.changes.AddReviewerResult;
import com.google.gerrit.extensions.restapi.RestApiException;

/**
 * @author Sukhpal Singh
 *
 */
public class GerritAddDefaultReviewers {
    public static void main(String[] args) throws IOException, RestApiException {
        GerritProperties gerritProperties = PropertyUtils.loadProperties();
        GerritApi gerritApi = GerritApiUtils.login(gerritProperties);
        List<String> reviewers = Arrays.asList(gerritProperties.reviewerEmails.split("\\s*,\\s*"));
        for (String reviewer : reviewers) {
            AddReviewerResult result = gerritApi.changes().id(gerritProperties.changeId)
                    .addReviewer(reviewer);
            System.out.println(InstanceHolder.getObjectMapper().writeValueAsString(result));
        }
    }

}

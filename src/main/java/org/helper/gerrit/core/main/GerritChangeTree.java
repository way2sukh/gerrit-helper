package org.helper.gerrit.core.main;

import java.io.IOException;
import java.util.ArrayList;
import org.helper.gerrit.core.GerritApiUtils;
import org.helper.gerrit.core.InstanceHolder;
import org.helper.gerrit.core.PropertyUtils;
import org.helper.gerrit.model.CommitDetail;
import org.helper.gerrit.model.GerritProperties;
import com.google.gerrit.extensions.api.GerritApi;
import com.google.gerrit.extensions.client.ChangeStatus;
import com.google.gerrit.extensions.common.ChangeInfo;
import com.google.gerrit.extensions.common.CommitInfo;
import com.google.gerrit.extensions.restapi.RestApiException;

/**
 * @author Sukhpal Singh
 *
 */
public class GerritChangeTree {

  public static void main(String[] args) throws IOException, RestApiException {

    GerritProperties gerritProperties = PropertyUtils.loadProperties();
    GerritApi gerritApi = GerritApiUtils.login(gerritProperties);

    CommitDetail detail = getCommitDetail(gerritApi, gerritProperties.changeId, gerritProperties);

    System.out.println(InstanceHolder.getObjectMapper().writeValueAsString(detail));
  }

  private static CommitDetail getCommitDetail(GerritApi gerritApi, String changeId,
      GerritProperties gerritProperties) throws RestApiException {
    CommitInfo commitInfo = gerritApi.changes().id(changeId).current().commit(true);
    ChangeInfo info = gerritApi.changes().id(changeId).info();

    if (info.status != ChangeStatus.NEW || commitInfo.parents == null
        || commitInfo.parents.isEmpty()) {
      return null;
    }

    CommitDetail detail =
        new CommitDetail(buildUrl(info._number, gerritProperties), commitInfo.subject);

    if (commitInfo.parents != null && !commitInfo.parents.isEmpty()) {
      detail.parents = new ArrayList<>();
      for (CommitInfo current : commitInfo.parents) {
        CommitDetail parent = getCommitDetail(gerritApi, current.commit, gerritProperties);
        if (parent != null) {
          detail.parents.add(parent);
        }

      }
    }
    return detail;
  }

  private static String buildUrl(int changeId, GerritProperties gerritProperties) {
    return gerritProperties.serverUrl + "/#/c/" + changeId;
  }

}

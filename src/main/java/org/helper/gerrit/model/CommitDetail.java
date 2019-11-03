package org.helper.gerrit.model;

import java.util.List;

/**
 * @author Sukhpal Singh
 *
 */
public class CommitDetail {
  public CommitDetail(String commitId, String subject) {
    this.commitId = commitId;
    this.subject = subject;
  }
  
  public String commitId;
  public String subject;
  public List<CommitDetail> parents;
}

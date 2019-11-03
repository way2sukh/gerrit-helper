package org.helper.gerrit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sukhpal Singh
 *
 */
public class FileInfo {
  public String name;
  public List<CommentDetail> unansweredComments = new ArrayList<>();
  public List<CommentDetail> answeredComments = new ArrayList<>();
}

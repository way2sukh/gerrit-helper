package org.helper.gerrit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sukhpal Singh
 *
 */
public class CommentDetail {
  public Integer patchSet;
  public Integer line;
  public String message;
  public String author;
  public List<CommentDetail> next = new ArrayList<>();
}

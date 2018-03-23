package org.helper.gerrit.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sukhpal Singh
 *
 */
public class ChangeCommentsInfo {
    public Map<String, FileInfo> files = new HashMap<>();
    public String subject;
    public List<UnansweredCommentInfo> unansweredComments = new ArrayList<>();
}

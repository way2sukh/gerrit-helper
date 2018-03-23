package org.helper.gerrit.core.main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.helper.gerrit.core.GerritApiUtils;
import org.helper.gerrit.core.InstanceHolder;
import org.helper.gerrit.core.PropertyUtils;
import org.helper.gerrit.model.ChangeCommentsInfo;
import org.helper.gerrit.model.CommentDetail;
import org.helper.gerrit.model.FileInfo;
import org.helper.gerrit.model.GerritProperties;
import org.helper.gerrit.model.UnansweredCommentInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gerrit.extensions.api.GerritApi;
import com.google.gerrit.extensions.client.ListChangesOption;
import com.google.gerrit.extensions.common.ChangeInfo;
import com.google.gerrit.extensions.common.CommentInfo;
import com.google.gerrit.extensions.restapi.RestApiException;

/**
 * @author Sukhpal Singh
 *
 */
public class GerritCommentsInfo {

    public static void main(String[] args) throws RestApiException, IOException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        GerritProperties gerritProperties = PropertyUtils.loadProperties();
        GerritApi gerritApi = GerritApiUtils.login(gerritProperties);
        ChangeInfo currentChangeInfo = gerritApi.changes().id(gerritProperties.changeId)
                .get(EnumSet.of(ListChangesOption.CURRENT_REVISION));

        int totalPatchSets = currentChangeInfo.revisions.values().iterator().next()._number;
        ChangeCommentsInfo outputChange = new ChangeCommentsInfo();
        outputChange.subject = currentChangeInfo.subject;

        Multimap<String, List<CommentInfo>> fileInfo = ArrayListMultimap.create();
        Map<String, CommentInfo> allComments = new HashMap<>();
        Multimap<String, String> childComments = ArrayListMultimap.create();
        for (int currentIndex = totalPatchSets; currentIndex > 0; currentIndex--) {
            Map<String, List<CommentInfo>> comments = gerritApi.changes()
                    .id(gerritProperties.changeId).revision(currentIndex).comments();
            for (Entry<String, List<CommentInfo>> entry : comments.entrySet()) {
                for (CommentInfo comment : entry.getValue()) {
                    comment.patchSet = currentIndex;
                    allComments.put(comment.id, comment);
                    if (comment.inReplyTo != null) {
                        childComments.put(comment.inReplyTo, comment.id);
                    }
                }
                fileInfo.put(entry.getKey(), entry.getValue());
            }
        }

        for (Entry<String, Collection<List<CommentInfo>>> entry : fileInfo.asMap().entrySet()) {
            Set<String> covered = new HashSet<>();
            FileInfo file = new FileInfo();
            file.name = entry.getKey();
            for (List<CommentInfo> comments : entry.getValue()) {
                for (CommentInfo comment : comments) {
                    if (comment.inReplyTo == null) {
                        CommentDetail outputComment = getComment(comment);
                        for (String commentId : childComments.get(comment.id)) {
                            outputComment.next.add(getComment(allComments.get(commentId)));
                            covered.add(commentId);
                        }
                        covered.add(comment.id);
                        if (outputComment.next.isEmpty()) {
                            file.unansweredComments.add(outputComment);
                        } else {
                            file.answeredComments.add(outputComment);
                        }
                    }
                }
            }
            outputChange.files.put(file.name, file);
        }

        for (FileInfo file : outputChange.files.values()) {
            if (!file.unansweredComments.isEmpty()) {
                for (CommentDetail oComment : file.unansweredComments) {
                    UnansweredCommentInfo comment = new UnansweredCommentInfo();
                    comment.author = oComment.author;
                    comment.fileName = file.name;
                    comment.line = oComment.line;
                    comment.patchSet = oComment.patchSet;
                    comment.comment = oComment.message;
                    outputChange.unansweredComments.add(comment);
                }

            }
        }
        System.out.println(InstanceHolder.getObjectMapper().writeValueAsString(outputChange));
    }

    private static CommentDetail getComment(CommentInfo comment) throws JsonProcessingException {

        CommentDetail outputComment = new CommentDetail();
        outputComment.author = comment.author.name;
        outputComment.line = comment.line;
        outputComment.message = comment.message;
        outputComment.patchSet = comment.patchSet;
        return outputComment;
    }

}

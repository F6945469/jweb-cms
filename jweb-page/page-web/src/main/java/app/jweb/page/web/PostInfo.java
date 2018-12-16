package app.jweb.page.web;

import app.jweb.page.web.service.PostInfoImpl;
import app.jweb.post.api.post.PostStatus;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public interface PostInfo {
    static Builder builder() {
        return new PostInfoImpl();
    }

    String id();

    String userId();

    String categoryId();

    String path();

    String templatePath();

    Integer version();

    String title();

    String description();

    String content();

    List<String> tags();

    List<String> keywords();

    String imageURL();

    List<String> imageURLs();

    Map<String, String> fields();

    PostStatus status();

    OffsetDateTime createdTime();

    OffsetDateTime updatedTime();

    String createdBy();

    String updatedBy();

    interface Builder {
        PostInfo build();

        Builder setId(String id);

        Builder setUserId(String userId);

        Builder setCategoryId(String categoryId);

        Builder setPath(String path);

        Builder setTemplatePath(String templatePath);

        Builder setVersion(Integer version);

        Builder setTitle(String title);

        Builder setDescription(String description);

        Builder setContent(String content);

        Builder setTags(List<String> tags);

        Builder setKeywords(List<String> keywords);

        Builder setImageURL(String imageURL);

        Builder setImageURLs(List<String> imageURLs);

        Builder setFields(Map<String, String> fields);

        Builder setStatus(PostStatus status);

        Builder setCreatedTime(OffsetDateTime createdTime);

        Builder setCreatedBy(String createdBy);

        Builder setUpdatedTime(OffsetDateTime updatedTime);

        Builder setUpdatedBy(String updatedBy);
    }
}

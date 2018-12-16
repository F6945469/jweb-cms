package app.jweb.post.api.post;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author chi
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CommentCreatedMessage {
    @XmlElement(name = "postId")
    public String postId;
    @XmlElement(name = "commentId")
    public String commentId;
}

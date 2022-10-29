package board.Comment.Model;

import board.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "comment")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    private int postId;

    private int parentCommentId;

    private String content;

    @Embedded
    private Writer writer;

    @Builder
    public Comment(int postId, int parentCommentId, String content,String writer){
        this.postId = postId;
        this.parentCommentId = parentCommentId;
        this.content = content;
        this.writer = new Writer(writer);
    }

}

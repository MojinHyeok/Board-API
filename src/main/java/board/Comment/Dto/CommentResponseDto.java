package board.Comment.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CommentResponseDto {

    private int commentId;

    private int postId;

    private int parentCommentId;

    private String content;

    private String writer;

}

package board.Comment.Dto;

import lombok.Data;

@Data
public class newCommentDto {

    private int postId;

    private int parentCommentId;

    private String content;

    private String writer;
}

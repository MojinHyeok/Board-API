package board.Comment.Service;

import board.Comment.Dto.newCommentDto;
import board.Comment.Model.Comment;
import board.Comment.Repository.CommentRepository;
import board.Comment.ServiceSupport.CommentServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentServiceSupport commentServiceSupport;

    public void registComment(newCommentDto commentDto) {
        Comment comment = Comment.builder()
                .postId(commentDto.getPostId())
                .parentCommentId(commentDto.getParentCommentId())
                .content(commentDto.getContent())
                .writer(commentDto.getWriter())
                .build();
        commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPostId(int postId, int requestPageNo) {
        PageImpl<Comment> pageInfo;
        pageInfo = commentServiceSupport.getCommentsByPostId(postId, requestPageNo);
        return pageInfo.getContent();
    }


}

package board.Comment.Service;

import board.Comment.Dto.CommentResponseDto;
import board.Comment.Dto.newCommentDto;
import board.Comment.Model.Comment;
import board.Comment.Repository.CommentRepository;
import board.Comment.ServiceSupport.CommentServiceSupport;
import board.Security.Util.AESUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentServiceSupport commentServiceSupport;

    @SneakyThrows
    public void registComment(newCommentDto commentDto) {
        AESUtil aesUtil = new AESUtil();
        Comment comment = Comment.builder()
                .postId(commentDto.getPostId())
                .parentCommentId(commentDto.getParentCommentId())
                .content(commentDto.getContent())
                .writer(commentDto.getWriter())
                .build();
        comment.getWriter().encodeEmail(aesUtil);
        commentRepository.save(comment);
    }

    public List<CommentResponseDto> getCommentsByPostId(int postId, int requestPageNo) throws Exception {
        AESUtil aesUtil = new AESUtil();
        PageImpl<Comment> pageInfo;
        pageInfo = commentServiceSupport.getCommentsByPostId(postId, requestPageNo);
        List<Comment> comments = pageInfo.getContent();
        for (Comment comment : comments) {
            comment.getWriter().decodeEmail(aesUtil);
            comment.getWriter().maskingEmail();
        }
        return comments.stream().map(it -> CommentResponseDto.builder()
                        .commentId(it.getCommentId())
                        .parentCommentId(it.getPostId())
                        .parentCommentId(it.getParentCommentId())
                        .content(it.getContent())
                        .writer(it.getWriter().toString()).build())
                .collect(Collectors.toList());
    }


}

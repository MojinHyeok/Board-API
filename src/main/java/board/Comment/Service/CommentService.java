package board.Comment.Service;

import board.Comment.Dto.CommentResponseDto;
import board.Comment.Dto.newCommentDto;
import board.Comment.Model.Comment;
import board.Comment.Repository.CommentRepository;
import board.Comment.ServiceSupport.CommentServiceSupport;
import board.Post.Model.Post;
import board.Post.Repository.PostRepository;
import board.Security.Util.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {


    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentServiceSupport commentServiceSupport;


    public CommentService(CommentRepository commentRepository, PostRepository postRepository, CommentServiceSupport commentServiceSupport) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentServiceSupport = commentServiceSupport;
    }

    public void registComment(newCommentDto commentDto) throws Exception {
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
        Post post = postRepository.findByPostId(postId).orElseThrow();
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

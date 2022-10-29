package board.Comment.ServiceSupport;

import board.Comment.Model.Comment;
import board.Comment.Model.QComment;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceSupport extends QuerydslRepositorySupport {


    private QComment comment = QComment.comment;

    public CommentServiceSupport() {
        super(Comment.class);
    }

    public PageImpl<Comment> getCommentsByPostId(int postId, int requestPageNo) {
        JPQLQuery<Comment> query = getQuerydsl().createQuery()
                .select(comment)
                .from(comment)
                .where(comment.postId.eq(postId))
                .orderBy(comment.createdAt.asc());
        PageRequest pageRequest = PageRequest.of(requestPageNo, 10);
        List<Comment> list = getQuerydsl().applyPagination(pageRequest, query).fetch();
        return new PageImpl<>(list, pageRequest, query.fetchCount());
    }
}

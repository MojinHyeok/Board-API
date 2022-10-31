package board.Comment.Controller;

import board.Comment.Dto.CommentResponseDto;
import board.Comment.Dto.newCommentDto;
import board.Comment.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment")
    public ResponseEntity<Map<String, Object>> registComment(@RequestBody newCommentDto commnetDto) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();
        commentService.registComment(commnetDto);
        resultMap.put("성공 여부", "succes");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @GetMapping("/comment")
    public ResponseEntity<Map<String, Object>> getComments(@RequestParam int postId, @RequestParam int requestPageNo) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();
        List<CommentResponseDto> comments = commentService.getCommentsByPostId(postId, requestPageNo);
        resultMap.put("data", comments);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}

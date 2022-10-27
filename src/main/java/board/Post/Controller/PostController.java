package board.Post.Controller;

import board.Post.Dto.newPostDto;
import board.Post.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/post/regist")
    public ResponseEntity<Map<String, Object>> registPost(@RequestBody newPostDto postDto) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();
        postService.registPost(postDto);
        resultMap.put("성공 여부", "succes");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}

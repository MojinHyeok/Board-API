package board.Post.Service;

import board.Post.Dto.newPostDto;
import board.Post.Model.Post;
import board.Post.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public void registPost(newPostDto postDto) {
        Post post = Post.builder()
                .title(postDto.getTitle()).content(postDto.getTitle())
                .writer(postDto.getWriter())
                .build();
        postRepository.save(post);
    }

}

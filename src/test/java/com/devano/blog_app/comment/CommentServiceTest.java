package com.devano.blog_app.comment;

import com.devano.blog_app.entity.Post;
import com.devano.blog_app.repository.jpa.PostRepository;
import com.devano.blog_app.request.comment.CreateCommentRequest;
import com.devano.blog_app.response.comment.CreateCommentResponse;
import com.devano.blog_app.service.CommentService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Import(value = {CommentService.class})
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("""

            """)
    public void save_comment_should_return_saved_comment() {
        Post save = postRepository.save(Post.builder()
                .title("memasak nasi")
                .body("memasak nasi itu menyenangkan")
                .slug("slug 1")
                .isPublished(true)
                .isDeleted(false)
                .commentCount(0)
                .build()
        );
        Integer postId = save.getId().intValue();
        CreateCommentRequest commentRequest = CreateCommentRequest.builder()
                .email("devanozaidan@gmail.com")
                .name("devanozaidan")
                .body("wah menarik sekali postingannya")
                .build();
        CreateCommentResponse commentResponse = commentService.createComment(postId, commentRequest);
        Post post = postRepository.findById(postId).get();
        Assertions.assertEquals(1,post.getCommentCount());
    }
}

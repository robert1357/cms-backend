package pe.edu.unap.oti.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unap.oti.cms.model.Post;
import pe.edu.unap.oti.cms.repository.PostRepository;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.18.24:3000", "http://localhost:8085"})
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable Long id) {
        return postRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Post create(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable Long id, @RequestBody Post postDetails) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
        
        post.setTitle(postDetails.getTitle());
        post.setSummary(postDetails.getSummary());
        post.setContent(postDetails.getContent());
        post.setImageUrl(postDetails.getImageUrl());
        post.setVideoUrl(postDetails.getVideoUrl());
        post.setVideoThumbnail(postDetails.getVideoThumbnail());
        post.setHeroPost(postDetails.isHeroPost());
        post.setFeatured(postDetails.isFeatured());
        post.setPublished(postDetails.isPublished());

        return postRepository.save(post);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        postRepository.deleteById(id);
    }
}
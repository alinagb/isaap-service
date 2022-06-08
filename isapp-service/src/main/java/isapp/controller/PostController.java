package isapp.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import isapp.model.Post;
import isapp.model.UserFavoritePost;
import isapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static isapp.controller.RegistrationController.method;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("")
    public ResponseEntity<List<Post>> getPosts() throws Exception {
//        ObjectMapper mapper = getJsonParserMapper();
//        Post createdPost = mapper.readValue(post, Post.class);
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPosts());
    }


    @PostMapping("/users/{userId}")
    public ResponseEntity<Post> createPost(@RequestParam(value = "files",required = false) MultipartFile[] files, @RequestParam(value = "post", required = false) String post, @PathVariable UUID userId) throws Exception {
        ObjectMapper mapper = getJsonParserMapper();
        Post createdPost = mapper.readValue(post, Post.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(files, createdPost, userId));
    }
    private ObjectMapper getJsonParserMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        return mapper;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable UUID postId) throws Exception {
//        ObjectMapper mapper = getJsonParserMapper();
//        Post createdPost = mapper.readValue(post, Post.class);
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(postId));
    }


    @GetMapping(value= "/image/{userId}/{fileId}", produces = org.springframework.http.MediaType.IMAGE_JPEG_VALUE)
    private ResponseEntity<byte[]> getFile(@PathVariable UUID userId, @PathVariable UUID fileId, HttpServletResponse response) throws IOException {
        String pathname = "/Users/albica/Desktop/disertatie/isapp-service/src/main/resources/static/images/postImages/" + userId + "/"+fileId+".png";
        File file = ResourceUtils.getFile(pathname);
        byte[] array = method(file);

        return ResponseEntity
                .ok()
                .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
                .body(array);
    }

}

package isapp.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.primitives.Bytes;
import isapp.model.Post;
import isapp.model.UserFavoritePost;
import isapp.model.user.User;
import isapp.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;
    @Autowired
    ResourceLoader resourceLoader;

    ApplicationContext context;

    private Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @PostMapping("/users")
    public ResponseEntity<String> userRegistration(@RequestBody User user) throws Exception {
        logger.info("hitting /registration/users route");

        registrationService.registerUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("created");
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) throws Exception {
        logger.info("hitting /users/email route");

        User user = registrationService.getUserByEmail(email);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping(value = "/users/{email}", consumes = { MediaType.TEXT_PLAIN,MediaType.MULTIPART_FORM_DATA})
    public ResponseEntity<User> updateUser(@RequestParam(value = "file",required = false) MultipartFile file, @PathVariable String email, @RequestParam(value = "user", required = false) String user, HttpServletRequest request) throws Exception {
        logger.info("hitting /users/email update route");
        System.out.println("user" + user);
        System.out.println("file" + file);

        ObjectMapper mapper = getJsonParserMapper();

        User userUpdated = mapper.readValue(user, User.class);

        User userCreated = registrationService.updateUser(file, email, userUpdated, request);

        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }

    @GetMapping(value= "/profile/{id}", produces = org.springframework.http.MediaType.IMAGE_JPEG_VALUE)
    private ResponseEntity<byte[]> getFile(@PathVariable UUID id, HttpServletResponse response) throws IOException {
        String pathname = "/Users/albica/Desktop/disertatie/isapp-service/src/main/resources/static/images/profile/" + id + ".png";
        File file = ResourceUtils.getFile(pathname);
        byte[] array = method(file);

        return ResponseEntity
                .ok()
                .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
                .body(array);
    }

    public Set<String> listFilesUsingDirectoryStream(String dir) throws IOException {
        Set<String> fileList = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileList.add(path.getFileName()
                            .toString());
                }
            }
        }
        return fileList;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() throws Exception {
        logger.info("hitting /users route");

        List<User> user = registrationService.getAllUsers();

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    public static byte[] method(File file)
            throws IOException
    {

        // Creating an object of FileInputStream to
        // read from a file
        FileInputStream fl = new FileInputStream(file);

        // Now creating byte array of same length as file
        byte[] arr = new byte[(int)file.length()];

        // Reading file content to byte array
        // using standard read() method
        fl.read(arr);

        // lastly closing an instance of file input stream
        // to avoid memory leakage
        fl.close();

        // Returning above byte array
        return arr;
    }

    private ObjectMapper getJsonParserMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        return mapper;
    }

    @PostMapping("/{postId}/{userId}")
    public ResponseEntity<User> updatePostAsFavourite(@PathVariable UUID postId, @PathVariable UUID userId) throws Exception {
//        ObjectMapper mapper = getJsonParserMapper();
//        Post createdPost = mapper.readValue(post, Post.class);
        return ResponseEntity.status(HttpStatus.OK).body(registrationService.updatePostAsFavourite(postId,userId ));
    }
    @GetMapping("/favorites/{email}")
    public ResponseEntity<Set<Post>> getFavouritePosts(@PathVariable String email) throws Exception {
//        ObjectMapper mapper = getJsonParserMapper();
//        Post createdPost = mapper.readValue(post, Post.class);
        return ResponseEntity.status(HttpStatus.OK).body(registrationService.getFavPosts(email));
    }
}

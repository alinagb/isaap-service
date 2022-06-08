package isapp.service;

import isapp.model.Faculty;
import isapp.model.FileRequest;
import isapp.model.Post;
import isapp.model.UserFavoritePost;
import isapp.model.user.User;
import isapp.repository.FacultyRepository;
import isapp.repository.FileRepository;
import isapp.repository.PostRepository;
import isapp.repository.UserFavoritePostRepository;
import isapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private UserFavoritePostRepository userFavoritePostRepository;
    @Autowired
    private FileRepository fileRepository;

    private final static String PATH = "/Users/albica/Desktop/disertatie/isapp-service/src/main/resources/static/images/postImages/";


    public Post createPost(MultipartFile[] files, Post post, UUID userId) throws Exception {

        Post postCreated  = new Post();
        Set<FileRequest> newFiles = new HashSet<>();
        Arrays.stream(files).forEach(file -> {
            try {
                String directoryName = PATH.concat(String.valueOf(userId));
//                String fileName = file.getOriginalFilename();

                File directory = new File(directoryName);
                if (! directory.exists()){
                    directory.mkdir();
                }

                FileRequest fileRequest = new FileRequest();
//                fileRequest.setPath(f.getPath());
                fileRequest.setName(file.getOriginalFilename());
                FileRequest save = fileRepository.save(fileRequest);

                File f = new File(directoryName + "/" + save.getFileId()+ ".png");
                file.transferTo(f);

                fileRequest.setPath(f.getPath());
                newFiles.add(save);
                System.out.println("File successfully saved as " + f.getAbsolutePath());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        postCreated.setFileSet(newFiles);
        postCreated.setMainPhoto(newFiles.stream().findFirst().orElseThrow());
        postCreated.setOwner(userId);
        Set<User> users = new HashSet<>();
        for(User user: post.getUserSet()){
            User userByEmail = registrationService.getUserByEmail(user.getEmail());
            users.add(userByEmail);
        }
        Set<Faculty> faculties = new HashSet<>();
        for(Faculty faculty : post.getFacultySet()){
            Optional<Faculty> findById =  facultyRepository.findById(faculty.getFacultyId());
            if(findById.isPresent()){
                faculties.add(findById.get());
            }else{
                throw new Exception("Faculty doesn t exist");
            }
        }
        postCreated.setFacultySet(faculties);
        postCreated.setUserSet(users);
        postCreated.setLng(post.getLng());
        postCreated.setLat(post.getLat());
        postCreated.setDescription(post.getDescription());
        postCreated.setPrice(post.getPrice());
        postCreated.setTitle(post.getTitle());
        return postRepository.save(postCreated);
    }

    public Post getPost(UUID postId) throws Exception {
        Optional<Post> byId = postRepository.findById(postId);
        if(byId.isPresent()){
            return byId.get();
        }else{
            throw new Exception("Post doesnt exist");

        }
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }


}

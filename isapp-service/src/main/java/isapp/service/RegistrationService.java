package isapp.service;

import isapp.client.AuthClient;
import isapp.model.FileRequest;
import isapp.model.Post;
import isapp.model.UserFavoritePost;
import isapp.model.user.User;
import isapp.model.user.UserKeycloak;
import isapp.repository.FileRepository;
import isapp.repository.PostRepository;
import isapp.repository.UserFavoritePostRepository;
import isapp.repository.UserRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class RegistrationService {

    @Autowired
    private AuthClient authClient;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserFavoritePostRepository userFavoritePostRepository;
    public User registerUser(User userRegistration) throws Exception {
        UserKeycloak userKeycloak = new UserKeycloak(userRegistration.getEmail(), userRegistration.getPassword());
        Response response = authClient.registerUser(userKeycloak);
        if (response.getStatus() == HttpStatus.SC_CREATED) {
            return userRepository.save(userRegistration);
        } else {
            throw new Exception("The registration user failed");
        }

    }

    public User getUserByEmail(String email) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
//            User u= ;
//            u.getFavoriteSet();
//            Set<Post> fav = new HashSet<>();
//            Optional<List<UserFavoritePost>> allByUser = userFavoritePostRepository.findAllByUser(user.get());
//            allByUser.ifPresent(userFavoritePosts -> userFavoritePosts.forEach(p -> fav.add(p.getPost())));
//            u.setFavoriteSet(fav);
            return user.get();
        } else {
            throw new Exception("The user doesn't exist");
        }
    }
    public Set<Post> getFavPosts( String email) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
                Set<Post> fav = new HashSet<>();
                Optional<List<UserFavoritePost>> allByUser = userFavoritePostRepository.findAllByUser(user.get());
                allByUser.ifPresent(userFavoritePosts -> userFavoritePosts.forEach(p -> fav.add(p.getPost())));
                return fav;

        } else {
            throw new Exception("The user doesn't exist");
        }
    }


    public User updateUser(MultipartFile file, String email, User user, HttpServletRequest request) throws Exception {
        Optional<User> userBD = userRepository.findByEmail(email);

        if (userBD.isPresent()) {
            User userUpdated = userBD.get();

            userUpdated.setName(user.getName());
            userUpdated.setPhone(user.getPhone());
            userUpdated.setFacultyId(user.getFacultyId());
            userUpdated.setDescription(user.getDescription());
            String pathname = "/Users/albica/Desktop/disertatie/isapp-service/src/main/resources/static/images/profile/" + userBD.get().getUserId() + "." + FilenameUtils.getExtension(file.getOriginalFilename());

            if (userBD.get().getProfilePhoto() != null) {
//                new ClassPathResource("/images/profile/" + userBD.get().getUserId() + "." + FilenameUtils.getExtension(file.getOriginalFilename())).getFile().delete();
                try{
                    boolean result = Files.deleteIfExists(Paths.get(pathname)); //surround it in try catch block
                    if(result){
                        System.out.println("deleteteeeed");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

                Optional<FileRequest> byPath = fileRepository.findByPath(pathname);
                userBD.get().setProfilePhoto(null);
                if (byPath.isPresent()) {
                    fileRepository.delete(byPath.get());
                }
            }

            File f = new File(pathname);
            file.transferTo(f);
            System.out.println("File successfully saved as " + f.getAbsolutePath());

            FileRequest fileDB = new FileRequest();
            fileDB.setName(file.getOriginalFilename());
            fileDB.setPath(f.getPath());
            userUpdated.setProfilePhoto(fileDB);


            return userRepository.save(userUpdated);
        } else {
            throw new Exception("Error");
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User updatePostAsFavourite(UUID postId, UUID userId) throws Exception {
        Optional<Post> postById = postRepository.findById(postId);
        Optional<User> userById = userRepository.findById(userId);
        if(postById.isPresent()) {
            if (userById.isPresent()) {
                User user = userById.get();
                Set<Post> posts = new HashSet<>();
                posts.add(postById.get());
                user.setFavoriteSet(posts);
                return userRepository.save(user);
            }else{
                throw new Exception("User doesn't exist") ;
            }
        }else{
            throw new Exception("Post doesn't exist");

        }
    }
}

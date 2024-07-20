package com.bid90.psm.service;

import com.bid90.psm.exceptions.ResourceNotFoundException;
import com.bid90.psm.model.Post;
import com.bid90.psm.repository.PostRepository;
import io.minio.GetObjectArgs;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    final PostRepository postRepository;
    final MinioService minioService;


    public PostServiceImpl(PostRepository postRepository, MinioService minioService) {
        this.postRepository = postRepository;
        this.minioService = minioService;
    }

    @Override
    public Post findOneById(Long id) {
        return postRepository.findOneById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id: " + id + " not found."));
    }

    @Override
    public Post create(MultipartFile file, String title, String subtitle, String description) {
        var newPost = new Post();
        newPost.setTitle(title);
        newPost.setSubtitle(subtitle);
        newPost.setDescription(description);

        if(file != null && file.getOriginalFilename() != null){
            String newFileName = changeOriginalFilename(file.getOriginalFilename());
            newPost.setImageName(newFileName);
            minioService.uploadFile(file,newFileName);
        }

        return postRepository.save(newPost);

    }

    @Override
    public Post update(Long id, MultipartFile file, String title, String subtitle, String description) {
        var post = findOneById(id);
        Optional.ofNullable(title).ifPresent(post::setTitle);
        Optional.ofNullable(subtitle).ifPresent(post::setSubtitle);
        Optional.ofNullable(description).ifPresent(post::setDescription);

        Optional.ofNullable(file).ifPresent(multipartFile -> {
            if(post.getImageName() != null){
                minioService.deleteFile(post.getImageName());
            }

            if(file.getOriginalFilename() != null){
                String newFileName = changeOriginalFilename(file.getOriginalFilename());
                post.setImageName(newFileName);
                minioService.uploadFile(file,newFileName);
            }
        });

        return postRepository.save(post);
    }

    @Override
    public void delete(Long id) {
        var post = findOneById(id);
        if(!post.getImageName().isBlank()){
            minioService.deleteFile(post.getImageName());
        }
        postRepository.delete(post);
    }

    @Override
    public void getImage(String imageName, HttpServletResponse response) {
        try (InputStream inputStream =minioService.downloadFile(imageName)) {
            String contentType = getContentType(imageName);
            if (contentType == null) {
                contentType = "application/octet-stream"; // default to binary if type is unknown
            }
            response.setContentType(contentType);
            org.apache.commons.io.IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Image not found");
        }
    }

    @Override
    public Page<Post> listPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    /**
     * Retrieves the content type (MIME type) of a file based on its filename.
     *
     * @param fileName the name of the file
     * @return the content type (MIME type) of the file
     * @throws Exception if an I/O error occurs or the file type cannot be determined
     */
    private String getContentType(String fileName) throws Exception {
        Path path = Paths.get(fileName);
        return Files.probeContentType(path);
    }

    /**
     * Changes the original filename by generating a new unique filename with the same extension.
     *
     * @param originalFilename the original filename
     * @return the new unique filename with the same extension
     */
    public static String changeOriginalFilename(String originalFilename) {
        String extension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = originalFilename.substring(dotIndex);
        }
        return UUID.randomUUID().toString().replace("-", "") + extension;
    }
}

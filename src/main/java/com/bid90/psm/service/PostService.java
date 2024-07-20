package com.bid90.psm.service;

import com.bid90.psm.model.Post;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.nio.channels.FileChannel;

public interface PostService {
    Post findOneById(Long id);

    Post create(MultipartFile file, String title, String subtitle, String description);

    Post update(Long id, MultipartFile file, String title, String subtitle, String description);

    void delete(Long id);

    void getImage(String imageName, HttpServletResponse response);
    Page<Post> listPosts(Pageable pageable);
}

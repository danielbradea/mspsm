package com.bid90.psm.controller;

import com.bid90.psm.model.dto.*;
import com.bid90.psm.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    @Operation(
            summary = "List posts",
            description = "Get a list of all posts"
    )
    public PageList<PostDto> listUsers(Pageable pageable) {
        var pageList = new PageList<>(postService.listPosts(pageable).map(PostDto::new));
        pageList.setMessage("Post list retrieved successfully.");
        pageList.setSuccess(true);
        return pageList;
    }


    @Operation(
            summary = "Get Post by ID",
            description = "Retrieve details of a post by its unique ID."
    )
    @GetMapping("/{id}")
    public ResponseContent<PostDto> getPostById(@PathVariable Long id) {
        return new ResponseContent<>(new PostDto(postService.findOneById(id)));
    }

    @Operation(
            summary = "Create Post",
            description = "Create a new post with the provided details.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseContent<PostDto> createPost(@RequestParam("file")  MultipartFile file,
                                               @RequestParam("title")  String title,
                                               @RequestParam("subtitle")  String subtitle,
                                               @RequestParam("description")  String description) {
        return new ResponseContent<>(new PostDto(postService.create(file, title,subtitle,description)));
    }

    @Operation(
            summary = "Update Post",
            description = "Update post with the provided details.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PatchMapping("/{id}")
    public Object updatePost(@PathVariable Long id,
                             @RequestParam("file")  MultipartFile file,
                             @RequestParam("title")  String title,
                             @RequestParam("subtitle")  String subtitle,
                             @RequestParam("description")  String description) throws Exception {

        return new ResponseContent<>(new PostDto(postService.update(id,file,title,subtitle,description)));
    }

    @Operation(
            summary = "Delete Post",
            description = "Delete post by id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/{id}")
    public Response deletePost(@PathVariable Long id) {
        postService.delete(id);
        var response = new Response();
        response.setMessage("Post successfully deleted for ID: " + id);
        response.setSuccess(true);
        return response;
    }

    @Operation(
            summary = "Get image",
            description = "Get image by name"
    )
    @GetMapping("/images/{imageName}")
    public void getImage(@PathVariable String imageName, HttpServletResponse response) throws Exception {
            postService.getImage(imageName,response);

    }
}
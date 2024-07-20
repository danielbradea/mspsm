package com.bid90.psm.model.dto;

import com.bid90.psm.model.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {

    Long id;
    String title;
    String subtitle;
    String description;
    String imageName;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;

    public PostDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.subtitle = post.getSubtitle();
        this.description = post.getDescription();
        this.imageName = post.getImageName();
        this.createdDate = post.getCreatedDate();
        this.updatedDate = post.getUpdatedDate();
    }
}

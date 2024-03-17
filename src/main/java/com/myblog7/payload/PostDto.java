package com.myblog7.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
public class PostDto {

    private Long id;

    @NotEmpty
    @Size(min = 2,message = "Post title should be at least 2")
    private String title;

    @NotEmpty
    @Size(min = 4,message = "Post description should be at least 4")
    private String description;

    @NotEmpty
    @Size(min = 5,message = "Post content should be at least 5")
    private String Content;

}

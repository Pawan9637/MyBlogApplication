package com.myblog7.controller;

import com.myblog7.payload.PostDto;
import com.myblog7.payload.PostResponse;
import com.myblog7.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //http://localhost:8080/api/post
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> savePost(@Valid @RequestBody PostDto postDto, BindingResult result){
        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.savePost(postDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

//    {
//        Flow Summary:PostMapping
//
//        When the client sends an HTTP POST request to /api/post, the request is received by the PostController.
//        The PostController validates the incoming PostDto object using the validation annotations defined in the PostDto class.
//        If there are validation errors, the controller returns an error response with the appropriate validation messages.
//        If the validation passes, the controller calls the savePost method in the PostServiceImpl.
//        The service method converts the PostDto to a Post entity, saves it using the PostRepository, and converts the saved Post back to a PostDto.
//        The saved PostDto is then returned as the response to the client with HTTP status code 201 (CREATED) if the post was successfully saved.
//        That's the overall flow of the project based on the provided code snippets and the usage of the MVC architecture.
//        The code follows the general pattern of handling incoming HTTP requests in the controller, processing the business logic in the service layer,
//        and interacting with the database through the repository. The data is transferred between the client and the server using DTOs to separate the
//         internal structure from the external representation.
//    }


    //http://localhost:8080/api/post/1
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post is deleted",HttpStatus.OK);
    }

    //http://localhost:8080/api/post/1
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id, @RequestBody PostDto postDto){
        PostDto dto = postService.updatePost(id,postDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //http://localhost:8080/api/post/1
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
       PostDto dto = postService.getPostById(id);
       return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //To get List of Object:
    //Pagenation: http://localhost:8080/api/post?pageNo=0&pageSize=2&sortBy=title&sortDir=desc
    @GetMapping
    public  PostResponse getPosts(
         @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
         @RequestParam(value = "pageSize",defaultValue = "2",required = false) int pageSize,
         @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
         @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
      PostResponse postResponse = postService.getPosts(pageNo,pageSize,sortBy,sortDir);
        return postResponse;
    }

}

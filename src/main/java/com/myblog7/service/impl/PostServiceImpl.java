package com.myblog7.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.myblog7.entity.Post;
import com.myblog7.exception.ResourcesNotFound;
import com.myblog7.payload.PostDto;
import com.myblog7.payload.PostResponse;
import com.myblog7.repository.PostRepository;
import com.myblog7.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto savePost(PostDto postDto) {

        Post post = mapToEntity(postDto);

        Post savedPost = postRepository.save(post);

        PostDto dto = mapToDto(savedPost);

        return dto;

    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
          Post  post = postRepository.findById(id).orElseThrow(
                  ()->new ResourcesNotFound("Post Not Found with id:"+id)
          );

          post.setTitle(postDto.getTitle());
          post.setContent(postDto.getContent());
          post.setDescription(postDto.getDescription());

          Post updatePost = postRepository.save(post);

           PostDto dto = mapToDto(updatePost);
           return dto;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourcesNotFound("Post Not Found With Id:" + id)
        );
        PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    public PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort   = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
        Sort.by(sortBy).ascending():
        Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Post> pagePosts = postRepository.findAll(pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponseo = new PostResponse();
        postResponseo.setPostDto(postDtos);
        postResponseo.setPageNo(pagePosts.getNumber());
        postResponseo.setPageSize(pagePosts.getSize());
        postResponseo.setTotalElements(pagePosts.getTotalElements());
        postResponseo.setTotalPages(pagePosts.getTotalPages());
        postResponseo.setLast(pagePosts.isLast());

        return postResponseo;
    }

    //Dto-->Entity
    Post mapToEntity(PostDto postDto){
         Post post = modelMapper.map(postDto,Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }

    PostDto mapToDto(Post post){
  PostDto dto = modelMapper.map(post,PostDto.class);
//        PostDto dto = new PostDto();
//        dto.setId(Post.getId());
//        dto.setTitle(Post.getTitle());
//        dto.setDescription(Post.getDescription());
//        dto.setContent(Post.getContent());
        return dto;
    }

}

package com.myblog7.service.impl;

import com.myblog7.entity.Comment;
import com.myblog7.entity.Post;
import com.myblog7.exception.ResourcesNotFound;
import com.myblog7.payload.CommentDto;
import com.myblog7.repository.CommentRepository;
import com.myblog7.repository.PostRepository;
import com.myblog7.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourcesNotFound("Post not found ith id:"+postId)
        );

        comment.setPost(post);

        // Explicitly specify the types for the save method
        Comment savedComment = commentRepository.save(comment);

        return mapToDto(savedComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourcesNotFound("Post not found with id:"+postId)
        );
          List<Comment> comments =  commentRepository.findByPostId(postId);

          List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto getCommentsById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourcesNotFound("Post not found with id:"+postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourcesNotFound("Comment not found with id:"+commentId)
        );

        CommentDto commentDto = mapToDto(comment);

        return commentDto;
    }

    @Override
    public List<CommentDto> getAllCommentsById() {
        List<Comment> comments = commentRepository.findAll();

        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourcesNotFound("Post not found with id:"+postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourcesNotFound("Comment not found with id:"+commentId)
        );

        commentRepository.deleteById(commentId);
    }


    private CommentDto mapToDto(Comment savedComment) {
        CommentDto dto = modelMapper.map(savedComment, CommentDto.class);
        return dto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }
}


//package com.myblog7.service.impl;
//
//import com.myblog7.entity.Comment;
//import com.myblog7.entity.Post;
//import com.myblog7.exception.ResourcesNotFound;
//import com.myblog7.payload.CommentDto;
//import com.myblog7.repository.CommentRepository;
//import com.myblog7.repository.PostRepository;
//import com.myblog7.service.CommentService;
//import org.modelmapper.ModelMapper;
//
//public class CommentServiceImpl implements CommentService {
//
//    private CommentRepository commentRepository;
//    private PostRepository postRepository;
//    private ModelMapper modelMapper;
//
//    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
//        this.commentRepository = commentRepository;
//        this.postRepository = postRepository;
//        this.modelMapper = modelMapper;
//    }
//
//
//
//    @Override
//    public CommentDto createComment(long postId, CommentDto commentDto) {
//        Comment comment = mapToEntity(commentDto);
//
//        Post post = postRepository.findById(postId).orElseThrow(
//                ()-> new ResourcesNotFound("Post not found ith id:"+postId)
//        );
//
//        comment.setPost(post);
//
//         Comment savedcomment = commentRepository.save(comment);
//
//       CommentDto dto = mapToDto(savedcomment);
//        return dto;
//    }
//
//    private CommentDto mapToDto(Comment savedcomment) {
//        CommentDto dto = modelMapper.map(savedcomment, CommentDto.class);
//        return dto;
//    }
//
//    private Comment mapToEntity(CommentDto commentDto) {
//        Comment comment= modelMapper.map(commentDto,Comment.class);
//        return comment;
//    }
//}

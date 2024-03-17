

package com.myblog7.repository;

import com.myblog7.entity.Comment; // Make sure the correct Comment class is imported

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Your custom methods (if any) go here
         List<Comment> findByPostId(long postId);
}


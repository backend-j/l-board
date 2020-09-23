package me.backendj.lboard.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("select p from Posts p where p.deleted = false order by p.createdDate desc")
    List<Posts> findAllDesc();
}

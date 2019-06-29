package com.esi.project.buildit.planthire.procurement.domain.repository;

import com.esi.project.buildit.planthire.procurement.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}

package com.tenPines.persistence;

import com.tenPines.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface WishlistRepository extends JpaRepository<Wish, Long> {
}


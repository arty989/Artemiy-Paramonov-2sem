
package com.example.S2_H1.repository;

import com.example.S2_H1.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
  @Transactional
  void deleteByUserUserId(Long userId);
}
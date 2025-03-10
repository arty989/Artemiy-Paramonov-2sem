
package com.example.S2_H1.repository;

import com.example.S2_H1.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
  void deleteByUserUserId(Long userId);
}
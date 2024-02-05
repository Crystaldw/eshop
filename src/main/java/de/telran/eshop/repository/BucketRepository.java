package de.telran.eshop.repository;

import de.telran.eshop.entity.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {
    Bucket findAllByUserId (Long userId);
}

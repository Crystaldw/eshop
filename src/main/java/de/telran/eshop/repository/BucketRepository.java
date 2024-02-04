package de.telran.eshop.repository;

import de.telran.eshop.entity.Bucket;
import de.telran.eshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {

    List<Bucket> findAllByUserOrderByCreateDateDesc(User user);
}

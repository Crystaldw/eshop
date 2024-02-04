package de.telran.eshop.service;

import de.telran.eshop.dto.BucketDTO;
import de.telran.eshop.entity.Bucket;
import de.telran.eshop.entity.User;

import java.util.List;

public interface BucketService {

    Bucket createBucked(User user, List<Long> productIds);

    void addProducts(Bucket bucket, List<Long> productIds);



    BucketDTO getBucketByUser(String name);
}

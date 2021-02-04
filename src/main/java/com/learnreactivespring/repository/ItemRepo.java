package com.learnreactivespring.repository;

import com.learnreactivespring.domain.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends ReactiveMongoRepository<Item, Long> {
}

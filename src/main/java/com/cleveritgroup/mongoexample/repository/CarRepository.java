package com.cleveritgroup.mongoexample.repository;

import com.cleveritgroup.mongoexample.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepository extends MongoRepository<Car, String> { }
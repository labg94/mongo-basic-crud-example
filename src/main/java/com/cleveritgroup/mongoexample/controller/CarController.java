package com.cleveritgroup.mongoexample.controller;

import com.cleveritgroup.mongoexample.payload.request.CarRequest;
import com.cleveritgroup.mongoexample.payload.response.CarResponse;
import com.cleveritgroup.mongoexample.service.CarService;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping(value = "car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;


    @GetMapping(value = "{id}")
    public CarResponse getById(@PathVariable String id) {
        return carService.findById(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) {
        carService.deleteById(id);
    }


    @GetMapping
    public List<CarResponse> getAll() {
        return carService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponse create(@RequestBody CarRequest request) {
        return carService.create(request);
    }

    @PutMapping
    public CarResponse update(@RequestBody CarRequest request) {
        return carService.update(request);
    }

}
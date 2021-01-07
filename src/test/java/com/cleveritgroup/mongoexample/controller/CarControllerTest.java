package com.cleveritgroup.mongoexample.controller;

import com.cleveritgroup.mongoexample.exception.ErrorController;
import com.cleveritgroup.mongoexample.exception.ErrorMessage;
import com.cleveritgroup.mongoexample.model.Car;
import com.cleveritgroup.mongoexample.payload.request.CarRequest;
import com.cleveritgroup.mongoexample.payload.response.CarResponse;
import com.cleveritgroup.mongoexample.repository.CarRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CarControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    @Autowired
    private CarController carController;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ErrorController errorController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();
        this.mockMvc = MockMvcBuilders.standaloneSetup(carController).setControllerAdvice(errorController).build();
    }


    @Test
    void getById() throws Exception {

        Car first = Car.builder().model("Siena").brand("Fiat").build();


        Car saved = carRepository.save(first);

        String jsonCar = mockMvc.perform(get("/car/" + saved.getId())
                .header("Content-type", "application/json")
        )
                                .andDo(print())
                                .andExpect(status()
                                        .is2xxSuccessful())
                                .andReturn().getResponse().getContentAsString();


        CarResponse carResponse = objectMapper.readValue(jsonCar, CarResponse.class);

        assertEquals("Siena", carResponse.getModel());
        assertEquals("Fiat", carResponse.getBrand());

    }

    @Test
    void getByIdNotFound() throws Exception {


        MvcResult mvcResult = mockMvc.perform(get("/car/1")
                .header("Content-type", "application/json"))
                                     .andDo(print())
                                     .andExpect(status()
                                             .is(HttpStatus.NOT_FOUND.value()))
                                     .andReturn();


        String contentAsString = mvcResult.getResponse().getContentAsString();


        ErrorMessage errorMessage = objectMapper.readValue(contentAsString, ErrorMessage.class);

        assertEquals("Car with id 1 not found", errorMessage.getMessage());
        assertNotNull(errorMessage.getTimestamp());
        assertEquals(HttpStatus.NOT_FOUND.value(), errorMessage.getStatusCode());
        assertEquals("uri=/car/1", errorMessage.getDescription());

    }
    @Test
    void deleteByIdNotFound() throws Exception {


        MvcResult mvcResult = mockMvc.perform(delete("/car/1")
                .header("Content-type", "application/json"))
                                     .andDo(print())
                                     .andExpect(status()
                                             .is(HttpStatus.NOT_FOUND.value()))
                                     .andReturn();


        String contentAsString = mvcResult.getResponse().getContentAsString();


        ErrorMessage errorMessage = objectMapper.readValue(contentAsString, ErrorMessage.class);

        assertEquals("Car with id 1 not found", errorMessage.getMessage());
        assertNotNull(errorMessage.getTimestamp());
        assertEquals(HttpStatus.NOT_FOUND.value(), errorMessage.getStatusCode());
        assertEquals("uri=/car/1", errorMessage.getDescription());

    }

    @Test
    void deleteById() throws Exception {


        Car first = Car.builder().model("Siena").brand("Fiat").build();

        Car saved = carRepository.save(first);

        mockMvc.perform(delete("/car/" + saved.getId())
                .header("Content-type", "application/json"))
               .andDo(print())
               .andExpect(status()
                       .is(HttpStatus.NO_CONTENT.value()))
               .andReturn();

        List<Car> all = carRepository.findAll();

        assertTrue(all.isEmpty());

    }

    @Test
    void getAll() throws Exception {


        IntStream.range(0, 10).mapToObj(value -> Car.builder().model("Siena" + value).brand("Fiat").build()).forEach(carRepository::save);

        String contentAsString = mockMvc.perform(get("/car")
                .header("Content-type", "application/json"))
                                        .andDo(print())
                                        .andExpect(status()
                                                .is(HttpStatus.OK.value()))
                                        .andReturn().getResponse().getContentAsString();


        List<CarResponse> carResponses = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        assertEquals(10, carResponses.size());

    }

    @Test
    void create() throws Exception {
        CarRequest request = CarRequest.builder().brand("Fiat").model("Siena").build();

        String contentAsString = mockMvc.perform(post("/car")
                .header("Content-type", "application/json")
                .content(objectMapper.writeValueAsString(request)))
                                        .andDo(print())
                                        .andExpect(status()
//                                       .is2xxSuccessful()
.is(HttpStatus.CREATED.value()))
                                        .andReturn().getResponse().getContentAsString();

        CarResponse carResponse = objectMapper.readValue(contentAsString, CarResponse.class);

        assertNotNull(carResponse.getId());

        carRepository.findById(carResponse.getId());
    }

    @Test
    void update() throws Exception {

        Car first = Car.builder().model("Siena").brand("Fiat").build();

        Car saved = carRepository.save(first);

        CarRequest request = CarRequest.builder().brand("Chevrolet").model("Spark").id(saved.getId()).build();

        String contentAsString = mockMvc.perform(put("/car")
                .header("Content-type", "application/json")
                .content(objectMapper.writeValueAsString(request)))
                                        .andDo(print())
                                        .andExpect(status().is2xxSuccessful())
                                        .andReturn().getResponse().getContentAsString();

        CarResponse carResponse = objectMapper.readValue(contentAsString, CarResponse.class);

        assertEquals("Chevrolet", carResponse.getBrand());
        assertEquals("Spark", carResponse.getModel());

    }
}
package com.tr4.db.restapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr4.db.restapi.RestapiApplication.PubsubOutboundGateway;
import com.tr4.db.restapi.controller.BookingController;
import com.tr4.db.restapi.domain.Booking;
import com.tr4.db.restapi.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class BookingControllerTest {

    @Mock
    private PubsubOutboundGateway messagingGateway;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void whenValidBooking_thenReturnsSuccess() throws Exception {
        // Arrange
        Booking booking = new Booking(UUID.randomUUID(), "Test Event", 2, "test@example.com");
        String bookingJson = new ObjectMapper().writeValueAsString(booking);

        // Mocking ObjectMapper to return the expected JSON string
        doReturn(bookingJson).when(objectMapper).writeValueAsString(booking);

        // Act
        ResponseEntity<String> response = bookingController.publish(booking);

        // Assert
        verify(messagingGateway).sendToPubsub(bookingJson);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Success", response.getBody());
    }

    @Test
    public void whenJsonProcessingException_thenReturnsInternalServerError() throws Exception {
        // Arrange
        Booking booking = new Booking(UUID.randomUUID(), "Test Event", 2, "test@example.com");

        // Mocking ObjectMapper to throw JsonProcessingException
        doThrow(new JsonProcessingException("JSON processing error") {}).when(objectMapper).writeValueAsString(booking);

        // Act
        ResponseEntity<String> response = bookingController.publish(booking);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to process booking", response.getBody());
    }

    @Test
    public void whenInvalidInput_thenReturnValidationException() {
        // Arrange
        Booking booking = new Booking(null, "Test Event", 2, "test@example.com");

        BindingResult bindingResult = new BindException(booking, "booking");
        bindingResult.addError(new FieldError("booking", "eventId", "Event Id cannot be null; must be UUID"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        // Act
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<Map<String, String>> response = handler.handleValidationExceptions(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Event Id cannot be null; must be UUID", response.getBody().get("eventId"));
    }
}

package com.usm4.controller;

import com.usm4.entity.AppUser;
import com.usm4.entity.Booking;
import com.usm4.entity.Property;
import com.usm4.repository.BookingRepository;
import com.usm4.repository.PropertyRepository;
import com.usm4.service.BucketService;
import com.usm4.service.PDFService;
import com.usm4.service.TwilioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;
    private TwilioService twilioService;
    private PDFService pdfService;

    private BucketService bucketService;

    public BookingController(BookingRepository bookingRepository, PropertyRepository propertyRepository, TwilioService twilioService, PDFService pdfService, BucketService bucketService) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.twilioService = twilioService;
        this.pdfService = pdfService;
        this.bucketService = bucketService;
    }

    @PostMapping("/addBooking")
    public ResponseEntity<Booking> createBooking(
            @RequestParam long propertyId,
            @RequestBody Booking booking,
            @AuthenticationPrincipal AppUser appUser
            ){
        Optional<Property> byId = propertyRepository.findById(propertyId);
        Property property = byId.get();
        System.out.println(property);
        int nightlyPrice = property.getNightlyPrice();
        int totalPrice = nightlyPrice * booking.getTotalNights();
//        int tax = totalPrice*(18/100);
        booking.setTotalPrice(totalPrice);

        booking.setProperty(property);
        booking.setAppUser(appUser);

        System.out.println(booking);


        Booking saveBooking = bookingRepository.save(booking);
        String filePath = pdfService.generateBookingDetailsPdf(saveBooking);

        try {
            File file = new File(filePath);
            FileInputStream input = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            input.read(bytes);
            input.close();
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), "application/pdf", bytes);
            String bookingPdf = bucketService.uploadFile(multipartFile, "ssairbnb");
            System.out.println(bookingPdf);
            sendMessage(bookingPdf);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return new ResponseEntity<>(saveBooking, HttpStatus.CREATED);
    }

    public  void sendMessage(String url){
        twilioService.sendSms("+918962755808","Your Booking is confirmed. Click here:"+url);
    }

}

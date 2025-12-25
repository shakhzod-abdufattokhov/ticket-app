package uz.shaxzod.ticketapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.shaxzod.ticketapp.service.TestDataGenerator;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class TestDataController {
    private final TestDataGenerator testDataGenerator;

    @PostMapping("/generate")
    public ResponseEntity<String> generateTest(@RequestParam Long numOfVenue){
        testDataGenerator.generateData(numOfVenue);
        return ResponseEntity.ok("Data is generated");
    }
}

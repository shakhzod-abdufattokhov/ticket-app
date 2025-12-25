package uz.shaxzod.ticketapp.service;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.models.enums.EventType;
import uz.shaxzod.ticketapp.models.enums.Language;
import uz.shaxzod.ticketapp.models.requestDto.*;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class TestDataGenerator {
    private final VenueService venueService;
    private final EventService eventService;
    private final ShowService showService;
    private final SectorService sectorService;
    private final SeatService seatService;

    public void generateData(Long numOfVenue) {
        for (int i = 0; i < numOfVenue; i++) {
            createFakeVenue();
        }
    }

    private void createFakeVenue() {
        Faker faker = new Faker();

        VenueRequest request = new VenueRequest();
        request.setAddress(faker.address().fullAddress());
        request.setCity(faker.address().city());
        request.setName(faker.company().name());
        request.setPhoneNumber(faker.phoneNumber().phoneNumber());

        ApiResponse<String> response = venueService.create(request);
        String venueId = response.getData();
        String sectorId = createFakeCategories(venueId);
        createFakeSeats(venueId, sectorId);
        createFakeEventsForVenue(venueId, 3);
    }

    private void createFakeSeats(String venueId, String sectorId) {
        CreateSeatsRequest request = new CreateSeatsRequest();
        int rows = 10;
        while (rows > 0) {
            request.setSectorId(sectorId);
            request.setVenueId(venueId);
            request.setRow(rows);
            request.setNumOfSeats(70);
            rows--;
            seatService.createSeatsRow(request);
        }
    }

    private String createFakeCategories(String venueId) {
        SectorRequest request = new SectorRequest();
        request.setVenueId(venueId);
        request.setName("Ground");

        ApiResponse<String> response = sectorService.create(request);
        return response.getData();
    }


    private void createFakeEventsForVenue(String venueId, int numOfEvents) {
        Faker faker = new Faker();
        Random random = new Random();

        EventRequest request = new EventRequest();
        for (int i = 0; i < numOfEvents; i++) {
            int num = random.nextInt(18);
            request.setVenueId(venueId);
            request.setTitle(faker.funnyName().name());
            request.setDescription(faker.lorem().paragraph(3));
            request.setType(num % 2 == 0 ? EventType.CONCERT.name() : EventType.THEATER.name());
            request.setLanguage(num % 2 == 0 ? Language.UZ.name() : Language.EN.name());
            request.setMinAge((short) num);

            ApiResponse<String> response = eventService.create(request);
            createFakeShows(response.getData(), venueId, 4);
        }


    }

    private void createFakeShows(String eventId, String venueId, int numOfShows) {
        Faker faker = new Faker();
        ShowRequest request = new ShowRequest();
        Random random = new Random();
        LocalTime time = LocalTime.of(9,0,0);
        long mins = 0L;

        for (int i = 0; i < numOfShows; i++) {
            if(time.isAfter(LocalTime.of(20, 0, 0))){
                time =  LocalTime.of(9,0,0);
            }
            request.setVenueId(venueId);
            request.setEventId(eventId);
            request.setDay(LocalDate.now().plusDays(1));
            request.setBasePrice((long) random.nextInt(50, 100));
            request.setStartTime(time.plusMinutes(mins));
            request.setEndTime(time.plusMinutes(120+mins));
            mins += 60;
            ApiResponse<String> response = showService.create(request);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

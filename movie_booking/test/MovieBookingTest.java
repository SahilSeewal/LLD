package movie_booking.test;

import movie_booking.service.CinemaService;

public class MovieBookingTest {
    public static void main(String []args) {
        CinemaService cinemaService = new CinemaService();
        BookingManagementService bookingManagementService = 
                new BookingManagementService();
        
        Movie movie1 = new Movie("movie1", "m1");
        Movie movie2 = new Movie("movie2", "m2");
        Movie movie3 = new Movie("movie3", "m3");

        CinemaHall cinemaHall1 = new CinemaHall("h1", "DL", 200);
        CinemaHall cinemaHall2 = new CinemaHall("h2", "MB", 300);
        
        cinemaService.addCinema(cinemaHall1);
        cinemaService.addCinema(cinemaHall2);

        Show show1 = new Show(cinemaHall1, movie1, 1100, 2000, 500);
        Show show2 = new Show(cinemaHall1, movie2, 3000, 5000, 600);
        Show show3 = new Show(cinemaHall2, movie2, 1100, 2000, 600);
        Show show4 = new Show(cinemaHall2, movie3, 6000, 7000, 1000);

        cinemaService.addShow(show1);
        cinemaService.addShow(show2);
        cinemaService.addShow(show3);
        cinemaService.addShow(show4);
        
        cinemaService.listShowsByCity("DL");
        cinemaService.listShowsByCity("MB");

        cinemaService.listCinemaHallByMovie(movie1);
        cinemaService.listCinemaHallByMovie(movie2);

        User user1 = new User("u1");
        User user2 = new User("u2");

        Ticket ticket1 = bookingManagementService.bookTiket(user1, show1);
        Ticket ticket2 = bookingManagementService.bookTiket(user2, show1);

        bookingManagementService.cancelTicket(ticket1);
    }
}

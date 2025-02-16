package movie_booking.service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import parking_lot.model.User;

public class BookingManagementService {
    public Ticket bookTiket(User user, Show show) {
        Ticket ticket = new Ticket(user, show);
        int cinemaHallCapacity = show.getCinemaHall().getCapacity();
        String cinemaId = show.getCinemaHall().getId();
        List<Ticket> ticketList = BookingRepo.cinemaHallTicketMap.get(cinemaId);
        if(Objects.isNull(ticketList) ||(Objects.nonNull(ticketList) 
            && ticketList.size() < cinemaHallCapacity)) {
            BookingRepo.cinemaHallTicketMap.get(cinemaId).add(ticket);
        } 

        if(Objects.isNull(BookingRepo.userShowMap.get(user))) {
            BookingRepo.userShowMap.put(user, new CopyOnWriteArrayList<>());
        }
        BookingRepo.userShowMap.get(user).add(ticket);
    }

    public void cancelTicket(User user, Ticket ticket) {
        String cinemaId = ticket.getCinemaHall().getId();
        BookingRepo.cinemaHallTicketMap.get(cinemaId).remove(ticket);
        BookingRepo.userShowMap.get(user).remove(ticket);
    }
}

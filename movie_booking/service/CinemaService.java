package movie_booking.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.management.RuntimeErrorException;

public class CinemaService {
    private void isTimeValidForShow(Show show) {
        String cinemaId = show.getCinemaHall().getId();
        List<Show> showList = ShowsRepo.showMovieMap.get(cinemaId);
        for(Show s: showList) {
            if(s.getEndTime()>=show.getStartTime() 
                && show.getEndTime() >= s.getStartTime()) {
                    throw new RuntimeException("Show timing interfere with existing show!");
            }
        }
    }

    public void addShow(Show show) {
        String cinemaId = show.getCinemaHall().getId();
        String city = show.getCinemaHall().getCity();
        String movieId = show.getMovie().getId();

        isTimeValidForShow(show);

        if(!ShowsRepo.movieCinemaMap.containsKey(movieId)) {
            ShowsRepo.movieCinemaMap.put(movieId, 
                                new CopyOnWriteArrayList<>());
        }

        ShowsRepo.movieCinemaMap.get(movieId).add(show.getCinemaHall());

        if(!ShowsRepo.showMovieMap.containsKey(cinemaId)) {
            ShowsRepo.showMovieMap.put(cinemaId, new CopyOnWriteArrayList<>());
        }
        ShowsRepo.showMovieMap.get(cinemaId).add(show);
        
        if(!ShowsRepo.cityShowMap.containsKey(city)) {
            ShowsRepo.cityShowMap.put(city, new CopyOnWriteArrayList<>());
        }
        ShowsRepo.cityShowMap.get(city).add(show);
    }

    public List<Show> listShowsByCity(String city) {
        return ShowsRepo.cityShowMap.get(city);
    }
    
    public List<CinemaHall> listCinemaHallByMovie(Movie movie) {
        return ShowsRepo.movieCinemaMap.get(movie); 
    }
}

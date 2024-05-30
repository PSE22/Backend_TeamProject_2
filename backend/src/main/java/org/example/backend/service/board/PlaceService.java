package org.example.backend.service.board;

import org.example.backend.model.entity.board.Place;
import org.example.backend.repository.board.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {
    @Autowired
    PlaceRepository placeRepository;

    public Place savePlace(Long boardId, Place place) {
        place.setBoardId(boardId);
        Place place2 = placeRepository.save(place);

        return place2;
    }
}

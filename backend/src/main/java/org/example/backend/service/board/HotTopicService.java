package org.example.backend.service.board;

import org.example.backend.model.dto.board.HotTopicDto;
import org.example.backend.repository.board.HotTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName : org.example.backend.service.board
 * fileName : HotTopicService
 * author : GGG
 * date : 2024-06-03
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-03         GGG          최초 생성
 */
@Service
public class HotTopicService {
    @Autowired
    private HotTopicRepository hotTopicRepository;

    public List<HotTopicDto> getHotTopics() {
        return hotTopicRepository.findHotTopics();
    }
}
package org.example.backend.service.board;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.dto.board.FileDto;
import org.example.backend.model.dto.board.VoteDto;
import org.example.backend.model.entity.board.File;
import org.example.backend.model.entity.board.Vote;
import org.example.backend.repository.board.FileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    @Autowired
    ModelMapper modelMapper;

//    1개의 파일 저장
    public File saveFile(File file) {
        File file2 = fileRepository.save(file);
        return file2;
    }

    public void saveFiles(List<FileDto> fileDtos) {
        // 리스트에 저장된 voteDto를 반복해서 저장
        for (FileDto fileDto : fileDtos) {
            // Dto를 Vote 엔티티로 변환
            File file = modelMapper.map(fileDto, File.class);

            fileRepository.save(file);
        }
    }
}

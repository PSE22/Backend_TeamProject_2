package org.example.backend.service.board;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.dto.board.FileDto;
import org.example.backend.model.dto.board.VoteDto;
import org.example.backend.model.entity.board.BoardFile;
import org.example.backend.model.entity.board.File;
import org.example.backend.model.entity.board.Vote;
import org.example.backend.repository.board.BoardFileRepository;
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
    private final BoardFileRepository boardFileRepository;
    private final ModelMapper modelMapper;

//    1개의 파일 저장
    public File saveFile(File file) {
        File file2 = fileRepository.save(file);
        return file2;
    }

    public void saveFiles(List<FileDto> fileDtos) {
        // 리스트에 저장된 voteDto를 반복해서 저장
        for (FileDto fileDto : fileDtos) {
            if (fileDto.getFileUrl()!= null){
                File file = modelMapper.map(fileDto, File.class);
                fileRepository.save(file);
            }
        }
    }

    public BoardFile save(BoardFile boardFile) {
//        JPA 저장 함수 : return 값 : 저장된 객체
        BoardFile boardFile2 = boardFileRepository.save(boardFile);
        return boardFile2;
    }
}

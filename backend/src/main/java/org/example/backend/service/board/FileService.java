package org.example.backend.service.board;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.entity.board.File;
import org.example.backend.repository.board.FileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public List<File> saveFiles(List<File> files) {
        List<File> savedFiles = fileRepository.saveAll(files);

        return savedFiles;
    }
}

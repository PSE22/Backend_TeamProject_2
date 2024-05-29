package org.example.backend.service.board;

import org.example.backend.model.entity.board.File;
import org.example.backend.repository.board.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    @Autowired
    FileRepository fileRepository;

    public File saveFile(File file) {
        File file2 = fileRepository.save(file);

        return file2;
    }
}

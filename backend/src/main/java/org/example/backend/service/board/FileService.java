package org.example.backend.service.board;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.entity.board.File;
import org.example.backend.repository.board.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public File saveFile(File file) {
        File file2 = fileRepository.save(file);

        return file2;
    }
}

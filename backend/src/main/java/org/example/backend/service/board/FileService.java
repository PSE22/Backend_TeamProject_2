package org.example.backend.service.board;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.entity.board.File;
import org.example.backend.repository.board.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

//    1개의 파일 저장
    public File saveFile(File file) {
        File file2 = fileRepository.save(file);
        return file2;
    }

//    여러개 파일 저장
    @Transactional
    public void saveFiles(List<File> files) {
        fileRepository.saveAll(files);
    }
}

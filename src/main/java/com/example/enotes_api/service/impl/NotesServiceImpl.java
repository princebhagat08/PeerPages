package com.example.enotes_api.service.impl;


import com.example.enotes_api.dto.NotesDto;
import com.example.enotes_api.entity.FileDetails;
import com.example.enotes_api.entity.Notes;
import com.example.enotes_api.exception.ResourceNotFoundException;
import com.example.enotes_api.repository.CategoryRepository;
import com.example.enotes_api.repository.FileRepository;
import com.example.enotes_api.repository.NotesRepository;
import com.example.enotes_api.service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import java.util.UUID;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepository notesRepository;

   @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Autowired
    private FileRepository fileRepository;


    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception{

        ObjectMapper obj = new ObjectMapper();
        NotesDto notesDto = obj.readValue(notes, NotesDto.class);


        // category validation
        checkCategoryExistence(notesDto.getCategory());

        Notes notesMap = mapper.map(notesDto, Notes.class);


        FileDetails fileDetails = saveFileDetails(file);

        if(!ObjectUtils.isEmpty(fileDetails)){
            notesMap.setFile(fileDetails);
        }else {
            notesMap.setFile(null);
        }


        Notes saved = notesRepository.save(notesMap);

        if(!ObjectUtils.isEmpty(saved)){
            return true;
        }

        return false;

    }

    private FileDetails saveFileDetails(MultipartFile file) throws IOException {

        if(!ObjectUtils.isEmpty(file) && !file.isEmpty()){

            String originalFileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFileName);

            List<String> extensionAllow = Arrays.asList("pdf", "xlsx", "jpg", "png", "docx");
            if (!extensionAllow.contains(extension)) {
                throw new IllegalArgumentException("invalid file format ! Upload only .pdf , .xlsx,.jpg");
            }

            String randomString = UUID.randomUUID().toString();

            String uploadFileName = randomString+"."+extension;

            File saveFile = new File(uploadPath);

            if(!saveFile.exists()){
                saveFile.mkdir();
            }

            // path
            String storePath = uploadPath.concat(uploadFileName);


            // upload file locally at the given location
            long upload = Files.copy(file.getInputStream(), Paths.get(storePath));

            if(upload!=0){

                FileDetails fileDetails = new FileDetails();
                fileDetails.setOriginalFileName(originalFileName);
                fileDetails.setDisplayFileName(getDisplayName(originalFileName));
                fileDetails.setUploadFileName(uploadFileName);
                fileDetails.setFileSize(file.getSize());
                fileDetails.setPath(storePath);


                FileDetails savedFile = fileRepository.save(fileDetails);

                return savedFile;
            }
        }

        return null;
    }


    private String getDisplayName(String originalFileName) {
        String extension = FilenameUtils.getExtension(originalFileName);
        String fileName = FilenameUtils.removeExtension(originalFileName);

        if(fileName.length()>8){
            fileName = fileName.substring(0,7);
        }

        fileName = fileName+"."+extension;

        return fileName;

    }


    private void checkCategoryExistence(NotesDto.CategoryDto category) throws Exception {
         categoryRepository.findById(category.getId()).orElseThrow(
                 ()->new ResourceNotFoundException("Category not exist"));
    }


    @Override
    public List<NotesDto> getAllNotes() {
        return notesRepository.findAll().stream()
                .map(note->mapper.map(note,NotesDto.class)).toList();
    }

    @Override
    public byte[] downloadFile(FileDetails fileDetails) throws Exception {

        InputStream io = new FileInputStream(fileDetails.getPath());

        return StreamUtils.copyToByteArray(io);
    }

    @Override
    public FileDetails getFileDetails(Integer id) throws Exception{
       return  fileRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("File not found"));
    }
}

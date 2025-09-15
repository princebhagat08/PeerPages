package com.example.enotes_api.service.impl;


import com.example.enotes_api.dto.FavouriteNotesDto;
import com.example.enotes_api.dto.NotesDto;
import com.example.enotes_api.dto.NotesResponse;
import com.example.enotes_api.entity.FavouriteNotes;
import com.example.enotes_api.entity.FileDetails;
import com.example.enotes_api.entity.Notes;
import com.example.enotes_api.exception.ResourceNotFoundException;
import com.example.enotes_api.repository.CategoryRepository;
import com.example.enotes_api.repository.FavouriteNotesRepository;
import com.example.enotes_api.repository.FileRepository;
import com.example.enotes_api.repository.NotesRepository;
import com.example.enotes_api.service.NotesService;
import com.example.enotes_api.utils.CommonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import java.util.UUID;
import java.util.stream.Stream;

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

    @Autowired
    private FavouriteNotesRepository favouriteNotesRepository;


    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception{

        ObjectMapper obj = new ObjectMapper();
        NotesDto notesDto = obj.readValue(notes, NotesDto.class);

        notesDto.setIsDeleted(false);

        if(!ObjectUtils.isEmpty(notesDto.getId())){
            updateNotes(notesDto,file);
        }

        // category validation
        checkCategoryExistence(notesDto.getCategory());

        Notes notesMap = mapper.map(notesDto, Notes.class);


        FileDetails fileDetails = saveFileDetails(file);

        if(!ObjectUtils.isEmpty(fileDetails)){
            notesMap.setFile(fileDetails);
        }else {
            if (ObjectUtils.isEmpty(notesDto.getId())) {
                notesMap.setFile(null);
            }
        }


        Notes saved = notesRepository.save(notesMap);

        if(!ObjectUtils.isEmpty(saved)){
            return true;
        }

        return false;

    }

    private void updateNotes(NotesDto notes, MultipartFile file) throws Exception {
        Notes exitingNotes = notesRepository.findById(notes.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Invalid notes id"));

        if(ObjectUtils.isEmpty(file) && !ObjectUtils.isEmpty(exitingNotes.getFile())){
            notes.setFile(mapper.map(exitingNotes.getFile(), NotesDto.FileDetailsDto.class));
        }

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



    @Override
    public NotesResponse getAllNotesByUser(Integer pageNo, Integer pageSize) {
        Integer userId = CommonUtil.getLoggedInUser().getId();

        PageRequest request = PageRequest.of(pageNo, pageSize);

        Page<Notes> notes = notesRepository.findByCreatedByAndIsDeletedFalse(userId,request);

        List<NotesDto> notesDtos = notes.get().map((notes1)->mapper.map(notes1,NotesDto.class)).toList();

        NotesResponse notesResponse = NotesResponse.builder()
                .notes(notesDtos)
                .pageSize(notes.getSize())
                .pageNo(notes.getNumber())
                .isFirst(notes.isFirst())
                .isLast(notes.isLast())
                .totalElements(notes.getTotalElements())
                .totalPages(notes.getTotalPages())
                .build();

        return notesResponse;

    }

    @Override
    public void softDeleteNotes(Integer id) throws  Exception{
        Notes notes = notesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Invalid notes id"));
        notes.setIsDeleted(true);
        notes.setDeletedOn(LocalDateTime.now());
        notesRepository.save(notes);

    }

    @Override
    public void restoreNotes(Integer id) throws  Exception{
        Notes notes = notesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Invalid notes id"));
        notes.setIsDeleted(false);
        notes.setDeletedOn(null);
        notesRepository.save(notes);
    }

    @Override
    public List<NotesDto> getUserRecycleBinNotes() {
        Integer userId = CommonUtil.getLoggedInUser().getId();
       List<Notes> notesList = notesRepository.findByCreatedByAndIsDeletedTrue(userId);
        List<NotesDto> notesDtoList = notesList.stream().map((
                notes -> mapper.map(notes, NotesDto.class))).toList();

        return notesDtoList;

    }

    @Override
    public void hardDeleteNotes(Integer id) throws Exception {
        Notes notes = notesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Invalid notes id"));

        if(notes.getIsDeleted()){
            notesRepository.delete(notes);
        }else {
            throw new IllegalArgumentException("Sorry you cannot hard delete directly");
        }

    }

    @Override
    public void emptyRecycleBin() throws Exception {
        Integer userId = CommonUtil.getLoggedInUser().getId();
        List<Notes> notesList = notesRepository.findByCreatedByAndIsDeletedTrue(userId);

        if(!CollectionUtils.isEmpty(notesList)){
            notesRepository.deleteAll(notesList);
        }
    }

    @Override
    public void favouriteNotes(Integer id) throws  Exception{
        Integer userId = CommonUtil.getLoggedInUser().getId();
        Notes notes = notesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Notes not found"));

        FavouriteNotes favouriteNotes = FavouriteNotes.builder()
                .notes(notes)
                .userId(userId)
                .build();

        favouriteNotesRepository.save(favouriteNotes);

    }

    @Override
    public void unFavouriteNotes(Integer favouriteNotesId) throws Exception {
        FavouriteNotes favouriteNotes = favouriteNotesRepository.findById(favouriteNotesId).orElseThrow(
                () -> new ResourceNotFoundException("Notes not found or Id invalid"));

        favouriteNotesRepository.delete(favouriteNotes);
    }

    @Override
    public List<FavouriteNotesDto> getUserFavouriteNotes() throws Exception{
        Integer userId = CommonUtil.getLoggedInUser().getId();

        List<FavouriteNotes> favouriteNotes = favouriteNotesRepository.findByUserId(userId);

        return favouriteNotes.stream().map(
                (notes) -> mapper.map(notes, FavouriteNotesDto.class)).toList();
    }

    @Override
    public NotesResponse getNotesByUserSearch(Integer pageNo, Integer pageSize,String keyword) {
        Integer userId = CommonUtil.getLoggedInUser().getId();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Notes> pageNotes = notesRepository.searchNotes(keyword,userId, pageable);

        List<NotesDto> notesDto = pageNotes.get().map(n -> mapper.map(n, NotesDto.class)).toList();

        NotesResponse notes = NotesResponse.builder().notes(notesDto).pageNo(pageNotes.getNumber())
                .pageSize(pageNotes.getSize()).totalElements(pageNotes.getTotalElements())
                .totalPages(pageNotes.getTotalPages()).isFirst(pageNotes.isFirst()).isLast(pageNotes.isLast()).build();

        return notes;
    }


}

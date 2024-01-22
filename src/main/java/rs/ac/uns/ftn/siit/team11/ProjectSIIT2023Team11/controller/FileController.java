package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/files")
public class FileController {
    public static final String UPLOAD_DIRECTORY = "./src/main/resources/pictures/";

//    public static String UPLOAD_DIRECTORY = File.listRoots()[0].getAbsolutePath() + "./ISS/src/main/resources/pictures/";

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("images") List<MultipartFile> files) throws IOException {
        List<String> filenames = new ArrayList<String>();

        for(MultipartFile file: files){
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage = get(UPLOAD_DIRECTORY, filename).toAbsolutePath().normalize();
            copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
            filenames.add(filename);
        }

        return ResponseEntity.ok().body(filenames);
    }

    @PostMapping("/uploadMobile")
    public ResponseEntity<List<String>> uploadFilesForMobile(@RequestPart("images") List<MultipartFile> files) throws IOException {
        List<String> filenames = new ArrayList<String>();

        for(MultipartFile file: files){
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage = get(UPLOAD_DIRECTORY, filename).toAbsolutePath().normalize();
            copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
            filenames.add(filename);
        }

        return ResponseEntity.ok().body(filenames);
    }

}

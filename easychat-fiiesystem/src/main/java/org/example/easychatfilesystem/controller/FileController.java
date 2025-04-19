package org.example.easychatfilesystem.controller;


import org.example.easychatcommon.protocol.ResultResponse;
import org.example.easychatfilesystem.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public ResultResponse<Object> uploadFileToMinio(@RequestParam("file")MultipartFile  file, @RequestParam("path")String path){
        return fileService.uploadFile(file, path);
    }
    @GetMapping("/isExist")
    public ResultResponse<Object> getFile(@RequestParam("path")String path){
        return fileService.checkFile(path);
    }
}

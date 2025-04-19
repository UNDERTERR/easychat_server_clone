package org.example.easychatfilesystem.service;
import org.example.easychatcommon.exception.CustomException;
import org.example.easychatcommon.protocol.ResultResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    ResultResponse<Object> uploadFile(MultipartFile file,String path) throws CustomException;
    ResultResponse<Object> checkFile(String path) throws CustomException;
}

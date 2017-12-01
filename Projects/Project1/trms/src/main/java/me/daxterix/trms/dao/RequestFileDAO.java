package me.daxterix.trms.dao;

import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.RequestFile;

import java.util.List;

public interface RequestFileDAO {
    RequestFile getFile(long id) throws NonExistentIdException;
    List<RequestFile> getFilesForRequest(long requestId) throws NonExistentIdException;
    List<RequestFile> getGradeFileForRequest(long requestId) throws NonExistentIdException;

    long save(RequestFile file) throws DuplicateIdException;
    void update(RequestFile file) throws NonExistentIdException;
    void deleteFile (long fileId) throws NonExistentIdException;
}

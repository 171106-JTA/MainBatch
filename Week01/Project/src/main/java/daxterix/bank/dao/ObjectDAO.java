package daxterix.bank.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public abstract class ObjectDAO <T extends Serializable> {
    final Logger logger = Logger.getLogger(getClass());
    String saveDir;

    /**
     * Abstraction of Object persistence using object Streams
     * supports basic CRUD operations
     *
     * Implementing class has to provide method to extract an id
     * String given an object instance
     *
     * Object type being managed has to implement the Serializable interface
     *
     * @param saveDir
     */
    public ObjectDAO(String saveDir) {
        this.saveDir = saveDir;
    }

    /**
     * given an instance of the model, retrieve the id String
     *
     * @param obj
     * @return
     */
    public abstract String getId(T obj);

    /**
     * load previously saved object given its id
     *
     * @param id
     * @return - true if id is valid and object is successfully loaded
     */
    public T readById(String id) {
        T res = (T) readObject(saveLoc(id));
        logger.debug(String.format("fetched object %s from %s", id, getClass()));
        return res;
    }

    /**
     * get all saved objects
     *
     * @return - returns null if something goes wrong
     */
    public List<T> readAll() {
        List<T> res = new ArrayList<>();
        File saveDirFile = new File(saveDir);
        File[] files = saveDirFile.listFiles();

        if (files == null) {
            logger.debug(String.format("failed to fetch all objects from %s", getClass()));
            return res;
        }

        for (File file : files) {
            if (file.isFile())
                res.add((T) readObject(file.getAbsolutePath()));
        }
        logger.debug(String.format("fetched all objects from %s", getClass()));
        return res;
    }

    /**
     * read a saved object from given file path
     *
     * @param saveLoc
     * @return
     */
    public Object readObject(String saveLoc) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveLoc))) {
            Object res = ois.readObject();
            logger.debug(String.format("fetched object from %s at path %s", getClass(), saveLoc));
            return res;
        }
        catch (IOException | ClassNotFoundException e) {
            logger.debug(String.format("failed to fetch object from %s with exception %s", getClass(), e));
            return null;
        }
    }

    /**
     * persist an instance of the model to the file system.
     * returns false if an instance with the same id already exists (use
     * 'update' to update an existing record.
     *
     *
     * important: saved file does not have an extension (this
     * allows an extra safety check when deleting/emptying database/save directory)
     *
     * @param model
     * @return
     */
    public boolean save(T model) {
        String id = getId(model);
        if (doesExist(getId(model)))
            return false;
        logger.debug(String.format("attempting to save object %s from %s", getId(model), getClass()));
        return saveObject(model, saveLoc(id));
    }

    /**
     * save object to given filepath, creates the required directories if they do not exist
     *
     * @param obj
     * @param saveLoc
     * @return
     */
    public boolean saveObject(Object obj, String saveLoc) {
        File saveLocFile = new File(saveLoc);
        saveLocFile.getParentFile().mkdirs();

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveLocFile))) {
            oos.writeObject(obj);
            logger.debug(String.format("saved object from %s at path %s", getClass(), saveLoc));
            return true;
        }
        catch (IOException e) {
            logger.debug(String.format("failed to save object from %s at path %s with exception %s", getClass(), saveLoc, e));
            return false;
        }
    }

    /**
     * Update an object based on id. Assumes id is never changed
     *
     * @param model
     * @return
     */
    public boolean update(T model) {
        if (!doesExist(getId(model))) {
            logger.debug(String.format("invalid attempt to update nonexistent object %s from %s", getId(model), getClass()));
            return false;
        }
        logger.debug(String.format("attempting to update object %s from %s", getId(model), getClass()));
        // return save(obj);    // can't use this, or update will create new id for ObjectSerialIDObjectDAO
        return saveObject(model, saveLoc(getId(model)));
    }

    /**
     * given id, delete previously saved object
     *
     * @param id
     * @return
     */
    public boolean deleteById(String id) {
        String fileLoc = saveLoc(id);
        boolean res = deleteRecord(fileLoc);
        if (res)
            logger.debug(String.format("deleted object %s from %s", id, getClass()));
        else
            logger.debug(String.format("failed to delete object %s from %s at path %s", id, getClass(), fileLoc));
        return res;
    }

    /**
     * get the save path for an object given its id String
     *
     * @param id
     * @return
     */
    public String saveLoc(String id) {
        return combinePaths(saveDir, id);
    }

    /**
     * returns true if an object with given id is saved
     *
     * @param id
     * @return
     */
    public boolean doesExist(String id) {
        return resourceExists(saveLoc(id));
    }

    /**
     * check if a file path exists
     *
     * @param resourcePath
     * @return
     */
    public static boolean resourceExists(String resourcePath) {
        return (new File(resourcePath)).exists();
    }

    /**
     * see name
     *
     * @return
     */
    public boolean dropDatabase() {
        File pathFile = new File(saveDir);
        boolean retVal = true;

        try {
            for (File f : pathFile.listFiles()) {
                boolean hasNoExtension = (f.getName().lastIndexOf('.') == -1);    // just a safeguard
                if (f.isFile() && hasNoExtension)
                    retVal &= f.delete();
            }
            return retVal;
        }
        catch(NullPointerException e) {
            return false;
        }
    }

    public boolean deleteRecord(String saveLoc) {
        return (new File(saveLoc)).delete();
    }

    /**
     * combine two file paths
     *
     * @param p1
     * @param p2
     * @return
     */
    public static String combinePaths(String p1, String p2) {
        File file = new File(p1, p2);
        return file.getAbsolutePath();
    }
}

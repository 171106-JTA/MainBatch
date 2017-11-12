package daxterix.bank.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ObjectDAO <T extends Serializable> {
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
        return (T) readObject(saveLoc(id));
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

        if (files == null)
            return res;

        for (File file : files) {
            if (file.isFile())
                res.add((T) readObject(file.getAbsolutePath()));
        }
        return res;
    }

    /**
     * read an from a given file path
     *
     * @param saveLoc
     * @return
     */
    public Object readObject(String saveLoc) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveLoc))) {
            return ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
            return null;
        }
    }

    /**
     * persist an instance of the model to the file system
     *
     * @param model
     * @return
     */
    public boolean save(T model) {
        String id = getId(model);
        return saveObject(model, saveLoc(id));
    }

    /**
     * save object to given filepath
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
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update an object based on id. Assumes id is never changed
     *
     * @param obj
     * @return
     */
    public boolean update(T obj) {
        if (!doesExist(getId(obj)))
            return false;
        return save(obj);
    }

    /**
     * given id, delete previously saved object
     *
     * @param id
     * @return
     */
    public boolean deleteById(String id) {
        String fileLoc = saveLoc(id);
        return (new File(fileLoc)).delete();
    }

    /**
     * get the save path for an object given its id String
     *
     * @param id
     * @return
     */
    public String saveLoc(String id) {
        return DAOUtils.combinePaths(saveDir, id);
    }

    /**
     * returns true if an object with given id is saved
     *
     * @param id
     * @return
     */
    public boolean doesExist(String id) {
        return DAOUtils.resourceExists(saveLoc(id));
    }
}

package daxterix.bank.presistence;

import daxterix.bank.model.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ObjectDAO <T extends Model> {
    String saveDir;

    public ObjectDAO(String saveDir) {
        this.saveDir = saveDir;
    }

    public abstract String getId(T obj);

    public T readById(String id) {
        return (T) readObject(saveLoc(id));
    }

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

    public Object readObject(String saveLoc) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveLoc))) {
            return ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public boolean save(T model) {
        String id = getId(model);
        return saveObject(model, saveLoc(id));
    }

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

    public boolean deleteById(String id) {
        String fileLoc = saveLoc(id);
        return (new File(fileLoc)).delete();
    }

    public String saveLoc(String username) {
        return PersistUtils.combinePaths(saveDir, username);
    }

    public boolean doesExist(String id) {
        return PersistUtils.resourceExists(saveLoc(id));
    }
}

package daxterix.bank.presistence;

import daxterix.bank.model.Model;

public abstract class SerialIDObjectDAO<T extends Model> extends ObjectDAO<T> {
    String highestIdPath;
    static final String saveDirToHighestIdPath = "highestId\\highestId";

    public SerialIDObjectDAO(String saveDir) {
        super(saveDir);
        highestIdPath = PersistUtils.combinePaths(saveDir, saveDirToHighestIdPath);
    }

    public abstract void setId(T obj, String newId);

    public long getNextId(T o) {

        long ret;
        if (!PersistUtils.resourceExists(highestIdPath))
            ret = 0;

        else {
            String highestRead = (String) readObject(highestIdPath);
            if (highestRead == null)
                return -1;
            try {
                ret = Long.valueOf(highestRead);
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        long nextHighest = ret + 1;
        saveObject(Long.toString(nextHighest), highestIdPath);
        return ret;
    }

    @Override
    public boolean save(T model) {
        long nextId = getNextId(model);
        setId(model, "" + nextId);
        return super.save(model);
    }
}

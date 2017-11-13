package daxterix.bank.dao;

import java.io.Serializable;

public abstract class SerialIDObjectDAO<T extends Serializable> extends ObjectDAO<T> {
    String highestIdPath;
    static final String saveDirToHighestIdPath = "highestId\\highestId";

    /**
     * This DAO class is for those objects without user-given IDs. It generates
     * uniques IDs with a mechanism that behaves like the autoincrement feature
     * in SQL databases.
     *
     * @param saveDir
     */
    public SerialIDObjectDAO(String saveDir) {
        super(saveDir);
        highestIdPath = combinePaths(saveDir, saveDirToHighestIdPath);
    }

    /**
     * implementing class must provide a means to set the id to the
     * generated value
     *
     * @param obj
     * @param newId
     */
    public abstract void setId(T obj, String newId);

    /**
     * When a new id is requested, this method fetches the value (this is the
     * value that will be returned) from a designated file, then increments this
     * value and stores it back in the file, ready for the next id request.
     *
     * @param o
     * @return
     */
    public long getNextId(T o) {

        long ret;
        if (!resourceExists(highestIdPath))
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

    /**
     * extends the original save method of ObjectDAO, by assigning the unique
     * id fetched from getNextId()
     *
     * @param model
     * @return
     */
    @Override
    public boolean save(T model) {
        long nextId = getNextId(model);
        setId(model, "" + nextId);
        return super.save(model);
    }

    /**
     * does the same ops as ObjectDAO.dropDatabae, but also deletes highest id file
     *
     * @return
     */
    @Override
    public boolean dropDatabase() {
        boolean retval = super.dropDatabase();
        return retval && deleteRecord(highestIdPath);
    }
}

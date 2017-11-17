import java.io.*;

public class Serialize implements Serializable {

	static String file = "modelF.ser";

	public static void main(String[] args) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		FileInputStream fis;
		ObjectInputStream ois;
		Serialize s;
		try {
			s = new Serialize();
			fos = new FileOutputStream(new File(file));
			oos = new ObjectOutputStream(fos);
			oos.writeObject(s);
			s = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.flush();
					fos.close();
				}
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			}
		}
		try {

			fis = new FileInputStream(new File(file));
			ois = new ObjectInputStream(fis);
			s = null;
			s = (Serialize) ois.readObject();

			System.out.println(s);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}

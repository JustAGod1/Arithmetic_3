package Stuff.Utilities;

import java.io.*;

/**
 * Created by Yuri on 06.12.16.
 */
public class Statistics implements Serializable {

    private static Statistics instance;
    private long launchCount =0;

    public static Statistics getInstance() {return instance;}

    public Statistics() {}

    public long getLaunchCount() {
        return launchCount;
    }

    static {
        try {
            ClassLoader loader = Statistics.class.getClassLoader();
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(new File(loader.getResource("files/Statistics").getFile()).getAbsoluteFile()));
            Statistics s = (Statistics) input.readObject();
            instance = s;
            input.close();
        } catch (Exception e) {
            instance = new Statistics();
            System.err.println("Не прочиталось");}

    }

    public void incLaunchCount() {
        launchCount++;
        writeFile();
    }

    private void writeFile() {
        try {
            ClassLoader loader = Statistics.class.getClassLoader();
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(loader.getResource("files/Statistics").getFile()).getAbsoluteFile()));
            out.writeObject(instance);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Не записалось");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        writeFile();

    }
}

import Stuff.Statistics;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import sun.misc.IOUtils;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Yuri on 06.12.16.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        new Main();
    }

    public Main() throws IOException {
        String result = "";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("file/hint.txt").getFile());

        Scanner reader = new Scanner(new FileInputStream(file.getAbsoluteFile()));
        String content = getFileContent(reader);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsoluteFile())));
        System.out.print("Девиз сегодняшнего дня: ");
        out.write(content + new BufferedReader(new InputStreamReader(System.in)).readLine() + "\n");
        out.flush();

        reader = new Scanner(new FileInputStream(file.getAbsoluteFile()));
        Statistics s = Statistics.getInstance();
        s.incLaunchCount();

        System.out.printf("Приложение было запущено %d раз(а)\n", Statistics.getInstance().getLaunchCount());

        while (reader.hasNextLine()) {
            System.out.println(reader.nextLine());
        }

    }

    private String getFileContent(Scanner reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        while (reader.hasNext()) {
            builder.append(reader.next() + '\n');
        }

        return builder.toString();
    }
}

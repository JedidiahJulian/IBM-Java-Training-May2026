package day5;
import java.io.*;

public class StudentFile {
    public static void main(String[] args){
            String readPath = "src/main/resources/file.csv";
            String writePath = "src/main/resources/student.json";
        if (readPath == null || readPath.isEmpty()){
            System.err.println("Invalid file Path");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(readPath));
            BufferedWriter bw = new BufferedWriter(new FileWriter(writePath))){
            String line;
            boolean first = true;

            line = br.readLine();

            bw.write("[");
            bw.newLine();

            while ((line = br.readLine()) != null){
                String[] values = line.replace("\"", "").split(",");

                String id = values[0].trim();
                String name = values[1].trim();
                String course = values[2].trim();

                if (!first){
                    bw.write(",");
                    bw.newLine();
                }

                bw.write(" {");
                bw.newLine();
                bw.write("  \"id\":\"" + id + "\",");
                bw.newLine();
                bw.write("  \"name\":\"" + name + "\",");
                bw.newLine();
                bw.write("  \"course\":\"" + course + "\"");
                bw.newLine();
                bw.write(" }");
                System.out.println(line);

                first = false;
            }   

            bw.newLine();
            bw.write("]");

            bw.close();
            br.close();

        }catch(IOException e){
            System.out.println("File error");
        }
    }
}

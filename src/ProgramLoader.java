import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProgramLoader {
    private String programPath;
    private String[] program;

    public void getProgramPath() throws IOException {
        programPath = "tests/" + (new BufferedReader(new InputStreamReader(System.in))).readLine().trim() + ".txt";
    }

    public void loadProgram() throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(programPath));
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        program = lines.toArray(new String[0]);
    }

    public void displayProgram() {
        System.out.println("PROGRAM:\n--------------");
        for (String line : program) {
            System.out.println(line);
        }
        System.out.println("------------");
    }


    public String[] getProgram() {
        return program;
    }
}

import java.util.ArrayList;
import java.io.*;

public class FileManager {
    private static final String FILE_PATH = "notes.txt";

    public static void saveNotes(ArrayList<Note> notes) {
        try (FileWriter fw = new FileWriter(FILE_PATH)) {
            for (Note note : notes) {
                fw.write(note.getTitle() + "\n");
                fw.write(note.getContent() + "\n");
                fw.write("----\n"); // separator
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Note> loadNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String title = line;
                String content = br.readLine();
                br.readLine(); // read the separator
                notes.add(new Note(title, content));
            }
        } catch (IOException e) {
            System.out.println("notes.txt file not found.");
            System.out.println("New notes.txt file created.");
        }
        return notes;
    }


    }

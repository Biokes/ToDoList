import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateFile {
    public static void main() {
        try{
            Files.createFile(Paths.get(
                    "C:\\Users\\DELL\\IdeaProjects\\ToDoList\\src\\task\\main\\file.cdr"));
            DirectoryStream<Path> directory = Files.newDirectoryStream(Paths.get(
                    "C:\\Users\\DELL\\IdeaProjects\\ToDoList\\src\\task\\main"));
            directory.forEach(System.out::println);
        }catch(IOException error){
            System.err.println(error);
        }
        try{
            Path path = Paths.get(
"\\C:\\Users\\DELL\\Documents\\chapter2 exercises\\tasks and assingment\\Biokes-codes\\my python codes\"");
//            DirectoryStream<Path> dir = Files.newDirectoryStream(Paths.get(path));

            Files.deleteIfExists(path);
        }catch(IOException error){
            System.err.println(error);
        }
    }
}

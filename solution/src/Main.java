import core.Engine;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Engine engine;

        try {
            engine = new Engine();
        } catch (IOException e) {
            System.out.println("Exception occurs when trying to read from file, please check the path of the source file!");
            return;
        }
        engine.run();
    }

}

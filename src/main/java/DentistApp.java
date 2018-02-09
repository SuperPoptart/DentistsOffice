import Controller.DentistManager;

import java.io.IOException;

public class DentistApp {

    public static void main(String[] args) throws IOException {
        DentistManager dentistManager = new DentistManager();
        dentistManager.run();
    }
}

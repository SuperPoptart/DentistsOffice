package Controller;

import Model.Appointment;
import View.TextUI;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DentistManager {

    private TextUI textUI;
    private Appointment appointment;
    private static final int EDIT_USERS = 1;
    private static final int EDIT_PROVIDERS = 2;
    private static final int EDIT_PATIENTS = 3;
    private static final int VIEW_APPOINTMENTS = 4;
    private static final int EXIT = 5;

    public DentistManager() throws IOException{
        appointment = new Appointment();
        textUI = new TextUI();
//        this.loadInfo();
    }

    public void run() throws IOException {
        boolean exitTime = false;

        while (!exitTime){
            int selection = textUI.showMenu(generateMenuOptions());
        }

    }

    private Map<Integer, String> generateMenuOptions() {
        Map<Integer, String> options = new HashMap<>();
        options.put(EDIT_USERS, "Edit Users");
        options.put(EDIT_PROVIDERS, "Edit Providers");
        return options;
    }
}

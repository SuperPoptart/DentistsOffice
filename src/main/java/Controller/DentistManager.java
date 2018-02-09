package Controller;

import Model.*;
import View.TextUI;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DentistManager {

    private TextUI textUI;
    private AppointmentList appointment;
    private UserList usersList;
    private static final int EDIT_USERS = 1;
    private static final int EDIT_PROVIDERS = 2;
    private static final int EDIT_PATIENTS = 3;
    private static final int VIEW_APPOINTMENTS = 4;
    private static final int EXIT = 5;

    public DentistManager() throws IOException {
        appointment = new AppointmentList();
        usersList = new UserList();
        textUI = new TextUI();
        this.loadUser();
//        this.loadInfo();
    }

    public void run() throws IOException {
        boolean exitTime = true;

        if (usersList.isEmpty()) {
            User user = UserFactory.getInstance("Administrator", "1234Password", true);
            this.usersList.add(user);
            this.saveUser();
        }
        login();

        while (!exitTime) {
            int selection = textUI.showMenu(generateMenuOptions());
            switch (selection) {
                case EDIT_USERS:
                    editUsers();
                    break;
                case EDIT_PROVIDERS:
                    break;
                case EDIT_PATIENTS:
                    break;
                case VIEW_APPOINTMENTS:
                    break;
                case EXIT:
                    this.saveUser();
                    exitTime = true;
                default:

            }
        }

    }

    private boolean login() throws IOException {

        String uname;
        String pword;

        textUI.display("Enter a Username");
        uname = textUI.readStringFromUser();
        textUI.display("Enter a Password");
        pword = textUI.readStringFromUser();
return true;
    }

    private void editUsers() throws IOException {
        final int ADD = 1, EDIT = 2, REMOVE = 3, QUIT = 4;

        Map<Integer, String> userMenu = new HashMap<>();
        userMenu.put(ADD, "Add User");
        userMenu.put(EDIT, "Edit User");
        userMenu.put(REMOVE, "Remove User");
        userMenu.put(QUIT, "Go Back");

        int selection = this.textUI.showMenu(userMenu);
        switch (selection) {
            case (ADD):
                addUser();
                break;
            case (EDIT):
                editUser();
                break;
            case (REMOVE):
                removerUser();
                break;
            case (QUIT):
                break;
            default:
                throw new IllegalArgumentException("Did not expect: " + selection);
        }
    }

    private void removerUser() {
    }

    private void editUser() {
    }

    private void addUser() {
//        String hold1;
//        String hold2;
//        boolean hold3;
//        textUI.display("Enter a User Name");
//        hold1 = text
    }


    private static final String filename = "dentistinfo.sav";
    private static final String userFilename = "users.sav";

    private void saveUser() throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(userFilename))) {
            out.writeObject(this.usersList);
        }
    }

    private void loadUser() throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(userFilename))) {
            try {
                this.usersList = (UserList) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<Integer, String> generateMenuOptions() {
        Map<Integer, String> options = new HashMap<>();
        options.put(EDIT_USERS, "Edit Users");
        options.put(EDIT_PROVIDERS, "Edit Providers");
        options.put(EDIT_PATIENTS, "Edit Patients");
        options.put(VIEW_APPOINTMENTS, "View Appointments");
        options.put(EXIT, "Exit");
        return options;
    }
}

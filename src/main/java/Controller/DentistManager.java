package Controller;

import Model.*;
import View.TextUI;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DentistManager {

    private TextUI textUI;
    private AppointmentList appointment;
    private UserList usersList;
    private PatientList patientList;
    private ProviderList providerList;
    private static UserImpl holder;
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
//        this.loadPatient();
//        this.loadProvider();
    }

    public void run() throws IOException {
        boolean exitTime = false;

        checkEmpty();

        while (!exitTime) {
            exitTime = login();
            if (!exitTime) {
                textUI.display("Please enter a valid login!\n");
            }
        }
        while (exitTime) {
            if (holder.isAdmin()) {
                int selection = textUI.showMenu(generateMenuOptions());
                switch (selection) {
                    case EDIT_USERS:
                        editUsers();
                        break;
                    case EDIT_PROVIDERS:
                        editProviders();
                        break;
                    case EDIT_PATIENTS:
                        //richie will do
                        break;
                    case VIEW_APPOINTMENTS:
                        //dalton should try
                        break;
                    case EXIT:
                        this.saveUser();
                        this.savePatient();
                        this.saveProvider();
                        exitTime = false;
                    default:

                }
            } else {
                //fill with normal user menu also richie
            }
        }

    }

    private void editProviders() throws IOException {
        final int ADD = 1, EDIT = 2, REMOVE = 3, QUIT = 4;

        Map<Integer, String> userMenu = new HashMap<>();
        userMenu.put(ADD, "Add Provider");
        userMenu.put(EDIT, "Edit Provider");
        userMenu.put(REMOVE, "Remove Provider");
        userMenu.put(QUIT, "Go Back");

        int selection = this.textUI.showMenu(userMenu);
        switch (selection) {
            case (ADD):
                addProvider();
                break;
            case (EDIT):
                editProvider();
                break;
            case (REMOVE):
                removeProvider();
                break;
            case (QUIT):
                break;
            default:
                throw new IllegalArgumentException("Did not expect: " + selection);
        }
    }

    private void editProvider() {
    }

    private void addProvider() throws IOException {
        String fName;
        String lName;
        String title;
        int id;
        textUI.display("Enter their First Name");
        fName = textUI.readStringFromUser();
        textUI.display("Enter their Last Name");
        lName = textUI.readStringFromUser();
        textUI.display("Enter their Title");
        title = textUI.readStringFromUser();
        textUI.display("Enter their ID");
        id = readIdforProvider();
        Provider provider = new ProviderImpl(fName, lName, id, title);
        providerList.add(provider);
    }

    private int readIdforProvider() throws IOException {
        int holdin;
        holdin = textUI.readIntFromUser();
        for (int i = 0; i < providerList.size(); i++) {
            if (holdin == providerList.get(i).getId()) {
                textUI.display("That username is taken try a different one");
                holdin = textUI.readIntFromUser();
            }
        }
        return holdin;
    }

    private void removeProvider() {
    }

    private void checkEmpty() throws IOException {
        if (usersList.isEmpty()) {
            User user = UserFactory.getInstance("Administrator", "1234Password", true);
            this.usersList.add(user);
            this.saveUser();
            if (login()) {
                String holding;
                textUI.display("Hello admin, choose a password!");
                holding = textUI.readStringFromUser();
                usersList.get(0).setPassword(holding);
                this.saveUser();
            }
        }
    }

    private boolean login() throws IOException {

        String uname;
        String pword;

        textUI.display("Enter a Username");
        uname = textUI.readStringFromUser().trim();
        textUI.display("Enter a Password");
        pword = textUI.readStringFromUser().trim();

        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getUsername().equals(uname) && usersList.get(i).getPassword().equals(pword)) {
                holder = (UserImpl) usersList.get(i);
                return true;
            }
        }
        return false;
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
                removeUser();
                break;
            case (QUIT):
                break;
            default:
                throw new IllegalArgumentException("Did not expect: " + selection);
        }
    }

    private void removeUser() throws IOException {
        String hold;
        textUI.display("Enter the users name you'd like to delete:");
        hold = readForUsername();
        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getUsername().equals(hold)) {
                usersList.remove(i);
            }
        }

    }

    private String readForUsername() throws IOException {
        String holdin;

        holdin = textUI.readStringFromUser().trim();
        for (int i = 0; i < usersList.size(); i++) {
            if (holdin.equals(usersList.get(i).getUsername())) {
                return holdin;
            }
        }
        textUI.display("There is no-one by that name, try again");
        return readForUsername();
    }

    private void editUser() throws IOException {
        String hold;
        textUI.display("Enter the users name you'd like to change:");
        hold = readForUsername();
        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getUsername().equals(hold)) {
                usersList.remove(i);
                addUser();
            }
        }

    }

    private void addUser() throws IOException {
        String hold1;
        String hold2;
        boolean hold3;
        textUI.display("Enter a User Name");
        hold1 = readUserName();
        textUI.display("Enter a Password");
        hold2 = textUI.readStringFromUser();
        textUI.display("Is the user an admin? (0 = true : 1 = false)");
        hold3 = textUI.readBooleanFromUser();
        User user = new UserImpl(hold1, hold2, hold3);
        usersList.add(user);
    }

    private String readUserName() throws IOException {
        String holdin;
        holdin = textUI.readStringFromUser().trim();
        for (int i = 0; i < usersList.size(); i++) {
            if (holdin.equals(usersList.get(i).getUsername())) {
                textUI.display("That username is taken try a different one");
                holdin = textUI.readStringFromUser();
            }
        }
        return holdin;
    }


    private static final String apfilename = "appointmentinfo.sav";
    private static final String userFilename = "users.sav";
    private static final String profilename = "providers.sav";
    private static final String patfilename = "patients.sav";

    private void savePatient() throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(patfilename))) {
            out.writeObject(this.patientList);
        }
    }

    private void loadPatient() throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(patfilename))) {
            try {
                this.patientList = (PatientList) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveProvider() throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(profilename))) {
            out.writeObject(this.providerList);
        }
    }

    private void loadProvider() throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(profilename))) {
            try {
                this.providerList = (ProviderList) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

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

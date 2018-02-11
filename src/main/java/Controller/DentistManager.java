package Controller;

import Model.*;
import View.TextUI;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
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
//        this.loadAppointment();
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
                        appointmentView();
                        break;
                    case EXIT:
                        this.saveUser();
                        this.savePatient();
                        this.saveProvider();
                        this.saveAppointment();
                        exitTime = false;
                    default:

                }
            } else {
                //fill with normal user menu also richie
            }
        }

    }

    private void appointmentView() throws IOException {
        final int ADD = 1, EDIT = 2, REMOVE = 3, SEARCH = 4, QUIT = 5;

        Map<Integer, String> appMenu = new HashMap<>();
        appMenu.put(ADD, "Add Appointment");
        appMenu.put(EDIT, "Edit Appointment");
        appMenu.put(REMOVE, "Remove Appointment");
        appMenu.put(SEARCH, "Search Appointments");
        appMenu.put(QUIT, "Go Back");

        int selection = this.textUI.showMenu(appMenu);
        switch (selection) {
            case (ADD):
                addAppointment();
                break;
            case (EDIT):
                editAppointment();
                break;
            case (REMOVE):
                removeAppointment();
                break;
            case (SEARCH):
                searchAppointment();
                break;
            default:
                textUI.display("Please enter a valid menu option. " + selection + " is not a valid option.");
        }
    }

    private void searchAppointment() throws IOException {
        final int TIME = 1, PROVIDER = 2, PATIENT = 3, PROCEDURE = 4, QUIT = 5;

        Map<Integer, String> searchMenu = new HashMap<>();
        searchMenu.put(TIME, "By Time");
        searchMenu.put(PROVIDER, "By Provider");
        searchMenu.put(PATIENT, "By Patient");
        searchMenu.put(PROCEDURE, "By Procedure");
        searchMenu.put(QUIT, "Go Back");

        int selection = this.textUI.showMenu(searchMenu);
        switch (selection) {
            case (TIME):
                appTimeSearch();
                break;
            case (PROVIDER):
                appProviderSearch();
                break;
            case (PATIENT):
//                appPatientSearch();
                break;
            case (PROCEDURE):
//                appProcedureSearch();
                break;
            case (QUIT):
                break;
            default:
                this.textUI.display(selection + " is not valid selection.");
        }
    }

    private void appProviderSearch() throws IOException {
        this.textUI.display("What is the ID number of the Provider you with to see?");
        int lookUp = this.textUI.readIntFromUser();

        for (int i = 0; i < appointment.size(); i++) {
            for (int j = 0; j < appointment.get(i).getProcedures().size(); j++) {
                if (appointment.get(i).getProcedures().get(j).getProvider().getId() == lookUp) {
                    this.textUI.display(appointment.get(i).toString());
                }
            }
        }

    }

    private void appTimeSearch() throws IOException {
        Calendar max = makeMaxTime();
        Calendar min = makeMinTime();

        for (int i = 0; i < appointment.size(); i++) {
            if (appointment.get(i).getTime().getTimeInMillis() < max.getTimeInMillis() && appointment.get(i).getTime().getTimeInMillis() > min.getTimeInMillis()) {
                this.textUI.display(appointment.get(i).toString());
            }
        }

    }

    private Calendar makeMinTime() throws IOException {
        Calendar min = makeMinTime();
        this.textUI.display("What is the earliest year you wish to see? (IE 00)");
        int minYear = this.textUI.readIntFromUser();
        this.textUI.display("What is the earliest month in this year you wish to see?(IE 00)");
        int minMonth = this.textUI.readIntFromUser();
        this.textUI.display("What is the earliest day of the month you wish to see? (IE 00)");
        int minDay = this.textUI.readIntFromUser();
        this.textUI.display("What is the earliest hour in this day you wish to see? (IE 00)");
        int minHour = this.textUI.readIntFromUser();
        this.textUI.display("What is the earliest minute in this hour you with to see?");
        int minMin = this.textUI.readIntFromUser();

        min.set(minYear, minMonth, minDay, minHour, minMin);

        return min;
    }

    private Calendar makeMaxTime() throws IOException {
        Calendar max = Calendar.getInstance();

        this.textUI.display("What is the latest year you wish to see? (IE 00)");
        int maxYear = this.textUI.readIntFromUser();
        this.textUI.display("What is the latest month in this year you wish to see?(IE 00)");
        int maxMonth = this.textUI.readIntFromUser();
        this.textUI.display("What is the latest day of the month you wish to see? (IE 00)");
        int maxDay = this.textUI.readIntFromUser();
        this.textUI.display("What is the latest hour in this day you wish to see? (IE 00)");
        int maxHour = this.textUI.readIntFromUser();
        this.textUI.display("What is the latest minute in this hour you with to see?");
        int maxMin = this.textUI.readIntFromUser();

        max.set(maxYear, maxMonth, maxDay, maxHour, maxMin);

        return max;
    }

    private void removeAppointment() {
        //remove the specified appointment
    }

    private void editAppointment() {
        //edit a specified appointment
    }

    private void addAppointment() {
        //add a new appointment
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

    private void editProvider() throws IOException {
        int hold;
        textUI.display("Enter the providers id you'd like to change:");
        hold = readIdforProvider();
        for (int i = 0; i < usersList.size(); i++) {
            if (providerList.get(i).getId() == hold) {
                providerList.remove(i);
                addProvider();
            }
        }
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

    private int readIdforPatient() throws IOException {
        int holdin;
        holdin = textUI.readIntFromUser();
        for (int i = 0; i < patientList.size(); i++) {
            if (holdin == patientList.get(i).getId()) {
                textUI.display("That username is taken try a different one");
                holdin = textUI.readIntFromUser();
            }
        }
        return holdin;
    }

    private void removeProvider() throws IOException {
        int hold;
        textUI.display("Enter the providers id you'd like to delete:");
        hold = readIdforProvider();
        for (int i = 0; i < usersList.size(); i++) {
            if (providerList.get(i).getId() == hold) {
                providerList.remove(i);
            }
        }

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

    private void saveAppointment() throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(apfilename))) {
            out.writeObject(this.appointment);
        }
    }

    private void loadAppointment() throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(apfilename))) {
            try {
                this.appointment = (AppointmentList) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

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

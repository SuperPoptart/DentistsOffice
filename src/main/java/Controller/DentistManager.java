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
    private static final int CHANGE_PASSWORD = 1;
    private static final int EDIT_USERS = 1;
    private static final int EDIT_PROVIDERS = 2;
    private static final int EDIT_PATIENTS = 3;
    private static final int VIEW_APPOINTMENTS = 4;
    private static final int EXIT = 5;

    public DentistManager() throws IOException {
        appointment = new AppointmentList();
        usersList = new UserList();
        providerList = new ProviderList();
        patientList = new PatientList();
        textUI = new TextUI();
        this.loadUser();
        this.loadPatient();
        this.loadProvider();
//        this.loadAppointment();
    }

    public void run() throws IOException {
        boolean exitTime = false;

        displayPatients();
        displayProviders();
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
                        editPatients();
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
                int selection = textUI.showMenu(generateNonAdminOptions());
                switch (selection) {
                    case CHANGE_PASSWORD:
                        changePassword();
                        break;
                    case EDIT_PROVIDERS:
                        editProviders();
                        break;
                    case EDIT_PATIENTS:
                        editPatients();
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
            }
        }

    }

    private void changePassword() throws IOException {
        String holding;
        textUI.display("Please enter your current password");
        holding = textUI.readStringFromUser();
        boolean runnin = true;
        while (runnin) {
            if (holding.equals(holder.getPassword())) {
                for (int i = 0; i < usersList.size(); i++) {
                    if (holding.equals(usersList.get(i).getPassword())) {
                        textUI.display("Enter your new password");
                        holding = textUI.readStringFromUser();
                        usersList.get(i).setPassword(holding);
                        runnin = false;
                    }
                }
            }else{
                textUI.display("Please enter your CURRENT password");
                holding = textUI.readStringFromUser();
            }
        }
    }

    private void displayProviders() {
        for (int i = 0; i < providerList.size(); i++) {
            textUI.display(providerList.get(i).toString());
        }
    }

    private void displayPatients() {
        for (int i = 0; i < patientList.size(); i++) {
            textUI.display(patientList.get(i).toString());
        }
    }

    private void editPatients() throws IOException {
        final int ADD = 1, EDIT = 2, REMOVE = 3, QUIT = 4;

        Map<Integer, String> patientMenu = new HashMap<>();
        patientMenu.put(ADD, "Add Patient");
        patientMenu.put(EDIT, "Edit Patient");
        patientMenu.put(REMOVE, "Remove Patient");
        patientMenu.put(QUIT, "Go Back");

        int selection = this.textUI.showMenu(patientMenu);
        switch (selection) {
            case (ADD):
                addPatient();
                break;
            case (EDIT):
                editPatient();
                break;
            case (REMOVE):
                removePatient();
                break;
            case (QUIT):
                break;
            default:
                throw new IllegalArgumentException("Did not expect: " + selection);
        }
    }

    private void removePatient() throws IOException {
        int hold;
        textUI.display("Enter the patients id you'd like to delete:");
        hold = readIdforPatient();
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getId() == hold) {
                patientList.remove(i);
            }
        }
    }

    private void editPatient() throws IOException {
        int hold;
        textUI.display("Enter the patients id you'd like to change:");
        hold = readIdforPatient();
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getId() == hold) {
                patientList.remove(i);
                addPatient();
            }
        }
    }

    private void addPatient() throws IOException {
        String fName;
        String lName;
        long pNum;
        int id;
        String cName;
        long gId;
        String mId;
        String pCard;
        textUI.display("Enter their First Name");
        fName = textUI.readStringFromUser();
        textUI.display("Enter their Last Name");
        lName = textUI.readStringFromUser();
        textUI.display("Enter their ID");
        id = readIdforPatient();
        textUI.display("Enter their Phone Number");
        pNum = textUI.readLongFromUser();
        textUI.display("Please enter a payment card");
        pCard = textUI.readCardNumber();
        textUI.display("Please enter an insurance company name");
        cName = textUI.readStringFromUser();
        textUI.display("Please enter a group ID (a number)");
        gId = textUI.readLongNormFromUser();
        textUI.display("Please enter a member ID for your insurance");
        mId = textUI.readStringFromUser();
        Insurance insurance = InsuranceFactory.getInstance(cName, gId, mId);
        Patient patient = PatientFactory.getInstance(fName, lName, id, pNum, (InsuranceImpl) insurance, pCard);
        patientList.add(patient);
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
//                editAppointment();
                break;
            case (REMOVE):
//                removeAppointment();
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
                appPatientSearch();
                break;
            case (PROCEDURE):
                appProcedureSearch();
                break;
            case (QUIT):
                break;
            default:
                this.textUI.display(selection + " is not valid selection.");
        }
    }

    private void appProcedureSearch() throws IOException {
        this.textUI.display("What is the Procedure code you wish to see?");
        String lookUp = this.textUI.readStringFromUser();
        for (int i = 0; i < appointment.size(); i++) {
            for (int j = 0; i < appointment.get(i).getProcedures().size(); j++) {
                if (appointment.get(i).getProcedures().get(j).getCode().equalsIgnoreCase(lookUp)) {
                    this.textUI.display(appointment.get(i).toString());
                }
            }
        }
    }

    private void appPatientSearch() throws IOException {
        this.textUI.display("What is the ID number of the Patient you with to see?");
        int lookUp = this.textUI.readIntFromUser();

        for (int i = 0; i < appointment.size(); i++) {
            for (int j = 0; j < appointment.get(i).getProcedures().size(); j++) {
                if (appointment.get(i).getProcedures().get(j).getPatient().getId() == lookUp) {
                    this.textUI.display(appointment.get(i).toString());
                }
            }
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
        Calendar min = Calendar.getInstance();
        this.textUI.display("What is the earliest year you wish to see? (IE 1000)");
        int minYear = this.textUI.readYearFromUser();
        this.textUI.display("What is the earliest month in this year you wish to see?(IE 1-12)");
        int minMonth = this.textUI.readMonthFromUser();
        this.textUI.display("What is the earliest day of the month you wish to see? (IE 1-31)");
        int minDay = this.textUI.readDayFromUser();
        this.textUI.display("What is the earliest hour in this day you wish to see? (IE 1-24)");
        int minHour = this.textUI.readHourFromUser();
        this.textUI.display("What is the earliest minute in this hour you with to see? (IE 1-59)");
        int minMin = this.textUI.readMinuteFromUser();

        min.set(minYear, minMonth, minDay, minHour, minMin);

        return min;
    }

    private Calendar makeMaxTime() throws IOException {
        Calendar max = Calendar.getInstance();
        this.textUI.display("What is the latest year you wish to see? (IE 1000)");
        int maxYear = this.textUI.readYearFromUser();
        this.textUI.display("What is the latest month in this year you wish to see?(IE 1-12)");
        int maxMonth = this.textUI.readMonthFromUser();
        this.textUI.display("What is the latest day of the month you wish to see? (IE 1-31)");
        int maxDay = this.textUI.readDayFromUser();
        this.textUI.display("What is the latest hour in this day you wish to see? (IE 1-24)");
        int maxHour = this.textUI.readHourFromUser();
        this.textUI.display("What is the latest minute in this hour you with to see? (IE 1-59)");
        int maxMin = this.textUI.readMinuteFromUser();

        max.set(maxYear, maxMonth, maxDay, maxHour, maxMin);

        return max;
    }

    private void removeAppointment() {

    }

    private void editAppointment() {
        //edit a specified appointment
    }

    private void addAppointment() throws IOException {
        Appointment tmp = new Appointment();
        ProcedureList appProcedure = new ProcedureList();

        Calendar appTime = makeAppTime();
        appProcedure = makeAppProcedures();

        tmp.setProcedures(appProcedure);
        tmp.setTime(appTime);

        appointment.add(tmp);

    }

    private ProcedureList makeAppProcedures() throws IOException {
        ProcedureList tmp = new ProcedureList();
        boolean isDone = false;

        while (!isDone) {
            final int ADD = 1, REMOVE = 2, QUIT = 3;
            int selection = this.textUI.readIntFromUser();
            switch (selection) {
                case (ADD):
                    tmp.add(addProcedure());
                    break;
                case (REMOVE):
                    tmp = removeProcedure(tmp);
                    break;
                case (QUIT):
                    isDone = true;
                    break;
                default:
                    textUI.display(selection + " is not a valid input please retry.");
            }
        }
        return tmp;
    }

    private ProcedureList removeProcedure(ProcedureList tmp) throws IOException {

        boolean isRemovable = false;
        while (!isRemovable) {
            this.textUI.display("What is the code of the Procedure you wish to remove?");
            String remove = this.textUI.readStringFromUser();
            for (int i = 0; i < tmp.size(); i++) {
                if (remove.equalsIgnoreCase(tmp.get(i).getCode())) {
                    tmp.remove(i);
                    isRemovable = true;
                }
            }

        }
        return tmp;
    }

    private Procedure addProcedure() throws IOException {
//        patient, provider, code, description, amount
        Patient appPatient = addProcedurePatient();
        Provider appProvider = addProcedureProvider();
        String code = addProcedureCode();
        String description = addProcedureDescription();
        double amount = addProcedureAmount();

        Procedure appProcedure = new ProcedureImpl(appPatient, appProvider, code, description, amount);

        return appProcedure;

    }

    // concerned on the implementation of this class.
    private Patient addProcedurePatient() throws IOException {
        boolean isPatient = false;
        int lookUp = 0;
        while (!isPatient) {
            this.textUI.display("What is the Patient ID you wish to associate with this Procedure?");
            lookUp = this.textUI.readIntFromUser();
            for (int i = 0; i < patientList.size(); i++) {
                if (lookUp == patientList.get(i).getId()) {
                    return patientList.get(i);
                }
            }
            this.textUI.display("The Patient ID was not found please try again.");
        }
        return patientList.get(0);
    }

    private Provider addProcedureProvider() throws IOException {
        boolean isProvider = false;
        int lookUp = 0;
        while (!isProvider) {
            this.textUI.display("What is the Provider ID associated with this procedure?");
            lookUp = this.textUI.readIntFromUser();
            for (int i = 0; i < providerList.size(); i++) {
                if (lookUp == providerList.get(i).getId()) {
                    return providerList.get(i);
                }
            }
            this.textUI.display("The provider ID was not found please try again.");
        }
        return providerList.get(0);
    }

    private String addProcedureCode() throws IOException {
        boolean isValid = false;
        String lookUp = "";
        while (!isValid) {
            this.textUI.display("What is the code for this Procedure?");
            lookUp = this.textUI.readStringFromUser();
            isValid = verifyCode(lookUp);
        }
        return lookUp;
    }

    private boolean verifyCode(String lookUp) {
        int codeSize = 6;
        String[] codeCheck = new String[codeSize];
        //how would I do this one?
        //returns true for good, and false for not.
        return true;
    }

    private String addProcedureDescription() throws IOException {
        boolean isValid = false;
        String description = "";
        while (!isValid) {
            if (description.equalsIgnoreCase("") || description.isEmpty()) {
                this.textUI.display("What is the description of this procedure?");
                description = this.textUI.readStringFromUser();
            }
            isValid = true;
        }
        return description;
    }

    private double addProcedureAmount() throws IOException {
        boolean isValid = false;
        double amt = 0;
        while (!isValid) {
            if (amt == 0 || amt < 0) {
                this.textUI.display("What is the cost of this procedure?");
                amt = this.textUI.getDoubleFromUser();
            }
        }
        return amt;
    }


    private Calendar makeAppTime() throws IOException {
        Calendar tmp = Calendar.getInstance();

        this.textUI.display("What is the year for this appointment? (IE 1000)");
        int appYear = textUI.readYearFromUser();
        this.textUI.display("What is the month for this appointment??(IE 1-12)");
        int appMonth = textUI.readMonthFromUser();
        this.textUI.display("What is the day for this appointment?? (IE 1-31)");
        int appDay = textUI.readDayFromUser();
        this.textUI.display("What is the hour for this appointment?? (IE 1-23)");
        int appHour = textUI.readHourFromUser();
        this.textUI.display("What is the minute for this appointment?? (IE 0-59)");
        int appMin = textUI.readMinuteFromUser();

        tmp.set(appYear, appMonth, appDay, appHour, appMin);

        return tmp;
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
        for (int i = 0; i < providerList.size(); i++) {
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
        Provider provider = ProviderFactory.getInstance(fName, lName, id, title);
        providerList.add(provider);
    }

    private int readIdforProvider() throws IOException {

        int holdin;
        holdin = textUI.readIntFromUser();
        for (int i = 0; i < providerList.size(); i++) {
            if (holdin == providerList.get(i).getId()) {
                textUI.display("That id is taken try a different one");
                holdin = textUI.readIntFromUser();
            }
        }
        return holdin;
    }

    private int readIdforPatient() throws IOException {
        if (patientList.isEmpty()) {
            int holders;
            holders = textUI.readIntFromUser();
            return holders;
        } else {
            int holdin;
            holdin = textUI.readIntFromUser();
            for (int i = 0; i < patientList.size(); i++) {
                if (holdin == patientList.get(i).getId()) {
                    textUI.display("That id is taken try a different one");
                    holdin = textUI.readIntFromUser();
                }
            }
            return holdin;
        }
    }

    private void removeProvider() throws IOException {
        int hold;
        textUI.display("Enter the providers id you'd like to delete:");
        hold = readIdforProvider();
        for (int i = 0; i < providerList.size(); i++) {
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

    private Map<Integer, String> generateNonAdminOptions() {
        Map<Integer, String> options = new HashMap<>();
        options.put(CHANGE_PASSWORD, "Change Password");
        options.put(EDIT_PROVIDERS, "Edit Providers");
        options.put(EDIT_PATIENTS, "Edit Patients");
        options.put(VIEW_APPOINTMENTS, "View Appointments");
        options.put(EXIT, "Exit");
        return options;
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

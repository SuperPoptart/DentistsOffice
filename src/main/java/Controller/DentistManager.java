package Controller;

import Model.*;
import View.TextUI;

import java.io.*;
import java.util.*;

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
    private static final int REPORTS = 6;
    private static final int EXIT = 5;

    public DentistManager() throws IOException {
        String help = "plz work";
        appointment = new AppointmentList();
        usersList = new UserList();
        providerList = new ProviderList();
        patientList = new PatientList();
        textUI = new TextUI();
        this.loadUser();
        this.loadPatient();
        this.loadProvider();
        this.loadAppointment();
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
                        break;
                    case REPORTS:
                        reportView();
                        break;
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
                        break;
                    case REPORTS:
                        reportView();
                        break;
                    default:

                }
            }
        }

    }

    private void reportView() throws IOException {
        final int PRODUCTION = 1, PATIENT_BALANCE = 2, COLLECTIONS = 3, QUIT = 4;

        Map<Integer, String> reportMenu = new HashMap<>();
        reportMenu.put(PRODUCTION, "Production");
        reportMenu.put(PATIENT_BALANCE, "Patient Balance");
        reportMenu.put(COLLECTIONS, "Collections");
        reportMenu.put(QUIT, "Go Back");

        int selection = this.textUI.showMenu(reportMenu);
        switch (selection) {
            case (PRODUCTION):
                productionView();
                break;
            case (PATIENT_BALANCE):
                patientBalance();
                break;
            case (COLLECTIONS):
//                collections();
                break;
            case (QUIT):
                break;
            default:
                throw new IllegalArgumentException("Did not expect: " + selection);
        }
    }

    private void patientBalance() throws IOException {
        textUI.display("Would you like to sort this? (Y = 0 / N = 1)");
        boolean choice = textUI.readBooleanFromUser();

        if (choice) {
            textUI.display("How do you want to sort? (Name = 0 / Amount = 1)");
            boolean another = textUI.readBooleanFromUser();
            if (another) {
                PatientSortByName e = new PatientSortByName();
                patientList.sort(e);
                for (int k = 0 ; k<patientList.size() ; k++){
                    textUI.display(patientList.get(k).getLastName() + " owes : $" + getTotalPayments(appointment, patientList.get(k)));
                }
            }
        }
    }

    /**
     * returns total payments due
     * @param e the list its comparing to
     * @return the total amount due
     */
    public double getTotalPayments(AppointmentList e, Patient p) {
        double total = 0;
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < e.size(); i++) {
            for (int j = 0; j < e.get(i).getProcedures().size(); j++) {
                if (e.get(i).getProcedures().get(j).getPatient().equals(p)){
                    if(e.get(i).getTime().before(cal)){
                        total += e.get(i).getProcedures().get(j).getAmount();
                    }
                }
            }
        }
        return total;
    }

    private void productionView() throws IOException {
        Calendar min = makeMinTime();
        Calendar max = makeMaxTime();
        textUI.display("Would you like these to be grouped by day(0) or month(1)?");
        boolean choice = textUI.readBooleanFromUser();
        if (choice) {
            for (int i = 0; i < appointment.size(); i++) {
                if (appointment.get(i).getTime().after(min) && appointment.get(i).getTime().before(max)) {
                    int temp = appointment.get(i).getTime().get(Calendar.DATE);
                    textUI.display(temp + " \n" + appointment.get(i).toString());
                }
            }
        } else if (!choice) {
            for (int i = 0; i < appointment.size(); i++) {
                if (appointment.get(i).getTime().after(min) && appointment.get(i).getTime().before(max)) {
                    int temp = appointment.get(i).getTime().get(Calendar.MONTH);
                    textUI.display(temp + " \n" + appointment.get(i).toString());
                }
            }
        }
    }

    private void displayAppointments() {
        for (int i = 0; i < appointment.size(); i++) {
            textUI.display((i + 1) + ". " + appointment.get(i).toString());
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
            } else {
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
        hold = textUI.readIntFromUser();
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
            } else textUI.display("This patient doesn't exist!");
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
        boolean patientCheck = readIdforPatientImp(patient);
        int ids;
        while (!patientCheck) {
            if (readIdforPatientImp(patient)) {
                patientCheck = true;
            } else if (!readIdforPatientImp(patient)) {
                textUI.display("That id is in use, please type another one!");
                ids = textUI.readIntFromUser();
                patient = PatientFactory.getInstance(fName, lName, ids, pNum, (InsuranceImpl) insurance, pCard);
            }
        }
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
        AppointmentList second = new AppointmentList();
        second.clear();
        second = appointment;
        boolean response;

        textUI.display("Would you like to search by time? (0 = y/n = 1)");
        response = textUI.readBooleanFromUser();
        if (response) {
            appTimeSearch(second);
        } else {
        }
        textUI.display("Would you like to search by Provider? (0 = y/n = 1)");
        response = textUI.readBooleanFromUser();
        if (response) {
            appProviderSearch(second);
        } else {
        }
        textUI.display("Would you like to search by Patient? (0 = y/n = 1)");
        response = textUI.readBooleanFromUser();
        if (response) {
            appPatientSearch(second);
        } else {
        }
        textUI.display("Would you like to search by Procedure? (0 = y/n = 1)");
        response = textUI.readBooleanFromUser();
        if (response) {
            appProcedureSearch(second);
        } else {
        }
        for (int i = 0; i < second.size(); i++) {
            textUI.display(second.get(i).toString());
        }

    }

    private void appProcedureSearch(AppointmentList appointment) throws IOException {
        this.textUI.display("What is the Procedure code you wish to see?");
        String lookUp = this.textUI.readStringFromUser();
        for (int i = 0; i < appointment.size(); i++) {
            for (int j = 0; i < appointment.get(i).getProcedures().size(); j++) {
                if (appointment.get(i).getProcedures().get(j).getCode().equalsIgnoreCase(lookUp)) {

                } else {
                    appointment.remove(i);
                }
            }
        }
    }

    private void appPatientSearch(AppointmentList appointment) throws IOException {
        this.textUI.display("What is the ID number of the Patient you with to see?");
        int lookUp = this.textUI.readIntFromUser();

        for (int i = 0; i < appointment.size(); i++) {
            for (int j = 0; j < appointment.get(i).getProcedures().size(); j++) {
                if (appointment.get(i).getProcedures().get(j).getPatient().getId() == lookUp) {

                } else {
                    appointment.remove(i);
                }
            }
        }
    }

    private void appProviderSearch(AppointmentList appointment) throws IOException {
        this.textUI.display("What is the ID number of the Provider you with to see?");
        int lookUp = this.textUI.readIntFromUser();

        for (int i = 0; i < appointment.size(); i++) {
            for (int j = 0; j < appointment.get(i).getProcedures().size(); j++) {
                if (appointment.get(i).getProcedures().get(j).getProvider().getId() == lookUp) {

                } else {
                    appointment.remove(i);
                }
            }
        }

    }

    private void appTimeSearch(AppointmentList appointment) throws IOException {
        Calendar max = makeMaxTime();
        Calendar min = makeMinTime();

        for (int i = 0; i < appointment.size(); i++) {
            if (appointment.get(i).getTime().getTimeInMillis() < max.getTimeInMillis() && appointment.get(i).getTime().getTimeInMillis() > min.getTimeInMillis()) {

            } else {
                appointment.remove(i);
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

    private void removeAppointment() throws IOException {
        int holdin;
        displayAppointments();
        textUI.display("\nEnter the appointment number on the list you'd like to remove!");
        Set<Integer> valid = new TreeSet<Integer>();
        int MAX = appointment.size();
        Collection<Integer> addin = new TreeSet<Integer>();
        for (int i = 0; i < MAX; i++) {
            addin.add(i + 1);
        }
        valid.addAll(addin);
        holdin = textUI.readIntFromUser(valid);
        for (int i = 0; i < appointment.size(); i++) {
            if (i == holdin - 1) {
                appointment.remove(i);
            }
        }
    }

    private void editAppointment() throws IOException {
        int holdin;
        displayAppointments();
        textUI.display("\nEnter the appointment number on the list you'd like to edit!");
        Set<Integer> valid = new TreeSet<Integer>();
        int MAX = appointment.size();
        Collection<Integer> addin = new TreeSet<Integer>();
        for (int i = 0; i < MAX; i++) {
            addin.add(i + 1);
        }
        valid.addAll(addin);
        holdin = textUI.readIntFromUser(valid);
        for (int i = 0; i < appointment.size(); i++) {
            if (i == holdin - 1) {
                appointment.remove(i);
            }
        }
        addAppointment();
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
            Map<Integer, String> options = new HashMap<>();
            options.put(ADD, "Add Procedure");
            options.put(REMOVE, "Remove Procedure");
            options.put(QUIT, "Quit");
            int selection = textUI.showMenu(options);
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
        textUI.display("Enter the ID of the patient for this procedure:");
        int idHoldin;
        boolean runner = true;
        idHoldin = textUI.readIntFromUser();
        while (runner) {
            for (int i = 0; i < patientList.size(); i++) {
                if (patientList.get(i).getId() == idHoldin) {
                    return this.patientList.get(i);
                }
            }
            textUI.display("Sorry this patient doesn't exit, try again!");
            idHoldin = textUI.readIntFromUser();
        }
        Patient dont_be_here = PatientFactory.getInstance();
        return dont_be_here;
    }

    private Provider addProcedureProvider() throws IOException {
        textUI.display("Enter the ID of the provider for this procedure:");
        int idHoldin;
        boolean runner = true;
        idHoldin = textUI.readIntFromUser();
        while (runner) {
            for (int i = 0; i < providerList.size(); i++) {
                if (providerList.get(i).getId() == idHoldin) {
                    return this.providerList.get(i);
                }
            }
            textUI.display("Sorry this provider doesn't exit, try again!");
            idHoldin = textUI.readIntFromUser();
        }
        Provider dont_be_here = ProviderFactory.getInstance();
        return dont_be_here;
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
            } else {
                return amt;
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
        hold = textUI.readIntFromUser();
        for (int i = 0; i < providerList.size(); i++) {
            if (providerList.get(i).getId() == hold) {
                providerList.remove(i);
                addProvider();
            } else textUI.display("This provider doesn't exist!");
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
        id = textUI.readIntFromUser();
        Provider provider = ProviderFactory.getInstance(fName, lName, id, title);
        boolean justKillMe = readIdforProvider(provider);
        int ids;
        while (!justKillMe) {
            if (readIdforProvider(provider)) {
                justKillMe = true;
            } else if (!readIdforProvider(provider)) {
                textUI.display("That id is in use, please type another one!");
                ids = textUI.readIntFromUser();
                provider = ProviderFactory.getInstance(fName, lName, ids, title);
            }
        }
        providerList.add(provider);

    }

    private boolean readIdforProvider(Provider provider) throws IOException {

        for (int i = 0; i < providerList.size(); i++) {
            if (providerList.get(i).equals(provider)) {
                return false;
            }
        }
        return true;

    }

    private boolean readIdforPatientImp(Patient patient) throws IOException {

        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).equals(patient)) {
                return false;
            }
        }
        return true;

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
        hold = textUI.readIntFromUser();
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
        options.put(REPORTS, "Reports");
        return options;
    }
}

/**
 * class that compares patients by name
 */
class PatientSortByName implements Comparator<Patient> {

    @Override
    public int compare(Patient o1, Patient o2) {
        return o1.getLastName().compareTo(o2.getLastName());
    }
}

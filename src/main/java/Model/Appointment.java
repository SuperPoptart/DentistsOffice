package Model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * represents an appointment
 */
public class Appointment {

    private Calendar time;
    private ArrayList<Procedure> procedures;

    /**
     * default constructor
     */
    Appointment() {
        this.procedures = new ArrayList<Procedure>();
    }

    /**
     * overloaded constructor
     *
     * @param cal        calender
     * @param procedures list of procedures
     */
    Appointment(Calendar cal, ArrayList<Procedure> procedures) {
        this.setTime(cal);
        this.setProcedures(procedures);
    }

    /**
     * gets the time
     *
     * @return time
     */
    public Calendar getTime() {
        return time;
    }

    /**
     * sets the time
     *
     * @param time time
     */
    public void setTime(Calendar time) {
        if (time == null) {
            throw new IllegalArgumentException("Please specify a time");
        }
        this.time = time;
    }

    /**
     * returns the list of procedures
     *
     * @return the list of procedures
     */
    public ArrayList<Procedure> getProcedures() {
        return procedures;
    }

    /**
     * sets the list of procedures
     *
     * @param procedures list of procedures
     */
    public void setProcedures(ArrayList<Procedure> procedures) {
        this.procedures = procedures;
    }

    /**
     * adds a procedure to the list
     *
     * @param p procedure
     */
    public void addProcedure(Procedure p) {
        if (p == null) {
            throw new IllegalArgumentException("You must put a procedure here");
        }
        procedures.add(p);
    }

    /**
     * removes a procedure from the list given the code
     *
     * @param code code
     */
    public void removeProcedure(String code) {
        for (int i = 0; i < procedures.size(); i++) {
            if (procedures.get(i).getCode().equals(code)) {
                procedures.remove(i);
            }
        }
    }
}

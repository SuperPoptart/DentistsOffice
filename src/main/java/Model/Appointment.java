package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

/**
 * represents an appointment
 */
public class Appointment implements Serializable, Comparator<Procedure>{

    private Calendar time;
    private ProcedureList procedures;

    /**
     * default constructor
     */
    public Appointment() {
        this.procedures = new ProcedureList();
    }

    /**
     * overloaded constructor
     *
     * @param cal        calender
     * @param procedures list of procedures
     */
    Appointment(Calendar cal, ProcedureList procedures) {
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
    public ProcedureList getProcedures() {
        return procedures;
    }

    /**
     * sets the list of procedures
     *
     * @param procedures list of procedures
     */
    public void setProcedures(ProcedureList procedures) {
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

    @Override
    public String toString() {
        return this.getTime().getTime() + ", " + this.printProcedures();
    }

    private String printProcedures() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i<procedures.size() ; i++){
            sb.append(procedures.get(i).toString());
            if (i < procedures.size() - 1){
                sb.append(" ; ");
            }
        }
        return sb.toString();
    }

    @Override
    public int compare(Procedure o1, Procedure o2) {
        return 0;
    }
}

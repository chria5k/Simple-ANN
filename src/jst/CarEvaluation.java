/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jst;

/**
 *
 * @author Ersaa
 */
public class CarEvaluation {

//    private int id;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
    private double buying;
    private double maint;
    private double doors;
    private double persons;
    private double lug_boot;
    private double safety;
    private double[] acceptability = new double[4];
    private double[] error = new double[4];

    public CarEvaluation() {
    }
    
    public double[] getDataArray(){
        double[] value = new double[]{buying,maint,doors,persons,lug_boot,safety};
        return value;
    }

    public CarEvaluation(String buying, String maint, String doors, String persons, String lug_boot, String safety, String acceptability) {
        this.setBuying(buying);
        this.setDoors(doors);
        this.setLug_boot(lug_boot);
        this.setMaint(maint);
        this.setPersons(persons);
        this.setSafety(safety);
        this.setAcceptability(acceptability);
        for (int i = 0; i < 4; i++) {
            this.error[i] = 0;
        }
    }

    public double getError(int i) {
        return error[i];
    }

    public void setError(double[] error) {
        this.error = error;
    }

    public double getAcceptability(int id) {
        return acceptability[id];
    }

    public void setAcceptability(String acceptability) {
        this.acceptability[0] = 0;
        this.acceptability[1] = 0;
        this.acceptability[2] = 0;
        this.acceptability[3] = 0;
        if ("unacc".equals(acceptability)) {
            this.acceptability[0] = 1;
        } else if ("acc".equals(acceptability)) {
            this.acceptability[1] = 1;
        } else if ("vgood".equals(acceptability)) {
            this.acceptability[2] = 1;
        } else if ("good".equals(acceptability)) {
            this.acceptability[3] = 1;
        }
    }

    public double getSafety() {
        return safety;
    }

    public void setSafety(String safety) {
        double safetyInt = 0;
        if ("low".equals(safety)) {
            safetyInt = 0.1;
        } else if ("med".equals(safety)) {
            safetyInt = 0.5;
        } else if ("high".equals(safety)) {
            safetyInt = 0.9;
        }
        this.safety = safetyInt;
    }

    public double getLug_boot() {
        return lug_boot;
    }

    public void setLug_boot(String lug_boot) {
        double lug_bootInt = 0;
        if ("small".equals(lug_boot)) {
            lug_bootInt = 0.1;
        } else if ("med".equals(lug_boot)) {
            lug_bootInt = 0.5;
        } else if ("big".equals(lug_boot)) {
            lug_bootInt = 0.9;
        }
        this.lug_boot = lug_bootInt;
    }

    public double getPersons() {
        return persons;
    }

    public void setPersons(String persons) {
        double personsInt = 0;
        if ("2".equals(persons)) {
            personsInt = 0.1;
        } else if ("4".equals(persons)) {
            personsInt = 0.5;
        } else if ("more".equals(persons)) {
            personsInt = 0.9;
        }
        this.persons = personsInt;
    }

    public double getDoors() {
        return doors;
    }

    public void setDoors(String Doors) {
        double doorsInt = 0;
        if ("2".equals(Doors)) {
            doorsInt = 0.1;
        } else if ("3".equals(Doors)) {
            doorsInt = 0.3;
        } else if ("4".equals(Doors)) {
            doorsInt = 0.5;
        } else if ("5".equals(Doors)) {
            doorsInt = 0.7;
        } else if ("5more".equals(Doors)) {
            doorsInt = 0.9;
        }
        this.doors = doorsInt;
    }

    public double getMaint() {
        return maint;
    }

    public void setMaint(String Maint) {
        double maintInt = 0;
        if ("vhigh".equals(Maint)) {
            maintInt = 0.1;
        } else if ("high".equals(Maint)) {
            maintInt = 0.36;
        } else if ("med".equals(Maint)) {
            maintInt = 0.63;
        } else if ("low".equals(Maint)) {
            maintInt = 0.9;
        }
        this.maint = maintInt;
    }

    public double getBuying() {
        return buying;
    }

    public void setBuying(String buying) {
        double buyingInt = 0;
        if ("vhigh".equals(buying)) {
            buyingInt = 0.1;
        } else if ("high".equals(buying)) {
            buyingInt = 0.36;
        } else if ("med".equals(buying)) {
            buyingInt = 0.63;
        } else if ("low".equals(buying)) {
            buyingInt = 0.9;
        }
        this.buying = buyingInt;
    }

}

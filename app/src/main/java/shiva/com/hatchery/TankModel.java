package shiva.com.hatchery;

public class TankModel {

    String tank_number;
    String species;
    String group;
    String entry_date;
    String last_sample_date;
    String average_Weight;
    String biomass;
    String source_tank;

    public String getInv_number() {
        return inv_number;
    }

    public void setInv_number(String inv_number) {
        this.inv_number = inv_number;
    }

    String inv_number;

    public TankModel() {
    }

    public TankModel(String tank_number, String species, String group, String entry_date, String last_sample_date, String average_Weight, String biomass, String source_tank, String inv_number) {
        this.tank_number = tank_number;
        this.species = species;
        this.group = group;
        this.entry_date = entry_date;
        this.last_sample_date = last_sample_date;
        this.average_Weight = average_Weight;
        this.biomass = biomass;
        this.source_tank = source_tank;
        this.inv_number = inv_number;
    }

    public String getTank_number() {
        return tank_number;
    }

    public void setTank_number(String tank_number) {
        this.tank_number = tank_number;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getLast_sample_date() {
        return last_sample_date;
    }

    public void setLast_sample_date(String last_sample_date) {
        this.last_sample_date = last_sample_date;
    }

    public String getAverage_Weight() {
        return average_Weight;
    }

    public void setAverage_Weight(String average_Weight) {
        this.average_Weight = average_Weight;
    }

    public String getBiomass() {
        return biomass;
    }

    public void setBiomass(String biomass) {
        this.biomass = biomass;
    }

    public String getSource_tank() {
        return source_tank;
    }

    public void setSource_tank(String source_tank) {
        this.source_tank = source_tank;
    }
}

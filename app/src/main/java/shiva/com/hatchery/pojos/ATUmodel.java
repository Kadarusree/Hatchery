package shiva.com.hatchery.pojos;

public class ATUmodel {

    String date, initial, temperature, atu;

    public ATUmodel() {
    }

    public ATUmodel(String date, String initial, String temperature, String atu) {
        this.date = date;
        this.initial = initial;
        this.temperature = temperature;
        this.atu = atu;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getAtu() {
        return atu;
    }

    public void setAtu(String atu) {
        this.atu = atu;
    }
}

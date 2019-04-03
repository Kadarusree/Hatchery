package shiva.com.hatchery.egg;

/**
 * Created by srikanthk on 4/3/2019.
 */

public class EggDistPojo {
    String tray, volume, number;

    public EggDistPojo(String tray, String volume, String number) {
        this.tray = tray;
        this.volume = volume;
        this.number = number;
    }

    public EggDistPojo() {
    }

    public String getTray() {
        return tray;
    }

    public void setTray(String tray) {
        this.tray = tray;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

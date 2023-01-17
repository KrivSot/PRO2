package models.Objects;

public class Stopky {
    int hour;
    int minutes;
    int seconds;
    int miliseconds;

    public Stopky()
    {
    }

    public Stopky(Stopky s)
    {
        setMiliseconds(s.getMiliseconds());
        setHour(s.getHour());
        setMinutes(s.getMinutes());
        setSeconds(s.getSeconds());
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getMiliseconds() {
        return miliseconds;
    }

    public void setMiliseconds(int miliseconds) {
        this.miliseconds = miliseconds;
    }

    @Override
    public String toString() {
        return hour +
                ":" + minutes +
                ":" + seconds +
                ":" + miliseconds;
    }
}

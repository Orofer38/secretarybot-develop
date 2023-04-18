package io.onhigh.bot.secretary.gateway.tg.model;

import org.glassfish.grizzly.http.util.TimeStamp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Person")
public class Person {
@Id
    private long chatId;
    private String firsName;
    private String lastName;
    private String useName;
    private TimeStamp registered;
    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public TimeStamp getRegistered() {
        return registered;
    }

    public void setRegistered(TimeStamp registered) {
        this.registered = registered;
    }

}

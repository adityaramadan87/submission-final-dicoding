package co.id.ramadanrizky.watchmovie.entity;

public class ListNotifEntity {
    private int idNotif;
    private String title;
    private String message;

    public ListNotifEntity(int idNotif, String title, String message) {
        this.idNotif = idNotif;
        this.title = title;
        this.message = message;
    }

    public ListNotifEntity() {
    }

    public int getIdNotif() {
        return idNotif;
    }

    public void setIdNotif(int idNotif) {
        this.idNotif = idNotif;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

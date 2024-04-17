package africa.semoicolon.model;

import java.time.LocalDateTime;

public enum TaskStatus{
    PENDING(LocalDateTime.now());
    TaskStatus(LocalDateTime date){
        this.date = date;
    }
    LocalDateTime date ;
    public LocalDateTime getDate(){
        return this.date;
    }
}

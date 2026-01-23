package prot.graalvm.tr.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String num;
    private String type;

    @Column(name = "open_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate openOn;

    public Account() {}

    public Account(Long id, String num, String type, LocalDate openOn) {
        this.id = id;
        this.num = num;
        this.type = type;
        this.openOn = openOn;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNum() { return num; }
    public void setNum(String num) { this.num = num; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDate getOpenOn() { return openOn; }
    public void setOpenOn(LocalDate openOn) { this.openOn = openOn; }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", num='" + num + '\'' +
                ", type='" + type + '\'' +
                ", openOn=" + openOn +
                '}';
    }
}

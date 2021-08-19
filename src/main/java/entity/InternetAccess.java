package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "internet")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class InternetAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "megabytes")
    private int megabytes;

    @OneToOne(cascade = CascadeType.ALL)
    private Subscriber subscriber;

    public InternetAccess(int megabytes) {
        this.megabytes = megabytes;
    }
}

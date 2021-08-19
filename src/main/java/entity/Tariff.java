package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "tariff")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "currentTariff")
    private String currentTariff;

    @OneToOne(cascade = CascadeType.ALL)
    private Subscriber subscriber;

    public Tariff(String currentTariff) {
        this.currentTariff = currentTariff;
    }
}

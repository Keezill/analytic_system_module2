package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "calls")
@Getter
@Setter
@NoArgsConstructor
public class Calls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "callLength_min")
    private int callLength;

    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private Subscriber subscriber;

    public Calls(int callLength) {
        this.callLength = callLength;
    }

    @Override
    public String toString() {
        return "Calls{" +
                "id=" + id +
                ", callLength=" + callLength +
                ", subscriber=" + subscriber +
                '}';
    }
}

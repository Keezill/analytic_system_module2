package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "device")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "deviceName")
    private String deviceName;

    @OneToOne(cascade = CascadeType.ALL)
    private Subscriber subscriber;

    public Device(String deviceName) {
        this.deviceName = deviceName;
    }
}

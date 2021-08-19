package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subscriber")
@Getter
@Setter
@NoArgsConstructor
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private int age;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subscriber", fetch = FetchType.EAGER)
    private List<Calls> calls = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subscriber")
    private List<Message> messages = new ArrayList<>();

    @OneToOne(mappedBy = "subscriber", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private InternetAccess megabytes;

    @OneToOne(mappedBy = "subscriber", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Tariff tariff;

    @OneToOne(mappedBy = "subscriber", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Device device;

    public Subscriber(String name, String surname, String gender, int age) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                '}';
    }
}

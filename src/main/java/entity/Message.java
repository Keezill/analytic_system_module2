package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "text")
    private String text;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subscriber_id")
    private Subscriber subscriber;

    public Message(String text) {
        this.text = text;
    }
}

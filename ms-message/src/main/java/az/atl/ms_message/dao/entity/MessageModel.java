package az.atl.ms_message.dao.entity;


import az.atl.ms_message.model.consts.Channel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "t_message")
public class MessageModel {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.TABLE)
    Long id;

    String messageBody;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    UserEntity sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    UserEntity receiver;


    @Enumerated(EnumType.STRING)
    private Channel channel;



    LocalDateTime sendDateTime;
}

package az.atl.ms_message.dao.repo;


import az.atl.ms_message.dao.entity.MessageModel;
import az.atl.ms_message.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageModel,Long> {
    @Query("SELECT m FROM MessageModel m JOIN m.sender s JOIN m.receiver r WHERE s.userName = :senderName AND r.userName = :receiverName")
    List<MessageModel> findBySenderNameAndReceiverName(@Param("senderName") String senderName, @Param("receiverName") String receiverName);

    @Query("SELECT m FROM MessageModel m JOIN m.sender s WHERE s.userName = :senderName")
    List<MessageModel> findBySenderName(@Param("senderName") String senderName);

    @Query("SELECT m FROM MessageModel m JOIN m.receiver r WHERE r.userName = :receiverName")
    List<MessageModel> findByReceiverName(@Param("receiverName") String receiverName);




    List<MessageModel> findBySenderOrReceiver(UserEntity currentUser, UserEntity currentUser1);

    List<MessageModel> findBySenderAndReceiverOrReceiverAndSender(UserEntity currentUser, UserEntity otherUser, UserEntity otherUser1, UserEntity currentUser1);

    @Query("SELECT m FROM MessageModel m JOIN m.sender s JOIN m.receiver r WHERE (s.userName = :senderName AND r.userName = :receiverName) OR (s.userName = :receiverName AND r.userName = :senderName)")
    List<MessageModel> findBySenderNameAndReceiverNameOrReceiverNameAndSenderName(@Param("senderName") String senderName, @Param("receiverName") String receiverName);
}




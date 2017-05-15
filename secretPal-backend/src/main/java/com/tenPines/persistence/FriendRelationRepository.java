package com.tenPines.persistence;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {
    List<FriendRelation> findByGiftGiverAndWasFulfilledTrue(Worker worker);
    List<FriendRelation> findByGiftReceiverAndWasFulfilledTrue(Worker worker);
    List<FriendRelation> findByGiftReceiverAndScheduledDate(Worker receiver, LocalDate scheduledDate);
}

package com.tenPines.persistence;
import com.tenPines.model.FriendRelation;
import com.tenPines.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {

    Worker findBygiftReceiver(Worker unWorker);
}

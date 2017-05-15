package com.tenPines.persistence;

import com.tenPines.model.Worker;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Transactional
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    List<Worker> findByMail(String mail);

    List<Worker> findByWantsToParticipate(Boolean bool);

    Worker findByFullName(String token);

    List<Worker> findByBirthday(LocalDate birthday);

    List<Worker> findAll(Specification<Worker> spec);
}



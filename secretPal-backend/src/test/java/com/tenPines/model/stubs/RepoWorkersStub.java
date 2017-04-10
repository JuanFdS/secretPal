package com.tenPines.model.stubs;

import com.tenPines.model.User;
import com.tenPines.model.Worker;
import com.tenPines.persistence.WorkerRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tenpines on 10/04/17.
 */
public class RepoWorkersStub implements WorkerRepository {

    List<Worker> workers;

    public RepoWorkersStub(){
        workers = new ArrayList<Worker>();
    }

    @Override
    public Worker save(Worker worker) {
        workers.add(worker);
        return worker;
    }

    @Override
    public List<Worker> findByeMail(String email) {
        return workers.stream().filter((w)->w.geteMail().equals(email)).collect(Collectors.toList());
    }

    @Override
    public List<Worker> findBywantsToParticipate(Boolean bool) {
        return null;
    }

    @Override
    public Worker findByfullName(String token) {
        return null;
    }

    @Override
    public Worker findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public List<Worker> findAll() {
        return workers;
    }

    @Override
    public List<Worker> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Worker> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Worker> findAll(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void delete(Worker worker) {

    }

    @Override
    public void delete(Iterable<? extends Worker> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void flush() {

    }

    @Override
    public void deleteInBatch(Iterable<Worker> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Worker getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends Worker> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Worker> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Worker> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public <S extends Worker> List<S> save(Iterable<S> iterable) {
        return null;
    }

    @Override
    public <S extends Worker> S findOne(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Worker> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Worker> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Worker> boolean exists(Example<S> example) {
        return false;
    }
}

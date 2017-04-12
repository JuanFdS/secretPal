package com.tenPines.model.stubs;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.Worker;
import com.tenPines.persistence.FriendRelationRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class RepoRelationsStub implements FriendRelationRepository {
    public List<FriendRelation> friendRelations;

    public RepoRelationsStub(){
        friendRelations = new ArrayList<FriendRelation>();
    }

    @Override
    public FriendRelation findBygiftReceiver(Worker unWorker) {
        return friendRelations.stream().filter(fr -> fr.getGiftReceiver().equals(unWorker)).findFirst().orElse(null);
    }

    @Override
    public <S extends FriendRelation> S save(S s) {
        friendRelations.add(s);
        return s;
    }

    @Override
    public FriendRelation findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public List<FriendRelation> findAll() {
        return null;
    }

    @Override
    public List<FriendRelation> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<FriendRelation> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<FriendRelation> findAll(Iterable<Long> iterable) {
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
    public void delete(FriendRelation friendRelation) {

    }

    @Override
    public void delete(Iterable<? extends FriendRelation> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void flush() {

    }

    @Override
    public void deleteInBatch(Iterable<FriendRelation> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public FriendRelation getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends FriendRelation> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends FriendRelation> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends FriendRelation> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public <S extends FriendRelation> List<S> save(Iterable<S> iterable) {
        return null;
    }

    @Override
    public <S extends FriendRelation> S findOne(Example<S> example) {
        return null;
    }

    @Override
    public <S extends FriendRelation> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends FriendRelation> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends FriendRelation> boolean exists(Example<S> example) {
        return false;
    }
}

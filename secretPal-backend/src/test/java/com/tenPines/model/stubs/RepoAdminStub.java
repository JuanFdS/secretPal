package com.tenPines.model.stubs;

import com.tenPines.model.AdminProfile;
import com.tenPines.model.User;
import com.tenPines.persistence.AdminRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RepoAdminStub implements AdminRepository {

    List<AdminProfile> admins;

    public RepoAdminStub(){
        admins = new ArrayList<AdminProfile>();
    }

    @Override
    public <S extends AdminProfile> S save(S s) {
        admins.add(s);
        return s;
    }

    @Override
    public AdminProfile findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public List<AdminProfile> findAll() {
        return admins;
    }

    @Override
    public List<AdminProfile> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<AdminProfile> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<AdminProfile> findAll(Iterable<Long> iterable) {
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
    public void delete(AdminProfile adminProfile) {

    }

    @Override
    public void delete(Iterable<? extends AdminProfile> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void flush() {

    }

    @Override
    public void deleteInBatch(Iterable<AdminProfile> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public AdminProfile getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends AdminProfile> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends AdminProfile> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends AdminProfile> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public <S extends AdminProfile> List<S> save(Iterable<S> iterable) {
        return null;
    }

    @Override
    public <S extends AdminProfile> S findOne(Example<S> example) {
        return null;
    }

    @Override
    public <S extends AdminProfile> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends AdminProfile> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends AdminProfile> boolean exists(Example<S> example) {
        return false;
    }
}
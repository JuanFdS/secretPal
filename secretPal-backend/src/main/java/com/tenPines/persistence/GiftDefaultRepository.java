package com.tenPines.persistence;

import com.tenPines.model.GiftDefault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Aye on 02/11/16.
 */
@Transactional
public interface GiftDefaultRepository extends JpaRepository<GiftDefault, Long> {
}

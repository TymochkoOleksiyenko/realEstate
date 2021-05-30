package com.realEstate.jpa;

import com.realEstate.entity.HistoryView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryViewJPA extends JpaRepository<HistoryView,Integer> {
}

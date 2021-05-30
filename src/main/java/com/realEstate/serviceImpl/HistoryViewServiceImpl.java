package com.realEstate.serviceImpl;

import com.realEstate.entity.HistoryView;
import com.realEstate.entity.Users;
import com.realEstate.jpa.HistoryViewJPA;
import com.realEstate.service.FlatService;
import com.realEstate.service.HistoryViewService;
import com.realEstate.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.OptionalInt;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class HistoryViewServiceImpl implements HistoryViewService {
    private final HistoryViewJPA historyViewJPA;
    private final UsersService usersService;
    private final FlatService flatService;

    @Override
    public HistoryView add(int flatId) {
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.findByMail(mail).orElse(null);
        if(user!=null){
            if(user.getHistoryViews()!=null){
                long count = user.getHistoryViews().stream().filter(historyView -> historyView.getFlat().getId()==flatId).count();
                if(count>0){
                    for(HistoryView historyView:user.getHistoryViews()){
                        if(historyView.getFlat().getId()==flatId){
                            historyViewJPA.deleteById(historyView.getId());
                        }
                    }
                }
                if(user.getHistoryViews().size()>=10) {
                    OptionalInt lastId = user.getHistoryViews()
                            .stream().flatMapToInt(historyView -> IntStream.of(historyView.getId()))
                            .min();
                    if (lastId.isPresent()) {
                        historyViewJPA.deleteById(lastId.getAsInt());
                    }
                }
            }
            HistoryView historyView = new HistoryView();
            historyView.setFlat(flatService.findById(flatId));
            historyView.setUser(user);
            historyViewJPA.save(historyView);
        }
        return null;
    }
}

package com.realEstate.serviceImpl;

import com.realEstate.entity.*;
import com.realEstate.enums.StatusOFWishList;
import com.realEstate.service.SelectedForVotingService;
import com.realEstate.service.WishListService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShulceServiceImpl {
    private SelectedForVotingService selectedForVotingService;
    private WishListService wishListService;

    public WishList orderByPriority(WishList wishList){
        List<SelectedForVoting> selectedList = wishList.getList();
        System.out.println(selectedList);
        selectedList.sort((o1, o2) -> o1.getId()-o2.getId());
        System.out.println(selectedList);
        if(selectedList!=null && selectedList.size()>0){
            int size = selectedList.size();
            int[][] arrOfPriority = new int[size][size];
            for(int i=0; i< size;i++){
                for(int j=0; j< size;j++){
                    if(i!=j){
                        arrOfPriority[i][j] = countOfBetterThan(selectedList.get(i),selectedList.get(j));
                    }
                }
            }
            for(int i=0; i< size;i++){
                for(int j=0; j< size;j++){
                    if(i!=j){
                        System.out.print(arrOfPriority[i][j] + " ");
                    }
                    if(i==j)  System.out.print("_ ");

                }
                System.out.println();
            }

            int[][] output = new int[size][size];

            for(int i=0; i< size;i++){
                for(int j=0; j< size;j++){
                    if(i!=j){
                        if (arrOfPriority[i][j] > arrOfPriority[j][i]){
                            output[i][j] = arrOfPriority[i][j];
                        }else {
                            output[i][j] = 0;
                        }
                    }
                }
            }

            for(int i=0; i< size;i++){
                for(int j=0; j< size;j++){
                    if(i!=j){
                        for(int k=0; k< size;k++){
                            if(i!=k && j!=k){
                                output[j][k] = Math.max(output[j][k], Math.min( output[j][i], output[i][k]));
                            }
                        }
                    }
                }
            }
            System.out.println(); System.out.println();

            for(int i=0; i< size;i++){
                for(int j=0; j< size;j++){
                    if(i!=j){
                        System.out.print(output[i][j] + " ");
                    }
                    if(i==j)  System.out.print("_ ");

                }
                System.out.println();
            }

//            set order
            for(int i=0; i< size;i++){
                SelectedForVoting selected = selectedList.get(i);
                int order = 0;
                for(int j=0; j< size;j++){
                    if(i!=j){
                        if(output[i][j]>0) {
                            order++;
                        }
                    }
                }
                selected.setOrderByRate(order);
                selectedForVotingService.save(selected);
            }
            wishList.setStatus(StatusOFWishList.RATED);

        }
        return wishListService.save(wishList);
    }

    public int countOfBetterThan(SelectedForVoting flat1, SelectedForVoting flat2){
        int count = 0;
        for(Vote vote:flat1.getVoteList()){
            Users expert = vote.getExpert();
            Vote voteFlat2 = findVoteByExpert(flat2,expert);
            if(voteFlat2!=null){
                if (vote.getVote()<voteFlat2.getVote()){
                    count++;
                }
            }
        }
        return count;
    }

    public Vote findVoteByExpert(SelectedForVoting selected,Users expert){
        for(Vote voteItem:selected.getVoteList()){
            if(voteItem.getExpert().getId()==expert.getId()){
                return voteItem;
            }
        }
        return null;
    }
}

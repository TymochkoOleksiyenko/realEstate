package com.realEstate.controller;

import com.realEstate.entity.District;
import com.realEstate.entity.Flat;
import com.realEstate.entity.Role;
import com.realEstate.entity.Users;
import com.realEstate.service.DistrictService;
import com.realEstate.service.FlatService;
import com.realEstate.service.InfrastructureService;
import com.realEstate.service.UsersService;
import com.realEstate.serviceImpl.ShulceServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class MainController {
    private final FlatService flatService;
    private final UsersService usersService;
    private final DistrictService districtService;
    private final InfrastructureService infrastructureService;
    private final ShulceServiceImpl shulceService;

    @PostMapping
    public String post(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))){
            return "redirect:/admin/experts";
        }
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("EXPERT"))){
            return "redirect:/expert/getListForRate";
        }
        return "redirect:/";
    }

    @GetMapping("/")
    public String getMain(Model model){

        model.addAttribute("listOfFlats",flatService.findFirst6Desc());
        model.addAttribute("listOfExperts",usersService.findByRole(Role.EXPERT));
        System.out.println(flatService.getMaxPrice());
        System.out.println(flatService.getMinPrice());
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.findByMail(mail).orElse(null);
        if(user!=null) {
            shulceService.orderByPriority(user.getWishList());
        }
        return "user/index";
    }

    @GetMapping("/flat-{id}")
    public String getFlat(@PathVariable int id, Model model){
        model.addAttribute("flat",flatService.findById(id));
        return "user/flatDetail";
    }

    @GetMapping("/allFlats")
    public String allFlats(Model model, BigDecimal priceMin, BigDecimal priceMax, Integer yearMin, Integer yearMax, String[] infs,
                           Integer districtsId, Integer countOfRoomsMin, Integer countOfRoomsMax, String search,Integer page){
        Integer[] infsInt;
        if(infs!=null) {
            List<String> list = Arrays.stream(infs).filter(s -> !s.isEmpty()).collect(Collectors.toList());
            infsInt = new Integer[list.size()];
            for(int i=0; i<list.size();i++){
                infsInt[i]=Integer.parseInt(list.get(i));
            }
        }else {
            infsInt = null;
        }
        Set<Flat> flatList =new TreeSet<>(flatService.getFiltered(priceMin,priceMax,yearMin,yearMax,infsInt,districtsId,countOfRoomsMin,countOfRoomsMax,search));

        if(page==null){
            page = 1;
        }
        model.addAttribute("priceMinInit",flatService.getMinPrice());
        model.addAttribute("priceMaxInit",flatService.getMaxPrice());
        model.addAttribute("yearMinInit",flatService.getMinYearOfEndingDevelopment());
        model.addAttribute("yearMaxInit",flatService.getMaxYearOfEndingDevelopment());
        model.addAttribute("roomsMinInit",flatService.getMinCountOfRooms());
        model.addAttribute("roomsMaxInit",flatService.getMaxCountOfRooms());



        model.addAttribute("checkedInfs",(infsInt!=null)?Arrays.asList(infsInt):null);
        model.addAttribute("districtId",districtsId);
        model.addAttribute("priceMin",(priceMin!=null)?priceMin:flatService.getMinPrice());
        model.addAttribute("priceMax",(priceMax!=null)?priceMax:flatService.getMaxPrice());
        model.addAttribute("yearMin",(yearMin!=null)?yearMin:flatService.getMinYearOfEndingDevelopment());
        model.addAttribute("yearMax",(yearMax!=null)?yearMax:flatService.getMaxYearOfEndingDevelopment());
        model.addAttribute("roomsMin",(countOfRoomsMin!=null)?countOfRoomsMin:flatService.getMinCountOfRooms());
        model.addAttribute("roomsMax",(countOfRoomsMax!=null)?countOfRoomsMax:flatService.getMaxCountOfRooms());


        model.addAttribute("listOfDistricts",districtService.findAll());
        model.addAttribute("listOfInfs",infrastructureService.findAll());
        model.addAttribute("page",page);
        model.addAttribute("currentLinkWithoutPage","/allFlats"+getLink(priceMin,priceMax,yearMin,yearMax,infs,districtsId,countOfRoomsMin,countOfRoomsMax,search,null));
        model.addAttribute("listOfFlats",getPage(new ArrayList<>(flatList),page,8));
        model.addAttribute("countOfPages",getCountOfPages(new ArrayList<>(flatList),8));
        return "user/allFlats";
    }

    private String getLink(BigDecimal priceMin, BigDecimal priceMax, Integer yearMin, Integer yearMax, String[] infs,
                           Integer districtsId, Integer countOfRoomsMin, Integer countOfRoomsMax, String search,Integer page) {
        String current = "?";
        if(priceMin!=null){
            current+="&priceMin="+priceMin;
        }
        if(priceMax!=null){
            current+="&priceMax="+priceMax;
        }
        if(yearMin!=null){
            current+="&yearMin="+yearMin;
        }
        if(yearMax!=null){
            current+="&yearMax="+yearMax;
        }
        if(infs!=null){
            for(String i:infs) {
                if(i.isEmpty()) continue;
                current += "&infs="+i;
            }
        }
        if(districtsId!=null){
            current+="&districtsId="+districtsId;
        }
        if(countOfRoomsMin!=null){
            current+="&countOfRoomsMin="+countOfRoomsMin;
        }
        if(countOfRoomsMax!=null){
            current+="&countOfRoomsMax="+countOfRoomsMax;
        }
        if(search!=null){
            current+="&search="+search;
        }
        if(page!=null){
            current+="&page="+page;
        }
        return current;
    }

    public static <T> List<T> getPage(List<T> sourceList, Integer page, int pageSize) {
        if (page==null || pageSize <= 0 || page <= 0 ) {
            page=1;
        }

        int fromIndex = (page - 1) * pageSize;
        if (sourceList == null || sourceList.size() <= fromIndex) {
            return Collections.emptyList();
        }

        // toIndex exclusive
        return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
    }

    public static <T> int getCountOfPages(List<T> source, int pageSize){
        double sz = source.size();
        double ps = pageSize;
        return (int) Math.ceil(sz/ps);
    }
}
